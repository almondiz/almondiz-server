package org.almondiz.almondiz.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.common.Relation;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.shop.entity.ShopSimpleDto;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.user.dto.UserSimpleResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long postId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    private UserSimpleResponseDto user;

    private Relation relation;

    private ShopSimpleDto shop;

    private List<TagResponseDto> tags;

    private String text;

    private List<String> postFileImgUrls;

    private Long scrappedCount;

    private boolean scrap;

    private Long commentCount;

    public PostResponseDto(Post post, List<String> postFileImgUrls, UserSimpleResponseDto user, Relation relation, ShopSimpleDto shop, List<TagResponseDto> tagList,
                           Long scrappedCount, boolean scrap, Long commentCount) {
        this.postId = post.getPostId();
        this.text = post.getContent();
        this.postFileImgUrls = postFileImgUrls;
        this.user = user;
        this.relation = relation;
        this.shop = shop;
        this.tags = tagList;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.scrappedCount = scrappedCount;
        this.scrap = scrap;
        this.commentCount = commentCount;
    }
}
