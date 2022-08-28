package org.almondiz.almondiz.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    // 임시 메세지
    ALERT("관련 알림이 도착했습니다"),
    FOLLOW("님이 팔로우하셨습니다");

    private String message;

    NotificationType(String message) {
        this.message = message;
    }
}
