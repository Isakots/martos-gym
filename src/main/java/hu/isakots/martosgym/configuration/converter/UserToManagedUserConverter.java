package hu.isakots.martosgym.configuration.converter;

import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.GymPeriod;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.admin.model.ManagedUser;
import org.modelmapper.AbstractConverter;

import java.util.Optional;
import java.util.stream.Collectors;

public class UserToManagedUserConverter extends AbstractConverter<User, ManagedUser> {

    @Override
    protected ManagedUser convert(User user) {
        ManagedUser managedUser = new ManagedUser();
        managedUser.setId(user.getId());
        managedUser.setFirstName(user.getFirstName());
        managedUser.setLastName(user.getLastName());
        managedUser.setEmail(user.getEmail());
        managedUser.setAuthorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        Optional<GymPeriod> gymPeriod = user.getTickets()
                .stream()
                .filter(GymPeriod::isActive)
                .findFirst();
        managedUser.setHasTicketForActivePeriod(gymPeriod.isPresent());

        return managedUser;
    }

}
