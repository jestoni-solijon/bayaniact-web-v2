package com.bayaniact.common.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import com.bayaniact.common.file.BrgyOfficialFile;
import com.bayaniact.common.file.EventFile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "event_id") private Long eventId;

    @Column(name = "event_title") private String eventTitle;

    @Column(name = "event_desc") private String eventDesc;

    @Column(name = "enabled") private int enabled;

    @Column(name = "event_start_date") private LocalDateTime eventStartDate;

    @Column(name = "event_end_date") private LocalDateTime eventEndDate;

    @Column(name = "event_location") private String eventLocation;

    @Column(name = "event_status") private int eventStatus;

    @Column(name = "event_type") private String eventType;

    @Column(name = "attendee") private String attendee;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventFile> eventFiles;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventId
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the eventTitle
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * @param eventTitle the eventTitle to set
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * @return the eventDesc
     */
    public String getEventDesc() {
        return eventDesc;
    }

    /**
     * @param eventDesc the eventDesc to set
     */
    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    /**
     * @return the enabled
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the eventStartDate
     */
    public LocalDateTime getEventStartDate() {
        return eventStartDate;
    }

    /**
     * @param eventStartDate the eventStartDate to set
     */
    public void setEventStartDate(LocalDateTime eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    /**
     * @return the eventEndDate
     */
    public LocalDateTime getEventEndDate() {
        return eventEndDate;
    }

    /**
     * @param eventEndDate the eventEndDate to set
     */
    public void setEventEndDate(LocalDateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    /**
     * @return the eventLocation
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * @param eventLocation the eventLocation to set
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * @return the eventStatus
     */
    public int getEventStatus() {
        return eventStatus;
    }

    /**
     * @param eventStatus the eventStatus to set
     */
    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public List<EventFile> getEventFiles() {
        return eventFiles;
    }

    public void setEventFiles(List<EventFile> eventFiles) {
        this.eventFiles = eventFiles;
    }

    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    public String getBase64Image() {
        if (eventFiles != null && !eventFiles.isEmpty()) {
            byte[] imageData = eventFiles.get(0).getData();
            return "data:" + eventFiles.get(0).getFileType() + ";base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return null;
    }
}
