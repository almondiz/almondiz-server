package org.almondiz.almondiz.tag;

import java.util.List;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.TagExistedException;
import org.almondiz.almondiz.exception.exception.TagNotFoundException;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.tag.entity.TagRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public String getTagNameById(Long tagId) {
        return this.getTagById(tagId).getTagName();
    }

    @Transactional
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    }

    @Transactional
    public Tag getTagByTagName(String tagName) {
        return tagRepository.findByTagName(tagName).orElseThrow(TagNotFoundException::new);
    }

    @Transactional
    public Tag create(String tagName) {
        if (tagRepository.findByTagName(tagName).isPresent()) {
            throw new TagExistedException();
        }

        return tagRepository.save(Tag.builder()
                                     .tagName(tagName)
                                     // .status(Status.ALIVE)
                                     .build());
    }

    @Transactional
    public void deleteById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(TagNotFoundException::new);

        tagRepository.delete(tag);
    }

    @Transactional
    public void delete(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(TagNotFoundException::new);

        tagRepository.delete(tag);
    }

    @Transactional
    public List<Tag> findByTagNameLike(String tagName) {
        return tagRepository.findByTagNameLike(tagName);
    }
}
