package example.servlets;

import example.dbService.DBException;
import example.AccountServiceHandler;
import example.accounts.AccountService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "usersServlet", urlPatterns = {"/api/v1/users"})
public class UsersServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private final AccountService accountService;

    public UsersServlet() {
        this.accountService = AccountServiceHandler.getAccountService();
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if (login.equals("") || pass.equals("") || email.equals("")) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect("/pages/signup.html");
            return;
        }

        try {
            accountService.addNewUser(login, email, pass);
        }
        catch (DBException e) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect("/pages/signup.html");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);

        response.sendRedirect("/pages/index.html");
    }
}