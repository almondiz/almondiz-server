package org.almondiz.almondiz.post.entity;

import java.sql.Clob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.almondiz.almondiz.common.TimeStamped;

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

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeId;

    @Setter
    @Column(nullable = false)
    private Clob title;

    @Setter
    private Clob content;

    public Post(Long userId, Long storeId, Clob title, Clob content){
        this.userId = userId;
        this.storeId = storeId;
        this.title = title;
        this.content = content;
    }

}
