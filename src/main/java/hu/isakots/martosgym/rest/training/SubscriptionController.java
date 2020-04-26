package hu.isakots.martosgym.rest.training;

import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;
import static hu.isakots.martosgym.rest.util.EndpointConstants.TRAINING_ENDPOINT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class SubscriptionController {

    private final TrainingService trainingService;

    public SubscriptionController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping(TRAINING_ENDPOINT + "/{trainingId}" + "/subscribe")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> subscribeTraining(@PathVariable Long trainingId) throws ResourceNotFoundException {
        trainingService.subscribeToTraining(trainingId, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping(TRAINING_ENDPOINT + "/{trainingId}" + "/unsubscribe")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> unsubscribeTraining(@PathVariable Long trainingId) throws ResourceNotFoundException {
        trainingService.subscribeToTraining(trainingId, false);
        return ResponseEntity.ok().build();
    }


}
