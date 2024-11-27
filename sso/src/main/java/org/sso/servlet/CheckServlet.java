package org.sso.servlet;

import org.sso.utils.TokenUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkToken")
public class CheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("sso check token has been touch!!");
        String token = request.getParameter("token");
        System.out.println("sso check token:" + token);
        boolean result = false;
        try {
            result = TokenUtils.validateToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.getWriter().write(result ? "true" : "false");
    }
}
