package com.example.zoo.domain.model;

import com.example.zoo.domain.valueobjects.EnclosureType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Enclosure {
    private Integer id;
    private EnclosureType type;
    private double size;
    private int maxCapacity;
    private List<Integer> animalIds = new ArrayList<>();
    private boolean clean;

    public Enclosure(Integer id, EnclosureType type, double size, int maxCapacity) {
        this.id = id;
        this.type = type;
        this.size = size;
        this.maxCapacity = maxCapacity;
        this.clean = true;
    }

    public boolean addAnimal(Integer animalId) {
        if (animalIds.size() < maxCapacity) {
            animalIds.add(animalId);
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Integer animalId) {
        return animalIds.remove(animalId);
    }

    public void clean() {
        this.clean = true;
        System.out.println("Enclosure " + id + " has been cleaned");
    }

    public boolean hasSpace() {
        return animalIds.size() < maxCapacity;
    }
}