import com.google.gson.Gson;
import db.InvoiceHelperDb;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.ViewInvoice;

import java.io.IOException;
import java.io.PrintWriter;

public class SpecificInvoice extends HttpServlet {

    Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        int invoice_id = Integer.parseInt(req.getParameter("invoice_id"));

        ViewInvoice viewInvoice = InvoiceHelperDb.getInvoice(user_id,invoice_id);

        resp.setContentType("application/json");

        PrintWriter out =resp.getWriter();
        out.println(gson.toJson(viewInvoice));


    }
}
