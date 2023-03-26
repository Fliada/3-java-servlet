package example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");

        String requestEnc="ISO-8859-1";

        String path = new String(request.getParameter("path").getBytes(requestEnc), StandardCharsets.UTF_8);
        File my_file = new File(path);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                new String(String.format("attachment;filename=%s", my_file.getName()).getBytes(requestEnc), StandardCharsets.UTF_8));

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
