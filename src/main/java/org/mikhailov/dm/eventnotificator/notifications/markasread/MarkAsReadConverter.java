package org.mikhailov.dm.eventnotificator.notifications.markasread;

import org.springframework.stereotype.Component;

@Component
public class MarkAsReadConverter {
    public MarkAsRead toDomain(MarkAsReadDto dto) {
        return new MarkAsRead(dto.notificationIds());
    }
}