в spring-shell.log лежат примеры команд(нужно убрать цифры в начале - это время, по сути просто из 1745420730045:animal add --name "Leo" --type PREDATOR --birthDate "2020-01-15" --gender M --favoriteFood MEAT --healthy true нужно убрать 1745420730045: и заслать проге запрос вида animal add --name "Leo" --type PREDATOR --birthDate "2020-01-15" --gender M --favoriteFood MEAT --healthy true
. в таргете лежит сбилженный проект, но можно ручками mvn clean install && mvn spring-boot:run

# Отчет по реализации Zoo Management System

## a. Реализованный функционал

### Управление животными
| Функционал               | Классы/Модули                          | Расположение             |
|--------------------------|----------------------------------------|--------------------------|
| Добавление/удаление       | `AnimalController`                     | presentation/console     |
| Просмотр информации       | `AnimalController.listAnimals()`       | presentation/console     |
| Лечение/перемещение       | `AnimalTransferService`                | application/services     |
| Доменная модель           | `Animal`                               | domain/model             |
| Хранение данных           | `InMemoryAnimalRepository`             | infrastructure/persistence|

### Управление вольерами
| Функционал               | Классы/Модули                          | Расположение             |
|--------------------------|----------------------------------------|--------------------------|
| Добавление/удаление       | `EnclosureController`                  | presentation/console     |
| Просмотр информации       | `EnclosureController.listEnclosures()` | presentation/console     |
| Уборка вольеров           | `Enclosure` (метод `clean()`)          | domain/model             |
| Доменная модель           | `Enclosure`                            | domain/model             |
| Хранение данных           | `InMemoryEnclosureRepository`          | infrastructure/persistence|

### Управление кормлениями
| Функционал               | Классы/Модули                          | Расположение             |
|--------------------------|----------------------------------------|--------------------------|
| Добавление в расписание   | `FeedingScheduleController`            | presentation/console     |
| Просмотр расписания       | `FeedingScheduleController.listFeedings()` | presentation/console  |
| Отметка выполнения       | `FeedingSchedule.markAsCompleted()`    | domain/model             |
| Доменная модель           | `FeedingSchedule`                      | domain/model             |
| Хранение данных           | `InMemoryFeedingScheduleRepository`    | infrastructure/persistence|

### Сервисы
| Функционал               | Классы/Модули                          | Расположение             |
|--------------------------|----------------------------------------|--------------------------|
| Перемещение животных      | `AnimalTransferService`                | application/services     |
| Организация кормлений     | `FeedingOrganizationService`           | application/services     |
| Статистика зоопарка       | `ZooStatisticsService`                 | application/services     |

## b. Примененные концепции DDD и Clean Architecture

### Domain-Driven Design
| Концепция                | Реализация                              | Классы/Модули            |
|--------------------------|----------------------------------------|--------------------------|
| Сущности (Entities)       | Основные бизнес-объекты                | `Animal`, `Enclosure`, `FeedingSchedule` (domain/model) |
| Value Objects             | Неизменяемые объекты                   | `AnimalType`, `EnclosureType`, `FoodType` (domain/valueobjects) |
| Агрегаты                  | Группы связанных объектов              | `Enclosure` с коллекцией `Animal` |
| Доменные события          | События предметной области             | `AnimalMovedEvent`, `FeedingTimeEvent` (application/events) |
| Репозитории               | Хранение агрегатов                     | `InMemory*Repository` (infrastructure/persistence) |

### Clean Architecture
| Принцип                  | Реализация                              | Примеры                   |
|--------------------------|----------------------------------------|--------------------------|
| Разделение слоев         | Четкие границы между слоями            | domain → application → infrastructure/presentation |
| Независимость домена     | Домен не зависит от внешних слоев      | Все классы в domain/     |
| Dependency Rule          | Зависимости направлены внутрь          | Infrastructure зависит от Domain, а не наоборот |
| Инверсия зависимостей    | Интерфейсы в domain, реализация в infra| Репозитории              |
| Изолированность Use Case| Сервисы в application слое            | `*Service` классы        |

### Дополнительные принципы
1. **Rich Domain Model** - бизнес-логика в доменных объектах:
   - `Animal.feed()`, `Animal.heal()`
   - `Enclosure.clean()`
   - `FeedingSchedule.markAsCompleted()`

2. **CQRS** - разделение команд и запросов:
   - Команды: `addAnimal`, `moveAnimal`
   - Запросы: `listAnimals`, `getStatistics`

3. **Event-Driven** - реакция на события:
   - Публикация `AnimalMovedEvent` при перемещении
   - Обработка `FeedingTimeEvent` по расписанию
