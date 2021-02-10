package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface MealRepository {
    AtomicInteger counter = new AtomicInteger(0);
    List<Meal> getList();
    Meal get(int id);
    void create(Meal meal);
    void update(Meal oldMeal, Meal newMeal);
    void delete(Meal meal);
    static int nextId(){
        return counter.incrementAndGet();
    }
}
