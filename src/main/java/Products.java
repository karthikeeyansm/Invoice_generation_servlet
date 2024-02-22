import db.ProductsHelperDb;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Products extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Product> products = ProductsHelperDb.getProducts();
        PrintWriter out = resp.getWriter();
        out.println("showing all product\n\n");

        out.println("id\tName\t\tDescription\t\t\tPrice\tStocks_available");
//
        for(Product product : products){
            out.println(product.toString());
        }

    }
}
