package org.almondiz.almondiz.tagpost;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.TagPostNotFoundException;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.tag.dto.TagResponseDto;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.tagpost.entity.TagPost;
import org.almondiz.almondiz.tagpost.entity.TagPostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagPostService {

    private final TagPostRepository tagPostRepository;

    @Transactional
    public void create(Post post, Tag tag) {
        TagPost tagPost = TagPost.builder()
                                 .post(post)
                                 .tag(tag)
                                 .status(Status.ALIVE)
                                 .build();

        tagPostRepository.save(tagPost);
    }

    @Transactional
    public void deleteById(Long id) {
        TagPost tagPost = tagPostRepository.findById(id).orElseThrow(TagPostNotFoundException::new);

        tagPost.setStatus(Status.DELETED);
        tagPostRepository.save(tagPost);
    }

    @Transactional
    public void delete(Post post, Tag tag) {
        TagPost tagPost = tagPostRepository.findByPostAndTag(post, tag).orElseThrow(TagPostNotFoundException::new);

        tagPost.setStatus(Status.DELETED);
        tagPostRepository.save(tagPost);
    }

    @Transactional
    public List<TagResponseDto> getTagsByPost(Post post) {
        return tagPostRepository.findByPost(post)
                                .stream().map(tagPost -> this.getTagResponseDto(tagPost))
                                .collect(Collectors.toList());
    }

    @Transactional
    public TagResponseDto getTagResponseDto(TagPost tagPost) {
        return new TagResponseDto(tagPost.getTag());
    }


}
