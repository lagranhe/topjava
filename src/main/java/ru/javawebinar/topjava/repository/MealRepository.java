package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getMealList();
    Meal getMeal(int id);
    boolean createMeal(Meal meal);
    Meal updateMeal(Meal meal);
    boolean delete(Meal meal);
}
