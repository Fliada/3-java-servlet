package example;

import sun.nio.cs.UTF_8;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=windows-1251");

        String path = new String(request.getParameter("path").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        File my_file = new File(path);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                String.format("attachment;filename=%s", new String(my_file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)));

        // отправить файл в response
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }

        in.close();
        out.flush();
    }
}
