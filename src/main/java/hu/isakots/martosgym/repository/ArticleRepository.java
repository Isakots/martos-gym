package hu.isakots.martosgym.repository;

import hu.isakots.martosgym.domain.Article;
import hu.isakots.martosgym.domain.ArticleType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Optional<Article> findByType(ArticleType type);

    Optional<List<Article>> findAllByType(ArticleType type);

    Optional<List<Article>> findAllByCreatedDateIsAfter(LocalDateTime ldt);

    Optional<List<Article>> findAllByTypeIsAndCreatedDateIsAfter(ArticleType type, LocalDateTime ldt);
}
