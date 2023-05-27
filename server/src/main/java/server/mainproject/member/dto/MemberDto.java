package server.mainproject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.post.dto.DevPostDto;
import server.mainproject.post.dto.RecommendResponseDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String userName;
        private String profileImgNum;
    }
    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long memberId;
        private String userName;
        private String password;
        private int profileImgNum;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private String status;
        private String memberId;
        private String email;
        private String userName;
        private int profileImgNum;
        private List<DevPostDto.Response> posts;
        private List<CommentDto.ResponseComment> comments;
        private List<RecommendResponseDto> recommends;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
