package org.mikhailov.dm.eventnotificator.notifications;

import org.springframework.stereotype.Component;

@Component
public class NotificationDtoConverter {
    public NotificationDto toDto(Notification notification) {
        return new NotificationDto(
                notification.getEventId(),
                notification.getName(),
                notification.getMaxPlaces(),
                notification.getDate(),
                notification.getCost(),
                notification.getDuration(),
                notification.getLocationId()
        );
    }
}
