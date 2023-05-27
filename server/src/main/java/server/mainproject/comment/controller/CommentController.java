package server.mainproject.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.comment.entity.Comment;
import server.mainproject.comment.mapper.CommentMapper;
import server.mainproject.comment.service.CommentService;
import server.mainproject.response.SingleResponse;
import server.mainproject.utils.URICreator;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Slf4j
public class CommentController {
    private final CommentService service;
    private final CommentMapper mapper;

    public CommentController(CommentService service, CommentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("comments")  // 생성
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.PostComment post) {

        Comment comment = mapper.postToComment(post);

        Comment response = service.createComment(comment);
        URI uri = URICreator.createUri("/", response.getCommentId());

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("comments/{comment-id}")  // 수정
    public ResponseEntity patchComment(@PathVariable("comment-id") @Positive Long commentId,
                                       @Valid @RequestBody CommentDto.PatchComment patch) {
        patch.setCommentId(commentId);

        Comment comment = service.updateComment(mapper.patchToComment(patch));

        return new ResponseEntity<>(new SingleResponse<>(mapper.commentToResponse(comment)), HttpStatus.OK);
    }

    @GetMapping("comments") // 모든 댓글 조회
    public ResponseEntity getDevAnswers() {
        List<Comment> comments = service.findComments();

        return new ResponseEntity<>(new SingleResponse<>(mapper.ListCommentResponse(comments)),  HttpStatus.OK);
    }

    @DeleteMapping("comments/{comment-id}")  // 삭제
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive Long commentId) {
        service.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}