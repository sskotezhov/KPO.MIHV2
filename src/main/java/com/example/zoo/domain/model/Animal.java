package com.example.zoo.domain.model;

import com.example.zoo.domain.valueobjects.AnimalType;
import com.example.zoo.domain.valueobjects.FoodType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Animal {
    private Integer id;
    private String name;
    private AnimalType type;
    private LocalDate birthDate;
    private String gender;
    private FoodType favoriteFood;
    private boolean healthy;
    private Integer enclosureId;

    public Animal(Integer id, String name, AnimalType type, LocalDate birthDate, 
                 String gender, FoodType favoriteFood, boolean healthy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.birthDate = birthDate;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
        this.healthy = healthy;
    }

    public void feed() {
        System.out.println(name + " has been fed with " + favoriteFood);
    }

    public void heal() {
        this.healthy = true;
        System.out.println(name + " has been treated and is now healthy");
    }

    public void moveToEnclosure(Integer enclosureId) {
        this.enclosureId = enclosureId;
        System.out.println(name + " has been moved to enclosure " + enclosureId);
    }
}