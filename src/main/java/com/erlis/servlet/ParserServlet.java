package com.erlis.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/parser")
public class ParserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String stringConverter = Util.readStream(req.getInputStream());
        String parsedString = parser(stringConverter);
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(parsedString);
    }

    private String parser(String stringConverter) {

        StringBuilder json = new StringBuilder();
        json.append("{");
        stringConverter = stringConverter.replace(",", ":")
                .replace("\"", "").trim()
                .replace("{", "").replace("}", "").trim();
        String[] values = stringConverter.split(":");
        for (int i = 0; i < values.length; i++) {
            StringBuffer sb = new StringBuffer();
            json.append(sb.append("\"").append(values[i]).append("\"").reverse());
            if (i % 2 == 0) {
                json.append(": ");
            } else if (i == values.length - 1) {
                break;
            } else {
                json.append(", ");
            }
        }
        json.append("}");
        return json.toString();
    }
}
