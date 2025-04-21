package com.example.zoo.application.services;

import com.example.zoo.application.events.FeedingTimeEvent;
import com.example.zoo.domain.model.FeedingSchedule;
import com.example.zoo.infrastructure.persistence.InMemoryFeedingScheduleRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class FeedingOrganizationService {
    private final InMemoryFeedingScheduleRepository scheduleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FeedingOrganizationService(InMemoryFeedingScheduleRepository scheduleRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.scheduleRepository = scheduleRepository;
        this.eventPublisher = eventPublisher;
    }

    public void addFeedingSchedule(FeedingSchedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkFeedingTime() {
        List<FeedingSchedule> schedules = scheduleRepository.findAll();
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);

        schedules.stream()
                .filter(s -> s.getFeedingTime().equals(now) && !s.isCompleted())
                .forEach(s -> {
                    eventPublisher.publishEvent(new FeedingTimeEvent(s));
                    s.markAsCompleted();
                });
    }
}