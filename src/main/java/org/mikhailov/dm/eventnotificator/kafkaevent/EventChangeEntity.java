package org.mikhailov.dm.eventnotificator.kafkaevent;

import jakarta.persistence.*;

@Entity
@Table
public class EventChangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "eventInfoId")
    private EventInfoEntity eventInfo;
    @Column(name = "change_name")
    private String changeName;
    @Column(name = "old_value")
    private String oldValue;
    @Column(name = "new_value")
    private String newValue;

    public EventChangeEntity(Long id, EventInfoEntity eventInfo, String changeName, String oldValue, String newValue) {
        this.id = id;
        this.eventInfo = eventInfo;
        this.changeName = changeName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public EventChangeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventInfoEntity getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(EventInfoEntity eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
