package org.almondiz.almondiz.shopscrap;

import lombok.*;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.common.TimeStamped;
import org.almondiz.almondiz.shop.entity.Shop;
import org.almondiz.almondiz.user.entity.User;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "ShopScrap_Table")
public class ShopScrap extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
