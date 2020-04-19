package hu.isakots.martosgym.rest.training;

import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.exception.TrainingValidationException;
import hu.isakots.martosgym.rest.training.model.TrainingModel;
import hu.isakots.martosgym.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;
import static hu.isakots.martosgym.rest.util.EndpointConstants.TRAINING_ENDPOINT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class TrainingResource {

    private final TrainingService trainingService;

    public TrainingResource(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping(TRAINING_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<TrainingModel> getAllTrainings() {
        return trainingService.findAll();
    }

    @GetMapping("/user" + TRAINING_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<TrainingModel> getUserTrainings() {
        return trainingService.findAllUserTrainings();
    }

    @PostMapping(TRAINING_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public TrainingModel createTraining(@RequestBody TrainingModel trainingModel) throws TrainingValidationException {
        return trainingService.createTraining(trainingModel);
    }

    @PutMapping(TRAINING_ENDPOINT)
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public TrainingModel modifyTraining(@RequestBody TrainingModel trainingModel) throws TrainingValidationException, ResourceNotFoundException {
        return trainingService.modifyTraining(trainingModel);
    }

    @DeleteMapping(TRAINING_ENDPOINT + "{trainingId}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> unsubscribeTraining(@PathVariable Long trainingId) throws ResourceNotFoundException {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.ok().build();
    }


}
