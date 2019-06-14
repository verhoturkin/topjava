package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.function.Predicate;

public interface MealService {

    Meal create(Meal meal, int userId);

    void delete(int id, int usedId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getFiltered(int userId, Predicate<Meal> filter);
}