package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 9, 0), "Админ завтрак", 300);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID),DINNER, LUNCH, newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void createDublicateDate() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ завтрак", 300);
        Meal created = service.create(newMeal, ADMIN_ID);
    }

    @Test
    public void createDublicateDateDifferentId() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Юзер завтрак", 300);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal);
    }

    @Test
    public void delete() {
        service.delete(LUNCH_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), DINNER);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(LUNCH_ID, USER_ID);
    }

    @Test
    public void get() {
        Meal actual = service.get(LUNCH_ID, ADMIN_ID);
        assertMatch(actual, LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        service.get(LUNCH_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, Month.JUNE, 2);
        LocalDate endDate = LocalDate.of(2015, Month.JUNE, 3);
        List<Meal> meals = service.getBetweenDates(startDate, endDate, ADMIN_ID);
        assertMatch(meals, DINNER);
    }

    @Test
    public void getBetweenNullDates() {
        List<Meal> meals = service.getBetweenDates(null, null, ADMIN_ID);
        assertMatch(meals, DINNER, LUNCH);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.JUNE, 1, 15, 0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.JUNE, 2, 22,0,0);
        List<Meal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, ADMIN_ID);
        assertMatch(meals, DINNER);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, DINNER, LUNCH);
    }

    @Test
    public void update() {
        Meal updated = new Meal(LUNCH);
        updated.setDescription("Админ обед");
        updated.setCalories(900);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(LUNCH_ID, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        service.update(LUNCH, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(new Meal(1, LocalDateTime.now(), "Левая еда", 3000), USER_ID);
    }

}