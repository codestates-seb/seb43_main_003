package server.mainproject.post.dto;

import lombok.Getter;
import lombok.Setter;
import server.mainproject.member.entity.Member;
import server.mainproject.post.entity.DevPost;

import java.util.List;

@Getter @Setter
public class DevPostPatchDto {
//    private Long postId;
    private Long memberId;
    private String title;
    private String content;
    private int star;
    private String link;
    private List<String> tag;

    public Member getMember () {
        Member member = new Member();
        member.setMemberId(memberId);

        return member;
    }

    public DevPost toEntity() {
        return DevPost.builder()
                .title(title)
                .content(content)
                .star(star)
                .link(link)
                .build();
    }
}
