/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.TableUsers;


/**
 *
 * @author javier
 */
public class FileUser {
	
    public int getNumberUsers(){
		
		int number = 0;
		try{
                    BufferedReader bf = null;
    
                    bf  = FileBrowser.getfile("data",FileBrowser.fileUser) ;
                        
                    while ((bf.readLine())!=null) {
                         number++;
                          
                    }
		}
		catch(Exception e){
			
			System.out.println("Error en DbUsers.getnumberusers " +e);
		}
		return number;
	}
    public TableUsers[] getArrayUsers(){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        int max = 20;
        int i= 0;
        TableUsers[] data = new TableUsers[20];
        TableUsers users ;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileUser) ;
        
         try {
			while (((str = bf.readLine())!=null) && (i<max)){
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     users = new TableUsers();
			     
				users.setName(row[0]); //name
				//users.setUser(row[1]); //user
			//	songs.setAdded(row[2]); //added
			//	songs.setMulti(row[3]); //multi
			     
			     
			    data[i] = users;
			     i++;
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}   
         return data;
    
	}
	public List<TableUsers> getAllUsers(){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        
        List<TableUsers> data = new ArrayList<TableUsers>();
        TableUsers users ;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileUser) ;
        
         try {
			while ((str = bf.readLine())!=null) {
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     users = new TableUsers();
			     
				users.setName(row[0]); //name
				//users.setUser(row[1]); //user
			//	songs.setAdded(row[2]); //added
			//	songs.setMulti(row[3]); //multi
			     
			     
			    data.add(users);
			     
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}   
         return data;
    
}
	public TableUsers getUserName(String name){
		
		BufferedReader bf = null;
	    String[] row;
	    String str;
	    int i = 1;
	    
	    TableUsers users = null;
	    
	    bf  = FileBrowser.getfile("data",FileBrowser.fileUser) ;
	    
	     try {
			while ((str = bf.readLine())!=null)  {
			  //   row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     i++;
			     
				row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     if (row[1].equalsIgnoreCase(name)){
			  		users = new TableUsers();
			     
					users.setName(row[0]); 
			     }
	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}   
	     return users;
		
	}
	public TableUsers getUserNumber(int number){
		
	    BufferedReader bf = null;
	    String[] row;
	    String str;
	    int i = 1;
	    
	    TableUsers users = null;
	    
	    bf  = FileBrowser.getfile("data",FileBrowser.fileUser) ;
	    
	     try {
			while (((str = bf.readLine())!=null) && (i < number)) {
			  //   row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     i++;
			     
			 }
			
			if (i == number){
				
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     
			     users = new TableUsers();
			     
					users.setName(row[0]); //name
	//			songs.setAdded(row[2]); //added
				//	songs.setMulti(row[3]); //multi
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}   
	     return users;
	
	}
	public void deleteUser(String name){
		
		   FileBrowser.removeLineFromFile(name,FileBrowser.fileUser,"#");
		  
	}
	public void addUser(String name){
		
		FileWriter myFileWriter = null;

		try
		{
			
			myFileWriter = new FileWriter("data" + File.separator+ FileBrowser.fileUser,true);
			myFileWriter.write(name); 
			myFileWriter.write('\n'); 
			myFileWriter.close();
		}
		catch(Exception e){
			System.out.println("Error fileuser.adduser "+e);
		}
	}

	
}
