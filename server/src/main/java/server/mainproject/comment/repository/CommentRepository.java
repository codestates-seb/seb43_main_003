package server.mainproject.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.mainproject.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    Comment findByCommentId(long commentId);
}
