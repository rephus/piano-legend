package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.TableSongs;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author javier
 */
public class FileSongs {
    
    public int getNumberSongs(){
		
		int number = 0;
		try{
                    BufferedReader bf = null;

                    bf  = FileBrowser.getfile("data",FileBrowser.fileSongs) ;
                    
                    while ((bf.readLine())!=null) {
                         number++;
                          
                    }
		}
		catch(Exception e){
			
			System.out.println("Error en Filesongs.getNumberSongs " +e);
		}
		return number;
	}
    
	public List<TableSongs> getAllSongs(){
		
	        BufferedReader bf = null;
	        String[] row;
	        String str;
	        
	        List<TableSongs> data = new ArrayList<TableSongs>();
	        TableSongs songs ;
	        
	        bf  = FileBrowser.getfile("data",FileBrowser.fileSongs) ;
	        
	         try {
				while ((str = bf.readLine())!=null) {
				     row = str.split("#"); //partimos la linea en una fila con columnas
				     
				     songs = new TableSongs();
				     
					songs.setName(row[0]); //name
					songs.setUser(row[1]); //user
				
				    data.add(songs);
				     
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
	         return data;
	    
	}

	public TableSongs getSong(String name){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        
        TableSongs song = null;
        boolean encontrado = false;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileSongs) ;
        
         try {
			while (((str = bf.readLine())!=null) && (!encontrado)){
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     if (row[0].equalsIgnoreCase(name)){
			    	 song = new TableSongs();
			    	 
			    	 song.setName(row[0]); //name
					song.setUser(row[1]); //user
					encontrado = true;
			     }
			
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
         return song;
    
}

	public TableSongs getSongNumber(int number){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        int i = 1;
        
        TableSongs songs = null;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileSongs) ;
        
         try {
			while (((str = bf.readLine())!=null) && (i < number)) {
			  //   row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     i++;
			     
			 }
			
			if (i == number){
				
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     
			     songs = new TableSongs();
			     
				songs.setName(row[0]); //name
				songs.setUser(row[1]); //user
//				songs.setAdded(row[2]); //added
				//	songs.setMulti(row[3]); //multi
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
         return songs;
    
	}
	public void addSong(String name){
		
		FileWriter myFileWriter = null;

		try
		{
			
			myFileWriter = new FileWriter("data" + File.separator+ FileBrowser.fileSongs,true);
			myFileWriter.write(name); myFileWriter.write("#");
			myFileWriter.write("Anonymous"); 
			myFileWriter.write('\n'); 
			myFileWriter.close();
		}
		catch(Exception e){
			System.out.println("Error filesong.addsong "+e);
		}
	}
	public void deleteSong(String name){
		FileBrowser.removeLineFromFile(name,FileBrowser.fileSongs,"#");
	}

}
