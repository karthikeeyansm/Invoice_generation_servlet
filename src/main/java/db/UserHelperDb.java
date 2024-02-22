package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
