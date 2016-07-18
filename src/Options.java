
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import file.FileBrowser;
import sdljava.SDLException;
import sdljava.video.SDLSurface;

public class Options {

	private static boolean online;
	private boolean end ; //determina cuando salir del programa

	private Text textArabica8;
	private Text textCalculator40;
	private Text textCalculator20;

	
	private CFrame frameBackgroundOptions;
	private CSprite spriteBackgroundOptions;
	
	private Event event;
	
	private CFrame framebordercircleoff;
	private CFrame framebordercircleon;
	private CFrame frameborderoff;
	private CFrame frameborderon;
	private CFrame framebuttoncircle;
	private CFrame framebuttonoff;
	private CFrame framebuttonon;
	
	private CSprite spritebordercircle;
	private CSprite spriteborder;
	private CSprite spritebutton;
	private CSprite spritebuttoncircle;
	
	private  final int maxoption =5;

	private int numoption;
    
	private static boolean metronome = false ;
	public static boolean auto = false;

	public static Locale local ;
	

	public Options(){
		
		this.initsprites();
		this.init();
	}
    public void initsprites() {
		
    	//init sprites

		framebordercircleoff= new CFrame();
		framebordercircleon= new CFrame();
		frameborderoff= new CFrame();
		frameborderon= new CFrame();
		framebuttoncircle= new CFrame();
		framebuttonoff= new CFrame();
		framebuttonon= new CFrame();
			
		spritebordercircle = new CSprite();
		spriteborder = new CSprite();
		spritebutton = new CSprite();
		spritebuttoncircle = new CSprite();

		frameBackgroundOptions = new CFrame();
		spriteBackgroundOptions = new CSprite();
		
		try {
			framebordercircleoff.load("sprites"+  File.separator + "bordercircleoff.png");
			framebordercircleon.load("sprites"+  File.separator + "bordercircleon.png");
			frameborderoff.load("sprites"+  File.separator + "borderoff.png");
			frameborderon.load("sprites"+  File.separator + "borderon.png");
			framebuttonoff.load("sprites"+  File.separator + "buttonoff.png");
			framebuttonon.load("sprites"+  File.separator + "buttonon.png");
			framebuttoncircle.load("sprites"+  File.separator + "buttoncircle.png");
	    			
	    	spritebordercircle.addframe(framebordercircleoff);
	    	spritebordercircle.addframe(framebordercircleon);
	    	spriteborder.addframe(frameborderoff);
	    	spriteborder.addframe(frameborderon);
	    	
	    	spritebutton.addframe(framebuttonoff);
	    	spritebutton.addframe(framebuttonon);
	    	spritebuttoncircle.addframe(framebuttoncircle);
	  
	    	frameBackgroundOptions.load("sprites"+  File.separator + "pianooptions.png");
			spriteBackgroundOptions.addframe(frameBackgroundOptions);
		}
		catch(Exception e){
			System.out.println("Error Options.initsprites "+e );
		}
    }
	@SuppressWarnings("static-access")
	void draw(SDLSurface screen){
		//System.out.println("draw");
		
		try {
			//staff
			spriteBackgroundOptions.draw(screen);
	
			spritebutton.setx(160);
			spritebutton.sety(205);
			
			String staff = Options.getOption("staff");
			if (staff == null || staff.equals("off")){
				spritebutton.setframe(0);

			}
			else{
				spritebutton.setframe(1);

			}
			spritebutton.draw(screen);
			
			if (numoption==0){
				spriteborder.setframe(1);
			}
			else{
				spriteborder.setframe(0);
			}
			
			spriteborder.setx(160);
			spriteborder.sety(205);
			spriteborder.draw(screen);
			textArabica8.drawtext(Language.getString("Options.Sheet"), screen, 170, 210);
			
			//fullscreen
			String fullscreen= Options.getOption("Options.Fullscreen");

			if (fullscreen == null || fullscreen.equals("off")){
				
				spritebutton.setframe(0);
			}
			else{
				spritebutton.setframe(1);
			}
			
			spritebutton.sety(260); // + 55
			spritebutton.draw(screen);
			
			if (numoption==1){
				spriteborder.setframe(1);
			}
			else{
				spriteborder.setframe(0);
			}
			spriteborder.sety(260);
			spriteborder.draw(screen);
			textArabica8.drawtext(Language.getString("Options.Window"), screen, 170, 265);
		
		//language
			textArabica8.drawtext(Language.getString("Options.LANGUAGE"), screen, 160, 305);
			//text.drawtext("-", screen, 210, 370);
			
			if (numoption ==2){
				spritebordercircle.setframe(1);
			}
			else{
				spritebordercircle.setframe(0);
			}
			
			spritebuttoncircle.setx(160);
			spritebuttoncircle.sety(310);
			spritebuttoncircle.draw(screen);
			
			spritebordercircle.setx(160);
			spritebordercircle.sety(310);
			spritebordercircle.draw(screen);
		
			
			
			textCalculator40.drawtext(String.valueOf(this.local.getDefault().getLanguage()), screen, 300, 310);

	//conectivity
			
			if (numoption ==3){
				spritebordercircle.setframe(1);
			}
			else{
				spritebordercircle.setframe(0);
			}
			
			spritebuttoncircle.setx(160);
			spritebuttoncircle.sety(360);
			spritebuttoncircle.draw(screen);
			
			spritebordercircle.setx(160);
			spritebordercircle.sety(360);
			spritebordercircle.draw(screen);
		
			
			
			textArabica8.drawtext(Language.getString("Options.CONECTIVITY"), screen, 160, 350);
		
			String conectivity = Options.getOption("Conectivity");
			if (conectivity == null){
				textCalculator20.drawtext(Language.getString("Options.Question"), screen, 300, 380);
			
			}
			else{
				textCalculator20.drawtext(Language.getString("Options."+conectivity), screen, 300, 380);

			}
			
		//devicemidi
			if (numoption ==4){
				spritebordercircle.setframe(1);
			}
			else{
				spritebordercircle.setframe(0);
			}
			
			spritebuttoncircle.setx(160);
			spritebuttoncircle.sety(420);
			spritebuttoncircle.draw(screen);
			
			spritebordercircle.setx(160);
			spritebordercircle.sety(420);
			spritebordercircle.draw(screen);
		
			
			textArabica8.drawtext(Language.getString("Options.MIDIDEVICE"), screen, 160, 410);
			
			textArabica8.drawtext(Language.getString("Options.NAME"), screen, 300, 410);
			textArabica8.drawtext(Language.getString("Options.DESCRIPTION"), screen, 515, 410);

			String maxstr;
			maxstr = Piano.getDeviceInfo(Piano.getDeviceSelected()).getName();
			if (maxstr.length() > 15){
				maxstr = maxstr.substring(0, 15)+".";
			}
			textCalculator20.drawtext(maxstr, screen, 300, 440);
			
			maxstr = Piano.getDeviceInfo(Piano.getDeviceSelected()).getDescription();
			if (maxstr.length() > 25){
				maxstr = maxstr.substring(0, 25)+".";
			}
			textCalculator20.drawtext(maxstr, screen, 510, 440);

	    	screen.flip(); //Pintamos la pantalla
	    	   
		  	//screen.fillRect(0); //borramos la pantalla anterior	
		} catch (SDLException e) {
			System.out.println("Error options.draw"+e);
		}
		
	}
	@SuppressWarnings("static-access")
	public void init() { //inicializa
	
		
		event = new Event();
		 		
		textCalculator20 = new Text();
	    textCalculator20.init("fonts"+  File.separator + "calculator.ttf",20);
	    textCalculator20.setcolor(47, 131, 43);
		    
	    textCalculator40 = new Text();
	    textCalculator40.init("fonts"+  File.separator + "calculator.ttf",40);
	    textCalculator40.setcolor(47, 131, 43);

	    textArabica8 = new Text();
	    textArabica8.init("fonts"+  File.separator + "arabica.ttf",8);

	    textArabica8.setcolor(87, 88,90);
	    
		String language = Options.getOption("language");

		try{
			Locale l = new Locale(language,language);
			local.setDefault(l);
		}
		catch(Exception e){ //si el método string da error, esque no está bien inicializado local
			System.out.println("Error Options.init al inicializar bundle "+language+ " "+e);
			

			String nextLanguage = "";
			
			boolean exist=false;
			int i =0 ;
			while ((!exist) && (i<FileBrowser.getnumfiles("language") ) ){ //buscamos un idioma
				String file = FileBrowser.getfile("language", i);
				if ((file.length()==22)&&(file.endsWith("properties"))){
					exist = true;	
					nextLanguage = (String) file.subSequence(9,11); //obtenemos 'es','en',etc
				}
				i++;
			}
			if (!exist){ //si después del bucle no ha encontrdao ningún properties, cerramos la aplicación
			
				System.out.println("No existe ningún archivo de idioma .properties en la carpeta language");
				System.out.println("Instale un archivo de idioma y reinicie la aplicación");
				System.exit(-1);

			}
			else{
				local.setDefault(new Locale(nextLanguage,nextLanguage));
				Options.setOption("language",nextLanguage);

				System.out.println("Establecido idioma "+nextLanguage+" por defecto");
			}
		}
    
	}

	public void keyboard(SDLSurface screen) {
		
		int sym = 0;
		event.setEvent();
		try{
			if (event.isPressed()){
					
				sym = event.getSym();
	
				switch (sym){
	    	
			    	case 27 : 
			    		
			 	    	this.end = true;
			 	    	//event.waitrelease();
			    		break;
		    	
			    	case 276://izquierda
			    		break;
			    	case 275:
			    		break;
			    	case 273:
			    		if (this.numoption > 0 ){
			    			this.numoption--;
			    		}
			    		break;
			    	case 274:
			    		if (this.numoption+1 < this.maxoption){
			    			this.numoption++;
			    		}
			    		break;
			    	case 13: //enter
			    		
			    		changeOption(screen);
			    		
		
			    		break;
				}//fin switch
				this.draw(screen);
			}//fin if (event.isPressed())
		}//fin try
		catch(Exception e){
			System.out.println("Error options.keyboard "+e);
		}
	}

	@SuppressWarnings("static-access")
	private void changeOption(SDLSurface screen){
		
		if (numoption==0){ //modo partitura/vertical
			
			String staff = Options.getOption("staff");
			if (staff == null || staff.equals("off")){
				Options.setOption("staff", "on");
			}
			else{
				Options.setOption("staff", "off");
				
			}
		}
		if (numoption==1){ //pantalla completa
		
			String fullscreen = Options.getOption("fullscreen");
			if (fullscreen == null || fullscreen.equals("off")){
				Options.setOption("fullscreen", "on");
			}
			else{
				Options.setOption("fullscreen", "off");
				
			}
			screen.wmToggleFullScreen();
		}

		if (numoption==2){ //language
			
			try{
				String language = Options.getOption("language");
				
				List<String> list = new ArrayList<String>();
				String nextLanguage ="en";
				int numLanguages = 0;
				for (int i =0; i<FileBrowser.getnumfiles("language");i++){
					String file = FileBrowser.getfile("language", i);
					if ((file.length()==22)&&(file.endsWith("properties"))){
						numLanguages++;	
						list.add((String) file.subSequence(9,11));
					}
				}
				if (language != null){

					int sel = list.indexOf(language); //devuelve el índice actualmente seleccionado

					if (sel==numLanguages-1){
						nextLanguage = list.get(0);
					}
					else{
						nextLanguage = list.get(sel+1);
					}
				}
				else{
					
					nextLanguage = list.get(0);
				}
	
				Options.setOption("language",nextLanguage);
				local.setDefault(new Locale(nextLanguage,nextLanguage ));
			}
			catch(Exception e){
				System.out.println("Options.changeOption error al cambiar de idioma "+e);
			}
			
		}
		if	(numoption==3){ //conectivity
			
			String conectivity = Options.getOption("conectivity");
			if (conectivity == null){
				Options.setOption("conectivity", "offline");
			}
			else{
				if (conectivity.equals("offline")){
					Options.setOption("conectivity", "online");
				}
				else{
					Options.setOption("conectivity", null);
				}

			}

		}
		
		if	(numoption==4){ //midiDevice
			
			Piano.changeDevice();
		}
	}
	int go(SDLSurface screen){
		
		this.draw(screen);
		
		this.end = false;
		while(!this.end){
				
			try {
				this.keyboard(screen);
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		return 0 ;
	}
	public static void setOnline(boolean online) {
		Options.online = online;
	}
	public static boolean isOnline() {
		return online;
	}

	

	public static void setMetronome(boolean metronome) {
		Options.metronome = metronome;
	}

	public static boolean isMetronome() {
		return metronome;
	}
	public static void setOption(String option, String value){
		
		FileWriter myFileWriter = null;

		try
		{
			
			if (Options.getOption(option)!= null){
				FileBrowser.removeLineFromFile(option, FileBrowser.getFileOptions(),"=");
			}
			if (value != null){
				myFileWriter = new FileWriter("data" + File.separator+ FileBrowser.getFileOptions(),true);
				myFileWriter.write(option); 
				myFileWriter.write("="); 
				myFileWriter.write(value); 
				myFileWriter.write('\n'); 
				myFileWriter.close();
			}
		}
		catch(Exception e){
			System.out.println("Error filesong.addsong "+e);
		}
	}
	public static String getOption(String option){
		
		BufferedReader bf = null;
		String str;
	    String[] row;

	    String value= null;
	    
	     try {
	 	    bf  = FileBrowser.getfile("data",FileBrowser.getFileOptions()) ;

			while ((str = bf.readLine())!=null)  {
			  
				row = str.split("="); //partimos la linea en una fila con columnas
			     
			     if (row[0].equalsIgnoreCase(option)){
			  	
					value = row[1]; 
			     }
			}
			bf.close();
			
		} catch (Exception e) {
			System.out.println("Error Options.getOption "+option+ " "+e);
		}   
		finally{
			
		}
	  
		
		return value;
	}

}
