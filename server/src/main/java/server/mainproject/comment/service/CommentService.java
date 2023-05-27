package server.mainproject.comment.service;

import org.aspectj.weaver.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mainproject.comment.entity.Comment;
import server.mainproject.comment.repository.CommentRepository;
import server.mainproject.exception.BusinessLogicException;
import server.mainproject.exception.ExceptionCode;
import server.mainproject.member.service.MemberService;
import server.mainproject.post.service.DevPostService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Transactional
@Component
@Service
public class CommentService {
    private CommentRepository commentRepository;
    private final DevPostService devPostService;
    private final MemberService memberService;


    public CommentService(CommentRepository commentRepository, DevPostService devPostService, MemberService memberService) {
        this.commentRepository = commentRepository;
        this.devPostService = devPostService;
        this.memberService = memberService;
    }

    public Comment createComment(Comment comment) {  // 생성
//        long memberId = memberService.getLoginMemberId();
        DecimalFormat df = new DecimalFormat("#.##");
//        comment.getMember().setMemberId(memberId);
        Comment savedComment = commentRepository.save(comment);
        List<Comment> comments = commentRepository.findAll();
        devPostService.postCommentReviewAvg(comments,df);
        return savedComment;
    }


    public Comment updateComment(Comment comment) {  // 댓글과 별점 수정
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getComment())
                .ifPresent(content -> findComment.setComment(content));
        Optional.ofNullable(comment.getStar())
                .ifPresent(star -> findComment.setStar(star));


        return commentRepository.save(findComment);
    }

    public List<Comment> findComments() {  // 모든 댓글 조회
        return commentRepository.findAll();
    }

    public void deleteComment(Long commentId) {  // 특정 댓글 삭제
        commentRepository.deleteById(commentId);
    }

    public Comment findVerifiedComment(Long commentId) {  // 해당 댓글의 존재 유무 체크
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        return comment;
    }
}
