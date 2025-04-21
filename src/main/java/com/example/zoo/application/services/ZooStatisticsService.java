package com.example.zoo.application.services;

import com.example.zoo.domain.model.Enclosure;
import com.example.zoo.infrastructure.persistence.InMemoryAnimalRepository;
import com.example.zoo.infrastructure.persistence.InMemoryEnclosureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZooStatisticsService {
    private final InMemoryAnimalRepository animalRepository;
    private final InMemoryEnclosureRepository enclosureRepository;

    public ZooStatisticsService(InMemoryAnimalRepository animalRepository,
                              InMemoryEnclosureRepository enclosureRepository) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    public Map<String, Long> getAnimalCountByType() {
        return animalRepository.findAll().stream()
                .collect(Collectors.groupingBy(a -> a.getType().name(), Collectors.counting()));
    }

    public long getTotalAnimals() {
        return animalRepository.findAll().size();
    }

    public List<Enclosure> getAvailableEnclosures() {
        return enclosureRepository.findAll().stream()
                .filter(Enclosure::hasSpace)
                .collect(Collectors.toList());
    }

    public long getTotalEnclosures() {
        return enclosureRepository.findAll().size();
    }
}