package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMeals;
        if (repository.get(userId) == null){
            userMeals = new ConcurrentHashMap<>();
        } else {
            userMeals = repository.get(userId);
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        userMeals.put(meal.getId(), meal);
        repository.put(userId, userMeals);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        boolean bln = userMeals.remove(id) != null;
        repository.put(userId, userMeals);
        return bln;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return (repository.get(userId) == null) ? new ArrayList<>() : new ArrayList<>(repository.get(userId).values());
    }
}

