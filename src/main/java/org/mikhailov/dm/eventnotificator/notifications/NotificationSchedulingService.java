package org.mikhailov.dm.eventnotificator.notifications;

import org.mikhailov.dm.eventnotificator.kafkaevent.EventInfoEntity;
import org.mikhailov.dm.eventnotificator.kafkaevent.EventInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationSchedulingService {
    private final EventInfoRepository eventInfoRepository;
    private static final Logger log = LoggerFactory.getLogger(NotificationSchedulingService.class);

    public NotificationSchedulingService(EventInfoRepository eventInfoRepository) {
        this.eventInfoRepository = eventInfoRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void triggerEventSchedule() {
        log.info("Deleting old notifications");
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        eventInfoRepository.deleteChangesByEventCreatedBefore(cutoffDate); // Сначала удаляем связанные записи
        eventInfoRepository.deleteByCreatedBefore(cutoffDate); // Затем основные
    }
}