package org.mikhailov.dm.eventnotificator.notifications;

import org.mikhailov.dm.eventnotificator.kafkaevent.EventFieldChange;

import java.time.LocalDateTime;

public class Notification {
    private Long eventId;
    private EventFieldChange<String> name;
    private EventFieldChange<Integer> maxPlaces;
    private EventFieldChange<LocalDateTime> date;
    private EventFieldChange<Integer> cost;
    private EventFieldChange<Integer> duration;
    private EventFieldChange<Long> locationId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public EventFieldChange<String> getName() {
        return name;
    }

    public void setName(EventFieldChange<String> name) {
        this.name = name;
    }

    public EventFieldChange<Integer> getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(EventFieldChange<Integer> maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public EventFieldChange<LocalDateTime> getDate() {
        return date;
    }

    public void setDate(EventFieldChange<LocalDateTime> date) {
        this.date = date;
    }

    public EventFieldChange<Integer> getCost() {
        return cost;
    }

    public void setCost(EventFieldChange<Integer> cost) {
        this.cost = cost;
    }

    public EventFieldChange<Integer> getDuration() {
        return duration;
    }

    public void setDuration(EventFieldChange<Integer> duration) {
        this.duration = duration;
    }

    public EventFieldChange<Long> getLocationId() {
        return locationId;
    }

    public void setLocationId(EventFieldChange<Long> locationId) {
        this.locationId = locationId;
    }
}
