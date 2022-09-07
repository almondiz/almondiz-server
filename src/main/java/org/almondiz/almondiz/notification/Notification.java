package org.almondiz.almondiz.notification;

import lombok.*;
import org.almondiz.almondiz.response.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Notification_Table")
public class Notification {

    @Id
    // 상의할 필요
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(nullable = false)
    private boolean isRead;
}
