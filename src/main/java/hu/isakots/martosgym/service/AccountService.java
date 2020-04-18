package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.util.SecurityUtils;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.InvalidPasswordException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import hu.isakots.martosgym.rest.account.model.PasswordChangeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

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

    public User findById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageFormat.format("User not found with id: {0}", id))
                );
    }

    @Transactional
    public User updateUser(AccountModel accountModel) {
        User storedUser = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        modelMapper.map(accountModel, storedUser);
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
            throw new InvalidPasswordException("Hibás jelszó.");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(user);
    }
}
