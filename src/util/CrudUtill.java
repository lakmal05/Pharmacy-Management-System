package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtill {
    /*private static PreparedStatement execute(String sql, Object...params) throws SQLException, ClassNotFoundException { // varargs
      Connection connection= DBConnection.getInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(sql);
      for (int i = 0; i < params.length; i++) {
          statement.setObject((i+1),params[i]);
      }
      return statement;
  }

  public static ResultSet executeQuery(String sql, Object... params) throws SQLException, ClassNotFoundException {
      return execute(sql, params).executeQuery();
  }
  public static boolean executeUpdate(String sql, Object... params) throws SQLException, ClassNotFoundException {
      return execute(sql, params).executeUpdate()>0;
  }*/
    public static <T> T execute(String sql, Object...params) throws SQLException, ClassNotFoundException {
        PreparedStatement statement =
                DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject((i+1),params[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) statement.executeQuery();
        }else{
            return ((T)(Boolean)(statement.executeUpdate()>0));// boolean -> Boolean (Boxing) // type inference
        }
    }

}
