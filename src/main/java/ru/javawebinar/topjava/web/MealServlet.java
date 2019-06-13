package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOMapImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = "/meals", name = "mealServlet")
public class MealServlet extends HttpServlet {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final int CALLORIES = 2000;
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDAO dao = new MealDAOMapImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (Objects.equals(action, "delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.delete(id);
            resp.sendRedirect("meals");
            log.debug("forward to meals");

        } else if (Objects.equals(action, "edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = dao.getById(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("edit.jsp").forward(req, resp);

        } else if (Objects.equals(action, "add")) {
            req.getRequestDispatcher("edit.jsp").forward(req, resp);

        } else {
            req.setAttribute("meals", MealsUtil.getFilteredWithExcess(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALLORIES));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDate.parse(req.getParameter("date")).atTime(LocalTime.parse(req.getParameter("time")));

        Meal meal = new Meal(dateTime, description, calories, 0);

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }
        resp.sendRedirect("meals");
    }
}
