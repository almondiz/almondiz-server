package org.almondiz.almondiz.tagpost.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

}
