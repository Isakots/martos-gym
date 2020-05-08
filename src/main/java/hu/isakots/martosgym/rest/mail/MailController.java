package hu.isakots.martosgym.rest.mail;

import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.rest.mail.model.EmailRequestModel;
import hu.isakots.martosgym.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/users/mail")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> sendEmailForUsers(@RequestBody EmailRequestModel model) {
        mailService.sendCustomEmail(model);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trainings/{trainingId}/participants/mail")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> sendEmailTrainingParticipants(@PathVariable Long trainingId, @RequestBody EmailRequestModel model)
            throws ResourceNotFoundException {
        mailService.sendCustomEmailToTrainingParticipants(trainingId, model);
        return ResponseEntity.ok().build();
    }

}
