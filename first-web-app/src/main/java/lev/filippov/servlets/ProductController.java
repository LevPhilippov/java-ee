package lev.filippov.servlets;

import lev.filippov.exceptions.PageNotFoundException;
import lev.filippov.models.Product;
import lev.filippov.persistance.PersistanceBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/products/*")
public class ProductController extends HttpServlet {

    private PersistanceBean persistanceBean;
    private Pattern pattern = Pattern.compile("\\/+(\\d*)$");
    private Pattern patternNew = Pattern.compile("\\/+(new)$");

    @Override
    public void init() throws ServletException {
        super.init();
        this.persistanceBean = (PersistanceBean) getServletContext().getAttribute("persistanceBean");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            req.setAttribute("products", persistanceBean.getAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
        } else {
            Matcher matcher = pattern.matcher(req.getPathInfo());
            if (matcher.matches()) {
                Long id=null;
                try {
                    id = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                Product o = persistanceBean.getProductById(id).orElseThrow(PageNotFoundException::new);
                req.setAttribute("product", o);
                getServletContext().getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req, resp);
                return;
            }
            if (patternNew.matcher(req.getPathInfo()).matches()) {
//                req.setAttribute("product", o);
                getServletContext().getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req,resp);
            }
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            Product p = new Product(null, req.getParameter("name"),
                    new BigDecimal(req.getParameter("price")));
            if (!req.getParameter("id").equals("")) p.setId(Long.parseLong(req.getParameter("id")));
            persistanceBean.save(p);
        resp.sendRedirect(getServletContext().getContextPath() + "/products");
        }
    }
}
