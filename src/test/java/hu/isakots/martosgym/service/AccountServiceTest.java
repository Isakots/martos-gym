package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.ModelMapperConfiguration;
import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.InvalidPasswordException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.UserRepository;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import hu.isakots.martosgym.rest.account.model.PasswordChangeDTO;
import hu.isakots.martosgym.service.model.SubscriptionType;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_MEMBER;
import static hu.isakots.martosgym.service.util.Constants.ALL;
import static hu.isakots.martosgym.service.util.Constants.MEMBERS_ONLY;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Spy
    private ModelMapper modelMapper = new ModelMapperConfiguration().getModelMapper();

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void findAll() {
        accountService.findAll();
        verify(userRepository).findAll();
    }

    @Test
    public void getAuthenticatedUserWithData() {
        buildSecurityContext();
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any())).thenReturn(Optional.of(new User()));
        accountService.getAuthenticatedUserWithData();
        verify(userRepository).findOneWithAuthoritiesByEmailIgnoreCase(any());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void getAuthenticatedUserWithData_whenNoAuthenticatedUser() {
        accountService.getAuthenticatedUserWithData();
        verify(userRepository).findOneWithAuthoritiesByEmailIgnoreCase(any());
    }

    @Test
    public void findById_whenFound() throws ResourceNotFoundException {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        accountService.findById(1L);
        verify(userRepository).findById(any());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findById_whenNotFound() throws ResourceNotFoundException {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        accountService.findById(1L);
        verify(userRepository).findById(any());
    }

    @Test
    public void updateUser_whenSubscriptionNameIsValid() {
        buildSecurityContext();
        AccountModel accountModel = new AccountModel();
        String onNewArticlesSubscriptionName = SubscriptionType.ON_NEW_ARTICLES.name();
        accountModel.setSubscriptions(Sets.newHashSet(Collections.singletonList(onNewArticlesSubscriptionName)));

        User mockAuthenticatedUser = new User();
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockAuthenticatedUser));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        accountService.updateUser(accountModel);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getSubscriptions().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateUser_whenSubscriptionNameIsInvalid() {
        buildSecurityContext();
        AccountModel accountModel = new AccountModel();
        String invalidSubType = "InvalidSubType";
        accountModel.setSubscriptions(Sets.newHashSet(Collections.singletonList(invalidSubType)));

        User mockAuthenticatedUser = new User();
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockAuthenticatedUser));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        accountService.updateUser(accountModel);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getSubscriptions().size());
    }


    @Test
    public void saveAccount() {
        accountService.saveAccount(any());
        verify(userRepository).save(any());
    }

    @Test
    public void saveUserImage() {
        buildSecurityContext();
        String fileName = "fileName";
        User mockAuthenticatedUser = new User();
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockAuthenticatedUser));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        accountService.saveUserImage(fileName);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals(fileName, userArgumentCaptor.getValue().getImagePath());
    }

    @Test
    public void changePassword_whenOldPasswordMatching_setNewPassword() throws InvalidPasswordException {
        buildSecurityContext();
        PasswordChangeDTO dto = new PasswordChangeDTO();
        String newPassword = "newPassword";
        dto.setNewPassword(newPassword);
        String oldPassword = "oldPassword";
        dto.setCurrentPassword(oldPassword);
        User mockAuthenticatedUser = new User();
        String currentPassword = "currentPassword";
        mockAuthenticatedUser.setPassword(currentPassword);
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockAuthenticatedUser));
        when(passwordEncoder.matches(eq(oldPassword), eq(currentPassword))).thenReturn(true);

        accountService.changePassword(dto);

        verify(passwordEncoder).encode(eq(newPassword));
        verify(userRepository).save(eq(mockAuthenticatedUser));
    }

    @Test(expected = InvalidPasswordException.class)
    public void changePassword_whenOldPasswordNotMatching_throwInvalidPasswordException() throws InvalidPasswordException {
        buildSecurityContext();
        PasswordChangeDTO dto = new PasswordChangeDTO();
        String newPassword = "newPassword";
        dto.setNewPassword(newPassword);
        String oldPassword = "oldPassword";
        dto.setCurrentPassword(oldPassword);
        User mockAuthenticatedUser = new User();
        String currentPassword = "currentPassword";
        mockAuthenticatedUser.setPassword(currentPassword);
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockAuthenticatedUser));
        when(passwordEncoder.matches(eq(oldPassword), eq(currentPassword))).thenReturn(false);

        accountService.changePassword(dto);
        verifyNoInteractions(userRepository);
    }

    @Test
    public void extractUserEmails_whenAllIsSelected() {
        List<User> testUserList = createTestUsers(null);
        when(userRepository.findAll()).thenReturn(testUserList);

        List<String> emails = accountService.extractUserEmails(ALL);
        assertEquals(3, emails.size());
    }

    @Test
    public void extractUserEmails_whenMembersOnlyIsSelected() {
        List<User> testUserList = createTestUsers(null);
        when(userRepository.findAll()).thenReturn(testUserList);

        List<String> emails = accountService.extractUserEmails(MEMBERS_ONLY);
        assertEquals(1, emails.size());
    }

    @Test
    public void extractUserEmails_whenUnexpectedCode() {
        String mailToCode = "unknownCode";
        List<String> emails = accountService.extractUserEmails(mailToCode);
        assertEquals(0, emails.size());
    }

    @Test
    public void extractUsersWithNewTrainingSubscription() {
        List<User> testUserList = createTestUsers(new Subscription(SubscriptionType.ON_NEW_TRAININGS));
        when(userRepository.findAll()).thenReturn(testUserList);

        List<User> result = accountService.extractUsersWithNewTrainingSubscription();

        assertEquals(1, result.size());
    }

    @Test
    public void extractUsersWithNewArticleSubscription() {
        List<User> testUserList = createTestUsers(new Subscription(SubscriptionType.ON_NEW_ARTICLES));
        when(userRepository.findAll()).thenReturn(testUserList);

        List<User> result = accountService.extractUsersWithNewArticleSubscription();

        assertEquals(1, result.size());
    }

    private List<User> createTestUsers(Subscription subscription) {
        List<User> testUserList = new ArrayList<>();
        User user1 = new User();
        String email1 = "test@test1.hu";
        if (subscription != null) {
            Set<Subscription> subscriptionSet = new HashSet<>();
            subscriptionSet.add(subscription);
            user1.setSubscriptions(subscriptionSet);
        }
        user1.setEmail(email1);
        Set<Authority> authorities = new HashSet<>();
        Authority memberAuth = new Authority();
        memberAuth.setName(ROLE_MEMBER);
        authorities.add(memberAuth);
        user1.setAuthorities(authorities);
        User user2 = new User();
        String email2 = "test@test2.hu";
        user2.setEmail(email2);
        User user3 = new User();
        String email3 = "test@test3.hu";
        user3.setEmail(email3);

        testUserList.add(user1);
        testUserList.add(user2);
        testUserList.add(user3);
        return testUserList;
    }

    private void buildSecurityContext() {
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "password"));
        SecurityContextHolder.setContext(securityContext);
    }
}