package db;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserHelperDb {

    public static boolean checkUser(int user_id){

        boolean response =false;

        try(Connection db = Database.openConnection()){
            PreparedStatement statement = db.prepareStatement("SELECT name FROM public.users WHERE user_id=? ");
            statement.setInt(1,user_id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) response = true;

            statement.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return response;
    }


    //for sql injection
    public static boolean checkUser(String user_id){

        boolean response =false;

        try(Connection db = Database.openConnection()){
            String sql = "SELECT name FROM public.users WHERE user_id="+user_id;
            System.out.println(sql);

            Statement statement = db.createStatement();

//            PreparedStatement statement = db.prepareStatement("SELECT name FROM public.users WHERE user_id=?");
//            statement.setString(1,user_id);

            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()) response = true;

            statement.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return response;
    }



}
