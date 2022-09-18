package org.almondiz.almondiz.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.almondiz.almondiz.category.entity.Category;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponseDto {

    private Long storeId;

    private String storeName;

    private Category category;

    private double lati;

    private double longi;

    private String address;

    private String contact;

    public StoreResponseDto(Store store) {
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.category = store.getCategory();
        this.lati = store.getLati();
        this.longi = store.getLongi();
        this.address = store.getAddress();
        this.contact = store.getContact();
    }
}
