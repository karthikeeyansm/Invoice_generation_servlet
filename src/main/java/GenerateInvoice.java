import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.InvoiceHelperDb;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GenerateInvoice extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader bodyReader = req.getReader();

//        StringBuilder data = new StringBuilder();
////        String data,line;
//        int character;
//        while((character = bodyReader.read()) != -1 ){
//            data.append((char) character);
//        }

//        JsonObject json = JsonParser.parseString(data.toString()).getAsJsonObject();

        JsonObject json = JsonParser.parseReader(bodyReader).getAsJsonObject();

        int user_id = json.get("user_id").getAsInt();
        JsonObject products_json = json.get("products").getAsJsonObject();
        Set<String> products_id = products_json.keySet();
        HashMap<Integer,Integer> products = new HashMap<Integer,Integer>();

        for(String product : products_id){
            products.put(Integer.parseInt(product),products_json.get(product).getAsInt());
        }

        int invoice_id = InvoiceHelperDb.createInvoice(user_id,products);

        PrintWriter out = resp.getWriter();

        out.println("invoice generated successfully with id as "+invoice_id);

    }

}
