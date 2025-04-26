package org.mikhailov.dm.eventnotificator.notifications;

import org.mikhailov.dm.eventnotificator.kafkaevent.EventFieldChange;

import java.time.LocalDateTime;

public record NotificationDto (
     Long eventId,
     EventFieldChange<String> name,
     EventFieldChange<Integer> maxPlaces,
     EventFieldChange<LocalDateTime> date,
     EventFieldChange<Integer> cost,
     EventFieldChange<Integer> duration,
     EventFieldChange<Long> locationId
) {}


