package org.mikhailov.dm.eventnotificator.kafkaevent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EventKafkaEventListener {
    private static final Logger log = LoggerFactory.getLogger(EventKafkaEventListener.class);

    private final EventInfoRepository eventInfoRepository;
    private final EventChangeRepository eventChangeRepository;

    public EventKafkaEventListener(
            EventInfoRepository eventInfoRepository,
            EventChangeRepository eventChangeRepository
    ) {
        this.eventInfoRepository = eventInfoRepository;
        this.eventChangeRepository = eventChangeRepository;
    }
    @Transactional
    @KafkaListener(topics = "event-change-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listenEvents(ConsumerRecord<Long, EventChangeKafkaMessage> record) {
        log.info("Received records: {}", record.value().toString());

        EventChangeKafkaMessage message = record.value();
        // 1. Создаем и сохраняем EventInfoEntity (новая запись)
        EventInfoEntity eventInfo = new EventInfoEntity();
        eventInfo.setEventId(message.getEventId());
        eventInfo.setChangedByUserId(message.getChangedByUserId());
        eventInfo.setOwnerId(message.getOwnerId());
        eventInfo.setUsers(message.getUsers());

        eventInfo = eventInfoRepository.save(eventInfo);  // Сохраняем, чтобы получить ID

        // 2. Сохраняем все изменения (EventChangeEntity)
        saveFieldChange(eventInfo, "name", message.getName());
        saveFieldChange(eventInfo, "maxPlaces", message.getMaxPlaces());
        saveFieldChange(eventInfo, "date", message.getDate());
        saveFieldChange(eventInfo, "cost", message.getCost());
        saveFieldChange(eventInfo, "duration", message.getDuration());
        saveFieldChange(eventInfo, "locationId", message.getLocationId());
        saveFieldChange(eventInfo, "status", message.getStatus());
    }

    private <T> void saveFieldChange(EventInfoEntity eventInfo, String fieldName, EventFieldChange<T> change) {
        if (change != null) {
            EventChangeEntity changeEntity = new EventChangeEntity();
            changeEntity.setEventInfo(eventInfo);
            changeEntity.setChangeName(fieldName);
            changeEntity.setOldValue(change.getOldValue() != null ? change.getOldValue().toString() : null);
            changeEntity.setNewValue(change.getNewValue() != null ? change.getNewValue().toString() : null);

            eventChangeRepository.save(changeEntity);
        }
    }
}
