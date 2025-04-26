package org.mikhailov.dm.eventnotificator.kafkaevent;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class EventInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "changed_by_user_id")
    private Long changedByUserId;
    @Column(name = "owner_id")
    private Long ownerId;
    @ElementCollection
    @CollectionTable(name = "event_users", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "user_id")
    private List<Long> users;
    @OneToMany(mappedBy = "eventInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventChangeEntity> changes;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // Автоматически при создании
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;  // По умолчанию false




    public EventInfoEntity() {
    }

    public EventInfoEntity(Long id,
                           Long eventId,
                           Long changedByUserId,
                           Long ownerId,
                           List<Long> users,
                           List<EventChangeEntity> changes,
                           LocalDateTime createdAt,
                           boolean isRead) {
        this.id = id;
        this.eventId = eventId;
        this.changedByUserId = changedByUserId;
        this.ownerId = ownerId;
        this.users = users;
        this.changes = changes;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(Long changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public List<EventChangeEntity> getChanges() {
        return changes;
    }

    public void setChanges(List<EventChangeEntity> changes) {
        this.changes = changes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
