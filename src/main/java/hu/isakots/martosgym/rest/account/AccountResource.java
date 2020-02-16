package hu.isakots.martosgym.rest.account;

import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import hu.isakots.martosgym.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = API_CONTEXT)
@PreAuthorize("hasRole('ROLE_USER')")
public class AccountResource {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountResource(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<AccountModel> getUserInformation() {
        User user = accountService.getUserInformation();
        return new ResponseEntity<>(modelMapper.map(user, AccountModel.class), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<AccountModel> getUserInformation(@RequestBody AccountModel accountModel) {
        User user = accountService.updateUser(accountModel);
        return new ResponseEntity<>(modelMapper.map(user, AccountModel.class), HttpStatus.OK);
    }

}
