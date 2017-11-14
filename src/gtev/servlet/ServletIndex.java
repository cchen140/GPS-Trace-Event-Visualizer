package gtev.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cy on 11/14/2017.
 */
public class ServletIndex extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //request.setAttribute("articleId", "1212");
        request.getRequestDispatcher("index.ftl").forward(request, response);
    }
}
