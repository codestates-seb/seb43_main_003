package server.mainproject.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class Post_TagResponseDto {
    private List<String> tags;
}
