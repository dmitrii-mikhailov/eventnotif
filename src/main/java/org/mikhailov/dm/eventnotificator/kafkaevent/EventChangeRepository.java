package org.mikhailov.dm.eventnotificator.kafkaevent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventChangeRepository extends JpaRepository<EventChangeEntity, Long> {
}
