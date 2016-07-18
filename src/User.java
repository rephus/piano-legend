
import java.io.File;

import database.TableUsers;

import file.FileUser;

import sdljava.video.SDLSurface;

public class User {

	private boolean end ; //determina cuando salir del programa

	private Text textHand20;
	
	private FileUser fileuser;
	
	private static TableUsers user;
	private TableUsers[] arrayuser;

	private int maxuser;

	private int submenu ;
	
	private CFrame frameBackgroundNotebook;
	private CSprite spriteBackgroundNotebook;

	private CFrame framepencil;
	private CSprite spritepencil;
	
	private   int numuser;

	private   int menu;

    private int maxsubmenu;
    
	private   String newusername;

	private Event event ;
	
	User(){
		
		this.initsprites();
		this.init();
		this.updateuser();
		
	}

	public void initsprites() {
			
		try{
			frameBackgroundNotebook = new CFrame();
			spriteBackgroundNotebook = new CSprite() ;
			
			framepencil = new CFrame();
		    spritepencil  = new CSprite();
			
			frameBackgroundNotebook.load("sprites"+  File.separator +"notebook.png");
		    this.spriteBackgroundNotebook.addframe(this.frameBackgroundNotebook);
			
		    framepencil.load("sprites"+  File.separator +"pencil.png");
		    spritepencil.addframe(framepencil);
		    
		    
		}
		catch(Exception e){
			System.out.println("Error user.initsprites "+e);
		}

	}
	
	public void init() { //inicializa
			
		fileuser = new FileUser();
		event = new Event();
		this.arrayuser =new TableUsers[20];
		fileuser= new FileUser();
		menu = 1;
		submenu = 1;
		maxsubmenu = 3;
		
		newusername = "";

		numuser = 0;
		
	    textHand20 = new Text();
	    textHand20.init("fonts"+  File.separator + "brigitte.ttf",20);

	    textHand20.setcolor(0,0,0);
	   
	}
	
	public void draw(SDLSurface screen){

		try{
			int contusuarios =0;
			spriteBackgroundNotebook.draw(screen);
			int longitud= 0;
			
			if (menu == 1){ //listado de usuarios
		
			    textHand20.drawtext(Language.getString("User.Add"), screen, 320, 70 );
	
		    	while (contusuarios < maxuser ){
		    		
		    		textHand20.drawtext(arrayuser[contusuarios].getName(), screen,330, 133
		    				+(contusuarios*32)); 
		    		//System.out.println(filebrowser.getfile(i));
		    		contusuarios++;
		    	}
		    	textHand20.setcolor(0,0,0);
		    	
		    	if (numuser == -1){
					spritepencil.sety(80);
					spritepencil.setx(400);
				}
				else{
					longitud = arrayuser[numuser].getName().length();
					
					spritepencil.setx(longitud*12+350);
					spritepencil.sety(numuser *32 + 143);
				
				}
				spritepencil.draw(screen);
			}
	
		  	if (menu == 3){ //seleccionar,modificar,borrar
				
				textHand20.drawtext(Language.getString("User.Select"), screen, 320, 100+(0*33));
				textHand20.drawtext(Language.getString("User.Modify"), screen, 320, 100+(1*33));
				textHand20.drawtext(Language.getString("User.Delete"),screen, 320, 100+(2*33));
				
				switch(submenu){
					case 1: longitud = Language.getString("Select").length();
						break;
					case 2: longitud = Language.getString("Modify").length();
						break;
					case 3: longitud = Language.getString("Delete").length();
						break;
					
				}
				
				spritepencil.setx(longitud*12 +330);
				spritepencil.sety(submenu *33 + 80);
				spritepencil.draw(screen);
			}
		  	
		  	if (menu == 2){ //nuevo usuario
	
				textHand20.drawtext(Language.getString("User.NewUser"), screen, 320, 440);
				textHand20.drawtext(" " +newusername, screen, 330,480);
				
				longitud = newusername.length();
				
				spritepencil.setx(longitud*12 +340);
				spritepencil.sety( 500);
				spritepencil.draw(screen);
			}
		  	
			screen.flip(); //Pintamos la pantalla
		}
		catch(Exception e){
			System.out.println("Error user.draw "+e);
		}
 	   
	}
	@SuppressWarnings("static-access")
	public void keyboard(SDLSurface screen){
		
		int sym = 0;
		char key = ' ';
		event.setEvent();
		
		try{
			if (event.isPressed()){
				
				sym = event.getSym();
				
				switch (sym){
		    	
			    	case 27 : //esc
			    		
			    		if (menu == 1){
			    			this.end = true;
			    		}
			    		else if (menu == 2){

							menu=1;
			    		}
			    		else if (menu == 3){
			    			menu=1;
			    		}
			
			    		break;
		    	
			    	case 273 : //up
			    		
			    		if (menu == 1){
				    		if (numuser >-1){
				    			numuser--;
				    		}
			    		}
			    		else if (menu == 3){
				    		if (submenu > 1 ){
				    			submenu--;
				    		}	
			    		}
			    		
			    		break;
			    	case 274 : //down
			    		
			    		if (menu == 1){
				    		if (numuser+1 < maxuser){
				    			numuser++;
				    		}
			    		}
			    		else if (menu ==3){
				    		if (submenu < maxsubmenu ){
				    			submenu++;
				    		}
			    		}
				    	
			    		break;
			    	case 13 : //ENTER
			    		
			    		if (menu == 1){
				    		if (numuser == -1){ //añadir
				    			menu = 2;
				    			newusername = ""; //nueva cadena
				    		}
				    		if (numuser >=0 ){
				    			menu = 3;
				    		}
			    		}
			    		else if (menu == 2){ //añadiendo nuevo usuario
			    			
			    			if (!newusername.equalsIgnoreCase("")){
	
			    				fileuser.addUser(newusername);
								menu=1;
								this.updateuser();
			    			}
			    		}
			    			
			    		else if (menu == 3){
			    			
			    			if (submenu == 1){//activar
				    			
			    				this.end = true;
				    			this.user = this.arrayuser[numuser];
				    		
				    		}
				    		else if (submenu == 2){ //modificar
				    				
				    		}
				    		
				    		else if (submenu == 3){ //borrar
				    			
				    			fileuser.deleteUser(this.arrayuser[numuser].getName());
				    			this.updateuser();
				    			this.numuser = 0;
				    		
				    		}
				    		menu=1;	
			    		}
			    		
			    		break;
			    	case 8: //backspace
			    		if (menu ==2){
				    		if (newusername.length()>0) { //backspace
								newusername = (String) newusername.subSequence(0, newusername.length() - 1);
								
							}
			    		}
			    		break;
			    	
			    	case 303: //shift izquierdo
			    	case 304: //shift derecho
			    	
			    		if (menu ==2){
			    			
				    		event.setShift(true);
			    		}
			    		break;
			    	default:
			   
						if (event.isShift() ){
							key = (char) (sym -32);
							
						}
						else {
							key = (char)sym;
						}
					
						newusername = newusername + key;
						
			    		break;
		    	}//final switch
			this.draw(screen);
		} //final is pressed
		else if (event.isReleased()){
			
			sym = event.getSym();
			
			switch(sym){
				case 303:
		    	case 304:
		    		if (menu ==2){
		    			
			    		event.setShift(false);
		    		}
		    		break;
			}
			this.draw(screen);
		}
			
		}
		catch(Exception e){
			System.out.println("Error user.keyboard "+e);
		}
	}
	
	public void updateuser(){
		
		this.arrayuser = fileuser.getArrayUsers();
		this.maxuser = fileuser.getNumberUsers();

	}
	
	public static void setUser(TableUsers user){
		User.user = user;
	}
	
	int go (SDLSurface screen){
	
		this.draw(screen);
		
		this.end = false;
		while (!this.end){
			
			try {
				this.keyboard(screen);
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		return 0;
	}

	public static TableUsers getUser(){
		return User.user;
	}
	
}
