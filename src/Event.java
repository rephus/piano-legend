
import sdljava.event.SDLEventType;
import sdljava.event.SDLKeyboardEvent;
import sdljava.event.SDLMouseButtonEvent;

public class Event {
	
	public final int upPressed = 273;
	public final int downPressed = 274;
	public final int escPressed = 27;
	public final int enterPressed = 13;
	public final int aPressed = 97;
	public final int zPressed = 122;
	public final int backPressed = 8;
	public final int shiftPressed = 303;
	public final int leftPressed = 276;
	public final int rightPressed = 275;
	
	private sdljava.event.SDLEvent event;
	private boolean released ;
	private boolean pressed;
	private int sym;
	
	private boolean shift;
	
	public static int symtonote(int sym){
		
		int key=0;
		switch(sym){
			
			case 97: key = 60; //a
				break;
			case 115: key = 62; //s
				break;
			case 100: key = 64; //d
				break;
			case 102: key = 65; //f
				break;
			case 103: key = 67; //g
				break;
			case 104: key = 69; //h 
				break;
			case 106: key = 71; //j
				break;
			case 107: key = 72; //k
				break;
			case 108: key = 74; //l
				break;
			case 119: key = 61; //w
				break;
			case 101: key = 63; //e
				break;
			case 116: key = 66; //t
				break;
			case 121: key = 68; //y
				break;
			case 117: key = 70; //u
				break;
			case 111: key = 73; //i
				break;
			case 112: key = 75; //i
				break;
				
		}
		return key;
	}
	
	public boolean isMouseMove (){

		boolean move= false;
	   if (event != null){
		  switch(event.getType()){
		  
			  case SDLEventType.MOUSEMOTION:  	
				  move = true;
				  break;
		  }
	   }
		return move;	
	}
	public boolean isMouseReleased(){
		
		boolean release= false;
	   if (event != null){
		  switch(event.getType()){
		  
			  case SDLEventType.MOUSEBUTTONUP:  	
				  release = true;
				  break;
		  }
	   }
		return release;	
	}
	public boolean isMouseClicked (CSprite sprite){
		boolean click = false;
	
	   int posX;
	   int posY;
	    
	   SDLMouseButtonEvent eventMouse ;

	   try{
		   if (event != null){
			  switch(event.getType()){
			  
				  case SDLEventType.MOUSEBUTTONDOWN:
					  	
					  eventMouse = ((sdljava.event.SDLMouseButtonEvent)event);
					  
					  posX = eventMouse.getX();
					  posY = eventMouse.getY();
	
					  if ((posY > sprite.gety() )&&
						  (posY < sprite.gety()+sprite.geth())&&
						  (posX > sprite.getx() )&&
						  (posX < sprite.getx() + sprite.getw()) ){
						  
						  click = true;
					  }
			  }
		   }
	   }
	   catch(Exception e){
		   System.out.println("Error Event.isMouseClicked sprite "+e);
	   }
	
		return click;	
	}
	
	public boolean isMouseClicked (int x, int w, int y, int h){
		boolean click = false;
		
	   int posX;
	   int posY;
	    
	   SDLMouseButtonEvent eventMouse ;

	   try{
		   if (event != null){
			  switch(event.getType()){
			  
				  case SDLEventType.MOUSEBUTTONDOWN:
					  	
					  eventMouse = ((sdljava.event.SDLMouseButtonEvent)event);
					  
					  posX = eventMouse.getX();
					  posY = eventMouse.getY();
	
					  if ((posY > y )&&
						  (posY < y+h)&&
						  (posX > x )&&
						  (posX < x + w) ){
						  
						  click = true;
					  }
			  	}
		   }
	   }
	   catch(Exception e){
		   System.out.println("Error Event.isMouseClicked pos "+e);
	   }
	
		return click;	
	}
	@SuppressWarnings("static-access")
	public void setEvent(){
		
		try{
		   event=event.pollEvent();
		   
		   sym = ((SDLKeyboardEvent)event).getSym();
			 switch(event.getType()){
			  case SDLEventType.KEYDOWN:
				  	released = false;
				  	pressed = true;
				  	
				  	break;
			  case SDLEventType.KEYUP:
				  	released = true;
				  	pressed = false;
				  	break;
			 }
		}
		catch(Exception e){
			
			sym = -1;
			released = false;
			pressed = false;
		}
	}
	public boolean isPressed(){
	
		return (pressed);
	}
	public boolean isReleased(){
	
		return (released);
	}
	public int getSym(){
		
		return sym;
	}
	
	public char getKey(){
		char c = (char)sym;
		return c;
	}
	public boolean isKey(){
		
		return (sym >= aPressed) && (sym <= zPressed); 
	}
	public void setShift(boolean shift) {
		this.shift = shift;
	}
	public boolean isShift() {
		return shift;
	}

}
