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
public class ShopResponseDto {

    private Long shopId;

    private String shopName;

    private Category category;

    private double lati;

    private double longi;

    private String address;

    private String contact;

    public ShopResponseDto(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.category = shop.getCategory();
        this.lati = shop.getLati();
        this.longi = shop.getLongi();
        this.address = shop.getAddress();
        this.contact = shop.getContact();
    }
}
