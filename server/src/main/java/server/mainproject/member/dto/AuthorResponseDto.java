package server.mainproject.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AuthorResponseDto {
    private String name;
    private int star;
    private int profileImgNum;
}
