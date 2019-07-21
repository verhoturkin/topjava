package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles("datajpa")
public class DatajpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() throws Exception{
        User actual = service.get(USER_ID);
        assertMatch(actual, USER);
        assertMatch(actual.getMeals(), MEALS);
    }
}
