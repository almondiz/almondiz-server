package org.almondiz.almondiz.postscrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostScrapResponseDto {

    private Long scrapId;

    private Long userId;

    private Long postId;

    public PostScrapResponseDto(PostScrap postScrap) {
        this.scrapId = postScrap.getId();
        this.userId = postScrap.getUser().getUserId();
        this.postId = postScrap.getPost().getPostId();
    }
}
