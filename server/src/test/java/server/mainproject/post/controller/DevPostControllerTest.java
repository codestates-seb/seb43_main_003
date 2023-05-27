package server.mainproject.post.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import server.mainproject.member.dto.AuthorResponseDto;
import server.mainproject.member.entity.Member;
import server.mainproject.post.dto.DevPostDto;
import server.mainproject.post.dto.DevPostPatchDto;
import server.mainproject.post.entity.DevPost;
import server.mainproject.post.mapper.DevPostMapper;
import server.mainproject.post.service.DevPostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DevPostControllerTest {
    public AuthorResponseDto authorResponseDto = AuthorResponseDto
            .builder()
            .name("홍길동")
            .profileBgColor("블랙")
            .star(3)
            .build();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Gson gson;

    @MockBean
    DevPostService devPostService;

    @MockBean
    DevPostMapper devPostMapper;

    @Test
    @DisplayName("게시글 등록 테스트")
    void postPostTest() throws Exception {
        // given
        List<String> tag = new ArrayList<>();
        tag.add("java");

        DevPostDto.Post post = new DevPostDto.Post();

        post.setTitle("제목");
        post.setContent("내용");
        post.setStar(1);
        post.setSorta("text");
        post.setTag(tag);
        post.setThumbnailImage("http://image");

        String content = gson.toJson(post);

        DevPost devPost = new DevPost();
        devPost.setPostId(1L);

        given(devPostService.savedPost(Mockito.any(DevPostDto.Post.class)))
                .willReturn(devPost);

        // when

        ResultActions actions =
                mockMvc.perform(
                        post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then

        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/post"))));
    }
    @Test
    @DisplayName("게시글 수정 테스트")
    void patchPostTest () throws Exception {
        // given
//        DevPost devPost = new DevPost();
//        devPost.setPostId(1L);
//        devPost.setTitle("제목");
//        devPost.setContent("내용");
//        devPost.setStar(1);
//        devPost.setSorta("text");
////        devPost.setTag(tag);
//        devPost.setThumbnailImage("http://image");
//        devPost.setSourceMedia("http://mmmm");
//        devPost.setSourceURL("http://urlrul");
//
//        List<String> tag = new ArrayList<>();
//        tag.add("java");

        List<String> patchTag = new ArrayList<>();
        patchTag.add("tag");

        DevPostPatchDto patchDto = new DevPostPatchDto();
        patchDto.setContent("수정내용");
        patchDto.setTitle("제목 수정");
        patchDto.setSorta("video");
        patchDto.setStar(2);
        patchDto.setSourceMedia("media");
        patchDto.setThumbnailImage("image");
        patchDto.setSourceURL("https://velog.io");
        patchDto.setTag(patchTag);

        String patchContent = gson.toJson(patchDto);

        DevPostDto.Response response = new DevPostDto.Response(
                "성공", 1L, "제목 수정", "수정내용",
                "https://velog.io", 2, "image", 2.2,
                1, "media", "video", null, null, null,
                LocalDateTime.now(), LocalDateTime.now()
        );

        given(devPostService.updatePost(Mockito.any(DevPostPatchDto.class), Mockito.anyLong()))
                .willReturn(new DevPost());

        given(devPostMapper.EntityToResponse(Mockito.any(DevPost.class)))
                .willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/posts/{post-id}/edit", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchContent)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(patchDto.getTitle()))
                .andExpect(jsonPath("$.data.content").value(patchDto.getContent()))
                .andExpect(jsonPath("$.data.sourceURL").value(patchDto.getSourceURL()))
                .andExpect(jsonPath("$.data.star").value(patchDto.getStar()))
                .andExpect(jsonPath("$.data.thumbnailImage").value(patchDto.getThumbnailImage()))
                .andExpect(jsonPath("$.data.sourceMedia").value(patchDto.getSourceMedia()))
                .andExpect(jsonPath("$.data.sorta").value(patchDto.getSorta()));
    }
    @Test
    @DisplayName("게시글 전체 조회")
    void getAllPostsTest() throws Exception {
        // given
        List<DevPostDto.Response> posts = new ArrayList<>();

        DevPostDto.Response response = new DevPostDto.Response(
                "성공", 1L, "제목 수정", "수정내용",
                "https://velog.io", 2, "image", 2.2,
                1, "media", "video", null, null, null,
                LocalDateTime.now(), LocalDateTime.now()
        );
        posts.add(response);

        given(devPostService.findAllPost())
                .willReturn(new ArrayList<>());

        given(devPostMapper.ListResponse(Mockito.any()))
                .willReturn(posts);

        // when


        // then
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePostTest() throws Exception {
        // given
        DevPost post = new DevPost();
        post.setTitle("제목");
        post.setContent("내용");
        post.setSorta("소타");
        post.setSourceMedia("미디어");
        post.setSourceURL("주소");
        post.setStarAvg(1.1);
        post.setThumbnailImage("이미지");

        Long postId = 1L;
        Long memberId = 1L;
        post.setPostId(postId);

        Member member = new Member();
        member.setMemberId(memberId);
        post.setMember(member);

        doNothing().when(devPostService).deletePost(Mockito.anyLong(), Mockito.anyLong());

        // when
        ResultActions actions =
                mockMvc.perform(delete("/posts/{post-id}/{member-id}", postId, memberId));

        // then
        actions
                .andExpect(status().isNoContent());
    }

}