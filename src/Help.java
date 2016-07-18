
import java.io.File;

import sdljava.SDLException;
import sdljava.ttf.SDLTTF;
import sdljava.video.SDLSurface;


public class Help {

	Text text;
	CFrame framewindow ;
	CFrame framewindowred;
	
	CSprite spritewindow;
	boolean active;
	
	CFrame framekeyup;
	CFrame framekeydown;
	CFrame framekeyleft;
	CFrame framekeyright;
	CFrame framekeyenter;
	CFrame framekeyF1;
	CFrame framekeyesc;
	CFrame framekeyp;
	
	CSprite spritekeyup;
	CSprite spritekeydown;
	CSprite spritekeyleft;
	CSprite spritekeyright;
	CSprite spritekeyenter;
	CSprite spritekeyF1;
	CSprite spritekeyesc;
	CSprite spritekeyp;
	
	String  cl ;
	
	private int dify= 40;
	private int inity = 70;
	private int initx = 10;
	private final int f1x = 10;
	private final int f1y = 10;
	private final int inittext = 70;
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	Help (Class<?> cl) {
		
		try{
			SDLTTF.init();
		    text = new Text();
		    text.init("fonts"+  File.separator + "arabica.ttf",14);
		    text.setcolor(255,255,255);
		    
			framewindow = new CFrame();
			framewindowred = new CFrame();
			spritewindow = new CSprite();
			
			framewindow.load("sprites"+File.separator+"window.png");
	
			spritewindow.addframe(framewindow);
	
			initspriteskeys();
			active = false;
			
			this.cl = cl.toString();
		}
		catch(Exception e){
			System.out.println("Error Help constructor");
		}
		
	}
	private void initspriteskeys(){
		
		 framekeyup= new CFrame();
		 framekeydown= new CFrame();
		 framekeyleft= new CFrame();
		 framekeyright= new CFrame();
		 framekeyenter= new CFrame();
		 framekeyF1= new CFrame();
		 framekeyesc= new CFrame();
		 framekeyp= new CFrame();
		
		 spritekeyup= new CSprite();
		 spritekeydown= new CSprite();
		 spritekeyleft= new CSprite();
		 spritekeyright= new CSprite();
		 spritekeyenter= new CSprite();
		 spritekeyF1= new CSprite();
		 spritekeyesc= new CSprite();
		 spritekeyp= new CSprite();
		 
		 try{
			 framekeyup.load("sprites"+File.separator+"keys"+File.separator+"up.png");
			 framekeydown.load("sprites"+File.separator+"keys"+File.separator+"down.png");
			 framekeyleft.load("sprites"+File.separator+"keys"+File.separator+"left.png");
			 framekeyright.load("sprites"+File.separator+"keys"+File.separator+"right.png");
			 framekeyenter.load("sprites"+File.separator+"keys"+File.separator+"enter.png");
			 framekeyesc.load("sprites"+File.separator+"keys"+File.separator+"esc.png");
			 framekeyF1.load("sprites"+File.separator+"keys"+File.separator+"F1.png");
			 framekeyp.load("sprites"+File.separator+"keys"+File.separator+"p.png");
		 
			 spritekeyup.addframe(framekeyup);
			 spritekeydown.addframe(framekeydown);
			 spritekeyleft.addframe(framekeyleft);
			 spritekeyright.addframe(framekeyright);
			 spritekeyenter.addframe(framekeyenter);
			 spritekeyesc.addframe(framekeyesc);
			 spritekeyF1.addframe(framekeyF1);
			 spritekeyp.addframe(framekeyp);

		 }
		 catch(Exception e){
			 
		 }
		
	}
	void draw (SDLSurface screen) {

		String quotes = ">>";
		int options = 0;
		try {

			if (active){
				
				quotes = "<<";
				
				if (cl.equalsIgnoreCase("class main")){
						options = 4;
				}
				if (cl.equalsIgnoreCase("class selectsong")){
						options = 5;
				}
				if (cl.equalsIgnoreCase("class selectpractice")){
					options = 4;
				}	
				for (int i = 0;i<options+1;i++){
					
					spritewindow.sety(i*50);
					spritewindow.draw(screen);

				}
				
				if (cl.equalsIgnoreCase("class main")){
					drawmain(screen);
				}
				if (cl.equalsIgnoreCase("class selectsong")){
					this.drawselectsong(screen);
				}
				if (cl.equalsIgnoreCase("class selectpractice")){
					this.drawselectpractice(screen);
				}
				
			}
			else{
				spritewindow.sety(0);
				spritewindow.draw(screen);
	
			}

			spritekeyF1.setx(f1x);
			spritekeyF1.sety(f1y);
			spritekeyF1.draw(screen);
			
			text.drawtext(quotes, screen, 70, f1y);
			text.drawtext(Language.getString("Help"), screen, 100, f1y);
			
			screen.flip(); //Pintamos la pantalla

			
		} catch (SDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	private void drawselectsong(SDLSurface screen){
		try{
			int y = inity ;
			
			spritekeyright.setx(initx);
			spritekeyright.sety(y);
			spritekeyright.draw(screen);
			text.drawtext(Language.getString("Help.SelectSong.NextSong"), screen, inittext, y);
			y +=dify;
			spritekeyleft.setx(initx);
			spritekeyleft.sety(y);
			spritekeyleft.draw(screen);
			text.drawtext(Language.getString("Help.SelectSong.PreviousSong"), screen, inittext, y);
			y +=dify;		
			spritekeyenter.setx(initx);
			spritekeyenter.sety(y);
			spritekeyenter.draw(screen);
			text.drawtext(Language.getString("Help.SelectSong.PlaySong"), screen, inittext,y);
			y +=dify;		
			spritekeyp.setx(initx);
			spritekeyp.sety(y);
			spritekeyp.draw(screen);
			text.drawtext(Language.getString("Help.SelectSong.Auto"), screen, inittext, y);
			y +=dify;	
			spritekeyesc.setx(initx);
			spritekeyesc.sety(y);
			spritekeyesc.draw(screen);
			text.drawtext(Language.getString("Help.Back"), screen, inittext, y);
		}
		catch(Exception e){
			
		}
	}
	
	private void drawmain(SDLSurface screen) {
		try{
			int y = inity;
			spritekeyright.setx(initx);
			spritekeyright.sety(y);
			spritekeyright.draw(screen);
			text.drawtext(Language.getString("Help.Main.NextOption"), screen, inittext, y);
			y += dify;
			spritekeyleft.setx(initx);
			spritekeyleft.sety(y);
			spritekeyleft.draw(screen);
			text.drawtext(Language.getString("Help.Main.PreviousOption"), screen, inittext,y);
			y += dify;		
			spritekeyenter.setx(initx);
			spritekeyenter.sety(y);
			spritekeyenter.draw(screen);
			text.drawtext(Language.getString("Help.Main.SelectOption"), screen, inittext, y);
			y += dify;	
			spritekeyesc.setx(initx);
			spritekeyesc.sety(y);
			spritekeyesc.draw(screen);
			text.drawtext(Language.getString("Help.Main.Exit"), screen, inittext, y);
		}
		catch(Exception e){
			
		}
	}
	private void drawselectpractice(SDLSurface screen) {
		try{
			int y = inity;
			spritekeyright.setx(initx);
			spritekeyright.sety(y);
			spritekeyright.draw(screen);
			text.drawtext(Language.getString("Help.Practice.NextLevel"), screen, inittext, y);
			y += dify;
			spritekeyleft.setx(initx);
			spritekeyleft.sety(y);
			spritekeyleft.draw(screen);
			text.drawtext(Language.getString("Help.Practice.PreviousLevel"), screen, inittext,y);
			y += dify;		
			spritekeyenter.setx(initx);
			spritekeyenter.sety(y);
			spritekeyenter.draw(screen);
			text.drawtext(Language.getString("Help.Practice.PlayLevel"), screen, inittext, y);
			y += dify;	
			spritekeyesc.setx(initx);
			spritekeyesc.sety(y);
			spritekeyesc.draw(screen);
			text.drawtext(Language.getString("Help.Back"), screen, inittext, y);
		}
		catch(Exception e){
			
		}
	}
}
