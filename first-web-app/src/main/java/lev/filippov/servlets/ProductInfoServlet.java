package lev.filippov.servlets;

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

@WebServlet(urlPatterns = {"/product"})
public class ProductInfoServlet extends HttpServlet {

    private PersistanceBean persistanceBean;

    @Override
    public void init() throws ServletException {
        super.init();
        this.persistanceBean = (PersistanceBean) getServletContext().getAttribute("persistanceBean");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Product> o = persistanceBean.getProductById(id);
        if(o.isPresent()) {
            resp.getWriter().printf("<p>id: %d\n", o.get().getId());
            resp.getWriter().printf("<p>Name: %s\n", o.get().getName());
            resp.getWriter().printf("<p>Price: %s\n", o.get().getPrice().toString());
        }

    }
}
