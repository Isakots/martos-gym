package hu.isakots.martosgym.rest.gymperiod;

import hu.isakots.martosgym.domain.GymPeriod;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class GymPeriodResource {

    private final AdminService adminService;

    public GymPeriodResource(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/gym-periods")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public List<GymPeriod> getAllGymPeriods() {
        return adminService.getAllGymPeriods();
    }

    @PostMapping("/gym-periods")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public GymPeriod createGymPeriod(@RequestBody GymPeriod period) {
        return adminService.createGymPeriod(period);
    }

    @PatchMapping("/gym-periods")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public List<GymPeriod> closeActivePeriod() throws ResourceNotFoundException {
        return adminService.closeActivePeriod();
    }
}
