package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.util.SecurityUtils;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AccountService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User getUserInformation() {
        return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public User updateUser(AccountModel accountModel) {
        User storedUser = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        modelMapper.map(accountModel, storedUser);
        return userRepository.save(storedUser);
    }
}
