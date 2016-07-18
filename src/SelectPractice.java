
import java.io.File;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import sdljava.video.SDLSurface;

public class SelectPractice {

	//---------------------------------------------------------    	  	
	//DECLARAR VARIABLES
	//---------------------------------------------------------

	private boolean end ; //determina cuando salir del programa

	private Text textArabica10;
	private Text textHand;
	private Text textHand15;

	
	private CFrame frameBackgroundSelectSong;
	private CSprite spriteBackgroundSelectSong;
	
	private Midi[] midi;
	
	private  String path;
    
	private  int maxtracks;
    
	private   int numfile;
    
	private   MidiPlayer player;

//		private   String [] scores = new String[10];

	
	private Event event;
	
	private Help help;
	
	private int maxsongs ;
	private CFrame framesheet;
	private CSprite spritesheet;
	
	private CFrame framepiano;
	private CSprite spritepiano;
	
	private CFrame framestar;
	private CSprite spritestar;
	
	public SelectPractice(){
		
		this.initsprites();
		this.initgame();
		
	}
	
	 public void initsprites() {
			
		//init sprites
		 framesheet = new CFrame();
		 spritesheet = new CSprite();
		 
		 framepiano = new CFrame();
		 spritepiano = new CSprite();
		 
		 framestar = new CFrame();
		 spritestar = new CSprite();
		 
		 frameBackgroundSelectSong = new CFrame();
		 spriteBackgroundSelectSong = new CSprite();
		 try {
			framesheet.load("practice"+  File.separator + "sheet1.png");
			spritesheet.addframe(framesheet);
			
			framepiano.load("practice"+  File.separator + "piano1.png");
			spritepiano.addframe(framepiano);
				
			framestar.load("sprites"+  File.separator + "star.png");
			spritestar.addframe(framestar);
				
			frameBackgroundSelectSong.load("sprites"+  File.separator + "practicenotebook.png");
			spriteBackgroundSelectSong.addframe(frameBackgroundSelectSong);
		 }
		 catch(Exception e){
			 System.out.println("Error selectsong.initsprites "+e);
		 }
	
	}
		public String getString (String str,int num){
			
			ResourceBundle rb = ResourceBundle.getBundle("practice.song"+num);
			
			try {
				return rb.getString(str);
			} catch (MissingResourceException e) {
				return '!' + str + '!';
			}
		}
	 public void initgame() { //inicializa
			
		 try{
			 
			 help = new Help(this.getClass());
			 event = new Event();


			player = new MidiPlayer();
	
			end = false;

			maxsongs = 2;
	  	    numfile = 1; 

	  	  	textHand = new Text();
		    textHand.init("fonts"+  File.separator + "brigitte.ttf",30);

		    textHand.setcolor(50, 50,50);
		    
		    textHand15 = new Text();
		    textHand15.init("fonts"+  File.separator + "brigitte.ttf",15);

		    textHand15.setcolor(50, 50,50);
		    
		    textArabica10 = new Text();
		    textArabica10.init("fonts"+  File.separator + "arabica.ttf",30);

		    textArabica10.setcolor(87, 88,90);
		    
		 }
		 catch(Exception e){
			 
		 }
	}
	 public void initsong() {
			
		 try{
			
			path = "practice" + File.separator;
		    for (int i=0;i<maxtracks;i++){
		    	midi[i] = new Midi();
		    	midi[i].initFile(path + "song"+numfile);
		    }
		 }
		 catch(Exception e){
			 System.out.println("Error SelectPractice.initsong() "+e);
		 }
	    
	}
		
	public void draw(SDLSurface screen) {
		
		int dificulty =1;
		String[] strings;
		try{
	    	spriteBackgroundSelectSong.draw(screen);
	    	//for(int i=0;i<filebrowser.getnumfiles("music");i++){
	    
	    	textHand15.drawtext(Language.getString("Practice.Author"), screen, 10,80);

	    	textHand15.drawtext(Language.getString("Practice.Difficult"), screen, 10,110); 
	    	textHand15.drawtext(Language.getString("Practice.Tempo"), screen, 10, 140); 
	    	textHand15.drawtext(Language.getString("Practice.Description"), screen, 10, 170); 

	    	textHand15.drawtext(Language.getString("Practice.NotesPosition"), screen, 440, 340); 
	    	
	    	//author
	    	textHand15.drawtext(this.getString("author",numfile), screen, 100,80); //dificult

	    	
	    	//dificulty
	    	dificulty = Integer.parseInt(this.getString("dificulty",numfile) );
    		spritestar.sety(110);

	    	for (int i =0;i<dificulty;i++){
	    		spritestar.setx(100+i*20);
	    		spritestar.draw(screen);
	    	}
			String stringname = new String();
			
			//tempo
			textHand15.drawtext(String.valueOf(player.gettempo()),screen,100,140);
		
			//description
			
			strings = Text.split(this.getString("description",numfile),35); 
			for (int i = 0; i< strings.length;i++){ //separador de cadenas
				
				this.textHand15.drawtext(strings[i] , screen,50,i*20+200);
			}
			
			//song1
			textHand.drawtext(String.valueOf(numfile)+ "-",screen,440,20);
			
			stringname = this.getString("name",numfile);
		
			if (stringname.length() > 20 ) {
				stringname = stringname.substring(0,20)+" .";
			}
			else{
				stringname = stringname.substring(0,stringname.length());
			}
			
			textArabica10.drawtext(stringname ,screen,470, 20); 
			//song2
			if (numfile > 1 ){
	
		    	textHand15.drawtext(Language.getString("Practice.PreviousLevel"), screen, 10,530); //previous song
	
				//i-1
				textHand15.drawtext(String.valueOf(numfile-1)+"-",screen,10,550);
	    		 
				stringname = this.getString("name",numfile-1);
				
				if (stringname.length() > 30 ) {
					stringname = stringname.substring(0,30)+" .";
				}
	   			else{
	   				stringname = stringname.substring(0,stringname.length());
	   			}
	   			
				textHand15.drawtext(stringname ,screen, 30, 550); 
	   			
			}
			//song 3
			if (numfile < maxsongs){
		    	textHand15.drawtext(Language.getString("Practice.NextLevel"), screen, 600,530); //next song
	
				textHand15.drawtext(String.valueOf(numfile+1)+"-",screen,600,550);
	    		 stringname =this.getString("name",numfile+1);
	    		 
	   			if (stringname.length() > 30) {
					stringname = stringname.substring(0,30)+" .";
				}
	   			else{
	   				stringname = stringname.substring(0,stringname.length());
	   			}
				textHand15.drawtext(stringname ,screen,630, 550); 
				
			}
			
			//sheet
			try{
				framesheet.load("practice"+  File.separator + "sheet"+numfile+".png");
				spritesheet.setx(440);
				spritesheet.sety(80);
				spritesheet.draw(screen);
			}
			catch(Exception e){
				System.out.println("Error SelectPractice.draw no se ha podido cargar la partitura: sheet"+numfile+".png"+e);
			}
			//piano
			try{
				framepiano.load("practice"+  File.separator + "piano"+numfile+".png");
				spritepiano.setx(440);
				spritepiano.sety(370);
				spritepiano.draw(screen);
			}
			catch(Exception e){
				System.out.println("Error SelectPractice.draw no se ha podido cargar el piano: piano"+numfile+".png"+e);
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
		    		player.stop();
		    		
		    			//player.close();
		    		break;
	    	
		    	case 276:
		    		if (numfile > 1 ){
		    			
		    			numfile--;
		
		    			player.close();
		  
			    		player.play(  this.getString("song",numfile));
			    		
		    		}
		    		break;
		    	case 275:
		    		if (numfile < maxsongs){
	    				numfile++;
	    				
	    				player.close();
	    	    		
			    		player.play(  this.getString("song",numfile));
				    		
		    		}
		    		break;
		    	case 112: Options.auto = true;
		    	case 13:
		    		
					player.stop();
					
		    		Play play = new Play();
		    		play.go(screen,this.getString("song",numfile));
		    		

		    		Options.auto = false;
		    		player.play(  this.getString("song",numfile));
		    		
		    		break;
	    	}
			 //solo pinta cuando hay un evento de teclado
			this.draw(screen);
			
		}
	}
	
	public int go(SDLSurface screen){

		player.play( this.getString("song",numfile));

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
	public void close()
	{
		this.player.close();
		
	}
}
