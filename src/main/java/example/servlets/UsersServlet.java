package example.servlets;

import com.google.gson.Gson;
import example.AccountServiceHandler;
import example.accounts.AccountService;
import example.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "usersServlet", urlPatterns = {"/api/v1/users"})
public class UsersServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;

    public UsersServlet() {
        this.accountService = AccountServiceHandler.getAccountService();
    }

    //get public user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if (login == null || pass == null || email == null || accountService.getUserByLogin(login) != null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect("/pages/signup.html");
            return;
        }

        accountService.addNewUser(new UserProfile(login, email, pass));

        response.setStatus(HttpServletResponse.SC_OK);

        response.sendRedirect("/pages/index.html");
    }

    //change profile
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
    }

    //unregister
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
    }
}