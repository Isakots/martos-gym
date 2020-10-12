package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.ModelMapperConfiguration;
import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import hu.isakots.martosgym.domain.Article;
import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.exception.ArticleTypeAlreadyExistException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ArticleRepository;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    private static final String MOCK_ID = UUID.randomUUID().toString();
    private static final String MOCK_TITLE = "MOCK_TITLE";
    private static final String MOCK_INTRO = "MOCK_INTRO";
    private static final ArticleType MOCK_TYPE = ArticleType.NUTRITION;
    private static final String MOCK_CONTENT = "<p>MOCK_CONENT</p>";
    private static final LocalDateTime MOCK_CREATED_DATE = LocalDateTime.now();

    @Spy
    private ModelMapper modelMapper = new ModelMapperConfiguration().getModelMapper();

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MailService mailService;

    @Mock
    private MartosGymProperties martosGymProperties;

    @InjectMocks
    private ArticleService articleService;

    @Test
    public void getAllArticlesByType_whenNotFoundInDatabase_thenReturnEmptyList() {
        when(martosGymProperties.getShowArticlesThresholdInMonths()).thenReturn(12L);
        when(articleRepository.findAllByTypeIsAndCreatedDateIsAfter(eq(MOCK_TYPE), any())).thenReturn(Optional.empty());
        List<ArticleModel> result = articleService.getAllArticlesByType(MOCK_TYPE);
        assertEquals(0, result.size());
    }

    @Test
    public void getAllArticlesByType_whenFoundInDatabase_thenReturnArticleModelList() {
        when(martosGymProperties.getShowArticlesThresholdInMonths()).thenReturn(12L);
        Article mockArticle = createMockArticle();
        when(articleRepository.findAllByTypeIsAndCreatedDateIsAfter(eq(MOCK_TYPE), any()))
                .thenReturn(Optional.of(Collections.singletonList(mockArticle)));

        List<ArticleModel> result = articleService.getAllArticlesByType(MOCK_TYPE);
        assertEquals(1, result.size());
        ArticleModel resultModel = result.get(0);
        assertTrue(resultModel.getId().equals(MOCK_ID.toString()));
        assertEquals(MOCK_TITLE, resultModel.getTitle());
        assertEquals(MOCK_TYPE, resultModel.getType());
        assertEquals(MOCK_INTRO, resultModel.getIntroduction());
        assertEquals(MOCK_CONTENT, resultModel.getContent());
        assertEquals(MOCK_CREATED_DATE, resultModel.getCreatedDate());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getArticle_whenNotFoundInDatabase_thenResourceNotFoundExceptionIsThrown() throws ResourceNotFoundException {
        when(articleRepository.findById(eq(MOCK_ID))).thenReturn(Optional.empty());
        articleService.getArticle(MOCK_ID);
    }

    @Test
    public void getArticle() throws ResourceNotFoundException {
        Article mockArticle = createMockArticle();
        when(articleRepository.findById(eq(MOCK_ID))).thenReturn(Optional.of(mockArticle));

        Article result = articleService.getArticle(MOCK_ID);

        assertEquals(MOCK_ID, result.getId());
        assertEquals(MOCK_TITLE, result.getTitle());
        assertEquals(MOCK_TYPE, result.getType());
        assertEquals(MOCK_INTRO, result.getIntroduction());
        assertEquals(MOCK_CONTENT, result.getContent());
        assertEquals(MOCK_CREATED_DATE, result.getCreatedDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createArticle_whenArticleModelDoesNotHaveId_thenIllegalArgumentExceptionIsThrown() throws ArticleTypeAlreadyExistException {
        ArticleModel mockArticleModel = createArticleModel();
        articleService.createArticle(mockArticleModel);
    }

    @Test
    public void createArticle_whenArticleModelDoesNotHaveId_thenArticleIsSaved() throws ArticleTypeAlreadyExistException {
        ArticleModel mockArticleModel = createArticleModel();
        mockArticleModel.setId(null);
        when(articleRepository.save(any())).thenReturn(createMockArticle());
        articleService.createArticle(mockArticleModel);
        verify(articleRepository).save(any());
        verify(mailService).startEmailNotificationAsyncTaskOnNewArticle();
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateArticle_whenArticleModelDoesNotHaveId_thenIllegalArgumentExceptionIsThrown() {
        ArticleModel mockArticleModel = createArticleModel();
        mockArticleModel.setId(null);
        articleService.updateArticle(mockArticleModel);
        verify(articleRepository).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateArticle_whenArticleModelHasId_thenSaveArticle() {
        ArticleModel mockArticleModel = createArticleModel();
        mockArticleModel.setId(null);
        articleService.updateArticle(mockArticleModel);
    }

    @Test
    public void deleteArticle() {
        articleService.deleteArticle(MOCK_ID);
        verify(articleRepository).deleteById(eq(MOCK_ID));
    }

    private Article createMockArticle() {
        Article mockArticle = new Article();
        mockArticle.setId(MOCK_ID);
        mockArticle.setTitle(MOCK_TITLE);
        mockArticle.setType(MOCK_TYPE);
        mockArticle.setIntroduction(MOCK_INTRO);
        mockArticle.setContent(MOCK_CONTENT);
        mockArticle.setCreatedDate(MOCK_CREATED_DATE);
        return mockArticle;
    }

    private ArticleModel createArticleModel() {
        return modelMapper.map(createMockArticle(), ArticleModel.class);
    }

}