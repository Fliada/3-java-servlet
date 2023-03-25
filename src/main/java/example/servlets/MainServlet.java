package example.servlets;

import example.accounts.AccountService;
import example.accounts.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = {"/main"})
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountService accountService = new AccountService();

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        ServletContext context = getServletContext();
        context.addServlet("/api/v1/users", new UsersServlet(accountService));
        context.addServlet("/api/v1/sessions", new SessionsServlet(accountService));

        getServletContext().getRequestDispatcher("/pages/index.html")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
