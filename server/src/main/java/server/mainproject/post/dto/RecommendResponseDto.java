package server.mainproject.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.member.dto.AuthorResponseDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecommendResponseDto {
    private Long recommendsId;
    private Long postId;
    private Long memberId;
    private String title;
    private String link;
    private int star;
    private Double starAvg;
    private int recommend;
    private AuthorResponseDto author;
//    private List<Post_TagResponseDto> postTags;
    private List<String> postTags;
    private List<CommentDto.ResponseComment> comments;

}

