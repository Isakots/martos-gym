package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Authority;
import hu.isakots.martosgym.domain.Subscription;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.service.model.SubscriptionType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static hu.isakots.martosgym.configuration.util.AuthoritiesConstants.ROLE_MEMBER;

@Service
public class EmailAddressExtractorService {
    private static final String ALL = "ALL";
    private static final String MEMBERS_ONLY = "MEMBERS_ONLY";

    private final AccountService accountService;
    private final TrainingService trainingService;

    public EmailAddressExtractorService(AccountService accountService, TrainingService trainingService) {
        this.accountService = accountService;
        this.trainingService = trainingService;
    }

    public List<String> extractUserEmails(String mailToCode) {
        if (ALL.equals(mailToCode)) {
            return accountService.findAll()
                    .stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
        } else if (MEMBERS_ONLY.equals(mailToCode)) {
            return accountService.findAll()
                    .stream()
                    .filter(user -> {
                        Authority memberAuthority = new Authority();
                        memberAuthority.setName(ROLE_MEMBER);
                        return user.getAuthorities().contains(memberAuthority);
                    })
                    .map(User::getEmail)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<String> extractTrainingParticipantEmails(Long trainingId) throws ResourceNotFoundException {
        return trainingService.findTrainingById(trainingId).getParticipants()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    public List<String> extractUserEmailsWithNewTrainingSubscription() {
        return accountService.findAll()
                .stream()
                .filter(user -> {
                    Subscription subscription = new Subscription();
                    subscription.setName(SubscriptionType.ON_NEW_TRAININGS);
                    return user.getSubscriptions().contains(subscription);
                })
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    public List<String> extractUserEmailsWithNewArticleSubscription() {
        return accountService.findAll()
                .stream()
                .filter(user -> {
                    Subscription subscription = new Subscription();
                    subscription.setName(SubscriptionType.ON_NEW_ARTICLES);
                    return user.getSubscriptions().contains(subscription);
                })
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

}
