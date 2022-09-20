package org.almondiz.almondiz.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private boolean isRead;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.text = notification.getText();
        this.createdAt = notification.getCreatedAt();
        this.isRead = notification.isRead();
    }
}
