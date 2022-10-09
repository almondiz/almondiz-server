package org.almondiz.almondiz.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.tag.dto.TagResponseDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSimpleResponseDto {

    private Long shopId;
    private String text;
    private List<String> postFileImgUrls;
    private List<TagResponseDto> tags;

    public PostSimpleResponseDto(Post post, List<String> postFileImgUrls, List<TagResponseDto> tagList) {
        this.shopId = post.getShop().getShopId();
        this.text = post.getContent();
        this.postFileImgUrls = postFileImgUrls;
        this.tags = tagList;
    }
}
