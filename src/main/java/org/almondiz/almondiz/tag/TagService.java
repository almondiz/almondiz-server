package org.almondiz.almondiz.tag;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.TagNotFoundException;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.tag.entity.TagRepository;
import org.springframework.stereotype.Service;

@Service
@Setter
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public String getTagNameById(Long tagId){
        return this.getTagById(tagId).getTagName();
    }

    @Transactional
    public Tag getTagById(Long tagId){
        return tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    }

}
