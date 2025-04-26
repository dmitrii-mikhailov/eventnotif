package org.mikhailov.dm.eventnotificator.kafkaevent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EventInfoRepository extends JpaRepository<EventInfoEntity, Long> {
    List<EventInfoEntity> findByOwnerId(Long ownerId);

    @Modifying
    @Transactional
    @Query("UPDATE EventInfoEntity e SET e.isRead = true WHERE e.id IN :notificationIds AND e.ownerId = :userId")
    void markAsRead(@Param("notificationIds") List<Long> notificationIds, @Param("userId") Long userId);

    // Удаление EventChangeEntity связных записей
    @Modifying
    @Query("DELETE FROM EventChangeEntity e WHERE e.eventInfo.id IN " +
            "(SELECT ei.id FROM EventInfoEntity ei WHERE ei.createdAt < :cutoffDate)")
    void deleteChangesByEventCreatedBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

    // Удаление основных записей
    @Modifying
    @Query("DELETE FROM EventInfoEntity e WHERE e.createdAt < :cutoffDate")
    void deleteByCreatedBefore(@Param("cutoffDate") LocalDateTime cutoffDate);
}
