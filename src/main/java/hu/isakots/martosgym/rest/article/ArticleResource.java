package hu.isakots.martosgym.rest.article;

import hu.isakots.martosgym.domain.ArticleType;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.rest.article.model.ArticleModel;
import hu.isakots.martosgym.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.isakots.martosgym.rest.util.EndpointConstants.API_CONTEXT;

@RestController
@RequestMapping(value = API_CONTEXT)
public class ArticleResource {
    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleModel>> getAllArticlesByType(@RequestParam ArticleType type) {
        return ResponseEntity.ok(articleService.getAllArticlesByType(type));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleModel> getArticle(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/articles")
    //@PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> createArticle(@RequestBody ArticleModel article) {
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.CREATED);
    }

    @PutMapping("/articles")
    //@PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<ArticleModel> updateArticle(@RequestBody ArticleModel article) {
        return ResponseEntity.ok().body(articleService.updateArticle(article));
    }

    @DeleteMapping("/articles/{id}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}