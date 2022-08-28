package org.almondiz.almondiz.tag;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.TagNotFoundException;
import org.almondiz.almondiz.tag.entity.TagRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public String getTagNameById(Long tagId){
        String tagName = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new).getTagName();
        return tagName;
    }

}
