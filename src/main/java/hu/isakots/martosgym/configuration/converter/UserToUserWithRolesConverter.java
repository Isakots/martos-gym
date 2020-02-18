package hu.isakots.martosgym.configuration.converter;

import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.auth.model.UserWithRoles;
import org.modelmapper.AbstractConverter;

public class UserToUserWithRolesConverter extends AbstractConverter<User, UserWithRoles> {

    @Override
    protected UserWithRoles convert(User user) {
        UserWithRoles userWithRoles = new UserWithRoles();
        userWithRoles.setUsername(user.getEmail());
        userWithRoles.setAuthorities(user.getAuthorities());
        return userWithRoles;
    }

}
