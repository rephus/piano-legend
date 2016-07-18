/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import database.TableScores;


/**
 *
 * @author javier
 */
public class FileScores {
    public int getNumberScores(){
		
		int number = 0;
		try{
                    BufferedReader bf = null;

                    bf  = FileBrowser.getfile("data","scores.txt") ;
                        
                    while ((bf.readLine())!=null) {
                         number++;
                          
                    }
		}
		catch(Exception e){
			
			System.out.println("Error en DbSongs.getNumberSongs " +e);
		}
		return number;
	}
    public List<TableScores> getAllScores(){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        
        List<TableScores>  data = new ArrayList<TableScores> ();
        TableScores scores ;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileScores) ;
        
         try {
			while ((str = bf.readLine())!=null) {
			     row = str.split("#"); //partimos la linea en una fila con columnas
			     
			     scores= new TableScores();
			     
		    	  	scores.setSong(row[0]); //name
				     scores.setUser(row[1]); //user
				  
				     scores.setScore(Integer.parseInt(row[2])); //score
	
			    data.add(scores);
			     
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
         return data;
    
    }

    public List<TableScores>  getSongScores(String song){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        
        List<TableScores>  data = new ArrayList<TableScores> ();
        TableScores scores ;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileScores) ;
        
         try {
			while ((str = bf.readLine())!=null) {
			     row = str.split("#"); //partimos la linea en una fila con columnas
		
			     if (row[0].equalsIgnoreCase(song)){
			    	 scores= new TableScores();
			    	 
			    	  	scores.setSong(row[0]); //name
					     scores.setUser(row[1]); //user
					  
					     scores.setScore(Integer.parseInt(row[2])); //score
				
					    data.add(scores);
			     }
			   
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error fileScores.getSongScores " + e);
		}   
         return data;
    
    }
    
    public int getNumberScores(String song){
		
        BufferedReader bf = null;
        String[] row;
        String str;
        
       int i = 0;
        
        bf  = FileBrowser.getfile("data",FileBrowser.fileScores) ;
        
         try {
			while (((str = bf.readLine())!=null) && (i<5)) {
			     row = str.split("#"); //partimos la linea en una fila con columnas
		
			     if (row[0].equalsIgnoreCase(song)){
			    	
			    	 i++;
			     }
			   
			 }
		} 
         catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error fileScores.getSongScores " + e);
		}   
         return i;
    
    }
    
    public TableScores[] getMaxScores(String song){
		
        List<TableScores>  data = this.getSongScores(song);
        
        TableScores[] scores = (TableScores[]) data.toArray(new TableScores[data.size()]);

        this.ordenarQuicksort(scores, 0, scores.length-1);

        return scores;
    
    }
    void ordenarQuicksort(TableScores[] vector, int primero, int ultimo){
    	int i=primero, j=ultimo;
    	TableScores pivote=vector[(primero + ultimo) / 2];
    	TableScores auxiliar;
 
    	do{
    		while(vector[i].getScore()>pivote.getScore()) i++;    //invertir inecuaciones para obtener orden inverso		
    		while(vector[j].getScore()<pivote.getScore()) j--;
 
    		if (i<=j){
    			auxiliar=vector[j];
    			vector[j]=vector[i];
    			vector[i]=auxiliar;
    			i++;
    			j--;
    		}
 
    	} while (i<=j);
 
    	if(primero<j) ordenarQuicksort(vector,primero, j);
    	if(ultimo>i) ordenarQuicksort(vector,i, ultimo);
    }
	public void  addScore(String song, String user,Date added,int score){
		
		FileWriter myFileWriter = null;
		String stringscore =  String.valueOf(score);
		try
		{
			//campanitas#rephus2#300#12-2-2010
			myFileWriter = new FileWriter("data" + File.separator+ FileBrowser.fileScores,true);
			myFileWriter.write(song); 				myFileWriter.write("#"); 
			myFileWriter.write(user); 				myFileWriter.write("#"); 
			myFileWriter.write(stringscore); 		myFileWriter.write("#");
			myFileWriter.write(added.toString()); 	
				
			myFileWriter.write('\n'); 
			myFileWriter.close();
		}
		catch(Exception e){
			System.out.println("Error fileuser.adduser "+e);
		}
	}
    
}
