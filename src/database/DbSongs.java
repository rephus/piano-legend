package database;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;



public class DbSongs {
	
	
	public int getNumberSongs(){
		
		int number = 0;
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT COUNT(*) FROM songs s");
	
			rs.next();
		    number = rs.getInt(1);
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return number;
	}
	public List<TableSongs> getAllSongs(){
		
		List<TableSongs> data = new ArrayList<TableSongs>();
		TableSongs songs ;
	
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT * FROM songs s");
			while(rs.next())
			{	
				
				songs = new TableSongs();
				
				songs.setName(rs.getString (1)); //name
				songs.setUser(rs.getString (2)); //user
				songs.setAdded(rs.getDate (3)); //added
				songs.setMulti(rs.getInt (4)); //multi
				
				data.add(songs);
			
			}
			
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return data;
		
	}
	public TableSongs getSongNumber(int number){
		
        int i = 1;
        
        TableSongs songs = null;
       
         try {
        	 
        	 Statement s = DbSettings.connection.createStatement(); 
 			ResultSet rs = s.executeQuery ("SELECT * FROM songs s");
         
 			while((rs.next()) && (i< number))
			{	  //   row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     i++;
			     
			 }
			
			if (i == number){
				
				System.out.println(i +"=="+ number);
				songs = new TableSongs();
				
				songs.setName(rs.getString (1)); //name
				songs.setUser(rs.getString (2)); //user
				songs.setAdded(rs.getDate (3)); //added
				songs.setMulti(rs.getInt (4)); //multi
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
         return songs;
    
	}

	
}
