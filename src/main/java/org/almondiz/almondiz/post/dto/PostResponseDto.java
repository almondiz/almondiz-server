package org.almondiz.almondiz.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.shop.entity.ShopResponseDto;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long postId;

    private String text;

    private List<String> postFileImgUrls;

    private UserSimpleResponseDto user;

    private ShopResponseDto shop;

    private List<TagResponseDto> tags;

    private List<CommentResponseDto> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post, List<String> postFileImgUrls, UserSimpleResponseDto user, ShopResponseDto shop, List<TagResponseDto> tagList,
                           List<CommentResponseDto> commentList, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.postId = post.getPostId();
        this.text = post.getContent();
        this.postFileImgUrls = postFileImgUrls;
        this.user = user;
        this.shop = shop;
        this.tags = tagList;
        this.comments = commentList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
