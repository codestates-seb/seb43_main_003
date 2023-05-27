package server.mainproject.comment.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.comment.entity.Comment;
import server.mainproject.member.dto.AuthorResponseDto;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment postToComment(CommentDto.PostComment post);
    Comment patchToComment(CommentDto.PatchComment patch);

    default CommentDto.ResponseComment commentToResponse(Comment comment) {
        CommentDto.ResponseComment response = new CommentDto.ResponseComment(
                "success",
                comment.getPostId(),
                comment.getCommentId(),
                comment.getComment(),
                comment.getStar(),
                authorDtoResponse(comment),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
        return response;
    }

    default List<CommentDto.ResponseComment> ListCommentResponse(List<Comment> comments) {
        List<CommentDto.ResponseComment> responseComments = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDto.ResponseComment responseComment = CommentDto.ResponseComment
                    .builder()
                    .status("success")
                    .postId(comment.getPostId())
                    .commentId(comment.getCommentId())
                    .comment(comment.getComment())
                    .star(comment.getStar())
                    .author(authorDtoResponse(comment))
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();

            responseComments.add(responseComment);
        }

        return responseComments;
    }

    default AuthorResponseDto authorDtoResponse (Comment comment) {

        AuthorResponseDto auth = AuthorResponseDto
                .builder()
                .name(comment.getMember().getUserName())
                .profileImgNum(comment.getMember().getProfileImgNum())
                .star(comment.getStar())
                .build();


        return auth;
    }
}
