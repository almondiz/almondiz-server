package org.almondiz.almondiz.notification;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.CNotificationNotFoundException;
import org.almondiz.almondiz.exception.exception.CNotificationNotPermittedException;
import org.almondiz.almondiz.exception.exception.CUserNotFoundException;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    @Transactional
    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Transactional
    public void create(User user, NotificationType type) {
        Notification notification = Notification.builder()
                                                .user(user)
                                                .type(type)
                                                .text(type.getMessage())
                                                .createdAt(LocalDateTime.now())
                                                .isRead(false)
                                                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public List<NotificationResponseDto> findAllByUser(String email) {
        Optional<User> user = userService.findByEmail(email);

        if(user.isEmpty()){
            throw new CUserNotFoundException();
        }

        return notificationRepository.findAllByUser(user.get())
                                     .stream()
                                     .map(notification -> new NotificationResponseDto(notification))
                                     .collect(Collectors.toList());
    }

    @Transactional
    public void read(String email, Long notificationId) {
        Optional<User> user = userService.findByEmail(email);

        if(user.isEmpty()){
            throw new CUserNotFoundException();
        }

        Optional<Notification> notification = this.findById(notificationId);

        if(notification.isEmpty()){
            throw new CNotificationNotFoundException();
        }

        if(!notification.get().getUser().getUserId().equals(user.get().getUserId())){
            throw new CNotificationNotPermittedException();
        }

        Notification readNotification = notification.get();
        readNotification.setRead(true);
        notificationRepository.save(readNotification);
    }

    @Transactional
    public void delete(String email, Long notificationId) {
        Optional<User> user = userService.findByEmail(email);

        if(user.isEmpty()){
            throw new CUserNotFoundException();
        }

        Optional<Notification> notification = this.findById(notificationId);

        if(notification.isEmpty()){
            throw new CNotificationNotFoundException();
        }

        if(!notification.get().getUser().getUserId().equals(user.get().getUserId())){
            throw new CNotificationNotPermittedException();
        }

        notificationRepository.deleteById(notificationId);
    }
}
