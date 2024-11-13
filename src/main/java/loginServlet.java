import Dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import utils.RC;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(loginServlet.class);
    private UserService userService;
    @Override
    public void init(){
        userService = new UserService();
    }
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
//            String token = userService.generateToken(username);
//            Cookie cookie = new Cookie("sso-token", token);
//            cookie.setPath("/");
//            cookie.setMaxAge(3600);
//            response.addCookie(cookie);
            response.sendRedirect("home.jsp");
        }else{
            request.setAttribute("error", result.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
