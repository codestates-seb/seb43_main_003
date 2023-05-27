package server.mainproject.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.mainproject.audit.Auditable;
import server.mainproject.member.entity.Member;
import server.mainproject.post.entity.DevPost;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment;
    private int star;  // 별점

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private DevPost devPost;

    public Long getMemberId() {
        return member.getMemberId();
    }

    public String getUserName() {
        return member.getUserName();
    }

    public Long getPostId() {
        return devPost.getPostId();
    }
}
