package db;

import models.Product;

import javax.swing.text.DateFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProductsHelperDb {

    public static ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<Product>();

        try(Connection db = Database.openConnection()){

            Statement statement = db.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM public.product");

            while(result.next()){
                Product p = new Product();
                p.setProduct_id(result.getInt("product_id"));
                p.setName(result.getString("name"));
                p.setDescription(result.getString("description"));
                p.setPrice(result.getFloat("price"));
                p.setCreatedAt(result.getLong("createdAt"));
                p.setModifiedAt(result.getLong("modifiedAt"));

                products.add(p);
            }

            statement.close();
            result.close();

        }catch (Exception e){
            System.out.println(e);
        }


        return products;
    }

}
