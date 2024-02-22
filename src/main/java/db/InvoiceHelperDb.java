package db;

import models.Invoice;
import models.InvoiceItem;
import models.ViewInvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class InvoiceHelperDb {
    public static void createInvoice(int user_id, HashMap<Integer,Integer> products){

        try(Connection db = Database.openConnection();){

            db.setAutoCommit(false);

            PreparedStatement statement = null;

            try{
                statement = db.prepareStatement("INSERT INTO public.invoice(user_id) values(?)",Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,user_id);
                statement.executeUpdate();

                ResultSet keySet = statement.getGeneratedKeys();
                keySet.next();
                int invoice_id = keySet.getInt(1);

                statement = db.prepareStatement(
                        "INSERT INTO public.invoice_items_list (invoice_id,product_id,rate,quantity,total_amt) " +
                            "SELECT ?,?,price AS rate,?,? * price AS total_amt " +
                            "FROM public.product WHERE product_id = ?");


                for(Integer product_id : products.keySet()){
                    statement.setInt(1,invoice_id);
                    statement.setInt(2,product_id);
                    statement.setInt(3,products.get(product_id));
                    statement.setInt(4,products.get(product_id));
                    statement.setInt(5,product_id);

                    statement.addBatch();

                }

                statement.executeBatch();

                statement = db.prepareStatement("UPDATE public.invoice " +
                                                    "SET total_quantity = (SELECT SUM(quantity) FROM public.invoice_items_list WHERE invoice_id = ?),"+
                                                    "total_amount = (SELECT SUM(total_amt) FROM public.invoice_items_list WHERE invoice_id = ?)"+
                                                    "WHERE invoice_id = ?");
                statement.setInt(1,invoice_id);
                statement.setInt(2,invoice_id);
                statement.setInt(3,invoice_id);

                statement.executeUpdate();
                statement.close();

                db.commit();

            }catch (Exception e){
                System.out.println(e);
                System.out.println("Transaction failed & starts to roll back");
                db.rollback();
            }finally {
                if(statement!=null) statement.close();
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static ArrayList<Invoice> getRecentInvoices(int user_id){

        ArrayList<Invoice> invoices = new ArrayList<Invoice>();

        try(Connection db = Database.openConnection();){
            PreparedStatement statement = db.prepareStatement("SELECT * FROM public.invoice WHERE user_id = ? ORDER BY \"createdAt\" desc LIMIT 10");
            statement.setInt(1,user_id);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                Invoice invoice = new Invoice();
                invoice.setInvoice_id(result.getInt("invoice_id"));
                invoice.setUser_id(result.getInt("user_id"));
                invoice.setTotal_amount(result.getFloat("total_amount"));
                invoice.setTotal_quantity(result.getInt("total_quantity"));
                invoice.setCreatedAt(result.getLong("createdAt"));
                invoice.setModifiedAt(result.getLong("modifiedAt"));

                invoices.add(invoice);
            }

            statement.close();

        }
        catch (Exception e){
            System.out.println(e);
        }

        return invoices;
    }


    public static ViewInvoice getInvoice(int user_id,int invoice_id){
        ViewInvoice viewInvoice = new ViewInvoice();

        try(Connection db = Database.openConnection()){

            PreparedStatement statement = db.prepareStatement("SELECT invoice_id,user_id,total_quantity,total_amount,\"createdAt\",\"modifiedAt\" FROM public.invoice WHERE invoice.invoice_id = ? AND invoice.user_id = ?");
            statement.setInt(1,invoice_id);
            statement.setInt(2,user_id);
            ResultSet result = statement.executeQuery();
            result.next();

            Invoice invoice = new Invoice();
            invoice.setInvoice_id(result.getInt("invoice_id"));
            invoice.setUser_id(result.getInt("user_id"));
            invoice.setTotal_amount(result.getFloat("total_amount"));
            invoice.setTotal_quantity(result.getInt("total_quantity"));
            invoice.setCreatedAt(result.getLong("createdAt"));
            invoice.setModifiedAt(result.getLong("modifiedAt"));

            viewInvoice.setInvoice(invoice);

            statement = db.prepareStatement("select invoice_id,product.product_id,name,rate,quantity,total_amt,invoice_items_list.\"createdAt\",invoice_items_list.\"modifiedAt\" from invoice_items_list inner join public.product " +
                                                                    "on invoice_items_list.product_id = product.product_id " +
                                                                    "where invoice_items_list.invoice_id = ?");
            statement.setInt(1,invoice_id);
            result = statement.executeQuery();


            while(result.next()){

                InvoiceItem item =new InvoiceItem();
                item.setInvoice_id(result.getInt("invoice_id"));
                item.setProduct_id(result.getInt("product_id"));
                item.setProduct_name(result.getString("name"));
                item.setRate(result.getFloat("rate"));
                item.setQuantity(result.getInt("quantity"));
                item.setTotal_amount(result.getFloat("total_amt"));
                item.setCreatedAt(result.getLong("createdAt"));
                item.setModifiedAt(result.getLong("modifiedAt"));

                viewInvoice.addToInvoiceList(item);
            }

            statement.close();


        }catch (Exception e){
            System.out.println(e);
        }

        return viewInvoice;
    }




}
