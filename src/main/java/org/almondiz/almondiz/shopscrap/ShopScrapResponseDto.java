package org.almondiz.almondiz.shopscrap;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopScrapResponseDto {

    private Long scrapId;

    private Long userId;

    private Long shopId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ShopScrapResponseDto(ShopScrap shopScrap) {
        this.scrapId = shopScrap.getId();
        this.userId = shopScrap.getUser().getUserId();
        this.shopId = shopScrap.getShop().getShopId();
        this.createdAt = shopScrap.getCreatedAt();
    }
}
