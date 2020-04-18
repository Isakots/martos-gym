package hu.isakots.martosgym.rest.admin;


import hu.isakots.martosgym.rest.admin.model.ManagedUser;
import hu.isakots.martosgym.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
@PreAuthorize("hasAuthority('ROLE_MEMBER')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/managed-users")
    public List<ManagedUser> getManagedUsers() {
        return adminService.getAllUser();
    }

    @PutMapping("/managed-users")
    public List<ManagedUser> updateUsers(@RequestBody List<ManagedUser> managedUserList) {
        return adminService.updateManagedUsers(managedUserList);
    }
}
