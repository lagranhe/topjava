package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController{

    private final MealService service;
    private final int maxCalories = 2000;

    @Autowired
    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getAllMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), maxCalories));
        return "meals";
    }

    @GetMapping("/getFiltered")
    public String getFilteredMeals(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, maxCalories, startTime, endTime));
        return "meals";
    }

    @GetMapping("/create")
    public String createMealGet(Model model){
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMealGet(HttpServletRequest request, Model model){
        model.addAttribute("action", "update");
        model.addAttribute("meal", service.get(getId(request), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteMealPost(HttpServletRequest request){
        int mealId = Integer.parseInt(request.getParameter("id"));
        service.delete(mealId, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String createMealPost(HttpServletRequest request){
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        service.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String updateMealPost(HttpServletRequest request){
        Meal meal = service.get(getId(request), SecurityUtil.authUserId());
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        service.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
