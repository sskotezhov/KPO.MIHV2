package com.example.zoo.presentation.console;

import com.example.zoo.application.services.FeedingOrganizationService;
import com.example.zoo.domain.model.FeedingSchedule;
import com.example.zoo.domain.valueobjects.FoodType;
import com.example.zoo.infrastructure.persistence.InMemoryFeedingScheduleRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class FeedingScheduleController {
    private final InMemoryFeedingScheduleRepository scheduleRepository;
    private final FeedingOrganizationService feedingService;

    public FeedingScheduleController(InMemoryFeedingScheduleRepository scheduleRepository,
                                   FeedingOrganizationService feedingService) {
        this.scheduleRepository = scheduleRepository;
        this.feedingService = feedingService;
    }

    @ShellMethod(key = "feeding add", value = "Add a new feeding schedule")
    public String addFeedingSchedule(int animalId, String time, String foodType) {
        FeedingSchedule schedule = new FeedingSchedule(null, animalId, 
            LocalTime.parse(time), FoodType.valueOf(foodType.toUpperCase()));
        feedingService.addFeedingSchedule(schedule);
        return "Feeding schedule added with ID: " + schedule.getId();
    }

    @ShellMethod(key = "feeding list", value = "List all feeding schedules")
    public List<String> listFeedingSchedules() {
        return scheduleRepository.findAll().stream()
            .map(schedule -> String.format(
                "ID: %d | Animal: %d | Time: %s | Food: %s | Completed: %s",
                schedule.getId(),
                schedule.getAnimalId(),
                schedule.getFeedingTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                schedule.getFoodType(),
                schedule.isCompleted() ? "Yes" : "No"
            ))
            .collect(Collectors.toList());
    }

    @ShellMethod(key = "feeding complete", value = "Mark feeding as completed")
    public String completeFeeding(int id) {
        FeedingSchedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            schedule.markAsCompleted();
            return "Feeding marked as completed";
        }
        return "Feeding schedule not found";
    }
}