
import java.io.File;

import database.DbUsers;
import database.TableUsers;

import sdljava.video.SDLSurface;

public class Login {
	
	private Text textArial15;
	
	private	boolean end;

	private CFrame[] framepopups;
	
	private CSprite spritepopup;

	private CFrame frametext;
	private CFrame frametextempty;
	private CSprite spritetext;
	
	private CFrame framebutton;
	private CSprite spritebutton;

	private int option;

	private String username;
	private String userpass;
	private int usernamepass;
	private DbUsers dbusers; 
	private Event event;
	
	public Login(){
		event = new Event();
		
        dbusers = new DbUsers();
		try{
			 textArial15 = new Text();
			    textArial15.init("fonts"+  File.separator + "arial.ttf",15);
			    textArial15.setcolor(0, 0,0);
		}
		catch(Exception e){
			
		}
		
		this.username = "";
		this.userpass = "";
		
		this.usernamepass = 1;

		this.option = 1;
				 
		this.end = false;
		
		initsprites();
		
	}
	public void initsprites(){
		
		this.frametext = new CFrame();
		this.frametextempty = new CFrame();
		this.spritetext = new CSprite();
		
		this.framebutton = new CFrame();
		this.spritebutton = new CSprite();
		
		String dir = "";
		this.spritepopup = new CSprite();
		this.framepopups = new CFrame[10];
		try{
			for (int i=0;i<=9;i++ ) {
				
				framepopups[i] = new CFrame();
				dir = "sprites"+  File.separator +"popup" + File.separator +"popup"+i+".png";
				framepopups[i].load(dir);
				
				spritepopup.addframe(framepopups[i]);
			}
		
	    	this.framebutton.load("sprites"+  File.separator + "popup" + File.separator+ "popupbutton.png");
	    	
			this.spritebutton.addframe(framebutton);
			
			this.frametext.load("sprites"+  File.separator + "popup" + File.separator+ "text.png");
			this.frametextempty.load("sprites"+  File.separator + "popup" + File.separator+ "textempty.png");

			this.spritetext.addframe(frametext);
			this.spritetext.addframe(frametextempty);

	    }
	    catch(Exception e){
	    	System.out.println("Error Login() " + e);
	    }
	}
	public void draw(SDLSurface screen){
		
		try{
	
			this.spritepopup.setx(200);
			this.spritepopup.sety(200);
			this.spritepopup.draw(screen);
			
			this.textArial15.drawtextCenter(Language.getString("Login"), screen,200,500,210);
				
			this.textArial15.drawtext(Language.getString("User")+ " :", screen,this.spritepopup.getx()+20,this.spritepopup.gety()+50);
			this.textArial15.drawtext(Language.getString("Password")+ " :", screen,this.spritepopup.getx()+20,this.spritepopup.gety()+100);

			this.textArial15.drawtext(" " + this.username, screen,this.spritepopup.getx()+50,this.spritepopup.gety()+80);
			
			if (usernamepass ==1){
				
				spritetext.setx(this.spritepopup.getx()+60+this.username.length()*8);
				spritetext.sety(this.spritepopup.gety()+80);
				spritetext.changeframe(100,true);
				spritetext.draw(screen);
			}
			String userpasscod =" ";
			for (int i=0;i<this.userpass.length();i++){
				userpasscod = userpasscod + "*";
			}
		
			this.textArial15.drawtext( userpasscod, screen,this.spritepopup.getx()+50,this.spritepopup.gety()+125);

			if (usernamepass == 2){
				spritetext.setx(this.spritepopup.getx()+60+userpasscod.length()*6);
				spritetext.sety(this.spritepopup.gety()+125);
				spritetext.changeframe(100,true);

				spritetext.draw(screen);
			}
			this.spritebutton.setx(this.spritepopup.getx() + 90);
			this.spritebutton.sety(this.spritepopup.gety() + 140);
			this.spritebutton.draw(screen);
			
			if (usernamepass == 3){
				textArial15.setcolor(255, 0, 0);
			}
			else{
				textArial15.setcolor(0,0,0);
			}
			textArial15.drawtextCenter(Language.getString("Ok"), screen, spritebutton.getx(), spritebutton.getx()+spritebutton.getw(),this.spritepopup.gety() +  150);

			//textArial15.drawtext(Language.getString("Ok"), screen, this.spritepopup.getx() + 110,this.spritepopup.gety() +  150);

			textArial15.setcolor(0,0,0);
			
			screen.flip(); //Pintamos la pantalla
		
		}
		catch(Exception e){
			
			System.out.println("Error Popup.draw " + e);

		}
	}
	public void keyboard(SDLSurface screen)  {
		
		try {
			int sym = 0;
			char key = ' ';
			event.setEvent();
			
			if (event.isPressed()){
					
				sym = event.getSym();
				switch (sym){
				
					case 273: //arriba
						if (usernamepass>1){
							usernamepass--;
						}
						break;
					case 274:  //abajo
						if (usernamepass < 3){
							this.usernamepass++;
						}
						break;
					case 276: //izquierda
					case 275: //derecha
						break;
					case 13: //enter
						
						this.usernamepass++;
						if(this.usernamepass>= 3){
							
							this.login(screen);
							
						}					
						break;
					case 27 : //esc
						
						this.end = true;
						this.option = 0; //login incorrecto
						
						break;
						
			    	case 8: //backspace
			    		
			    		if (username.length()>0){ //backspace
							
							if (this.usernamepass ==1){
								username = (String) username.subSequence(0, username.length() - 1);
							}
							else{
								userpass = (String) userpass.subSequence(0, userpass.length() - 1);
							}
			    
			    		}
			    		break;
			    	case 303: //shift izquierdo
			    	case 304: //shift derecho
			    	
				    	event.setShift(true);
			    		
			    		break;
					default:
						   
						if (event.isShift() ){
							key = (char) (sym -32);
							
						}
						else {
							key = (char)sym;
						}
					
						if (this.usernamepass ==1){
							username = username + key;
						}
						else if (usernamepass == 2){
							userpass = userpass + key;
						}
						
			    		break;
		    	
				} //end switch
				
			}
			else if (event.isReleased()){
				
				sym = event.getSym();
				
				switch(sym){
					case 303:
			    	case 304:
			    		
				    	event.setShift(false);
			    		
			    		break;
				}
			}
			this.draw(screen);
		}
		catch(Exception e){
			System.out.println("Error login.draw "+e);
		}
	}

	public boolean login(SDLSurface screen){
		String pass;
		boolean login = false;
		
        TableUsers user = new TableUsers();

		user = dbusers.getUser(username);
		try{
			pass = TableUsers.getHash(this.userpass);
			
			if ((user != null) && (user.getPass().equalsIgnoreCase(pass) ) ) {
	
				User.setUser(user);
				
				this.end = true;
				this.option = 1; //login correcto
			}
			else{
				Popup errorlogin = new Popup();
				errorlogin.set(Language.getString("Error"), 
						Language.getString("LoginError"),
						Language.getString("Retry"));
				errorlogin.go(screen);
				
				this.usernamepass = 1;
			}
		}
		catch(Exception e){

			System.out.println("Error logig.login");
		}
		return login;
		
	}

	public int go (SDLSurface screen){
		
		this.spritepopup.setx(200);
		this.spritepopup.sety(200);
		this.spritepopup.animate(screen);
		
		while(!this.end){
			
			
				
				this.keyboard(screen);
				
				if (event.isMouseClicked(spritebutton)){
					this.login(screen);
					
				}
			try {
				Thread.sleep(10);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.option ;
	}

}
