package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.GymPeriod;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.GymPeriodRepository;
import hu.isakots.martosgym.rest.admin.model.ManagedUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_MEMBER;

@Service
public class AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class.getName());

    private final GymPeriodRepository gymPeriodRepository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AdminService(GymPeriodRepository gymPeriodRepository, AccountService accountService,
                        ModelMapper modelMapper) {
        this.gymPeriodRepository = gymPeriodRepository;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    public List<GymPeriod> getAllGymPeriods() {
        return gymPeriodRepository.findAll();
    }

    public List<GymPeriod> getUserGymPeriods() {
        return accountService.getAuthenticatedUserWithData().getTickets();
    }

    public GymPeriod createGymPeriod(GymPeriod period) {
        gymPeriodRepository.findByIsActiveTrue().ifPresent(
                gymPeriod -> {
                    throw new UnsupportedOperationException("Active period already exists!");
                }
        );
        period.setActive(true);
        return gymPeriodRepository.save(period);
    }

    public List<GymPeriod> closeActivePeriod() throws ResourceNotFoundException {
        GymPeriod gymPeriod = gymPeriodRepository.findByIsActiveTrue()
                .orElseThrow(() -> new ResourceNotFoundException("Active GymPeriod was not found."));
        gymPeriod.setActive(false);
        gymPeriodRepository.save(gymPeriod);
        return getAllGymPeriods();
    }

    public List<ManagedUser> getAllUser() {
        return accountService.findAll().stream()
                .map(user -> modelMapper.map(user, ManagedUser.class))
                .collect(Collectors.toList());
    }

    public List<ManagedUser> updateManagedUsers(List<ManagedUser> managedUserList) {
        LOGGER.debug("Updating the following users, with data: {}", managedUserList);

        managedUserList.forEach(managedUser -> {
            Optional<User> optionalUser;
            try {
                optionalUser = Optional.ofNullable(accountService.findById(managedUser.getId()));
            } catch (ResourceNotFoundException e) {
                optionalUser = Optional.empty();
            }

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                setTicketForUser(managedUser, user);
                setAuthoritiesForUser(managedUser, user);
                accountService.saveAccount(user);
            }
        });

        return getAllUser();
    }

    private void setTicketForUser(ManagedUser managedUser, User user) {
        Optional<GymPeriod> activePeriodOptional = gymPeriodRepository.findByIsActiveTrue();
        if (activePeriodOptional.isPresent()) {
            GymPeriod activePeriod = activePeriodOptional.get();
            if (managedUser.isHasTicketForActivePeriod()) {
                if (!user.getTickets().contains(activePeriod)) {
                    user.getTickets().add(activePeriod);
                }
            } else {
                user.getTickets().remove(activePeriod);
            }
        }
    }

    private void setAuthoritiesForUser(ManagedUser managedUser, User user) {
        Authority authority = new Authority();
        authority.setName(ROLE_MEMBER);

        if (managedUser.getAuthorities().contains(ROLE_MEMBER)) {
            user.getAuthorities().add(authority);
        } else {
            user.getAuthorities().remove(authority);
        }
    }

}
