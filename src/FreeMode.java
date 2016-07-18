
import java.io.File;
import javax.sound.midi.MidiUnavailableException;
import sdljava.SDLException;
import sdljava.video.SDLSurface;

public class FreeMode {
	
	private Event event;
	private Piano piano;
	private boolean end ; //determina cuando salir del programa
	

	private MidiPlayer player;
	
	private CFrame frameKey;
	private CFrame frameKeyPush;
	private CFrame frameKeyblack;
	private CFrame frameKeyblackPush;
	
	private 	CSprite spriteKey;
	private CSprite spriteKeyblack;
 
	public FreeMode(){
		
		this.init();
		this.initsprites();
		
	}
	public void initsprites() {
		
		//init sprites
		try{
		    frameKey = new CFrame();
		    frameKeyblack = new CFrame();
		    
		    frameKeyPush = new CFrame();
		    frameKeyblackPush = new CFrame();
		    
		    spriteKey = new CSprite();
		    spriteKeyblack = new CSprite();
		    
		    frameKey.load("sprites"+  File.separator + "key.png");
		    frameKeyPush.load("sprites"+  File.separator + "keypush.png");
		    spriteKey.addframe(frameKey);
		    spriteKey.addframe(frameKeyPush);
		    
		    frameKeyblack.load("sprites"+  File.separator + "keyblack.png");
		    frameKeyblackPush.load("sprites"+  File.separator + "keyblackpush.png");
		    spriteKeyblack.addframe(frameKeyblack);
		    spriteKeyblack.addframe(frameKeyblackPush);
		    
		}
		catch(Exception e){
			System.out.println("Error al cargar sprites freemode.initsprites "+e);
		}

	}
	
	public void init() { //inicializa

		player = new MidiPlayer();
		
		event = new Event();
		
		try{
			piano = new Piano();
		
			piano.init();
		}
		catch(Exception e){
			System.out.println("Error play.initgame , no se puede inicializar piano "+e);
		}
		end = false;

	}

	public void draw(SDLSurface screen) {
		
		try{
			
			
		  	spriteKey.sety(470);
		  	spriteKey.setx(-30);
		  	
		  	spriteKeyblack.sety(470);
		  	spriteKeyblack.setx(-3);
		  	for (int i=0;i<=24;i++){ //bucle pintar blancas
		 	
		  		if (Midi.isBlack(i+60)){
		  			
		  		}
		  		else{
		  			if ( piano.getPressed(i)){
		  				spriteKey.setframe(1);
		  			}
		  			else {
		  				spriteKey.setframe(0);
		  			}
		  			 //aumenta la diferencia segun las notas	
		    	  	spriteKey.setx(spriteKey.getx() + spriteKey.getw());
		    	  	spriteKey.draw(screen); //pinta blancas
		  		}
		  		
		  	}
		  	for (int i=0;i<=24;i++){ //bucle pintar negras
		  		 
		  		if (Midi.isBlack(i+60)){
		  			
		  			if ( piano.getPressed(i)){
		  				spriteKeyblack.setframe(1);
		  			}
		  			else {
		  				spriteKeyblack.setframe(0);
		  			}
		  			spriteKeyblack.draw(screen);
		  		}
		  		else{
	
		  			spriteKeyblack.setx(spriteKeyblack.getx() + spriteKey.getw());
		  		}
		  	}
	
		  	screen.flip(); //Pintamos la pantalla 
		  	screen.fillRect(0); //borramos la pantalla anterior
		}
		catch(Exception e){
			System.out.println("Error freemode.draw "+e);
		}
	}

	public void evaluatenotemouse(){
		int posx = -30;
		int posy = 510;
		int h= spriteKey.geth();
		int w= spriteKey.getw();
		int note =0;
		for (int i=0;i<=14;i++){ 
		 	
			note = Piano.array2note(i);
			posx = posx + w;
	  		if (event.isMouseClicked(posx, w, posy, h)){
	  			piano.setPressed(note-60, true);
	  			player.noteOn(note);	
	  		}
	  		if (event.isMouseReleased()){
	  			piano.setPressed(note-60, false);
	  			player.noteOff(note);	
	  		}

	  	}

		posx = -3;
		posy = 470;
		h= spriteKeyblack.geth()/2;
		w= spriteKeyblack.getw();
		
		for (int i=0;i<=14;i++){
		 	
			note = Piano.arrayblack2note(i);
			posx = posx + spriteKey.getw();
	  		if (event.isMouseClicked(posx, w, posy, h)){
	  			piano.setPressed(note-60, true);
	  			player.noteOn(note);
	  		}
	  		if (event.isMouseReleased()){
	  			piano.setPressed(note-60, false);
	  			player.noteOff(note);	
	  		}

	  	}
	}
	@SuppressWarnings("static-access")
	public void evaluatenotekeyboard() { //comprueba si se acierta la nota con el teclado

		if (event.isPressed() ){
			int note = 0;
			int sym = event.getSym();

			note = event.symtonote(sym);

	  		piano.setPressed(note-60, true);
	  		player.noteOn(note);	
		}
		if (event.isReleased() ){
			int note = 0;
			int sym = event.getSym();

			note = event.symtonote(sym);
		
			piano.setPressed(note-60, false);
			player.noteOff(note);	
		}
	}

	public void keyboard(SDLSurface screen) throws SDLException, InterruptedException, MidiUnavailableException{
		
		//System.out.println(piano.getpush());
		//System.out.println(piano);
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
				
			sym = event.getSym();
			
			switch (sym){
				case 27:
				  	  //today = Calendar.getInstance();
					  //time =today.getTimeInMillis() -inittime;

					Popup menuesc = new Popup();
					menuesc.set(Language.getString("Popup.Exit"),
							Language.getString("Popup.Exit.msg"),
							Language.getString("Popup.No"),
							Language.getString("Popup.Yes"));
					this.end = (1==  menuesc.go(screen));
					
					break;
				
			}
		}
		
	}
	
	public int go (SDLSurface screen){

		this.end = false;
		
		while (!this.end){
			
			try {
				
				this.draw(screen);
	    		
	    		this.evaluatenotemouse();
	    		this.evaluatenotekeyboard();

	    		this.keyboard(screen);
	    		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.close();
		
		return 0 ;
	}
	public void close(){
		piano.close();
		player.close();
	}
	
}

