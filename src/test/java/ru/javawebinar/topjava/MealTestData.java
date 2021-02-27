package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public final static int MEAL1_ID = AbstractBaseEntity.START_SEQ + 2;
    public final static int ADMIN_MEAL1_ID = AbstractBaseEntity.START_SEQ + 9;
    public final static int NO_EXIST_ID = 10;

    public final static Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public final static Meal meal2 = new Meal(MEAL1_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public final static Meal meal3 = new Meal(MEAL1_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public final static Meal meal4 = new Meal(MEAL1_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public final static Meal meal5 = new Meal(MEAL1_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public final static Meal meal6 = new Meal(MEAL1_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public final static Meal meal7 = new Meal(MEAL1_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public final static Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2020, Month.JANUARY, 21, 20, 39), "Ужин", 550);
    public final static Meal adminMeal2 = new Meal(ADMIN_MEAL1_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 22, 10, 21), "Завтрак", 450);

    public static final List<Meal> meals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    public static Meal getNew(){
        return new Meal(null, LocalDateTime.of(2020, Month.MARCH, 30, 13, 0), "Новая еда", 1000);
    }
    public static Meal getUpdated(){
        return new Meal(MEAL1_ID, meal1.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
