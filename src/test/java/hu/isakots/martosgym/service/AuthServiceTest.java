package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.ModelMapperConfiguration;
import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.auth.model.SignUpForm;
import hu.isakots.martosgym.rest.auth.model.UserWithRoles;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Spy
    private ModelMapper modelMapper = new ModelMapperConfiguration().getModelMapper();

    @Mock
    private AccountService accountService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private AuthService authService;

    @Test
    public void registerUser() {
        SignUpForm form = new SignUpForm();
        final String email = "test@test.com";
        form.setEmail(email);
        final String password = "password";
        form.setPassword(password);
        final String firstName = "firstName";
        form.setFirstName(firstName);
        final String lastName = "lastName";
        form.setLastName(lastName);
        final boolean studentStatus = true;
        form.setStudentStatus(studentStatus);
        final String institution = "INST";
        form.setInstitution(institution);
        final String faculty = "FACUL";
        form.setFaculty(faculty);
        final boolean isCollegian = false;
        form.setCollegian(isCollegian);
        final int roomNumber = 123;
        form.setRoomNumber(roomNumber);

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(eq(password))).thenReturn(encodedPassword);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        authService.registerUser(form);

        verify(accountService).saveAccount(userArgumentCaptor.capture());

        User capturedArgumentUser = userArgumentCaptor.getValue();
        assertEquals(email, capturedArgumentUser.getEmail());
        assertEquals(encodedPassword, capturedArgumentUser.getPassword());
        assertEquals(firstName, capturedArgumentUser.getFirstName());
        assertEquals(lastName, capturedArgumentUser.getLastName());
        assertEquals(studentStatus, capturedArgumentUser.isStudentStatus());
        assertEquals(institution, capturedArgumentUser.getInstitution());
        assertEquals(faculty, capturedArgumentUser.getFaculty());
        assertEquals(isCollegian, capturedArgumentUser.isCollegian());
        assertEquals(roomNumber, capturedArgumentUser.getRoomNumber());
        assertEquals(1, capturedArgumentUser.getAuthorities().size());

        verify(mailService).sendRegistrationEmail(eq(capturedArgumentUser));
    }

    @Test
    public void getIdentity() {
        User user = new User();
        final String email = "test@test.com";
        user.setEmail(email);
        Authority authority = new Authority();
        authority.setName(ROLE_USER);
        user.setAuthorities(Sets.newHashSet(Collections.singletonList(authority)));
        when(accountService.getAuthenticatedUserWithData()).thenReturn(user);

        UserWithRoles userWithRoles = authService.getIdentity();

        assertEquals(email, userWithRoles.getUsername());
        assertEquals(1, userWithRoles.getAuthorities().size());
        assertTrue(userWithRoles.getAuthorities().contains(ROLE_USER));

    }
}