package Employee;

import java.sql.*;
import javax.swing.*;


public class EmployeeData {
	
	public static Connection ConnectDB()
	{
		try
		{
			
			 Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedata", "root", "password");
				JOptionPane.showMessageDialog(null, "Connection made");
				return connection;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Connection error");
			return null;
		}
	}

}
