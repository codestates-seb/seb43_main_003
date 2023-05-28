package server.mainproject.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.mainproject.comment.entity.Comment;
import server.mainproject.comment.repository.CommentRepository;
import server.mainproject.exception.BusinessLogicException;
import server.mainproject.member.service.MemberService;
import server.mainproject.post.service.DevPostService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private DevPostService devPostService;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCommentTest() {
        // given
        Comment comment = new Comment();
        comment.setComment("serviceTesting");
        comment.setStar(5);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        //  verify로 확인할 것이므로 아무 동작이나 지정
        doNothing().when(devPostService).postCommentReviewAvg(anyList(), any(DecimalFormat.class));

        // when
        Comment savedComment = commentService.createComment(comment);

        // then
        assertNotNull(savedComment);

        verify(commentRepository).save(any(Comment.class));
        verify(commentRepository).findAll();
        verify(devPostService).postCommentReviewAvg(anyList(), any(DecimalFormat.class));

        // given
//        Comment comment = new Comment("serviceTest", 4);
//        given(commentRepository.save(Mockito.any(Comment.class))).willReturn(comment);

        // when / then
//        assertThrows(BusinessLogicException.class, () -> memberService.createMember(comment));
    }

    @Test
    void updateCommentTest() {
    }

    @Test
    void findCommentsTest() {
    }

    @Test
    void deleteCommentTest() {
    }
}