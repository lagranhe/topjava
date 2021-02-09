package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealRepositoryImpl implements MealRepository{

    public static final List<Meal> meals = new CopyOnWriteArrayList<>(
            Arrays.asList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410))
    );

    @Override
    public List<Meal> getList() {
        return meals;
    }

    @Override
    public Meal get(int id) {
        for (Meal meal : meals){
            if (meal.getId().get() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public boolean create(Meal meal) {
        meals.add(new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return true;
    }

    @Override
    public Meal update(Meal oldMeal, Meal newMeal) {
        for (Meal mealsFromList : meals){
            if (oldMeal.getId().get() == mealsFromList.getId().get()) {
                mealsFromList.setCalories(newMeal.getCalories());
                mealsFromList.setDateTime(newMeal.getDateTime());
                mealsFromList.setDescription(newMeal.getDescription());
                return mealsFromList;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Meal meal) {
        for (Meal mealsFromList : meals){
            if (meal.getId().get() == meal.getId().get()) {
                return meals.remove(mealsFromList);
            }
        }
        return false;
    }
}
