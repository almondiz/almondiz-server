package org.almondiz.almondiz.shop.entity;

import javax.persistence.Column;
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
import org.almondiz.almondiz.category.entity.Category;
import org.almondiz.almondiz.common.TimeStamped;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Shop_Table")
public class Shop extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false)
    private String shopName;

    // @ManyToOne(targetEntity = User.class)
    // @JoinColumn(name = "ownerId")
    // private User owner;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "categoryId")
    private Category category;

    private double lati;

    private double longi;

    private String address;

    private String contact;

}
