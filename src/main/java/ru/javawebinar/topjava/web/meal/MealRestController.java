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
    public List<MealTo> getFilteredByDateTime(String dateFrom, String dateTo, String timeFrom, String timeTo) {
        log.info("get filtered meals");
        return getWithExcess(service.getFiltered(authUserId(), meal -> {
            return isBetween(meal.getDate(),
                    (dateFrom == null || dateFrom == "") ? LocalDate.MIN : LocalDate.parse(dateFrom),
                    (dateTo == null || dateTo == "") ? LocalDate.MAX : LocalDate.parse(dateTo))
                    && isBetween(meal.getTime(),
                    (timeFrom == null || timeFrom == "") ? LocalTime.MIN : LocalTime.parse(timeFrom),
                    (timeTo == null || timeTo == "") ? LocalTime.MAX : LocalTime.parse(timeTo));
        }), authUserCaloriesPerDay());
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get meal {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}