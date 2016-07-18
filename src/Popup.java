
import java.io.File;
import sdljava.video.SDLSurface;

public class Popup {
	
	private Text textArial15;
	
	private	boolean end;
	
	private CSprite spritepopup;
	private String title;
	private String message;
	
	private CFrame framebutton;
	private CSprite[] spritebutton;
	private Event event;
	
	private int option;
	private int maxoption;
	private String[] buttonmessage;
	
	CFrame[] framepopups;
	
	public Popup(){
		
		try{
			 textArial15 = new Text();
			    textArial15.init("fonts" + File.separator + "arial.ttf",15);
			    textArial15.setcolor(30, 30,30);
		}
		catch(Exception e){
		
		}
		
		this.message = "";
		this.title  ="";
		this.option = 0;
		this.maxoption = 3;
		
		this.buttonmessage = new String[maxoption];
		
		this.event = new Event();
		 
		this.end = false;
		
		this.framebutton = new CFrame();
		
		this.spritebutton = new CSprite[3];
		for (int i = 0;i <this.spritebutton.length;i++){
			this.spritebutton[i] = new CSprite();
		}

		this.spritepopup = new CSprite();
		framepopups = new CFrame[10];
		String dir = "";
		try{
			for (int i=0;i<=9;i++ ) {
				
				framepopups[i] = new CFrame();
				dir = "sprites"+  File.separator +"popup" + File.separator +"popup"+i+".png";
		
				framepopups[i].load(dir);
				
				spritepopup.addframe(framepopups[i]);
			}
			
		}
		catch(Exception e){
			
		}
		
	    try{
	    	this.framebutton.load("sprites"+ File.separator +"popup"+File.separator +"popupbutton.png");
	    	
			for (int i = 0;i <this.spritebutton.length;i++){
				this.spritebutton[i].addframe(framebutton);
			}
	    	
	    	//this.framepopup.load("sprites"+  File.separator + "popup.png");
	    	//this.spritepopup.addframe(framepopup);
	    }
	    catch(Exception e){
	    	System.out.println("Error Popup() " + e);
	    }

	}
	public void draw(SDLSurface screen){
		
		String[] strings;
	
		try{
				
			this.spritepopup.setx(200);
			this.spritepopup.sety(200);
		
			this.spritepopup.draw(screen);
			
			this.textArial15.drawtextCenter(this.title, screen,200,500,210);
			strings = Text.split(message,35); 
			for (int i = 0; i< strings.length;i++){ //separador de cadenas
				
				this.textArial15.drawtext(strings[i] , screen,220,i*20+250);
			}
			
			if (this.maxoption ==0){
				
				this.spritebutton[0].setx(this.spritepopup.getx() + 90);
				this.spritebutton[0].sety(this.spritepopup.gety() + 140);
				this.spritebutton[0].draw(screen);
				
				textArial15.setcolor(255, 0, 0);
				textArial15.drawtextCenter(this.buttonmessage[0], screen, spritebutton[0].getx(), spritebutton[0].getx()+spritebutton[0].getw(),this.spritepopup.gety() +  150);
				textArial15.setcolor(0,0,0);
			}
			if (this.maxoption ==1){
				
				this.spritebutton[0].setx(this.spritepopup.getx() + 40);
				this.spritebutton[0].sety(this.spritepopup.gety() + 140);
				this.spritebutton[0].draw(screen);

				this.spritebutton[1].setx(this.spritepopup.getx() + 150);
				this.spritebutton[1].sety(this.spritepopup.gety() + 140);
				this.spritebutton[1].draw(screen);
				
				if(this.option ==0)	textArial15.setcolor(255, 0, 0);
				textArial15.drawtextCenter(this.buttonmessage[0], screen, spritebutton[0].getx(), spritebutton[0].getx()+spritebutton[0].getw(),this.spritepopup.gety() +  150);
				if(this.option ==0) textArial15.setcolor(0,0,0);
				
				if(this.option ==1)	textArial15.setcolor(255, 0, 0);		
				textArial15.drawtextCenter(this.buttonmessage[1], screen, spritebutton[1].getx(), spritebutton[1].getx()+spritebutton[1].getw(),this.spritepopup.gety() +  150);
				if(this.option ==1)	textArial15.setcolor(0,0,0);

			}
			if (this.maxoption ==2){
				
				this.spritebutton[0].setx(this.spritepopup.getx() + 30);
				this.spritebutton[0].sety(this.spritepopup.gety() + 140);
				this.spritebutton[0].draw(screen);
				
				this.spritebutton[1].setx(this.spritepopup.getx() + 120);
				this.spritebutton[1].sety(this.spritepopup.gety() + 140);
				this.spritebutton[1].draw(screen);
				
				this.spritebutton[2].setx(this.spritepopup.getx() + 210);
				this.spritebutton[2].sety(this.spritepopup.gety() + 140);
				this.spritebutton[2].draw(screen);
				
				if(this.option ==0)	textArial15.setcolor(255, 0, 0);		
				textArial15.drawtextCenter(this.buttonmessage[0], screen, spritebutton[0].getx(), spritebutton[0].getx()+spritebutton[0].getw(),this.spritepopup.gety() +  150);
				if(this.option ==0) textArial15.setcolor(0,0,0);
				
				if(this.option ==1)	textArial15.setcolor(255, 0, 0);		
				textArial15.drawtextCenter(this.buttonmessage[1], screen, spritebutton[1].getx(), spritebutton[1].getx()+spritebutton[1].getw(),this.spritepopup.gety() +  150);
				if(this.option ==1)	textArial15.setcolor(0,0,0);
				
				if(this.option ==2)	textArial15.setcolor(255, 0, 0);	
				textArial15.drawtextCenter(this.buttonmessage[2], screen, spritebutton[2].getx(), spritebutton[2].getx()+spritebutton[2].getw(),this.spritepopup.gety() +  150);
				if(this.option ==2)	textArial15.setcolor(0,0,0);
			}
	
			screen.flip(); //Pintamos la pantalla
		
		}
		catch(Exception e){
			System.out.println("Error Popup.draw " + e);

		}
	}
	public void keyboard(SDLSurface screen) {
		
		try {
			int sym = 0;
			event.setEvent();
			
			if (event.isPressed()){
						
				sym = event.getSym();
		    	switch (sym){
		    	
			    	/*case -2 : 
			    		
			    		break;
		    	*/
			    	case 273:
			    		if (this.option > 0 ){
			    			
			    			this.option--;
	
			    		}
			    		break;
			    	case 274:
			    		if (this.option < this.maxoption){
			    			this.option++;
			    				
			    		}
			    		break;
			    	case 13: //ENTER
			    		
						this.end = true;
		
			    		break;
		    	}
		    	this.draw(screen);
			}
		}
		catch(Exception e){
			System.out.println("Error popup.keyboard "+e);
		}
	    	
	}
	public void set(String title,String message){
		this.title = title;
		this.message = message +" Pulse intro para continuar";
		this.maxoption=0; 
	}
	public void set(String title,String message,String button0){
		this.title = title;
		this.message = message;
		this.buttonmessage[0] = button0;
		this.maxoption = 0;
	}
	
	public void set(String title,String message,String button0,String button1){
		this.title = title;
		this.message = message;
		this.buttonmessage[0] = button0;
		this.buttonmessage[1] = button1;
		this.maxoption = 1;
		
	}
	public void set(String title,String message,String button0,String button1,String button2){
		this.title = title;
		this.message = message;
		this.buttonmessage[0] = button0;
		this.buttonmessage[1] = button1;
		this.buttonmessage[2] = button2;
		this.maxoption = 2;
	}

	public int go (SDLSurface screen){
		
		this.spritepopup.setx(200);
		this.spritepopup.sety(200);
		this.spritepopup.animate(screen);
		
		this.draw(screen);
		
		while(!this.end){
			this.spritepopup.setx(200);
			this.spritepopup.sety(200);
			
			this.keyboard(screen);
			
			for (int i=0;i<=maxoption;i++){
			
				if (event.isMouseClicked(spritebutton[i])){
				
					this.end = true;
					this.option = i;
				}
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
