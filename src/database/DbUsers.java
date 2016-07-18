package database;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

public class DbUsers {
	
	public int getNumberUsers(){
		
		int number = 0;
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT COUNT(*) FROM users s");
	
			rs.next();
		    number = rs.getInt(1);
		}
		catch(Exception e){
			
			System.out.println("Error en DbUsers.getNumberUsers " +e);
		}
		return number;
	}
	public List<TableUsers> getAllUsers(){
		
		List<TableUsers> data = new ArrayList<TableUsers>();
		TableUsers users ;
	
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT * FROM users s");
			while(rs.next())
			{	
				
				users = new TableUsers();
				
				users.setName(rs.getString (1)); //name
				users.setPass(rs.getString (2)); //user
				users.setAdded(rs.getDate (3)); //multi
				users.setEmail(rs.getString (4)); //name
				users.setVerif(rs.getString (5)); //name
				
				data.add(users);
			
			}
		
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return data;
		
	}
	
	 public TableUsers getUser(String name){
		
		boolean finded = false;
	
		TableUsers users = null;
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT * FROM users s");
	
			while(rs.next() && !finded)
			{	
				
				if (rs.getString(1).equalsIgnoreCase(name)){
					finded = true;
					
					users = new TableUsers();
					users.setName(rs.getString (1)); //name
					users.setPass(rs.getString (2)); //user
					users.setAdded(rs.getDate (3)); //multi
					users.setEmail(rs.getString (4)); //name
					users.setVerif(rs.getString (5)); //name
					
				}
			}
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return users;
    
    }
	
}
