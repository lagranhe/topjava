package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String user = request.getParameter("user");

        switch (user == null ? "all" : user) {
            case "all" :
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
            case "logUser" :
                SecurityUtil.setAuthUserId(InMemoryUserRepository.USER_ID);
                request.getRequestDispatcher("meals?user=1").forward(request, response);
                break;
            case "logAdmin":
                SecurityUtil.setAuthUserId(InMemoryUserRepository.ADMIN_ID);
                request.getRequestDispatcher("meals?user=2").forward(request, response);
        }
    }
}
