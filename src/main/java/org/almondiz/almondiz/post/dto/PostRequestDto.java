package org.almondiz.almondiz.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    private Long shopId;

    private String content;

    private double lati;

    private double longi;

    private List<Long> tags;

    private List<String> images;

}
