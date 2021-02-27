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
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app1.xml",
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
    public void get() {
        Meal actual = service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        MealTestData.assertMatch(actual, MealTestData.meal1);
    }

    @Test
    public void delete() {
        service.delete(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFound(){
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NO_EXIST_ID, UserTestData.USER_ID));
    }

    @Test
    public void deleteNotOwn(){
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        MealTestData.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), UserTestData.USER_ID),
                MealTestData.meal3, MealTestData.meal2, MealTestData.meal1);
    }

    @Test
    public void getBetweenWithNullDates(){
        MealTestData.assertMatch(service.getBetweenInclusive(null, null, UserTestData.USER_ID), MealTestData.meals);
    }

    @Test
    public void getAll() {
        MealTestData.assertMatch(service.getAll(UserTestData.USER_ID), MealTestData.meals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), UserTestData.USER_ID), updated);
    }

    @Test
    public void updateNotOwn(){
        assertThrows(NotFoundException.class, () -> service.update(MealTestData.meal1, UserTestData.ADMIN_ID));
        MealTestData.assertMatch(service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID), MealTestData.meal1);
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), UserTestData.USER_ID);
        int newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate(){
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null,
                        MealTestData.meal1.getDateTime(),
                        "duplicate",
                        400), UserTestData.USER_ID));
    }
}