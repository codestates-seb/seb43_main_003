package server.mainproject.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mainproject.post.entity.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
