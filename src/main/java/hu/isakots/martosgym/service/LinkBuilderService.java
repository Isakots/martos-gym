package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import static hu.isakots.martosgym.service.util.Constants.*;

@Service
public class LinkBuilderService {

    private final MartosGymProperties martosGymProperties;

    public LinkBuilderService(MartosGymProperties martosGymProperties) {
        this.martosGymProperties = martosGymProperties;
    }

    public String createHomePageUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(createMartosGymUrl())
                .path(HOME_PAGE_PATH)
                .build()
                .toUriString();
    }

    public String createProfilePageUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(createMartosGymUrl())
                .path(PROFILE_PAGE_PATH)
                .build()
                .toUriString();
    }

    public String createTrainingsPageUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(createMartosGymUrl())
                .path(TRAININGS_PAGE_PATH)
                .build()
                .toUriString();
    }

    private String createMartosGymUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(martosGymProperties.getContextUrl())
                .build()
                .toUriString();
    }

}
