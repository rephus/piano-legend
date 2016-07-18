
import java.io.File;
import java.util.Iterator;
import java.util.List;

import database.DbScores;
import database.DbSongs;
import database.TableScores;
import database.TableSongs;

import file.FileBrowser;
import file.FileScores;
import file.FileSongs;

import sdljava.video.SDLSurface;

public class SelectSong {

	//---------------------------------------------------------    	  	
	//DECLARAR VARIABLES
	//---------------------------------------------------------

	private TableScores[] scores;
	private boolean end ; //determina cuando salir del programa

	private String song1;
	private String song2;
	private String song3;
	
	private FileScores filescores;
	private FileSongs filesongs;
	private TableSongs songs;
	
	private DbSongs dbsongs;
	private DbScores dbscores;

	private int maxsongs;
	
	private Text textArabica10;

	private Text textCalculator20;
	private Text textCalculator40;
	
	private CFrame frameBackgroundSelectSong;
	private CSprite spriteBackgroundSelectSong;
	
	private Midi[] midi;
	
	private  String path;
    
	private  int maxtracks;
    
	private   int numfile;
    
	private   MidiPlayer player;

//		private   String [] scores = new String[10];

	private int maxscores;
	
	private Event event;
	
	private Help help;
	
	public SelectSong(){
		this.initsprites();
		this.initgame();
		
		this.existsongs();
		
		this.setmaxsongs();
		this.updatesongs();
	}
	public void existsongs(){
		
		List<TableSongs> file;
		Iterator<TableSongs> it ;
		TableSongs song = new TableSongs();
		String filename;
		FileSongs filesongs = new FileSongs();
		
		file = filesongs.getAllSongs();
		it = file.iterator();
		
		while(it.hasNext()){
			song = (TableSongs) it.next();
			filename = song.getName();
			if (!FileBrowser.existfile(FileBrowser.getFolderSongs(), filename+".mid")){
				filesongs.deleteSong(filename);
			}
		}
		
	}
	 public void initsprites() {
			
		//init sprites
		 frameBackgroundSelectSong = new CFrame();
		 spriteBackgroundSelectSong = new CSprite();
		 try {
			frameBackgroundSelectSong.load("sprites"+  File.separator + "pianoselectsong.png");
			spriteBackgroundSelectSong.addframe(frameBackgroundSelectSong);
		 }
		 catch(Exception e){
			 System.out.println("Error selectsong.initsprites "+e);
		 }
	
	}
	 public void initgame() { //inicializa
			
		 try{
			 
			 help = new Help(this.getClass());
			 event = new Event();
			 scores = new TableScores[5];
			 
			 filescores = new FileScores();
			 filesongs = new FileSongs();
			 songs = new TableSongs();
				
			if (Options.isOnline()){
				dbsongs = new DbSongs();
				 dbscores = new DbScores();
			}
			
				
			player = new MidiPlayer();
	
			end = false;

	  	    numfile = 1; 

		    textCalculator20 = new Text();
		    textCalculator20.init("fonts"+  File.separator + "calculator.ttf");
		    textCalculator20.setcolor(47, 131, 43);
		    
		    textCalculator40 = new Text();
		    textCalculator40.init("fonts"+  File.separator + "calculator.ttf",40);
		    textCalculator40.setcolor(47, 131, 43);
		    
		    textArabica10 = new Text();
		    textArabica10.init("fonts"+  File.separator + "arabica.ttf",10);

		    textArabica10.setcolor(87, 88,90);
		    
		 }
		 catch(Exception e){
			 
		 }
	}
	 public void initsong(String song){
				
		 try{
			path = "music" + File.separator;
		    for (int i=0;i<maxtracks;i++){
		    	midi[i] = new Midi();
		    	midi[i].initFile(path + song);
		    }
		 }
		 catch(Exception e){
			 System.out.println("Error SelectSong.initsong "+path +song+" : "+e);
		 }
	    
	}
		
	public void draw(SDLSurface screen) {
		
		try{
	    	spriteBackgroundSelectSong.draw(screen);
	    	//for(int i=0;i<filebrowser.getnumfiles("music");i++){
	    
	    	textArabica10.drawtext(Language.getString("SelectSong.Song"), screen, 90, 60); //song
	    	textArabica10.drawtext(Language.getString("SelectSong.Difficult"), screen, 90, 120); //dificult
	    	textArabica10.drawtext(Language.getString("SelectSong.PreviousSong"), screen, 170,240); //previous song
	    	
	    	textArabica10.drawtext(Language.getString("SelectSong.NextSong"), screen, 320,240); //next song
	    	textArabica10.drawtext(Language.getString("SelectSong.User"), screen, 440,240); //usuario
	    	textArabica10.drawtext(Language.getString("SelectSong.Score"), screen, 510,240); //puntuación
	    	textArabica10.drawtext(Language.getString("SelectSong.Score"), screen, 630,60); //puntuación
	
			String stringname = new String();
			
		if (song2 != null ){

			//i-1
			textCalculator20.drawtext(String.valueOf(numfile-1),screen,170,180);
    		 
			stringname = song2;
			
			if (stringname.length() > 14 ) {
				stringname = stringname.substring(0,10)+" .";
			}
   			else{
   				stringname = stringname.substring(0,stringname.length());
   			}
   			
			textCalculator20.drawtext(stringname ,screen, 200, 180); 
   			
		}
		if (song3 !=null){
			textCalculator20.drawtext(String.valueOf(numfile+1),screen,170+130,180);
    		 stringname =song3;
    		 
    		 
   			if (stringname.length() > 14 ) {
				stringname = stringname.substring(0,10)+" .";
			}
   			else{
   				stringname = stringname.substring(0,stringname.length());
   			}
			textCalculator20.drawtext(stringname ,screen, 200+130, 180); 
			
		}
		//song1
			textCalculator40.drawtext(String.valueOf(numfile),screen,170,60);
			stringname = song1;
		
			if (stringname.length() > 13 ) {
				stringname = stringname.substring(0,13)+" .";
			}
			else{
				stringname = stringname.substring(0,stringname.length());
			}
			
				textCalculator40.drawtext(stringname ,screen, 200, 60); 
				textCalculator20.drawtext(String.valueOf(player.gettempo()),screen,360,130);

		int i=0;

		while (i < maxscores){
		
			textCalculator20.drawtext(scores[i].getUser() ,screen, 440, 65+(i)*35); 
    		textCalculator20.drawtext(" "+ scores[i].getScore() ,screen,550, 65+(i)*35); 
    		i++;
			
		}
		help.draw(screen);
    	screen.flip(); //Pintamos la pantalla
	    	   
		}
		catch(Exception e){
			System.out.println("Error selectsong.draw "+e);
		}
	  	
	}
		
	public void keyboard(SDLSurface screen){
			
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
				
			sym = event.getSym();
			switch (sym){
				case 282: //F1
					help.setActive(!help.isActive());
					break;
		    	case 27 : 
		    		
		    		this.end = true;
		    		
		    		break;
	    	
		    	case 276:
		    		if (numfile > 1 ){
		    			
		    			numfile--;
		    			this.updatesongs();
		
		    			player.close();
		  
			    		player.play(  song1);
			    		
		    		}
		    		break;
		    	case 275:
		    		if (numfile < maxsongs){
	    				numfile++;
	    				
	    				this.updatesongs();
	    				
	    				player.close();
	    	    		
			    		player.play(  song1);
				    		
		    		}
		    		break;
		    	case 112: Options.auto = true;
		    	case 13:
		    		
					player.close();//stop();
					
		    		Play play = new Play();
		    		play.go(screen,song1);
		    		
		    		this.updatesongs();

		    		Options.auto = false;
		    		player.play(  song1);
		    		
		    		break;
	    	}
			 //solo pinta cuando hay un evento de teclado
			this.draw(screen);
			
		}
	}
	public void updatesongs(){
		
		if(maxsongs > 0) {
			if (Options.isOnline()){
				songs = dbsongs.getSongNumber(numfile);
			}
			else{
				songs = filesongs.getSongNumber(numfile);
			}
			
			song1 = songs.getName();
			}
			else{
				song1 = null;
			}
			if (numfile > 1){
				if (Options.isOnline()){
					songs = dbsongs.getSongNumber(numfile-1);
				}
				else{
					songs = filesongs.getSongNumber(numfile-1);
				}
    			song2 = songs.getName();
    		}
    		else{
    			song2 = null;
    		}
    		if (numfile< maxsongs){
				if (Options.isOnline()){
					songs = dbsongs.getSongNumber(numfile+1);
				}
				else{
					songs = filesongs.getSongNumber(numfile+1);
				}
    			song3 = songs.getName();
    		}
			else{
				song3 = null;
			}
    		
    		if (Options.isOnline()){
    			scores = dbscores.getMaxScores(song1);
    			maxscores = dbscores.getNumberScores(song1);
    		}
    		else{
    			try{
    				maxscores = filescores.getNumberScores(song1);
    				scores = filescores.getMaxScores(song1);
	    		
    			}
    			catch(Exception e){
    				System.out.println("No hay puntuaciones");
    			}
    		}
			
	}
	public void setmaxsongs(){
		
		if (Options.isOnline()){
			maxsongs = dbsongs.getNumberSongs();
		}
		else{
			maxsongs = filesongs.getNumberSongs();

		}
	}
	public int go(SDLSurface screen){

		player.play(song1);

		this.end = false;
		
		this.draw(screen);

	    while (!end){ //bucle principal del juego
	
	    	try {
	    		this.keyboard(screen);
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
			}
	    }
	    this.close();
		return 0;
	}
	public void close(){
		this.player.close();
		
	}
}
