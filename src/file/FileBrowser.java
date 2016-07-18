package file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileBrowser {

	/**
	 * @param args
	 */

	protected static String folderSongs = "songs";
	protected static String fileOptions = "options.txt";
	public static String getFileOptions() {
		return fileOptions;
	}
	public static void setFileOptions(String fileOptions) {
		FileBrowser.fileOptions = fileOptions;
	}

	protected static String fileUser = "users.txt";
	protected static String fileSongs = "songs.txt";
	protected static String fileScores = "scores.txt";
	
	public static int getnumfiles(String folder) {
		// TODO Auto-generated method stub
		
		File dir = new File(folder);
		
		return dir.listFiles().length;
		
	}
	public static File[] getmidifiles(String folder){
		File dir = new File(folder);
		List<File> listfile = new ArrayList<File>();
		for (File f:dir.listFiles()){ //comprobamos que sea midi
			if (f.getName().substring((int) (f.getName().length()-4)).equalsIgnoreCase(".mid")){
				
				listfile.add(f);
			}
		}
		
		
		return (File[]) listfile.toArray(new File[0]);
	}
	public static void removeLineFromFile(String lineToRemove,String file,String separator) { 

		try { 
			File inFile = new File("data" + File.separator+ file); 
		
			if (!inFile.isFile()) { 
				System.out.println("Error FileBrowser.removeL : no se pudo eliminar "+file ); 
				return; 
			} 
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp"); 
		
			BufferedReader br = new BufferedReader(new FileReader("data" + File.separator+ file)); 
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile)); 
		
			String line = null; 
			String[] row ;

			while ((line = br.readLine()) != null)  { 
		
				row = line.split(separator); //partimos la linea en una fila con columnas
			    
				if (!row[0].equals(lineToRemove)) { 
			
					pw.println(line); 
					pw.flush(); 
					
				} 
			} 
			pw.close(); 
			br.close(); 
		
			if (!inFile.delete()) { 
				System.out.println("Error FileBrowser.removeL : no se pudo eliminar "+file ); 
				return; 
			} 
		
			if (!tempFile.renameTo(inFile)){ 
				System.out.println("Error FileBrowser.removeL : no se pudo renombrar "+file); 
		
			} 
		} catch (Exception e){
			System.out.println("Error FileBrowser.removeL "+file + " "+e);
		} 
	}
	public static String[] getmidinames(String folder){
		File dir = new File(folder);
		List<String> listfile = new ArrayList<String>();
		for (File f:dir.listFiles()){ //comprobamos que sea midi
			if (f.getName().substring((int) (f.getName().length()-4)).equalsIgnoreCase(".mid")){
				
				listfile.add(f.getName());
			}
		}
		
		
		return (String[]) listfile.toArray(new String[0]);
	}
	public static String getfile(String folder, int i) {
		// TODO Auto-generated method stub
		
		File dir = new File(folder);

		try{
			return dir.listFiles()[i].getName();
		}
		catch (java.lang.NullPointerException ex) {
			return null;
		}
	
	}
	public void deletefile(String folder, int i) {
		// TODO Auto-generated method stub
		
		File dir = new File(folder);
		
		File[] files = dir.listFiles();

		files[i].delete();
		
	}
    public static BufferedReader getfile(String folder,String filename) {
		// TODO Auto-generated method stub
            BufferedReader bf = null;
            try{
            	FileReader fr = new FileReader(folder + File.separator+ filename );
                 bf = new BufferedReader(fr);
 
            }
            catch(Exception e){
                
            }
            return bf;
            
	}
    public static boolean existfile(String folder, String filename){
 
    	return (FileBrowser.getfile(folder,filename) !=null);
    }
	public static void setFolderSongs(String folder) {
		FileBrowser.folderSongs = folder;
	}
	public static String getFolderSongs() {
		return folderSongs;
	}

}

