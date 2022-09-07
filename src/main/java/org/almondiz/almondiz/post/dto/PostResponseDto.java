package org.almondiz.almondiz.post.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.comment.dto.CommentResponseDto;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.store.entity.Store;
import org.almondiz.almondiz.tag.dto.TagResponseDto;

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
    private String storeAddress;
    private double lati;
    private double longi;
    private List<String> postFileImgUrls;
    private String userProfileImgUrl;
    private List<CommentResponseDto> commentList;
    private List<TagResponseDto> tagList;


    public PostResponseDto(Post post, String nickName, Store store, List<String> postFileImgUrls, String userProfileImgUrl, List<CommentResponseDto> commentList, List<TagResponseDto> tagList){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickName = nickName;
        this.storeName = store.getStoreName();
        this.storeAddress = store.getAddress();
        this.lati = store.getLati();
        this.longi = store.getLongi();
        this.postFileImgUrls = postFileImgUrls;
        this.userProfileImgUrl = userProfileImgUrl;
        this.commentList = commentList;
        this.tagList = tagList;
    }

}
