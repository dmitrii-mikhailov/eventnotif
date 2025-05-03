package org.mikhailov.dm.eventnotificator.notifications;

import org.mikhailov.dm.eventnotificator.kafkaevent.EventChangeEntity;
import org.mikhailov.dm.eventnotificator.kafkaevent.EventFieldChange;
import org.mikhailov.dm.eventnotificator.kafkaevent.EventInfoEntity;
import org.mikhailov.dm.eventnotificator.kafkaevent.EventInfoRepository;
import org.mikhailov.dm.eventnotificator.notifications.markasread.MarkAsRead;
import org.mikhailov.dm.eventnotificator.security.jwt.JwtTokenManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final EventInfoRepository eventInfoRepository;
    private final JwtTokenManager jwtTokenManager;

    public NotificationService(EventInfoRepository eventInfoRepository, JwtTokenManager jwtTokenManager) {
        this.eventInfoRepository = eventInfoRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public List<Notification> getUnreadNotifications(String token) {
        Long userid = jwtTokenManager.getUserIdFromToken(token);
        List<EventInfoEntity> entities = eventInfoRepository.findByOwnerId(userid);
        return entities
                .stream()
                .filter(eventInfoEntity -> !eventInfoEntity.isRead())
                .map(this::mapToNotification)
                .toList();
    }


    public void markNotificationAsRead(MarkAsRead ids, String token) {
        Long userid = jwtTokenManager.getUserIdFromToken(token);
        eventInfoRepository.markAsRead(ids.notificationIds(), userid);
    }

    private Notification mapToNotification(EventInfoEntity eventInfo) {
        Notification notification = new Notification();
        notification.setEventId(eventInfo.getEventId());

        // Собираем изменения из EventChangeEntity
        Map<String, EventFieldChange<?>> changes = eventInfo.getChanges().stream()
                .collect(Collectors.toMap(
                        EventChangeEntity::getChangeName,
                        this::mapToFieldChange
                ));

        // Заполняем
        notification.setName((EventFieldChange<String>) changes.get("name"));
        notification.setMaxPlaces((EventFieldChange<Integer>) changes.get("maxPlaces"));
        notification.setDate((EventFieldChange<LocalDateTime>) changes.get("date"));
        notification.setCost((EventFieldChange<Integer>) changes.get("cost"));
        notification.setDuration((EventFieldChange<Integer>) changes.get("duration"));
        notification.setLocationId((EventFieldChange<Long>) changes.get("locationId"));

        return notification;
    }

    private EventFieldChange<?> mapToFieldChange(EventChangeEntity change) {
        EventFieldChange<Object> fieldChange = new EventFieldChange<>();
        fieldChange.setOldValue(parseValue(change.getOldValue(), change.getChangeName()));
        fieldChange.setNewValue(parseValue(change.getNewValue(), change.getChangeName()));
        return fieldChange;
    }
    // Парсинг строковых значений в нужный тип (LocalDateTime, Integer и т.д.)
    private Object parseValue(String value, String fieldName) {
        if (value == null) return null;

        return switch (fieldName) {
            case "date" -> LocalDateTime.parse(value);
            case "maxPlaces", "cost", "duration" -> Integer.parseInt(value);
            case "locationId" -> Long.parseLong(value);
            default -> value; // name, status и другие строки
        };
    }
}