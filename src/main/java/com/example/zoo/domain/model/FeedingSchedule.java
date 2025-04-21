package com.example.zoo.domain.model;

import com.example.zoo.domain.valueobjects.FoodType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class FeedingSchedule {
    private Integer id;
    private Integer animalId;
    private LocalTime feedingTime;
    private FoodType foodType;
    private boolean completed;

    public FeedingSchedule(Integer id, Integer animalId, LocalTime feedingTime, FoodType foodType) {
        this.id = id;
        this.animalId = animalId;
        this.feedingTime = feedingTime;
        this.foodType = foodType;
        this.completed = false;
    }

    public void markAsCompleted() {
        this.completed = true;
        System.out.println("Feeding schedule " + id + " marked as completed");
    }

    public void reschedule(LocalTime newTime) {
        this.feedingTime = newTime;
        this.completed = false;
        System.out.println("Feeding schedule " + id + " rescheduled to " + newTime);
    }
}