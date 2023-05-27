package server.mainproject.comment.dto;

import lombok.*;
import server.mainproject.member.dto.AuthorResponseDto;
import server.mainproject.member.entity.Member;
import server.mainproject.post.entity.DevPost;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostComment {
        private Long memberId;
        private Long postId;
        @NotBlank
        private String comment;
        private int star;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);

            return member;
        }

        public DevPost getDevPost() {
            DevPost devPost = new DevPost();
            devPost.setPostId(postId);

            return devPost;
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchComment {
        private Long memberId;
        private Long postId;
        private Long commentId;
        @NotBlank
        private String comment;
        private int star;

        public Member getMember () {
            Member member = new Member();
            member.setMemberId(memberId);

            return member;
        }

        public DevPost getDevPost() {
            DevPost devPost = new DevPost();
            devPost.setPostId(postId);

            return devPost;
        }
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseComment {
        private String status;
        private Long postId;
        private Long commentId;
        private String comment;
        private int star;
        private AuthorResponseDto author;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
