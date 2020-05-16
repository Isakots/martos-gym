package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.ModelMapperConfiguration;
import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.GymPeriod;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.GymPeriodRepository;
import hu.isakots.martosgym.rest.admin.model.ManagedUser;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_MEMBER;
import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_USER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    private static final Long MOCK_ID = 1L;

    @Spy
    private ModelMapper modelMapper = new ModelMapperConfiguration().getModelMapper();

    @Mock
    private GymPeriodRepository gymPeriodRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void getAllGymPeriods() {
        adminService.getAllGymPeriods();
        verify(gymPeriodRepository).findAll();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createGymPeriod_whenActiveExists() {
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.of(new GymPeriod()));
        adminService.createGymPeriod(new GymPeriod());
        verifyNoInteractions(gymPeriodRepository);
    }

    @Test
    public void createGymPeriod_whenActiveNotExists() {
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        adminService.createGymPeriod(new GymPeriod());
        ArgumentCaptor<GymPeriod> periodArgumentCaptor = ArgumentCaptor.forClass(GymPeriod.class);
        verify(gymPeriodRepository).save(periodArgumentCaptor.capture());
        assertTrue(periodArgumentCaptor.getValue().isActive());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void closeActivePeriod_whenActiveNotExists() throws ResourceNotFoundException {
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        adminService.closeActivePeriod();
        verifyNoMoreInteractions(gymPeriodRepository);
    }

    @Test
    public void closeActivePeriod_whenActiveExists() throws ResourceNotFoundException {
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.of(new GymPeriod()));
        adminService.closeActivePeriod();
        ArgumentCaptor<GymPeriod> periodArgumentCaptor = ArgumentCaptor.forClass(GymPeriod.class);
        verify(gymPeriodRepository).save(periodArgumentCaptor.capture());
        assertFalse(periodArgumentCaptor.getValue().isActive());
        verify(gymPeriodRepository).findAll();
    }

    @Test
    public void getAllUser() {
        adminService.getAllGymPeriods();
        verify(gymPeriodRepository).findAll();
    }

    @Test
    public void updateManagedUsers_whenHasTicketAndActivePeriodExists_thenTicketShouldBeAdded() throws ResourceNotFoundException {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(MOCK_ID);
        managedUser.setHasTicketForActivePeriod(true);
        managedUser.setAuthorities(Sets.newHashSet(Collections.singletonList(ROLE_USER)));

        User mockUser = new User();
        when(accountService.findById(eq(MOCK_ID))).thenReturn(mockUser);
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.of(new GymPeriod()));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        adminService.updateManagedUsers(Collections.singletonList(managedUser));

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getTickets().size());
    }

    @Test
    public void updateManagedUsers_whenHasTicketAndActivePeriodNotExists_thenTicketShouldNotBeAdded() throws ResourceNotFoundException {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(MOCK_ID);
        managedUser.setHasTicketForActivePeriod(true);
        managedUser.setAuthorities(Sets.newHashSet(Collections.singletonList(ROLE_USER)));

        User mockUser = new User();
        when(accountService.findById(eq(MOCK_ID))).thenReturn(mockUser);
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        adminService.updateManagedUsers(Collections.singletonList(managedUser));

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(0, userArgumentCaptor.getValue().getTickets().size());
    }

    @Test
    public void updateManagedUsers_whenDoesNotHaveTicketAndActivePeriodNotExists_thenTicketShouldNotBeRemoved() throws ResourceNotFoundException {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(MOCK_ID);
        managedUser.setHasTicketForActivePeriod(false);
        managedUser.setAuthorities(Sets.newHashSet(Collections.singletonList(ROLE_USER)));

        GymPeriod activePeriod = new GymPeriod();
        User mockUser = new User();
        mockUser.setTickets(Collections.singletonList(activePeriod));
        when(accountService.findById(eq(MOCK_ID))).thenReturn(mockUser);
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        adminService.updateManagedUsers(Collections.singletonList(managedUser));

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getTickets().size());
    }


    @Test
    public void updateManagedUsers_whenHasRoleMember_memberRoleShouldBeAdded() throws ResourceNotFoundException {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(MOCK_ID);
        managedUser.setHasTicketForActivePeriod(false);
        managedUser.setAuthorities(Sets.newHashSet(Arrays.asList(ROLE_USER, ROLE_MEMBER)));

        User mockUser = new User();
        when(accountService.findById(eq(MOCK_ID))).thenReturn(mockUser);
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        adminService.updateManagedUsers(Collections.singletonList(managedUser));

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(1, userArgumentCaptor.getValue().getAuthorities().size());
    }

    @Test
    public void updateManagedUsers_whenDoesNotHaveRoleMember_memberRoleShouldBeRemoved() throws ResourceNotFoundException {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(MOCK_ID);
        managedUser.setHasTicketForActivePeriod(false);
        managedUser.setAuthorities(Sets.newHashSet(Arrays.asList(ROLE_USER)));

        User mockUser = new User();
        Authority memberAuth = new Authority();
        memberAuth.setName(ROLE_MEMBER);
        mockUser.setAuthorities(Sets.newHashSet(Collections.singletonList(memberAuth)));
        when(accountService.findById(eq(MOCK_ID))).thenReturn(mockUser);
        when(gymPeriodRepository.findByIsActiveTrue()).thenReturn(Optional.empty());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        adminService.updateManagedUsers(Collections.singletonList(managedUser));

        verify(accountService).saveAccount(userArgumentCaptor.capture());
        assertEquals(0, userArgumentCaptor.getValue().getAuthorities().size());
    }



}