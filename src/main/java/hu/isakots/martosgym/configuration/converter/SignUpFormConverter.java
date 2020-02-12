package hu.isakots.martosgym.configuration.converter;

import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.dto.SignUpForm;
import org.modelmapper.AbstractConverter;

public class SignUpFormConverter extends AbstractConverter<SignUpForm, User> {

    @Override
    protected User convert(SignUpForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setFirstName(form.getFirstname());
        user.setLastName(form.getLastname());
        return user;
    }
}
