package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.util.SecurityUtils;
import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.GymPeriod;
import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.InvalidPasswordException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import hu.isakots.martosgym.rest.account.model.PasswordChangeDTO;
import hu.isakots.martosgym.service.model.SubscriptionType;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_MEMBER;
import static hu.isakots.martosgym.service.util.ApplicationUtil.mapSubscriptions;
import static hu.isakots.martosgym.service.util.Constants.ALL;
import static hu.isakots.martosgym.service.util.Constants.MEMBERS_ONLY;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getAuthenticatedUserWithData() {
        return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public User findById(String id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageFormat.format("User not found with id: {0}", id))
                );
    }

    @Transactional
    public User updateUser(AccountModel accountModel) {
        User storedUser = getAuthenticatedUserWithData();
        modelMapper.map(accountModel, storedUser);
        mapSubscriptions(accountModel.getSubscriptions(), storedUser);
        return userRepository.save(storedUser);
    }

    @Transactional
    public void saveAccount(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void saveUserImage(String fileNameToSave) {
        User authenticatedUser = this.getAuthenticatedUserWithData();
        authenticatedUser.setImagePath(fileNameToSave);
        userRepository.save(authenticatedUser);
    }

    public void changePassword(PasswordChangeDTO passwordChangeDto) throws InvalidPasswordException {
        User user = getAuthenticatedUserWithData();
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);
    }

    public List<String> extractUserEmails(String mailToCode) {
        if (ALL.equals(mailToCode)) {
            return userRepository.findAll()
                    .stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
        } else if (MEMBERS_ONLY.equals(mailToCode)) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> {
                        Authority memberAuthority = new Authority();
                        memberAuthority.setName(ROLE_MEMBER);
                        return user.getAuthorities().contains(memberAuthority);
                    })
                    .map(User::getEmail)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<User> extractUsersWithNewTrainingSubscription() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getSubscriptions().contains(new Subscription(SubscriptionType.ON_NEW_TRAININGS)))
                .collect(Collectors.toList());
    }

    public List<User> extractUsersWithNewArticleSubscription() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getSubscriptions().contains(new Subscription(SubscriptionType.ON_NEW_ARTICLES)))
                .collect(Collectors.toList());
    }

    public List<GymPeriod> getUserGymPeriods() {
        return getAuthenticatedUserWithData().getTickets();
    }

}
