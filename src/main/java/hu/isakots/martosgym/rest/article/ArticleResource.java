package hu.isakots.martosgym.rest.article;

import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.exception.ArticleTypeAlreadyExistException;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import hu.isakots.martosgym.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class ArticleResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleResource.class.getName());

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleResource(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleModel>> getAllArticlesByType(@RequestParam ArticleType type) {
        return ResponseEntity.ok(articleService.getAllArticlesByType(type));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleModel> getArticle(@PathVariable Long id) throws ResourceNotFoundException {
        LOGGER.debug("REST request to get Article : {}", id);
        return ResponseEntity.ok(modelMapper.map(articleService.getArticle(id), ArticleModel.class));
    }

    @PostMapping("/articles")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> createArticle(@RequestBody ArticleModel article) throws ArticleTypeAlreadyExistException {
        LOGGER.debug("REST request to save Article : {}", article);
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/articles")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> updateArticle(@RequestBody ArticleModel article) {
        LOGGER.debug("REST request to update Article : {}", article);
        return ResponseEntity.ok().body(articleService.updateArticle(article));
    }

    @DeleteMapping("/articles/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        LOGGER.debug("REST request to delete Article : {}", id);
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}