

import java.util.ArrayList;
import java.util.List;

import sdljava.ttf.SDLTrueTypeFont;

import sdljava.video.SDLColor;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;

public class Text {

	private static SDLSurface ttext;		//creamos variable para mostrar texto

	private SDLColor fgcolor; 	//crea objects para el color de las fonts del texto

	private SDLTrueTypeFont font; 		//almacena el tipo de font

	private int marqueex;
	private String textmarquee;
	
	public Text() {
		marqueex = 800;
		fgcolor = new SDLColor();
		
		fgcolor.setRed(255);		//establecemos colores de primer plano
		fgcolor.setGreen(255);
		fgcolor.setBlue(255);
	
		
	}

	public void init(String name, int z) {
	
	
		try{
			font = sdljava.ttf.SDLTTF.openFont( name, z);//TTF_OpenFont(name,20); //cargamos la font
			}
			catch(Exception e){
				
				System.out.println("Error text.init(string,int) "+e);
			}
		
	}
	public void init(String name) {
		
		//System.out.println("init1");
		
		//System.out.println("init1.1");
		
		//System.out.println("init1.2");
		

		fgcolor = new SDLColor();
		
		fgcolor.setRed(255);		//establecemos colores de primer plano
		fgcolor.setGreen(255);
		fgcolor.setBlue(255);

		try{
		font = sdljava.ttf.SDLTTF.openFont( name, 20);//TTF_OpenFont(name,20); //cargamos la font
		}
		catch(Exception e){
			
			System.out.println("Error text.init(string) "+e);
		}
	}

	
	void setcolor(int r,int g, int b){
		
		fgcolor.setBlue(b);
		fgcolor.setRed(r);
		fgcolor.setGreen(g);
	}
	void drawtextCenter(String msg,SDLSurface screen,double minx,double maxx, double y) {

		try{
			ttext = font.renderTextBlended(msg,  fgcolor);//sdljava.ttf.SDLTrueTypeFont.renderTextShaded(msg, fgcolor, bgcolor);
	
			double middlex = (minx + maxx) / 2;
			int size = this.font.getPTSize();
			int posx = (int)middlex - (size/5*msg.length());
			SDLRect textpos = new SDLRect(0, 0);
			
			textpos.x =  posx;
			textpos.y = (int) y;
	
			ttext.blitSurface(screen, textpos);
		
		}
		catch(Exception e){
			System.out.println("error Text.drawtext "+e);
		}
	}
	
	void drawtext(String msg,SDLSurface screen,double x, double y) {

		try{
			ttext = font.renderTextBlended(msg,  fgcolor);//sdljava.ttf.SDLTrueTypeFont.renderTextShaded(msg, fgcolor, bgcolor);
	
			SDLRect textpos = new SDLRect(0, 0);
			textpos.x = (int) x;
			textpos.y = (int) y;
	
			ttext.blitSurface(screen, textpos);
		
		}
		catch(Exception e){
			System.out.println("error Text.drawtext "+e);
		}
	}
	public void resetmarquee(){
		marqueex = 800;
	}
	public void drawmarquee(String msg,SDLSurface screen, double y) {
		
		try{
			if (marqueex ==800){
				textmarquee = msg ;
			}
			ttext = font.renderTextBlended(textmarquee,  fgcolor);//sdljava.ttf.SDLTrueTypeFont.renderTextShaded(msg, fgcolor, bgcolor);
	
			SDLRect textpos = new SDLRect(0, 0);
			textpos.x = (int) marqueex;
			textpos.y = (int) y;
	
			ttext.blitSurface(screen, textpos);
		
			marqueex = marqueex -5 ;
			
			if (textmarquee.length() > 0){
				
				if (marqueex == 0){
					marqueex = 10;
					textmarquee = textmarquee.substring(1);
				}
			}
			if (textmarquee.length() == 0){
				marqueex = 800;
			}
		}
		catch(Exception e){
			System.out.println("error Text.drawtext "+e);
		}
		
	}

	public static String[] split(String str, int length){
		String[] strings = str.split(" ");
		String string = "";
		String word;
		List<String> list = new ArrayList<String>();
		int lines = 1;
		
		for (int i = 0; i< strings.length;i++){ //separador de cadenas
			word  = strings[i];
		
			if (string.length()+word.length() < length){
				string+=" "+word;
			}
			else{
				list.add(string);
				string=word;
				lines++;
			}
			
			
		}
		list.add(string);
		return ( (String[]) list.toArray(new String[0] ) );
		
	}
	
}