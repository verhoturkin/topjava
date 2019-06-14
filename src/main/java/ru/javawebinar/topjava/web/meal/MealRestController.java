package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExcess;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("get all meals");
        return getWithExcess(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    //TODO rewrite this shit
    public List<MealTo> getFilteredByDateTime(LocalDate dateMin, LocalDate dateMax, LocalTime timeMin, LocalTime timeMax) {
        log.info("get filtered meals");
        return getWithExcess(service.getFiltered(authUserId(), meal -> {
            return isBetween(meal.getTime(),
                    timeMin == null ? LocalTime.MIN : timeMin,
                    timeMax == null ? LocalTime.MAX : timeMax)
                    && isBetween(meal.getDate(),
                    dateMin == null ? LocalDate.MIN : dateMin,
                    dateMax == null ? LocalDate.MAX : dateMax);
        }), authUserCaloriesPerDay());
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(id, authUserId());
    }

    public MealTo get(int id) {
        log.info("get meal {}", id);
        return createWithExcess(service.get(id, authUserId()), false);
    }

    public MealTo create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return createWithExcess(service.create(meal, authUserId()), false);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}