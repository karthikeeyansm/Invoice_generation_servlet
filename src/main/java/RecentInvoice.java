import db.InvoiceHelperDb;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RecentInvoice extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = Integer.parseInt(req.getParameter("user_id"));

        ArrayList<models.Invoice> invoices = InvoiceHelperDb.getRecentInvoices(user_id);

        PrintWriter out = resp.getWriter();

        out.println("Invoice_id\tTotal quantity\tTotal amount\tGenerated");

        for(models.Invoice invoice : invoices){
            out.println(invoice.toString());
        }
    }
}
