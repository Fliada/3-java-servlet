package example.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

@WebServlet(name = "dirServlet", urlPatterns = {"/files"})
public class DirectoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String pathParam = "path";
        String timeAtt   = "time";
        String filesAtt  = "files";

        if(request.getAttribute(timeAtt) == null)
            request.setAttribute("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        if(request.getParameter(pathParam) == null) {
            request.setAttribute(pathParam, Paths.get("")
                    .toAbsolutePath()
                    .toString());
        }
        else
            request.setAttribute(pathParam, request.getParameter(pathParam));

        File folder = new File(request.getAttribute(pathParam).toString());
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles == null) listOfFiles = new File[0];

        Arrays.sort(listOfFiles, (a, b) -> a.isDirectory()
                ? (b.isDirectory() ? a.getName().compareTo(b.getName()) : -1)
                : (b.isFile() ? a.getName().compareTo(b.getName()) : 1));

        request.setAttribute(filesAtt, listOfFiles);

        if(folder.getParent() != null)
            request.setAttribute("prev", folder.getParentFile().getCanonicalPath());

        getServletContext().getRequestDispatcher("/pages/files.jsp")
                .forward(request, response);

        //response.sendRedirect(request.getContextPath() + "/pages/files.jsp");
    }
}