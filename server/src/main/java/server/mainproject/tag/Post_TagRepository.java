package server.mainproject.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mainproject.post.dto.DevPostDto;
import server.mainproject.post.entity.DevPost;

import java.util.List;
import java.util.Optional;

public interface Post_TagRepository extends JpaRepository<Post_Tag, Long> {
    List<Post_Tag> findByPost (DevPost post);
}
