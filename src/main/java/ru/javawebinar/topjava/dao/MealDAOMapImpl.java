package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsMock;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOMapImpl implements MealDAO {
    public static final Map<Integer, Meal> map = new ConcurrentHashMap<>();
    public static final AtomicInteger counter = new AtomicInteger(0);

    static {
        MealsMock.get().forEach(meal -> map.put(counter.getAndIncrement(), meal));
    }

    @Override
    public void add(Meal meal) {
        map.put(counter.getAndIncrement(), meal);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public void update(Meal meal) {
        map.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return (List<Meal>) map.values();
    }

    @Override
    public Meal getById(int id) {
        return map.get(id);
    }
}
