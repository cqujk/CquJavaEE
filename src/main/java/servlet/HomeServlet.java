package servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import utils.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HomeServlet.class);
    private UserService userService;
    private JwtUtil jwtUtil;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        jwtUtil = new JwtUtil();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从Cookie中获取令牌
        String token = null;
        Cookie[] cookies = req.getCookies();
        if(cookies !=null){
            for(Cookie cookie:cookies){
                if("sso-token".equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        //验证cookie当中的令牌
        if(token!=null&& jwtUtil.isTokenValid(token)){
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }else{
            resp.sendRedirect("index.jsp?error=Invalid token");
        }
    }
}
