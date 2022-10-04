package org.almondiz.almondiz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.category.entity.Category;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopSimpleDto {

    private Long shopId;

    private String shopName;

    private Category category;

    private Location location;

    public ShopSimpleDto(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.category = shop.getCategory();
        this.location = new Location(shop.getAddress(), shop.getLati(), shop.getLongi());
    }
}
