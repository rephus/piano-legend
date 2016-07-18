package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DbSettings {

	protected static Connection connection ;
	private boolean connected;
	
	
	
	public static String getString (String str){
		
		ResourceBundle rb = ResourceBundle.getBundle("data.dbconfig");
		
		try {
			return rb.getString(str);
		} catch (MissingResourceException e) {
			return '!' + str + '!';
		}
	}

	
	@SuppressWarnings("static-access")
	public DbSettings() {
		
			String ip = this.getString("ip");
			String port = this.getString("port");
			String user = this.getString("user");
			String pass = this.getString("password");
			String database = this.getString("db");
			String server = "";
			
			 try {

				Class.forName("com.mysql.jdbc.Driver");
			
				server =  "jdbc:mysql://"+ ip+ ":"+ port +"/"+database;
				
				System.out.println(server);
				 connection = DriverManager.getConnection(server, user, pass); 

				 connected = true;
			 } catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error establecer conexión base de datos "+server);
			}
	}


	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	public boolean isConnected() {
		return connected;
	}
	
	
}
