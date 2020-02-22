package hu.isakots.martosgym.service;

import hu.isakots.martosgym.domain.Article;
import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.repository.ArticleRepository;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    public List<ArticleModel> getAllArticlesByType(ArticleType type) {
        LOGGER.debug("Requesting all articles with type: {}", type);
        // minusMonths constant will not be necessary if frontend will have paging or dynamic content loading (on scrolling)
        return articleRepository.findAllByTypeIsAndCreatedDateIsAfter(type, LocalDateTime.now().minusMonths(12L))
                .orElse(Collections.emptyList())
                .stream()
                .map(article -> modelMapper.map(article, ArticleModel.class))
                .collect(Collectors.toList());
    }

    public ArticleModel getArticle(Long id) throws ResourceNotFoundException {
        LOGGER.debug("REST request to get Article : {}", id);
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("A keresett cikk nem található.")
        );
        return modelMapper.map(article, ArticleModel.class);
    }

    public ArticleModel createArticle(ArticleModel article) {
        LOGGER.debug("REST request to save Article : {}", article);
        if (article.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have ID");
        }
        return modelMapper.map(articleRepository.save(modelMapper.map(article, Article.class)), ArticleModel.class);
    }

    public ArticleModel updateArticle(ArticleModel article) {
        LOGGER.debug("REST request to update Article : {}", article);
        if (article.getId() == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }
        return modelMapper.map(articleRepository.save(modelMapper.map(article, Article.class)), ArticleModel.class);
    }

    public void deleteArticle(Long id) {
        LOGGER.debug("REST request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
