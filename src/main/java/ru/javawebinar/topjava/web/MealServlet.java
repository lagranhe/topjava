package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MealServlet extends HttpServlet {
    public final static Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealRepository mealRepository = MealsUtil.mealRepository;
    private static final String EDIT_MEAL = "/editMeal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String action = request.getParameter("action");
        String forward = "";
        if (action == null) {
            forward = LIST_MEAL;
        } else if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealRepository.delete(mealRepository.get(mealId));
            response.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("add")){
            forward = EDIT_MEAL;
        } else if (action.equalsIgnoreCase("update")){
            forward = EDIT_MEAL;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            request.setAttribute("meal", mealRepository.get(mealId));
        }
        int maxCalories = 2000;
        final List<MealTo> mealsToList = MealsUtil.filteredByStreams(mealRepository.getList(), LocalTime.MIN, LocalTime.MAX, maxCalories);
        mealsToList.sort(Comparator.comparing(MealTo::getDateTime));
        request.setAttribute("meals", mealsToList);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dataTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("id");
        Meal newMeal = new Meal(dateTime, description, calories);
        if (mealId.equals("")){
            mealRepository.create(newMeal);
        } else {
            int id = Integer.parseInt(mealId);
            mealRepository.update(mealRepository.get(id), newMeal);
        }
        response.sendRedirect("meals");
    }
}
