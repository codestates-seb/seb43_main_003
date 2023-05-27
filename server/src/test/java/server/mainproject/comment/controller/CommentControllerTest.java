package server.mainproject.comment.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.comment.entity.Comment;
import server.mainproject.comment.mapper.CommentMapper;
import server.mainproject.comment.repository.CommentRepository;
import server.mainproject.comment.service.CommentService;
import server.mainproject.member.dto.AuthorResponseDto;

import java.net.URI;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private CommentRepository repository;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentMapper mapper;

    @Test
    void postCommentTest() throws Exception {
        // given
        CommentDto.PostComment post = new CommentDto.PostComment(
                1L,
                1L,
                "댓글작성",
                4);

        Comment comment = new Comment();
        comment.setCommentId(1L);

        given(mapper.postToComment(
                Mockito.any(CommentDto.PostComment.class)))
                .willReturn(new Comment());

        given(commentService.createComment(
                Mockito.any(Comment.class)))
                .willReturn(comment);

        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/comments")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/comments"))));
    }

    @Test
    void patchCommentTest() throws Exception {
        Comment comment = new Comment();
        Comment saveComment = repository.save(comment);
        Long commentId = saveComment.getCommentId();

        CommentDto.PatchComment patch = new CommentDto.PatchComment(
                1L,
                1L,
                1L,
                "수정된 내용",
                5);

        AuthorResponseDto auth = AuthorResponseDto
                .builder()
                .name("홍길동")
                .profileImgNum(2)
                .star(5)
                .build();

        CommentDto.ResponseComment response = new CommentDto.ResponseComment(
                "success",
                1L,
                1L,
                "수정된 내용",
                5,
                auth,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(mapper.patchToComment(
                Mockito.any(CommentDto.PatchComment.class)))
                .willReturn(new Comment());

        given(commentService.updateComment(
                Mockito.any(Comment.class)))
                .willReturn(new Comment());

        given(mapper.commentToResponse(
                Mockito.any(Comment.class)))
                .willReturn(response);

        String patchContent = gson.toJson(patch);

        URI patchUri = UriComponentsBuilder.newInstance().path("/comments/{comment-id}").buildAndExpand(commentId).toUri();

        ResultActions actions = mockMvc.perform(patch(patchUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchContent));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postId").value(patch.getPostId()))
                .andExpect(jsonPath("$.data.commentId").value(patch.getCommentId()))
                .andExpect(jsonPath("$.data.comment").value(patch.getComment()))
                .andExpect(jsonPath("$.data.star").value(patch.getStar()));
    }

    @Test
    void getCommentTest() throws Exception {
        AuthorResponseDto auth = AuthorResponseDto
                .builder()
                .name("홍길동")
                .profileImgNum(2)
                .star(5)
                .build();

        CommentDto.ResponseComment response1 = new CommentDto.ResponseComment(
                "success",
                1L,
                1L,
                "내용1",
                5,
                auth,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        CommentDto.ResponseComment response2 = new CommentDto.ResponseComment(
                "success",
                1L,
                2L,
                "내용2",
                3,
                auth,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(mapper.commentToResponse(
                Mockito.any(Comment.class)))
                .willReturn(response1);

        given(mapper.commentToResponse(
                Mockito.any(Comment.class)))
                .willReturn(response2);


        URI getUri = UriComponentsBuilder.newInstance().path("/comments").build().toUri();

        ResultActions actions = mockMvc.perform(get(getUri)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult result = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andReturn();

//        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
    }

    @Test
    void deleteComment() throws Exception {
        Comment comment = new Comment();

        Comment savaComment = repository.save(comment);

        long todoId = savaComment.getCommentId();

        URI uri = UriComponentsBuilder.newInstance().path("/comments/{comment-id}").buildAndExpand(todoId).toUri();

        mockMvc.perform(delete(uri))
                .andExpect(status().isNoContent());
    }
}