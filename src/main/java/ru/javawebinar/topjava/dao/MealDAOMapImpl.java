package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAOMapImpl implements MealDAO {
    public static final Map<Integer, Meal> map = new ConcurrentHashMap<>();
    // for multithreading
    public static final AtomicInteger counter = new AtomicInteger(7);
    private static final Logger log = getLogger(MealDAOMapImpl.class);

    //Mock data
    static {
        map.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 1));
        map.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 2));
        map.put(3, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 3));
        map.put(4, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 4));
        map.put(5, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 5));
        map.put(6, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 6));
    }

    @Override
    public void add(Meal meal) {
        int id = counter.getAndIncrement();
        meal.setId(id);
        map.put(id, meal);

    }

    @Override
    public void delete(int id) {
        map.remove(id);
        log.debug("deleted meal with id: {}", id);
    }

    @Override
    public void update(Meal meal) {
        map.put(meal.getId(), meal);
        log.debug("updates meal with id: {}", meal.getId());
    }

    @Override
    public List<Meal> getAll() {

        return new ArrayList<>(map.values());
    }

    @Override
    public Meal getById(int id) {
        return map.get(id);
    }
}
