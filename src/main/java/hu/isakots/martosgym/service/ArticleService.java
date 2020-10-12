package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MartosGymProperties;
import hu.isakots.martosgym.domain.Article;
import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.exception.ArticleTypeAlreadyExistException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ArticleRepository;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class.getName());

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final MartosGymProperties martosGymProperties;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper, MailService mailService,
                          MartosGymProperties martosGymProperties) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
        this.martosGymProperties = martosGymProperties;
    }

    public List<ArticleModel> getAllArticlesByType(ArticleType type) {
        LOGGER.debug("Requesting all articles with type: {}", type);
        return articleRepository.findAllByTypeIsAndCreatedDateIsAfter(type, LocalDateTime.now()
                .minusMonths(martosGymProperties.getShowArticlesThresholdInMonths()))
                .orElse(Collections.emptyList())
                .stream()
                .map(article -> modelMapper.map(article, ArticleModel.class))
                .collect(Collectors.toList());
    }

    public Article getArticle(String id) throws ResourceNotFoundException {
        return articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Article not found")
        );
    }


    public ArticleModel createArticle(ArticleModel article) throws ArticleTypeAlreadyExistException {
        if (article.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have ID");
        }
        if (isArticleUnique(article)) {
            Optional<Article> persistedOptionalArticle = articleRepository.findByType(article.getType());
            if (persistedOptionalArticle.isPresent()) {
                throw new ArticleTypeAlreadyExistException(MessageFormat.format("Article already exist with type: {0}", article.getType().name()));
            }
        }
        Article persistedArticle = articleRepository.save(modelMapper.map(article, Article.class));
        mailService.startEmailNotificationAsyncTaskOnNewArticle();
        return modelMapper.map(persistedArticle, ArticleModel.class);
    }

    private boolean isArticleUnique(ArticleModel article) {
        return ArticleType.RULES.equals(article.getType()) || ArticleType.ABOUT_US.equals(article.getType()) || ArticleType.GYM.equals(article.getType());
    }

    public ArticleModel updateArticle(ArticleModel article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }
        return modelMapper.map(articleRepository.save(modelMapper.map(article, Article.class)), ArticleModel.class);
    }

    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
    }
}
