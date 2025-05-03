package org.mikhailov.dm.eventnotificator.notifications.markasread;

import java.util.List;

public record MarkAsRead(
        List<Long> notificationIds
) {
}
