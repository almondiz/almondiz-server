package org.almondiz.almondiz.report;


import lombok.*;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.entity.User;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Report_Table")
public class Report extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String text;

    private String type;
}
