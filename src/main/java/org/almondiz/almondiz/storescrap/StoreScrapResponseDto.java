package org.almondiz.almondiz.storescrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreScrapResponseDto {

    private Long scrapId;

    private Long userId;

    private Long storeId;

    private LocalDateTime createdAt;

    public StoreScrapResponseDto(StoreScrap storeScrap) {
        this.scrapId = storeScrap.getId();
        this.userId = storeScrap.getUser().getUserId();
        this.storeId = storeScrap.getStore().getStoreId();
        this.createdAt = storeScrap.getCreatedAt();
    }
}
