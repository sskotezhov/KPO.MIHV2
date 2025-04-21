package com.example.zoo.application.services;

import com.example.zoo.application.events.AnimalMovedEvent;
import com.example.zoo.domain.model.Animal;
import com.example.zoo.domain.model.Enclosure;
import com.example.zoo.infrastructure.persistence.InMemoryAnimalRepository;
import com.example.zoo.infrastructure.persistence.InMemoryEnclosureRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AnimalTransferService {
    private final InMemoryAnimalRepository animalRepository;
    private final InMemoryEnclosureRepository enclosureRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AnimalTransferService(InMemoryAnimalRepository animalRepository,
                               InMemoryEnclosureRepository enclosureRepository,
                               ApplicationEventPublisher eventPublisher) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.eventPublisher = eventPublisher;
    }

    public boolean transferAnimal(Integer animalId, Integer newEnclosureId) {
        Animal animal = animalRepository.findById(animalId);
        if (animal == null) {
            System.err.println("Animal with ID " + animalId + " not found");
            return false;
        }

        Enclosure newEnclosure = enclosureRepository.findById(newEnclosureId);
        if (newEnclosure == null) {
            System.err.println("Enclosure with ID " + newEnclosureId + " not found");
            return false;
        }

        Enclosure currentEnclosure = null;
        if (animal.getEnclosureId() != null) {
            currentEnclosure = enclosureRepository.findById(animal.getEnclosureId());
            if (currentEnclosure == null) {
                System.err.println("Current enclosure not found for animal");
                return false;
            }
        }

        if (!newEnclosure.hasSpace()) {
            System.err.println("Enclosure " + newEnclosureId + " is full");
            return false;
        }

        if (currentEnclosure != null) {
            if (!currentEnclosure.removeAnimal(animalId)) {
                System.err.println("Failed to remove animal from current enclosure");
                return false;
            }
        }

        if (!newEnclosure.addAnimal(animalId)) {
            System.err.println("Failed to add animal to new enclosure");
            return false;
        }

        animal.setEnclosureId(newEnclosureId);
        animalRepository.save(animal);

        eventPublisher.publishEvent(new AnimalMovedEvent(
            animalId,
            currentEnclosure != null ? currentEnclosure.getId() : null,
            newEnclosureId
        ));

        return true;
    }
}