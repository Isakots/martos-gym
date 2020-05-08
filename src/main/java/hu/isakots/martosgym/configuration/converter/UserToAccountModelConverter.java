package hu.isakots.martosgym.configuration.converter;

import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import org.modelmapper.AbstractConverter;

import java.util.stream.Collectors;

public class UserToAccountModelConverter extends AbstractConverter<User, AccountModel> {

    @Override
    protected AccountModel convert(User user) {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(user.getId());
        accountModel.setFirstName(user.getFirstName());
        accountModel.setLastName(user.getLastName());
        accountModel.setEmail(user.getEmail());
        accountModel.setStudentStatus(user.isStudentStatus());
        accountModel.setInstitution(user.getInstitution());
        accountModel.setFaculty(user.getFaculty());
        accountModel.setCollegian(user.isCollegian());
        accountModel.setRoomNumber(user.getRoomNumber());
        accountModel.setSubscriptions(user.getSubscriptions()
                .stream()
                .map(subscription -> subscription.getName().name())
                .collect(Collectors.toSet())
        );

        return accountModel;
    }

}
