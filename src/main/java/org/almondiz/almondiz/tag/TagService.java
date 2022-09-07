package org.almondiz.almondiz.tag;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.exception.exception.TagNotFoundException;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
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

    public List<TagResponseDto> getTagsByPost(Post post){

        // 중간 테이블 TagPost 생성 후 구현 필요
        return null;

    }



}
