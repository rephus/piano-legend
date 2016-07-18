package database;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import java.util.List;

public class DbScores {
	
	public int getNumberScores(){
		
		int number = 0;
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT COUNT(*) FROM scores s");
	
			rs.next();
		    number = rs.getInt(1);
		}
		catch(Exception e){
			
			System.out.println("Error en DbScores.getNumberScores " +e);
		}
		return number;
	}
	public void  addScore(String song, String user,Date added,int score){
		
		try{
		
			Statement s = DbSettings.connection.createStatement(); 
			s.executeUpdate("INSERT INTO scores (song,user,added,score) " +
					"VALUES ('"+song+"','"+user+"','"+added+"',"+score+") ");
	
		}
		catch(Exception e){
			
			System.out.println("Error en DbScores.addscore " +e);
		}
		
	}
	public List<TableScores> getAllScores(){
		
		List<TableScores> data = new ArrayList<TableScores>();
		TableScores scores ;
	
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT * FROM scores s");
			while(rs.next())
			{	
				
				scores = new TableScores();
				
				scores.setSong(rs.getString (1)); //name
				scores.setUser(rs.getString (2)); //user
				scores.setScore(rs.getInt (3)); //multi
				scores.setAdded(rs.getDate (4)); //added
	
				data.add(scores);
			
			}
		
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return data;
		
	}
	 public List<TableScores> getSongScores(String song){
			
		 List<TableScores> data = new ArrayList<TableScores>();
			TableScores scores ;
		
			try{
				Statement s = DbSettings.connection.createStatement(); 
				ResultSet rs = s.executeQuery ("SELECT * FROM scores s");
				while(rs.next())
				{	
					
					if (rs.getString(1).equalsIgnoreCase(song)){
						scores = new TableScores();
						
						scores.setSong(rs.getString (1)); //name
						scores.setUser(rs.getString (2)); //user
						scores.setScore(rs.getInt (3)); //multi
						scores.setAdded(rs.getDate (4)); //added
			
						data.add(scores);
					}
				
				}
			
			}
			catch(Exception e){
				
				System.out.println("Error en DbSongs.getNumberSongs " +e);
			}
			return data;
	    
	    }
	 public int getNumberScores(String song){
			
			int i=0;
		
			try{
				Statement s = DbSettings.connection.createStatement(); 
				ResultSet rs = s.executeQuery ("SELECT * FROM scores s");
				while((rs.next())&&(i<5))
				{	
					
					if (rs.getString(1).equalsIgnoreCase(song)){
						i++;
					}
				
				}
			
			}
			catch(Exception e){
				
				System.out.println("Error en DbSongs.getNumberSongs " +e);
			}
			return i;
	    
	    }
	
	public TableScores[] getMaxScores(String song){
		
		TableScores[] data = new TableScores[5];
		TableScores scores ;
	
		try{
			Statement s = DbSettings.connection.createStatement(); 
			ResultSet rs = s.executeQuery ("SELECT * FROM scores s WHERE s.song = '"+song+"' ORDER BY score DESC LIMIT 5");
			int i = 0;
			while(rs.next())
			{	
				
				scores = new TableScores();
				
				scores.setSong(rs.getString (1)); //name
				scores.setUser(rs.getString (2)); //user
				scores.setScore(rs.getInt (3)); //multi
				scores.setAdded(rs.getDate (4)); //added
			
				data[i] = scores;
				i++;
			
			}
		
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return data;
		
	}


	
}
