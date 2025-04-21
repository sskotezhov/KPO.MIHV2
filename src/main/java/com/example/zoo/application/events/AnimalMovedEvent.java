package com.example.zoo.application.events;

import lombok.Getter;

@Getter
public class AnimalMovedEvent {
    private final Integer animalId;
    private final Integer fromEnclosureId;
    private final Integer toEnclosureId;

    public AnimalMovedEvent(Integer animalId, Integer fromEnclosureId, Integer toEnclosureId) {
        this.animalId = animalId;
        this.fromEnclosureId = fromEnclosureId;
        this.toEnclosureId = toEnclosureId;
    }

}