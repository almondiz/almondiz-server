package org.almondiz.almondiz.notification;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponseDto {
    private Long id;

    private String text;

    private LocalDateTime createdAt;

    private boolean isRead;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.text = notification.getText();
        this.createdAt = notification.getCreatedAt();
        this.isRead = notification.isRead();
    }
}
