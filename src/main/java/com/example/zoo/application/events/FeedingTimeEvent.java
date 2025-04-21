package com.example.zoo.application.events;

import com.example.zoo.domain.model.FeedingSchedule;

public class FeedingTimeEvent {
    private final FeedingSchedule schedule;

    public FeedingTimeEvent(FeedingSchedule schedule) {
        this.schedule = schedule;
    }

    public FeedingSchedule getSchedule() {
        return schedule;
    }
}