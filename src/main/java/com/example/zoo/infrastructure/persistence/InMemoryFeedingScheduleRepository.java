package com.example.zoo.infrastructure.persistence;

import com.example.zoo.domain.model.FeedingSchedule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryFeedingScheduleRepository {
    private final Map<Integer, FeedingSchedule> schedules = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public FeedingSchedule save(FeedingSchedule schedule) {
        if (schedule.getId() == null) {
            schedule.setId(idGenerator.getAndIncrement());
        }
        schedules.put(schedule.getId(), schedule);
        return schedule;
    }

    public FeedingSchedule findById(Integer id) {
        return schedules.get(id);
    }

    public List<FeedingSchedule> findAll() {
        return new ArrayList<>(schedules.values());
    }

    public void delete(Integer id) {
        schedules.remove(id);
    }
}