package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static hu.isakots.martosgym.service.util.Constants.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LinkBuilderServiceTest {

    private static final String MOCK_CONTEXT_URL = "http://martoskondi:8090/Martos-Gym/";
    @Mock
    private MartosGymProperties martosGymProperties;

    @InjectMocks
    private LinkBuilderService linkBuilderService;

    @Test
    public void createHomePageUrl() {
        when(martosGymProperties.getContextUrl()).thenReturn(MOCK_CONTEXT_URL);
        String url = linkBuilderService.createHomePageUrl();
        assertTrue(url.endsWith(HOME_PAGE_PATH));
    }

    @Test
    public void createProfilePageUrl() {
        when(martosGymProperties.getContextUrl()).thenReturn(MOCK_CONTEXT_URL);
        String url = linkBuilderService.createProfilePageUrl();
        assertTrue(url.endsWith(PROFILE_PAGE_PATH));
    }

    @Test
    public void createTrainingsPageUrl() {
        when(martosGymProperties.getContextUrl()).thenReturn(MOCK_CONTEXT_URL);
        String url = linkBuilderService.createTrainingsPageUrl();
        assertTrue(url.endsWith(TRAININGS_PAGE_PATH));
    }
}