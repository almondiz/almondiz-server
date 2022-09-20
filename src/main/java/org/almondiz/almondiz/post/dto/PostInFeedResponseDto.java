package org.almondiz.almondiz.post.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.store.entity.StoreResponseDto;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.user.dto.UserAsWriterResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInFeedResponseDto {

    private Long postId;
    private String text;
    private List<String> postFileImgUrls;
    private UserAsWriterResponseDto user;
    private StoreResponseDto store;
    private List<TagResponseDto> tags;
    private CommentResponseDto bestComment;

    public PostInFeedResponseDto(Post post,  List<String> postFileImgUrls, UserAsWriterResponseDto user, StoreResponseDto store, List<TagResponseDto> tagList, CommentResponseDto bestComment){
        this.postId = post.getPostId();
        this.text = post.getContent();
        this.postFileImgUrls = postFileImgUrls;
        this.user = user;
        this.store = store;
        this.tags = tagList;
        this.bestComment = bestComment;
    }


}
