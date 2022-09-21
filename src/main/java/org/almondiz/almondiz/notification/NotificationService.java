package org.almondiz.almondiz.notification;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.exception.exception.NotificationNotFoundException;
import org.almondiz.almondiz.exception.exception.NotificationNotPermittedException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
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
            throw new UserNotFoundException();
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
            throw new UserNotFoundException();
        }

        Optional<Notification> notification = this.findById(notificationId);

        if(notification.isEmpty()){
            throw new NotificationNotFoundException();
        }

        if(!notification.get().getUser().getUserId().equals(user.get().getUserId())){
            throw new NotificationNotPermittedException();
        }

        Notification readNotification = notification.get();
        readNotification.setRead(true);
        notificationRepository.save(readNotification);
    }

    @Transactional
    public void delete(String email, Long notificationId) {
        Optional<User> user = userService.findByEmail(email);

        if(user.isEmpty()){
            throw new UserNotFoundException();
        }

        Optional<Notification> notification = this.findById(notificationId);

        if(notification.isEmpty()){
            throw new NotificationNotFoundException();
        }

        if(!notification.get().getUser().getUserId().equals(user.get().getUserId())){
            throw new NotificationNotPermittedException();
        }

        notificationRepository.deleteById(notificationId);
    }
}
