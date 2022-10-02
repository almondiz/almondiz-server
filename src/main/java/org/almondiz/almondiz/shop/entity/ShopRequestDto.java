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
public class ShopRequestDto {

    private String shopName;

    private Category category;

    private double lati;

    private double longi;

    private String address;

    private String contact;
}
