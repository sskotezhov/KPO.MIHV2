package com.example.zoo.presentation.console;

import com.example.zoo.application.services.AnimalTransferService;
import com.example.zoo.application.services.ZooStatisticsService;
import com.example.zoo.domain.model.Animal;
import com.example.zoo.domain.valueobjects.AnimalType;
import com.example.zoo.domain.valueobjects.FoodType;
import com.example.zoo.infrastructure.persistence.InMemoryAnimalRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class AnimalController {
    private final InMemoryAnimalRepository animalRepository;
    private final AnimalTransferService transferService;
    private final ZooStatisticsService statisticsService;

    public AnimalController(InMemoryAnimalRepository animalRepository,
                          AnimalTransferService transferService,
                          ZooStatisticsService statisticsService) {
        this.animalRepository = animalRepository;
        this.transferService = transferService;
        this.statisticsService = statisticsService;
    }

    @ShellMethod(key = "animal add", value = "Add a new animal")
    public String addAnimal(String name, String type, String birthDate, 
                           String gender, String favoriteFood, boolean healthy) {
        Animal animal = new Animal(null, name, AnimalType.valueOf(type.toUpperCase()),
                LocalDate.parse(birthDate), gender, 
                FoodType.valueOf(favoriteFood.toUpperCase()), healthy);
        animalRepository.save(animal);
        return "Animal added with ID: " + animal.getId();
    }

    @ShellMethod(key = "animal list", value = "List all animals")
    public List<String> listAnimals() {
        return animalRepository.findAll().stream()
            .map(animal -> String.format(
                "ID: %d | Name: %s | Type: %s | Age: %d | Gender: %s | Health: %s | Enclosure: %d",
                animal.getId(),
                animal.getName(),
                animal.getType(),
                Period.between(animal.getBirthDate(), LocalDate.now()).getYears(),
                animal.getGender(),
                animal.isHealthy() ? "Healthy" : "Sick",
                animal.getEnclosureId()
            ))
            .collect(Collectors.toList());
    }

    @ShellMethod(key = "animal delete", value = "Delete an animal")
    public String deleteAnimal(int id) {
        animalRepository.delete(id);
        return "Animal deleted";
    }

    @ShellMethod(key = "animal move", value = "Move animal to another enclosure")
    public String moveAnimal(int animalId, int enclosureId) {
        boolean success = transferService.transferAnimal(animalId, enclosureId);
        return success ? "Animal moved successfully" : "Failed to move animal";
    }

    @ShellMethod(key = "animal stats", value = "Show animal statistics")
    public String animalStatistics() {
        return "Total animals: " + statisticsService.getTotalAnimals() + "\n" +
               "Animals by type: " + statisticsService.getAnimalCountByType();
    }
}