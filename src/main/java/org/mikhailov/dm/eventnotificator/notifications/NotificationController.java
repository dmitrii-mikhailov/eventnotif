package org.mikhailov.dm.eventnotificator.notifications;

import org.mikhailov.dm.eventnotificator.notifications.markasread.MarkAsReadConverter;
import org.mikhailov.dm.eventnotificator.notifications.markasread.MarkAsReadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;
    private final MarkAsReadConverter markAsReadConverter;
    private final NotificationDtoConverter notificationDtoConverter;

    public NotificationController(NotificationService notificationService, MarkAsReadConverter markAsReadConverter, NotificationDtoConverter notificationDtoConverter) {
        this.notificationService = notificationService;
        this.markAsReadConverter = markAsReadConverter;
        this.notificationDtoConverter = notificationDtoConverter;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(@RequestHeader("Authorization") String token) {
        log.info("Getting all unread notifications");
        token = token.substring(7).trim();
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unreadNotifications
                        .stream()
                        .map(notificationDtoConverter::toDto)
                        .toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> markNotificationAsRead(@RequestBody MarkAsReadDto ids,
                                                       @RequestHeader("Authorization") String token) {
        log.info("Marking all unread notifications as read");
        token = token.substring(7).trim();
        notificationService.markNotificationAsRead(markAsReadConverter.toDomain(ids), token);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header("message", "Notifications marked as read successfully")
                .build();
    }

}
