import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
public class BaseDatos {

	static final String DATABASE_URL = "jdbc:mysql://localhost:3306/usuarios";
	static Connection connection;
	
    public Connection ConexionMYSQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           connection = DriverManager.getConnection(DATABASE_URL,"root", "");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return connection;
        }
    }
	public static boolean AutenticaUsuario(String login,String pass)throws Exception{
		Statement statement = null;
		ResultSet resultSet = null;
		String consulta="Select * from usuarios " + "where nombre = '" + login + "' and password = '" + pass + "'";
		try{
			  Class.forName("com.mysql.jdbc.Driver");
			  connection = DriverManager.getConnection(DATABASE_URL, "root", "");
			  statement=connection.createStatement();
			  resultSet=statement.executeQuery(consulta);//revisa si el nombre y la contraseña estan en la DB
			  
			  boolean NRegistros=resultSet.first();  
			 if (NRegistros) return true; else return false;//verdadero si esta el registro
			  
		}catch(ClassNotFoundException cnfe){
			  cnfe.printStackTrace();
			return false;
		}  catch (SQLException se) {
			se.printStackTrace();
            return false;
        }
        finally { 
           resultSet.close();
           connection.close(); 
           } 
    }
}
