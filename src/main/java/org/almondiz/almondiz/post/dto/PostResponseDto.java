package org.almondiz.almondiz.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.store.entity.Store;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String nickName;
    private String storeName;
    private double lati;
    private double longi;
    private List<String> postFileImgUrls;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post, String nickName, Store store, List<String> postFileImgUrls){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickName = nickName;
        this.storeName = store.getStoreName();
        this.lati = store.getLati();
        this.longi = store.getLongi();
        this.postFileImgUrls = postFileImgUrls;
        this.createdAt = post.getCreatedAt();
    }

}
