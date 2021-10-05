package lev.filippov.servlets;

import lev.filippov.exceptions.PageNotFoundException;
import lev.filippov.models.Product;
import lev.filippov.persistance.PersistanceBean;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/products/*"})
public class ProductInfoServlet extends HttpServlet {

    private PersistanceBean persistanceBean;
    private Pattern pattern = Pattern.compile("\\/+(\\d*)$");

    @Override
    public void init() throws ServletException {
        super.init();
        this.persistanceBean = (PersistanceBean) getServletContext().getAttribute("persistanceBean");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("/") || req.getPathInfo().isEmpty()) {
            resp.getWriter().println("<table>");
            resp.getWriter().println("<thead>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Id</td>");
            resp.getWriter().println("<td>Title</td>");
            resp.getWriter().println("<td>Price</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</thead>");
            resp.getWriter().println("<tbody>");
            for (Product p : persistanceBean.getAll()) {
                resp.getWriter().print("<tr>");
                resp.getWriter().printf("<td>%d</td>\n", p.getId());
                resp.getWriter().printf("<td><a href=\"%s/products/%d\">%s</a></td>\n", req.getContextPath(), p.getId(), p.getName());
                resp.getWriter().printf("<td>%s</td>\n", p.getPrice().toString());
                resp.getWriter().print("</tr>");
            }
            resp.getWriter().println("</tbody>");
            resp.getWriter().println("</table>");
        } else {
            Matcher matcher = pattern.matcher(req.getPathInfo());
            if (matcher.matches()) {
                try {
                    Long id = Long.parseLong(matcher.group(1));
                    Product o = persistanceBean.getProductById(id).orElseThrow(PageNotFoundException::new);
                        resp.getWriter().printf("<p>id: %d\n", o.getId());
                        resp.getWriter().printf("<p>Name: %s\n", o.getName());
                        resp.getWriter().printf("<p>Price: %s\n", o.getPrice().toString());
                        return;
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
    }
}
