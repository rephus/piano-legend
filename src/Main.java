
import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import database.DbSettings;
import database.TableUsers;

import sdljava.*;

import sdljava.ttf.SDLTTF;
import sdljava.video.*;

public class Main{
	
//---------------------------------------------------------    	  	
//DECLARAR VARIABLES
//---------------------------------------------------------

	private boolean end ; //determina cuando salir del programa

	private SDLSurface screen;

	private Text textCalculator40;
	private Text textArabica40;

	private User user;
	
	private Options options;
	
	private 	CFrame frameBackgroundMain;
	private CSprite spriteBackgroundMain;

	private CFrame frameHand;
	private CSprite spriteHand;
	
	private CFrame [][]framemenu;
	
	private CSprite[] spritemenu;
	
	private   int maxmenu;
	private  int nummenu;
	
	private CFrame framemarquee;
	private CSprite spritemarquee;
   
	private Event event;
	
	DbSettings dbsettings;
	
	int language ;
	
	long totaltime = 0;
	Help help;
	private final int handvelocity = 10;

	private long initfps ;
	private long endfps;
	private long fps; //intervalo de fps por ms = 1000/ fps (con 60 fps = 17ms )
	
	public void initsystem(){
    	try{
    		//SDLMain.init(SDLMain.SDL_INIT_AUDIO);
		    	
    		//public static void openAudio(int frequency, int format, int channels, int chunksize) 
		
			//No se utiliza SDLMixer para reproducir audio, directamente se usa MIDI
    		//SDLMixer.openAudio(22000, SDLAudio.AUDIO_S16SYS,2, 1024);
	
			//aumentando chunksize disminuye el tiempo de aparicion del sonido, pero disminuye calidad
	        // SDLMixer.openAudio(22050, SDLAudio.AUDIO_S16SYS, 1, 1024); //aumentando chunksize disminuye el tiempo de aparicion del sonido, pero disminuye calidad
	
			//System.out.println(SDLVideo.videoDriverName());
		
			SDLMain.init(SDLMain.SDL_INIT_EVERYTHING);
	
			screen = SDLVideo.setVideoMode(800, 600, 16,SDLVideo.SDL_SWSURFACE|SDLVideo.SDL_ANYFORMAT);
			//resolucion 800x600
			//preferencia por 16bit de colores
			//con ANYFORMAT permite al equipo elegir el mejor modo de video
			//aceleración gráfica por software
			
	    	SDLTTF.init();
    	}
	    catch (SDLException e) {
    		System.out.println("Error Main.initSystem ?Falta algúna librería? "+e);

		}
    }

    public void close(){
    		
    	System.out.println(Language.getString("ThanksForPlaying"));
    	String fullscreen = Options.getOption("fullscreen");
    	if (fullscreen !=null && fullscreen.equals("yes") ){
			screen.wmToggleFullScreen();
    	}

    }
	public void initsprites() {
		
//init sprites
		try{
			framemarquee = new CFrame();
			spritemarquee = new CSprite();
			
			frameBackgroundMain = new CFrame();
			spriteBackgroundMain = new CSprite();
			
			frameHand = new CFrame();
			spriteHand = new CSprite();
	
			framemenu = new CFrame[maxmenu][2];
			spritemenu = new CSprite[maxmenu];
			
			for (int i = 0 ;i<maxmenu;i++){
				framemenu[i][0] = new CFrame();
				framemenu[i][1] = new CFrame();
				spritemenu[i] = new CSprite();
				
				framemenu[i][0].load("sprites"+  File.separator + "menu" + File.separator+"menu"+i+"en.png");
				framemenu[i][1].load("sprites"+  File.separator + "menu" + File.separator+"menu"+i+"es.png");
			
				spritemenu[i].addframe(framemenu[i][0]);
				spritemenu[i].addframe(framemenu[i][1]);
			}
			
			framemarquee.load("sprites"+  File.separator + "marquee.png");
			spritemarquee.addframe(framemarquee);
			
			frameBackgroundMain.load("sprites"+  File.separator + "pianointro.png");
			spriteBackgroundMain.addframe(frameBackgroundMain);
	
			frameHand.load("sprites"+  File.separator + "hand.png");
			spriteHand.addframe(frameHand);
		}
		catch(Exception e){
			System.out.println("Error main.initsprites "+e);
		}
		
	}
	public void init(){ //inicializa
		
		try{
			help = new Help(this.getClass());
			event = new Event();
			//piano.getdevices();
			
			options = new Options();
			
			nummenu=0;
			maxmenu=8;
			
			end = false;
			
		    user = new User();
		    
		    textCalculator40 = new Text();
		    textCalculator40.init("fonts"+  File.separator + "calculator.ttf",40);
		    textCalculator40.setcolor(47, 131, 43);
		    
	
		    textArabica40 = new Text();
		    textArabica40.init("fonts"+  File.separator + "arabica.ttf",20);
		    textArabica40.setcolor(255,255,255);
		}
		catch(Exception e){
			System.out.println("Error Main.init() "+e);
		}
	}
	
//---------------------------------------------------------    	  	
//PROCESOS PINTAR
//---------------------------------------------------------

	@SuppressWarnings("static-access")
	public void draw(SDLSurface screen) {
		
		try{
			
			spriteBackgroundMain.draw(screen);
			
			String language = options.local.getDefault().toString();

			for (int i = 0;i<maxmenu;i++){
				
				if (language.equalsIgnoreCase("en_US")){
					spritemenu[i].setframe(0);
				}
				else{
					spritemenu[i].setframe(1);
				}
				spritemenu[i].setx(155+58*i);
				spritemenu[i].sety(430);
				spritemenu[i].draw(screen);
			}
			
			//textCalculator15.drawtext( language.getString(3) + " : " , screen, 133, 80);
			if (user.getUser().getName().length() < 9 ){
				textCalculator40.drawtext(" " + user.getUser().getName() , screen, 133, 75);
	
			}
			else{
				textCalculator40.drawtext(" " + user.getUser().getName().substring(0,9) , screen, 133, 80);
	
			}
			
			spriteHand.sety(450);

			spriteHand.draw(screen);
			
			spritemarquee.sety(550);
			spritemarquee.setx(0);
			for(int i =0;i<55;i++){
				
				spritemarquee.draw(screen);
				spritemarquee.setx(spritemarquee.getx()+30);
			}
			
			this.textArabica40.drawmarquee(Language.getString("Menu."+nummenu), screen, 550);
			
			help.draw(screen);
			screen.flip(); //Pintamos la pantalla
		}
		catch(Exception e){
			System.out.println("Error main.draw "+e);
		} 
	  //	screen.fillRect(0); //borramos la pantalla anterior
	}

//---------------------------------------------------------    	  	
//PROCESOS INPUT
//---------------------------------------------------------
	public void keyboard(SDLSurface screen){
		
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
			
		sym = event.getSym();

			switch (sym){
			case 282: //F1
				help.setActive(!help.isActive());
				break;
			case 27 :  //esc
				textArabica40.resetmarquee();

	 	    	nummenu = maxmenu-1;
	    		break;
		
	    	case 276: //izquierda
	    		if (nummenu > 0 ){
	    			textArabica40.resetmarquee();

	    			nummenu--;
	    		}
	    		break;
	    	case 275: //derecha
	    		if (nummenu+1 < maxmenu){
	    			textArabica40.resetmarquee();

	    			nummenu++;
	    		}
	    		break;
	    	case 13:

	    		this.goTo(nummenu);
			}
			this.totaltime = 0;
		}	
	}

	private void goTo(int nummenu){
		if (nummenu==0){ //nueva partida
			
	    	SelectSong selectsong = new SelectSong();
	    	selectsong.go(screen);
	    	
		}

		if (nummenu==1){ //freemode
			
    		FreeMode freemode = new FreeMode();
    		freemode.go(screen);
    		
		}
		if (nummenu==2){ //nueva partida
			
	    	SelectPractice selectpractice = new SelectPractice();
	    	selectpractice.go(screen);
	    	
	    	
		}
		if (nummenu==3){ //opciones
			
			options.go(this.screen);
			
		}
		if (nummenu==4){ //usuarios
			if (Options.isOnline()){
				try {
					Desktop.getDesktop().browse(new URI("http://"+ DbSettings.getString("ip")+"/blogpiano/?page_id=131"));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
			else{
				user.go(this.screen);
			}
		}
		if (nummenu==5){
			if (Options.isOnline()){
    			try {
					Desktop.getDesktop().browse(new URI("http://"+ DbSettings.getString("ip")+"/blogpiano/?page_id=54"));
				} catch (Exception e) {
					
	    		
				}
			}
			else{
				AdminSong adminsong = new AdminSong();
    			adminsong.go(screen);
			}
			
			
		}
		if (nummenu==6){ //credits
			Credits credit = new Credits();
			credit.go(screen);
		}
		if (nummenu==7){ //salir
			
    		end = true;
		}
		event = new Event();
		
	}
//---------------------------------------------------------    	  	
//PROCESO MAIN
//---------------------------------------------------------
	public static void main(String[] args) throws Exception {
   
		new Main().run();
	}
	
//---------------------------------------------------------    	  	
//PROCESO PRINCIPAL
//---------------------------------------------------------

	@SuppressWarnings("static-access")
	public void run() throws Exception{
		
//---------------------------------------------------------    	  	
//INICIALIZAR
//---------------------------------------------------------	
		initsystem();
		
		init();
		
		int sel = 0;
		
		String conectivity = Options.getOption("conectivity");
		
    	if (conectivity!= null){
    		if (conectivity.equals("online")){
    			sel = 1;
    		}
    		else{
    			sel =0;
    		}
    	}
    	else{
    		System.out.println("pintamos popup");
			Popup popup = new Popup();
	    	popup.set(Language.getString("Popup.Conectivity"),
	    			Language.getString("Popup.Conectivity.msg"),
	    			Language.getString("Popup.Offline"),
	    			Language.getString("Popup.Online"));
	    	sel = popup.go(screen);
	    	
	       	Popup rememberOption = new Popup();
			rememberOption.set(Language.getString("Popup.Remember"), 
					Language.getString("Popup.Remember.msg"),
					Language.getString("Popup.Yes"),
					Language.getString("Popup.No"));
			if (rememberOption.go(screen)==0){
				if (sel == 1){
					Options.setOption("conectivity","online");
				}
				else{
					Options.setOption("conectivity","offline");
				}
				System.out.println("nueva opcion");
			}

    	}
    
    	while (sel==1){ //online
	    	
	    		Options.setOnline(true);
	    		
	    		dbsettings = new DbSettings();
	    			
	    		if (dbsettings.isConnected()){
	    		
	    			Login login = new Login();
	            	
	            	sel = login.go(screen);
	            	if (sel ==1){ //login correcto
	            		sel++;
	            	}
	    		}
	    		else{

	    			Popup errorconexion = new Popup();
	    			errorconexion.set(Language.getString("Popup.ConectivityError"), 
	    					Language.getString("Popup.ConectivityError.msg"),
	    					Language.getString("Popup.Offline"),
	    					Language.getString("Popup.Retry"));
	    			sel = errorconexion.go(screen); 
	    	
	    		}
    	}
    	
    	if (sel==0){ //offline
    		Options.setOnline(false);
    		user.go(screen);
    		
    		if (user.getUser() == null){
    			TableUsers anonymous = new TableUsers();
    			anonymous.setName("Anonymous");
    			anonymous.setPass("");
    			anonymous.setEmail("");
    			//anonymous.setAdded(Date.parse("0000-00-00"));
    			user.setUser(anonymous);
    		}
    	}
 

		initsprites();
//---------------------------------------------------------    	  	
//BUCLE PRINCIPAL
//---------------------------------------------------------		
	    
	while (!end){ //bucle principal del juego
	
		initfps = System.currentTimeMillis();
    
		keyboard(screen);
		
		for (int i = 0;i<maxmenu;i++){
			if (event.isMouseClicked(160+60*i, 60, 190, 350)){
				nummenu = i;
				this.goTo(nummenu);
			}
		}
		
		constant();

    	this.draw(screen);
		
        endfps = System.currentTimeMillis();
        fps = endfps - initfps;

        try{
        	Thread.sleep(17-fps);
        }
        catch(Exception e){
        	
        }
	} //fin while	
	    close();
	    
	    System.exit(0);
	
  } //fin Main
	private void constant(){
		
		spriteHand.setx(spriteHand.getx() + ((100+nummenu*60)-spriteHand.getx())/handvelocity);
	
	}
}
