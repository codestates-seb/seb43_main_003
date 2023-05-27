package server.mainproject.post.mapper;

import org.mapstruct.Mapper;
import server.mainproject.comment.dto.CommentDto;
import server.mainproject.comment.entity.Comment;
import server.mainproject.member.dto.AuthorResponseDto;
import server.mainproject.post.dto.DevPostDto;
import server.mainproject.post.dto.DevPostMainResponse;
import server.mainproject.post.entity.DevPost;
import server.mainproject.tag.Post_Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DevPostMapper {

    default DevPostDto.Response EntityToResponse (DevPost post) {
        List<Post_Tag> postTags = post.getPostTags();

        DevPostDto.Response response = new DevPostDto.Response(
                "success",
                post.getPostId(), post.getTitle(), post.getContent(),
                post.getSourceURL(), post.getStar(), post.getThumbnailImage(),
                post.getStarAvg(),
                post.getRecommend(),
                post.getSourceMedia(),
                post.getSorta(),
                postMemberDtoResponse(post),
                postTagDtoResponse(postTags),
                postCommentResponse(post.getComments()),
                post.getCreatedAt(),post.getModifiedAt()
        );

        return response;
    }
    default List<CommentDto.ResponseComment> postCommentResponse (List<Comment> comments) {
        return comments
                .stream()
                .map(comment -> {
                            CommentDto.ResponseComment response = new CommentDto.ResponseComment();
                            response.setStatus("success");
                            response.setPostId(comment.getPostId());
                            response.setCommentId(comment.getCommentId());
                            response.setComment(comment.getComment());
                            response.setAuthor(
                                    AuthorResponseDto.builder()
                                            .name(comment.getUserName()).star(comment.getStar())
                                            .profileImgNum(comment.getMember().getProfileImgNum())
                                            .build());
                            response.setStar(comment.getStar());
                            response.setCreatedAt(comment.getCreatedAt());
                            response.setModifiedAt(comment.getModifiedAt());
                            return response;
                        }
                ).collect(Collectors.toList());
    }

    //Todo: 수정중!!

    default List<String> postTagDtoResponse (List<Post_Tag> postTags) {
        List<String> tagName = postTags.stream().map(tag -> tag.getTag().getName())
                .collect(Collectors.toList());

        return tagName;
    }

//    default List<Post_TagResponseDto> postTagDtoResponse (Set<Post_Tag> postTags) {
//        List<Post_TagResponseDto> result = new ArrayList<>();
//        List<String> tagName = postTags.stream().map(tag -> tag.getTag().getName())
//                .collect(Collectors.toList());
//
//        result.add(Post_TagResponseDto
//                .builder()
//                .tags(tagName)
//                .build());
//
//        return result;
//
//    }

    default AuthorResponseDto postMemberDtoResponse (DevPost devPost) {

        AuthorResponseDto ar = AuthorResponseDto
                .builder()
                .name(devPost.getMember().getUserName())
                .profileImgNum(devPost.getMember().getProfileImgNum())
                .star(devPost.getStar())
                .build();

        return ar;
    }
    List<DevPostDto.Response> ListResponse (List<DevPost> posts);

    default List<DevPostMainResponse> mainPageResponse(List<DevPost> posts) {
        if ( posts == null ) {
            return null;
        }

        List<DevPostMainResponse> list = new ArrayList<DevPostMainResponse>( posts.size() );
        for ( DevPost devPost : posts ) {
            list.add( devPostToDevPostMainResponse( devPost ) );
        }

        return list;
    }

    default DevPostMainResponse devPostToDevPostMainResponse(DevPost devPost) {
        if ( devPost == null ) {
            return null;
        }
        List<Post_Tag> postTags = devPost.getPostTags();

        Long postId = null;
        String title = null;
        String sourceURL = null;
        int star = 0;
        Double starAvg = null;
        int recommend = 0;
        String sourceMedia = null;
        String thumbnailImage = null;
        String sorta = null;

        postId = devPost.getPostId();
        title = devPost.getTitle();
        sourceURL = devPost.getSourceURL();
        star = devPost.getStar();
        starAvg = devPost.getStarAvg();
        recommend = devPost.getRecommend();
        sourceMedia = devPost.getSourceMedia();
        thumbnailImage = devPost.getThumbnailImage();
        sorta = devPost.getSorta();

        DevPostMainResponse devPostMainResponse = new DevPostMainResponse(
                postId,
                title,
                sourceURL,
                star,
                starAvg, recommend, sourceMedia, postMemberDtoResponse(devPost),
                postTagDtoResponse(postTags), thumbnailImage, sorta );

        return devPostMainResponse;
    }
}