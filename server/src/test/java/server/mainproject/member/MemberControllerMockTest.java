package server.mainproject.member;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import server.mainproject.member.dto.MemberDto;
import server.mainproject.member.entity.Member;
import server.mainproject.member.mapper.MemberMapper;
import server.mainproject.member.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post(
                "hgd@gmail.com",
                "hgd123!@",
                "홍길동",
                "black");

        Member member = new Member();
        member.setMemberId(1L);

        given(mapper.memberPostDtoToMember(
                Mockito.any(MemberDto.Post.class)))
                .willReturn(new Member());

        // given()은 Mock 객체 특정 값을 리턴하는 동작 지정
        // Mockito의 when()과 동일 기능
        given(memberService.createMember(
                Mockito.any(Member.class)))
                .willReturn(member); // .willReturn()은 리턴할 stub 데이터

        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        // then
//        MvcResult result =
//                actions.andExpect(status().isCreated())
//                        .andExpect(jsonPath("$.data.email").value(post.getEmail()))
//                        .andExpect(jsonPath("$.data.password").value(post.getPassword()))
//                        .andExpect(jsonPath("$.data.userName").value(post.getUserName()))
//                        .andReturn();
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/post"))));
    }
}
