package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import utils.JwtUtil;
import utils.RC;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(loginServlet.class);
    private final UserService userService= new UserService();
    private final JwtUtil jwtUtil= new JwtUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received POST request to /login");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RC result = userService.authenticate(username,password);
        if(result == RC.SUCCESS){
            logger.info("匹配到了用户名且密码正确,生成token");
            String token = jwtUtil.generateToken(username);
            logger.info("生成的token为: "+token);
            Cookie cookie = new Cookie("sso-token",token);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
//            //添加用户名到session
//            response.setAttribute("username",username);
            response.sendRedirect("home.jsp");
        }else{
            request.setAttribute("error", result.getMessage());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }
}
