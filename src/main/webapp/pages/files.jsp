<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.nio.charset.StandardCharsets" %>

<!doctype html>
<html lang="ru">
<head>
    <%@ page contentType="text/html; charset=UTF-8"  %>
    <link rel="stylesheet" type="text/css" href="/pages/css/files.css">
    <title>First JSP</title>
</head>
<body>
<font face="Montserrat, Consolas, Arial">

<%= request.getAttribute("time") %>

    <a href="/api/v1/sessions" methods="DELETE"><button class="element-grid__delite-button" style="float: right;" type="button">
        Sign out</button>
    </a>

<h1><%= new String(request.getAttribute("path").toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) %></h1>

    <a href="${pageContext.request.contextPath}/files?path=<%=  request.getAttribute("prev") %>"><img src="static/images/redir.png" alt="folder" style="width: 64px; height: 64px;"></a>

<hr/>
    <table id="messages" border="1">
        <thead>
        <tr>
            <th></th>
            <th>Файл</th>
            <th>Размер</th>
            <th>Дата</th>
        </tr>
        </thead>

        <tbody>
        <%
            DateFormat format = new SimpleDateFormat("M/dd/yy hh:mm:ss a");
            File[] list = (File[])request.getAttribute("files");

            for(File file : list) {
        %>
        <tr>
            <td><img src="<%=file.isDirectory() ? "static/images/folder.png" : "static/images/file.png" %>" alt="f" style="width: 32px; height: 32px;"></td>
            <td>
                <% if(file.isFile()) { %>
                <a href="${pageContext.request.contextPath}/download?path=<%= file.getAbsolutePath() %>">
                    <%= file.getName() %>
                </a>
                    <% } else { %>
                <a href="${pageContext.request.contextPath}/files?path=<%= file.getAbsolutePath() %>">
                    <%= file.getName() + "\\" %>
                </a>
                    <% } %>
            </td>
            <td align="right"><%=file.isFile() ? file.length() + " B" : "" %></td>
            <td align="right"><%=format.format(file.lastModified())%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</font>
</body>
</html>