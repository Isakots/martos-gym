package hu.isakots.martosgym.rest.article;

import hu.isakots.martosgym.domain.Article;
import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.repository.ArticleRepository;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class ArticleResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleResource.class);

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleResource(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleModel>> getAllArticlesByType(@RequestParam ArticleType type) {
        LOGGER.debug("Requesting all articles with type: {}", type);

        // minusMonths constant will not be necessary if frontend will have paging or dynamic content loading (on scrolling)
        List<ArticleModel> articles =
                articleRepository.findAllByTypeIsAndCreatedDateIsAfter(type, LocalDateTime.now().minusMonths(12L))
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(article -> modelMapper.map(article, ArticleModel.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleModel> getArticle(@PathVariable Long id) {
        LOGGER.debug("REST request to get Article : {}", id);
        Optional<Article> article = articleRepository.findById(id);
        return ResponseEntity.ok(modelMapper.map(article.orElse(new Article()), ArticleModel.class));
    }

    @PostMapping("/articles")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> createArticle(@RequestBody ArticleModel article) {
        LOGGER.debug("REST request to save Article : {}", article);
        if (article.getId() != null) {
            throw new IllegalArgumentException("The provided resource must not have ID");
        }
        ArticleModel result = modelMapper.map(articleRepository.save(modelMapper.map(article, Article.class)), ArticleModel.class);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/articles")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> updateArticle(@RequestBody ArticleModel article) {
        LOGGER.debug("REST request to update Article : {}", article);
        if (article.getId() == null) {
            throw new IllegalArgumentException("The provided resource must have an id.");
        }
        ArticleModel result = modelMapper.map(articleRepository.save(modelMapper.map(article, Article.class)), ArticleModel.class);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/articles/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        LOGGER.debug("REST request to delete Article : {}", id);
        articleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}