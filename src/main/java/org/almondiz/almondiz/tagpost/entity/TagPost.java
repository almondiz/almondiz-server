package org.almondiz.almondiz.tagpost.entity;

import javax.persistence.*;

import lombok.*;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.tag.entity.Tag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "TagPost_Table")
public class TagPost extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagPostId;

    @ManyToOne(targetEntity = Tag.class)
    @JoinColumn(name = "tagId")
    private Tag tag;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "postId")
    private Post post;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

}
