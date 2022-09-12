package org.almondiz.almondiz.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.tag.entity.Tag;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {

    private Long tagId;

    private String tagName;

    public TagResponseDto(Tag tag){
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }

}
