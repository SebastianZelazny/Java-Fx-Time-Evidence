package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	
	public Connection connection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/EvidenceJobTime?useSSL=false","root","Seb@stian1.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Nie uda�o si� ustanowi� po��czenia");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.out.println("Nie uda�o si� ustanowi� po��czenia, blad sk�adni");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

}
