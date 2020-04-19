package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Training;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.exception.TrainingValidationException;
import hu.isakots.martosgym.repository.TrainingRepository;
import hu.isakots.martosgym.rest.training.model.TrainingModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class.getName());

    private final TrainingRepository repository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public TrainingService(TrainingRepository repository, AccountService accountService, ModelMapper modelMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    public List<TrainingModel> findAll() {
        return repository.findAll().stream()
                .map(training -> {
                    TrainingModel model = modelMapper.map(training, TrainingModel.class);
                    model.setSubscribed(accountService.getAuthenticatedUserWithData().getTrainings().contains(training));
                    model.setActualParticipants(training.getParticipants().size());
                    return model;
                })
                .collect(Collectors.toList());
    }

    public List<TrainingModel> findAllUserTrainings() {
        return accountService.getAuthenticatedUserWithData().getTrainings().stream()
                .map(training -> modelMapper.map(training, TrainingModel.class))
                .collect(Collectors.toList());
    }

    public TrainingModel createTraining(TrainingModel trainingModel) throws TrainingValidationException {
        LOGGER.debug("REST request to create Training : {}", trainingModel);
        if (trainingModel.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have an id.");
        }
        validateTrainingModel(trainingModel);
        Training training = modelMapper.map(trainingModel, Training.class);
        training.getParticipants().add(accountService.getAuthenticatedUserWithData());

        // TODO send email notification to all subscribed user
        return modelMapper.map(repository.save(training), TrainingModel.class);
    }

    public TrainingModel modifyTraining(TrainingModel trainingModel) throws TrainingValidationException, ResourceNotFoundException {
        LOGGER.debug("REST request to update Training : {}", trainingModel);
        if (trainingModel.getId() == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }
        validateTrainingModel(trainingModel);
        Training training = repository.findById(trainingModel.getId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format("Training not found with id: {0}", trainingModel.getId()))
        );
        validateParticipantCount(training, trainingModel);
        training = modelMapper.map(trainingModel, Training.class);

        // TODO send email notification to all subscribed user if date is changed
        return modelMapper.map(repository.save(training), TrainingModel.class);
    }

    private void validateTrainingModel(TrainingModel trainingModel) throws TrainingValidationException {
        if (trainingModel.getStartDate().isBefore(LocalDateTime.now())) {
            throw new TrainingValidationException("Creating training into the past is not allowed.");
        }
        if (trainingModel.getStartDate().isAfter(trainingModel.getEndDate())) {
            throw new TrainingValidationException("End date of training must be after start date.");
        }
    }

    private void validateParticipantCount(Training training, TrainingModel trainingModel) throws TrainingValidationException {
        if (trainingModel.getMaxParticipants() < training.getParticipants().size()) {
            throw new TrainingValidationException("Count of actual participants is more than max participants.");
        }
    }


    public void deleteTraining(Long trainingId) throws ResourceNotFoundException {
        LOGGER.debug("REST request to delete Training : {}", trainingId);
        Training training = repository.findById(trainingId).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format("Training not found with id: {0}", trainingId))
        );
        training.getParticipants().clear();
        repository.save(training);
        repository.deleteById(trainingId);
    }

    public void subscribeToTraining(Long trainingId, boolean subscription) throws ResourceNotFoundException {
        User authenticatedUser = accountService.getAuthenticatedUserWithData();
        Training training = repository.findById(trainingId).orElseThrow(
                () -> new ResourceNotFoundException(MessageFormat.format("Training not found with id: {0}", trainingId))
        );
        if (subscription) {
            authenticatedUser.getTrainings().add(training);
        } else {
            authenticatedUser.getTrainings().remove(training);
        }
        accountService.saveAccount(authenticatedUser);
    }

}
