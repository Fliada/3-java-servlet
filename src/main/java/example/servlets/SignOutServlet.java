package example.servlets;

import example.AccountServiceHandler;
import example.accounts.AccountService;
import example.dbService.dataSets.UsersDataSet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignOutController", value = "/sign-out")
public class SignOutServlet extends HttpServlet {
    private final AccountService accountService;

    public SignOutServlet() {
        this.accountService = AccountServiceHandler.getAccountService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UsersDataSet profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.sendRedirect("/");
    }
}
