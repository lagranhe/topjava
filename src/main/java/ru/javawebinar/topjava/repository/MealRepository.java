package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getList();
    Meal get(int id);
    boolean create(Meal meal);
    Meal update(Meal oldMeal, Meal newMeal);
    boolean delete(Meal meal);
}
