
import java.io.File;

import sdljava.video.SDLSurface;

public class Credits {

	//---------------------------------------------------------    	  	
	//DECLARAR VARIABLES
	//---------------------------------------------------------

	private boolean end ; //determina cuando salir del programa

	private Text textArabica10;
	private Text textArabica20;

	private Text textArabica30;

    //		private   String [] scores = new String[10];

	private Event event;
	
	private long initfps ;
	private long endfps;
	private long fps; //intervalo de fps por ms = 1000/ fps (con 60 fps = 17ms )
	
	private int posy ;
	private int vspeed = 8;
	private static String javi="Javier Rengel Jiménez";
	private static String mar="Maria del Mar Bartolomé Rueda";
	private static String chicano="Jose Francisco Chicano García";
	
	private Info[] info ={
			new Info(Language.getString("Credits.Director"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.Desing"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.ProjectManager"),30),
			new Info(chicano,20),
			new Info(Language.getString("Credits.Programming"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.Ilustrator"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.SongSelector"),30),
			new Info(javi,20),
			new Info(mar,20),
			new Info(Language.getString("Credits.MidiCreation"),30),
			new Info(mar,20),
			new Info(Language.getString("Credits.TranslationEn"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.TranslationEs"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.Marketing"),30),
			new Info(javi,20),
			new Info(Language.getString("Credits.TestingAdvisor"),30),
			new Info(mar,20),
			new Info(chicano,20),
			new Info(Language.getString("Credits.SpecialThanks"),30),
			new Info(mar,20),
			new Info(chicano,20),
			new Info(Language.getString("Credits.PoweredBy"),30),
			new Info("eclipselogo.png"),
			new Info("sdljavalogo.png"),
			new Info("midilogo.png")
			
			};
	
	private class Info{
		
		String string;
		CFrame frame;
		CSprite sprite;
		
		boolean image;
		int size;
		
		public Info(String string, int size){
			this.image = false;
			this.string = string;
			this.size = size;
			
		}
		public Info(String string){
			this.image = true;
			try{
				frame = new CFrame();
				sprite = new CSprite();
				
				frame.load("sprites"+File.separator+"credits"+File.separator+string);
				sprite.addframe(frame);
				}
				catch(Exception e){
					
			}
		}
		public boolean isImage(){
			return image;
		}
		public CSprite getSprite(){
			return sprite;
		}
		public String getString(){
			return this.string;
		}
		public int getSize(){
			return size;
		}

		
	}
	
	public void drawInfo(Info info,SDLSurface screen,int minx, int maxx, int y){
		if (info.isImage()){ //es imagen
			info.getSprite().drawCenter(screen, minx, maxx, y);
		
			
		}
		else switch(info.getSize()) { //no es imagen
		
		
		case 10:
			this.textArabica10.drawtextCenter(info.getString(), screen,minx, maxx ,y);
			break;
		case 20:
			this.textArabica20.drawtextCenter(info.getString(), screen,minx, maxx ,y);
			break;
		case 30:
			this.textArabica30.drawtextCenter(info.getString(), screen,minx, maxx ,y);
			break;
		
		}
		
	}
	public Credits(){
		this.initsprites();
		this.initgame();

	}
	
	 public void initsprites() {
		
	
	}
	 public void initgame() { //inicializa
			
		 try{
			 
			event = new Event();
			
		    textArabica10 = new Text();
		    textArabica10.init("fonts"+  File.separator + "arabica.ttf",10);
		    textArabica10.setcolor(255, 255,255);
		    
		    textArabica20 = new Text();
		    textArabica20.init("fonts"+  File.separator + "arabica.ttf",20);
		    textArabica20.setcolor(255, 255,255);
		    
		    textArabica30 = new Text();
		    textArabica30.init("fonts"+  File.separator + "arabica.ttf",30);
		    textArabica30.setcolor(255, 255,255);
		    
		 }
		 catch(Exception e){
			 
		 }
	}
	
		
	public void draw(SDLSurface screen) {
		
		try{
			int y =  (600+(30)-(posy));
			int minx =0;
			int maxx = 800;
			for (int i = 0;i<this.info.length;i++){
				
				
				if (y < 100){
					minx = 0;
					maxx=  y*8;//800-y*2;
				}
				else if (y > 400) {
					maxx = 800;
					minx = (y-400)*5;
					
				}
				else{
					minx =0;
					maxx = 800;
				}
				//x = (int) Math.min(200, 8*y );
				if ((y > 5)&& (y < 500)){ //muestra texto si se encuentra entre 5 y 500
					
					this.drawInfo(info[i],screen,minx,maxx,y);
					
					//textArabica10.drawtext(text[i], screen, x,y);
				}
				
				else if ((i == this.info.length-1)&&(y < 5)){ //créditos finalizados
						end = true;
				}
				if (this.info[i].isImage()) {
					y+=this.info[i].getSprite().geth()+ 10;
				}
				else{
					y += this.info[i].getSize()*2;
				}
				
			}
			
			screen.flip(); //Pintamos la pantalla

			screen.fillRect(0); //borramos la pantalla anterior
	    	   
		}
		catch(Exception e){
			System.out.println("Error credits.draw "+e);
		}
	  	
	}
		
	public void keyboard(SDLSurface screen){
			
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
				
			sym = event.getSym();
			switch (sym){
				
		    	case 27 : 
		    		
		    		vspeed = 1;
		    			//player.close();
		    		break;
	    	
	    	}
			 //solo pinta cuando hay un evento de teclado
			
		}
		if (event.isReleased()){
			sym = event.getSym();
			switch (sym){
				
		    	case 27 : 
		    		
		    		vspeed = 8;
		    			//player.close();
		    		break;
	    	
	    	}
		}
	}
	
	public int go(SDLSurface screen){

		this.end = false;
		
		this.draw(screen);

	    while (!end){ //bucle principal del juego
	
    		initfps = System.currentTimeMillis();
        
        	this.draw(screen);
        	
            endfps = System.currentTimeMillis();         
            
        	fps = endfps - initfps;

    		posy = posy +((int) fps/ vspeed);
        	
	        try{
	        	Thread.sleep(17-fps);
	        }
	        catch(Exception e){
	        	
	        }
    		this.keyboard(screen);

    		keyboard(screen);

	    }
	    this.close();
		return 0;
	}
	public void close(){
		
	}
}
