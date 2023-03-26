package example.servlets;

import example.AccountServiceHandler;
import example.accounts.AccountService;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

@WebServlet(name = "dirServlet", urlPatterns = {"/files"})
public class DirectoryServlet extends HttpServlet {
    private final AccountService accountService;

    public DirectoryServlet() {
        this.accountService = AccountServiceHandler.getAccountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String pathParam = "path";
        String timeAtt   = "time";
        String filesAtt  = "files";
        String fileRoot  = "root";

        if(accountService.getUserBySessionId(request.getSession().getId()) == null) {
            response.sendRedirect("/");
            return;
        }

        if(request.getAttribute(timeAtt) == null)
            request.setAttribute("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        if(request.getParameter(fileRoot) == null) {
            request.setAttribute(fileRoot, "C:\\Users\\Public\\filemanager\\" + accountService.getUserBySessionId(request.getSession().getId()).getLogin());
            File folder = new File(new String(request.getAttribute(fileRoot).toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            if(!folder.exists())
                folder.mkdirs();
        }

        if(request.getParameter(pathParam) == null) {
            request.setAttribute(pathParam, request.getAttribute(fileRoot).toString());
        }
        else {
            File root = new File(new String(request.getAttribute(fileRoot).toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            File folder = new File(new String(request.getParameter(pathParam).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

            if(root.getCanonicalPath().compareTo(folder.getCanonicalPath()) > 0)
                request.setAttribute(pathParam, request.getAttribute(fileRoot).toString());
            else
                request.setAttribute(pathParam, request.getParameter(pathParam));
        }

        File folder = new File(new String(request.getAttribute(pathParam).toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles == null) listOfFiles = new File[0];

        Arrays.sort(listOfFiles, (a, b) -> a.isDirectory()
                ? (b.isDirectory() ? a.getName().compareTo(b.getName()) : -1)
                : (b.isFile() ? a.getName().compareTo(b.getName()) : 1));

        request.setAttribute(filesAtt, listOfFiles);

        if(folder.getParent() != null || folder.getCanonicalPath().compareTo(new File(request.getAttribute(fileRoot).toString()).getCanonicalPath()) < 0)
            request.setAttribute("prev", folder.getParentFile().getCanonicalPath());
        else
            request.setAttribute("prev", new String(request.getAttribute(pathParam).toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        getServletContext().getRequestDispatcher("/pages/files.jsp")
                .forward(request, response);

        //response.sendRedirect(request.getContextPath() + "/pages/files.jsp");
    }
}