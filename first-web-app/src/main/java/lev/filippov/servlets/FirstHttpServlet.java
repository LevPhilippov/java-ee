package lev.filippov.servlets;

import lev.filippov.models.Product;
import lev.filippov.persistance.PersistanceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/catalog")
public class FirstHttpServlet extends HttpServlet {

    Logger logger;
    PersistanceBean persistanceBean;
    private StringBuilder path;

    @Override
    public void init() throws ServletException {
        super.init();
        logger = LoggerFactory.getLogger(this.getClass().getName());
        this.persistanceBean = (PersistanceBean) getServletContext().getAttribute("persistanceBean");
        this.path = new StringBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Привет мир!</h1>");
        resp.getWriter().printf("<p>Context path: %s </p>\n", req.getContextPath());
        resp.getWriter().printf("<p>:Servlet path %s </p>\n", req.getServletPath());
        resp.getWriter().printf("<p>Path info: %s </p>\n", req.getPathInfo());
        resp.getWriter().printf("<p>Query string: %s </p>\n", req.getQueryString());
        resp.getWriter().printf("<p>Param1: %s </p>\n", req.getParameter("param1"));
        resp.getWriter().printf("<p>Param2: %s </p>\n", req.getParameter("param2"));
        resp.getWriter().println("<table>");
            resp.getWriter().println("<thead>");
                resp.getWriter().println("<tr>");
                    resp.getWriter().println("<td>Id</td>");
                    resp.getWriter().println("<td>Title</td>");
                    resp.getWriter().println("<td>Price</td>");
                resp.getWriter().println("</tr>");
            resp.getWriter().println("</thead>");
            resp.getWriter().println("<tbody>");
                for (Product p: persistanceBean.getAll()) {
                    resp.getWriter().print("<tr>");
                    resp.getWriter().printf("<td>%d</td>\n",p.getId());
                    resp.getWriter().printf("<td><a href=\"%s/product?id=%d\">%s</a></td>\n", req.getContextPath() , p.getId(), p.getName());
//                    resp.getWriter().printf("<td><a href=\"%s:%d/%s/product?id=%d\">%s</a></td>\n", req.getServerName(), req.getServerPort(),req.getContextPath() , p.getId(), p.getName());
                    resp.getWriter().printf("<td>%s</td>\n",p.getPrice().toString());
                    resp.getWriter().print("</tr>");
                }
            resp.getWriter().println("</tbody>");
        resp.getWriter().println("</table>");

    }
}
