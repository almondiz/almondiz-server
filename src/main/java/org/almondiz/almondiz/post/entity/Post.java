package org.almondiz.almondiz.post.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.post.dto.PostRequestDto;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.user.entity.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Post_Table")
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Shop shop;

    private String content;

    @Column(nullable = false)
    private double lati;

    @Column(nullable = false)
    private double longi;

    // @Setter
    // @Enumerated(EnumType.STRING)
    // @Column(nullable = false)
    // private Status status;

    public void update(PostRequestDto postRequestDto){
        this.content = postRequestDto.getContent();
    }

}
