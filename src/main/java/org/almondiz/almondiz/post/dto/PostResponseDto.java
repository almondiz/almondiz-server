package org.almondiz.almondiz.post.dto;

import java.sql.Clob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private Clob title;
    private Clob content;
    private String userName;
    private String storeName;
    private double lati;
    private double longi;
    private String postFileImgUrl;
}
