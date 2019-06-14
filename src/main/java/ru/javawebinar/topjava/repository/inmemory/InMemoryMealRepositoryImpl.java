package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    //TODO make edit work with checking userID correctly. Now it not saving userID in meal.
    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (repository.containsKey(meal.getId()) && Objects.equals(repository.get(meal.getId()).getUserId(), userId))
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.containsKey(id))
            return Objects.equals(repository.get(id).getUserId(), userId) && repository.remove(id) != null;
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(id))
            return Objects.equals(repository.get(id).getUserId(), userId) ? repository.get(id) : null;
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> Objects.equals(meal.getUserId(), userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, Predicate<Meal> filter) {
        return this.getAll(userId).stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}

