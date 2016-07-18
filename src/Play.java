
import java.io.File;
import java.util.Calendar;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import database.DbScores;
import file.FileScores;
import sdljava.video.SDLSurface;

public class Play {
	
	private Event event;
	private Piano piano;
	private boolean end ; //determina cuando salir del programa
	
	Calendar today;
	private String song ;

	private Text textArial15;
	private Text textCalculator20;
	private Text textCalculator40;
	   
	private Note[] listNote ; //objetos para las notas
	private Note[] listMetronome;
	
	private final int limitp1 = 450;
	private final int limitp2 = limitp1+10;
	private final int limitp3 = limitp1+20;
	private final int limitp4 = limitp1+30;

	private CFrame frameMetronomeBase;
	private CFrame[] frameMetronomePendulums;
	
	private CSprite spritePendulumBase; 
	private CSprite spritePendulum;
//keyboard
	private CFrame frameKey;
	private CFrame frameKeyPush;
	private CFrame frameKeyblack;
	private CFrame frameKeyblackPush;
	private CFrame frameLine;
//precision
	private CFrame frameOk;
	private CFrame frameBad;
	private CFrame framePerfect;
//staff
	private CFrame frameStaff;
	private CSprite spriteStaff;
//staffnotes	
	private CFrame frameNoteStaffwhole;
	private CFrame frameNoteStaffhalf;
	private CFrame frameNoteStaffquarter;
	private CFrame frameNoteStaffeighth;
	private CFrame frameNoteStaffsixteenth;
	//staffaux
	private CFrame frameNoteSharp;
	private CFrame frameNoteFlat;
	private CSprite spriteNoteSharp;
	private CSprite spriteNoteFlat;
//staffinversenotes
	private CFrame frameNoteStaffwholei;
	private CFrame frameNoteStaffhalfi;
	private CFrame frameNoteStaffquarteri;
	private CFrame frameNoteStaffeighthi;
	private CFrame frameNoteStaffsixteenthi;
	
	private CFrame framehudleft;
	private CFrame framehudright;
	
	private CSprite spritehud;
	
	private CFrame framehorizontalline;
	private CSprite spritehorizontalline;
//linenotes
	private CFrame framenotebar;
	private CFrame framenotebari;

	private CSprite spritenotebar;
	private CFrame frameNoteStaffAuxLine;
	
	private CSprite spriteNoteStaff;
	private CSprite spriteNoteStaffi;
	
	private CFrame frameQuarterBorder;
	private CSprite spriteQuarterBorder;
	
	private CSprite [] spritePrecision;
	
	private CSprite spriteNoteStaffAuxLine;
	
	private CSprite spriteLine;
	private CSprite spriteKey;
	private CSprite spriteKeyblack;
	
	private Midi midi;

	private final int verticalSpeed = 5;
	private int lastPrecision;
	private int lastNote; //variable que indica el ultimo objeto Nota usado
	private int lastMetronome; //variable que indica el ultimo objeto metronom usado
	private long inittime; //controla el numero de segundos actuales cuando es inicia el programa
	
	private long initfps;
	private long endfps;
	private long fps;
	private long time; //controla el tiempo actual, reiniciado cuando obtiene una nota
	private int channel;

	private   MidiPlayer player;

	private  int score;
	private int combo;

	private final int interval = 7500;
	private final  int maxprecision = 10;
	private final  int maxchannel = 5;
	private final  int maxnotes = 100;
	private final int numnotes= 24;
	
	private String staff;
	public Play(){
		this.initsprites();
		this.initgame();
		this.initobjects();
	}
	
	public void initsprites() {

		try{
			framehudleft = new CFrame();
			framehudright = new CFrame();
			spritehud = new CSprite();
			
			framehorizontalline = new CFrame();
			spritehorizontalline = new CSprite();
			
			framenotebar = new CFrame();
			framenotebari = new CFrame();
			spritenotebar = new CSprite();
		    
		    frameKey = new CFrame();
		    frameKeyblack = new CFrame();
		    
		    frameKeyPush = new CFrame();
		    frameKeyblackPush = new CFrame();
		    
		    frameLine = new CFrame();
		   
			 frameOk= new CFrame();
			 frameBad= new CFrame();
			 framePerfect= new CFrame();
	
			 frameQuarterBorder = new CFrame();
			 spriteQuarterBorder = new CSprite();
			 	 
			frameNoteStaffwhole = new CFrame();
			frameNoteStaffhalf = new CFrame();
			frameNoteStaffquarter = new CFrame();
			frameNoteStaffeighth = new CFrame();
			frameNoteStaffsixteenth = new CFrame();
			
			spriteNoteStaff  =new CSprite();
			
			frameNoteStaffwholei = new CFrame();
			frameNoteStaffhalfi = new CFrame();
			frameNoteStaffquarteri = new CFrame();
			frameNoteStaffeighthi = new CFrame();
			frameNoteStaffsixteenthi = new CFrame();
			
			spriteNoteStaffi  =new CSprite();
	
			frameNoteStaffAuxLine = new CFrame();
			
			 frameStaff = new CFrame();
		    
			 spriteStaff = new CSprite();
			 
			 spritePrecision = new CSprite[10];
	
		    spriteLine = new CSprite();
		    spriteKey = new CSprite();
		    spriteKeyblack = new CSprite();
		    
		    spriteNoteStaffAuxLine = new CSprite();
		    
		    frameNoteSharp = new CFrame();
		    spriteNoteSharp = new CSprite();
		    frameNoteSharp.load("sprites"+  File.separator +"staffmode"+File.separator + "sharp.png");
		    
		    spriteNoteSharp.addframe(frameNoteSharp);
		    
		    frameNoteFlat = new CFrame();
		    spriteNoteFlat = new CSprite();
		    frameNoteFlat.load("sprites"+  File.separator +"staffmode"+File.separator + "flat.png");
			spriteNoteFlat.addframe(frameNoteFlat);
			
			framenotebar.load("sprites"+  File.separator +"linemode"+File.separator+"notebar.png");
			framenotebari.load("sprites"+  File.separator +"linemode"+File.separator+"notebarinactive.png");
			spritenotebar.addframe(framenotebar);
			spritenotebar.addframe(framenotebari);
			
			frameNoteStaffwhole.load("sprites"+  File.separator +"staffmode"+File.separator +"whole.png");
			frameNoteStaffhalf.load("sprites"+  File.separator+"staffmode"+File.separator + "half.png");
			frameNoteStaffquarter.load("sprites"+  File.separator +"staffmode"+File.separator + "quarter.png");
			frameNoteStaffeighth.load("sprites"+  File.separator +"staffmode"+File.separator + "eighth.png");
			frameNoteStaffsixteenth.load("sprites"+  File.separator +"staffmode"+File.separator + "sixteenth.png");
			
			spriteNoteStaff.addframe(frameNoteStaffwhole);
			spriteNoteStaff.addframe(frameNoteStaffhalf);
			spriteNoteStaff.addframe(frameNoteStaffquarter);
			spriteNoteStaff.addframe(frameNoteStaffeighth);
			spriteNoteStaff.addframe(frameNoteStaffsixteenth);
			
			frameNoteStaffwholei.load("sprites"+  File.separator +"staffmode"+File.separator +"wholeinverse.png");
			frameNoteStaffhalfi.load("sprites"+  File.separator+"staffmode"+File.separator + "halfinverse.png");
			frameNoteStaffquarteri.load("sprites"+  File.separator +"staffmode"+File.separator + "quarterinverse.png");
			frameNoteStaffeighthi.load("sprites"+  File.separator +"staffmode"+File.separator + "eighthinverse.png");
			frameNoteStaffsixteenthi.load("sprites"+  File.separator +"staffmode"+File.separator + "sixteenthinverse.png");
			
			spriteNoteStaffi.addframe(frameNoteStaffwholei);
			spriteNoteStaffi.addframe(frameNoteStaffhalfi);
			spriteNoteStaffi.addframe(frameNoteStaffquarteri);
			spriteNoteStaffi.addframe(frameNoteStaffeighthi);
			spriteNoteStaffi.addframe(frameNoteStaffsixteenthi);
			
			frameNoteStaffAuxLine.load("sprites"+  File.separator +"staffmode"+File.separator + "auxline.png");
			
			spriteNoteStaffAuxLine.addframe(frameNoteStaffAuxLine);
		    
		    frameQuarterBorder.load("sprites"+  File.separator +"staffmode"+File.separator + "quarterborder.png");
		    spriteQuarterBorder.addframe(frameQuarterBorder);
		    
		    frameStaff.load("sprites"+  File.separator +"staffmode"+File.separator + "staff.png");
		    spriteStaff.addframe(frameStaff);
		   
			 frameOk.load("sprites"+  File.separator + "score"+File.separator+"ok.png");
			 frameBad.load("sprites"+  File.separator +"score"+File.separator+ "bad.png");
			 framePerfect.load("sprites"+  File.separator + "score"+File.separator+"perfect.png");
			 
		    for (int i=0;i<10;i++){
	
		    	spritePrecision[i] = new CSprite();
		    	
		    	spritePrecision[i].addframe(frameOk);
		    	spritePrecision[i].addframe(frameBad);
		    	spritePrecision[i].addframe(framePerfect);
		    }
			 
		    frameKey.load("sprites"+  File.separator + "key.png");
		    frameKeyPush.load("sprites"+  File.separator + "keypush.png");
		    spriteKey.addframe(frameKey);
		    spriteKey.addframe(frameKeyPush);
		    
		    frameKeyblack.load("sprites"+  File.separator + "keyblack.png");
		    frameKeyblackPush.load("sprites"+  File.separator + "keyblackpush.png");
		    spriteKeyblack.addframe(frameKeyblack);
		    spriteKeyblack.addframe(frameKeyblackPush);
		    
		    frameLine.load("sprites"+  File.separator +"linemode"+File.separator+ "line.png");
		    spriteLine.addframe(frameLine);
		    
		    framehorizontalline.load("sprites"+  File.separator + "linemode"+  File.separator +"horizontalline.png");
		    spritehorizontalline.addframe(framehorizontalline);
		    
		    framehudleft.load("sprites"+  File.separator + "hudleft.png");
		    framehudright.load("sprites"+  File.separator + "hudright.png");
		    spritehud.addframe(framehudleft);
		    spritehud.addframe(framehudright);
		    
		    frameMetronomeBase = new CFrame();
	    	frameMetronomeBase.load("sprites"+  File.separator + "metronome"+  File.separator + "base.png");

		    frameMetronomePendulums = new CFrame[5];
		    for (int i=0;i<5;i++){
		    	
		    	frameMetronomePendulums[i] = new CFrame();
		    	frameMetronomePendulums[i].load("sprites"+  File.separator + "metronome"+  File.separator + "pendulum"+(i+1)+".png");
		    }
		    spritePendulumBase = new CSprite();
		    spritePendulumBase.addframe(frameMetronomeBase);
		    
		    spritePendulum = new CSprite();
		    for (int i=0;i<5;i++){		    	
		    	 spritePendulum.addframe(frameMetronomePendulums[i]);
		    }

		}
		 catch(Exception e){
			 System.out.println("Error play.initsprites "+e);
		 }
	}
	
	public void initgame() { //inicializa

		staff = Options.getOption("staff");
		event = new Event();
		
		try{
			piano = new Piano();
		
		//piano.getdevices();
			piano.init();
		}
		catch(Exception e){
			System.out.println("Error play.draw , no se puede inicializar piano "+e);
			
		}

		player = new MidiPlayer();
		
		score = 0;
		combo = 1;
		
		end = false;

  	    try{
		    textArial15 = new Text();
		    textArial15.init("fonts"+  File.separator + "arial.ttf",15);
		    textArial15.setcolor(255, 255,255);
	    
		    textCalculator20 = new Text();
		    textCalculator20.init("fonts"+  File.separator + "calculator.ttf");
		    textCalculator20.setcolor(47, 131, 43);
		    
		    textCalculator40 = new Text();
		    textCalculator40.init("fonts"+  File.separator + "calculator.ttf",40);
		    textCalculator40.setcolor(47, 131, 43);
  		}
		catch(Exception e){
			System.out.println("Error play.draw , no se puede inicializar las fuentes "+e);
		}

	    
	}
	public void initsong(String song) {
		try{
			
			channel = 0;

	  	    today = Calendar.getInstance();
			inittime = today.getTimeInMillis() +2000;
	
			time = 0 ;
			lastPrecision=0;
		
			lastNote = 0;
	
			midi = new Midi();
			midi.initFile(song);
		    
		   			
		    for (int i=0;i<maxnotes;i++){
	
		    	listNote[i].setinactive();
		    }
		    
		    for (int i=0;i<maxprecision;i++){
	
		    	spritePrecision[i].setinActive();
		    }
		}
		catch(Exception e){
			System.out.println("Error play.initsong");
		}
	    
	}
	 public void initobjects() {
	    try{
	    		
	    	 
		     listNote = new Note[maxnotes]; //este máximo determina el número de notas simultaneas en funcionamiento
		  
		    for (int i=0;i<maxnotes;i++){
	
		    	listNote[i] = new Note();
		    }
		    
		    listMetronome = new Note[maxnotes]; //este máximo determina el número de notas simultaneas en funcionamiento
			  
		    for (int i=0;i<maxnotes;i++){
	
		    	listMetronome[i] = new Note();
		    }
	    }
		catch(Exception e){
			System.out.println("Error play.initobjects");
		}
    	
    }
	public void drawstaff(SDLSurface screen){
		try {
			spriteStaff.setx(10);
			spriteStaff.sety(130);
			spriteStaff.draw(screen);
		}
		catch(Exception e){
			System.out.println("Error play.drawstaff");
		}
	}
	public void drawstaffnotes(SDLSurface screen){
		try{
			for (int i=0;i<maxnotes;i++){ //pintar las notas
	  			
		  		if ( (listNote[i].isActive() ) && (listNote[i].getPush() ==1 ) ){ //no se pintan las notas de liberacion = numero de notas disponibles / 2
		  		
		  		//Pintado de auxiliares
		  			if ((listNote[i].getStaffy() == Midi.note2posStaff(60)) ||
		  				(listNote[i].getStaffy() == Midi.note2posStaff(81)) ||
		  				(listNote[i].getStaffy() == Midi.note2posStaff(84))){
		  				spriteNoteStaffAuxLine.setx(listNote[i].getStaffx());
		  				spriteNoteStaffAuxLine.sety(listNote[i].getStaffy());
		  				
		  				spriteNoteStaffAuxLine.draw(screen);
		  			}//pintamos el que tiene la línea auxiliar por abajo
		  			if (listNote[i].getStaffy() == Midi.note2posStaff(83)){
		  				spriteNoteStaffAuxLine.setx(listNote[i].getStaffx());
		  				spriteNoteStaffAuxLine.sety(listNote[i].getStaffy()+15);
		  				spriteNoteStaffAuxLine.draw(screen);
		  			}
		  			
		  		//Pintado de notas y bordes
		  			if (listNote[i].getNote() <= 71){ 
			  			spriteQuarterBorder.setx(320);
			  			spriteQuarterBorder.sety(listNote[i].getStaffy());
			  			spriteQuarterBorder.draw(screen);
			  		
			  			spriteNoteStaff.setframe(listNote[i].getDuration()-1);
			  			spriteNoteStaff.setx(listNote[i].getStaffx());
			  			spriteNoteStaff.sety(listNote[i].getStaffy());
			  			spriteNoteStaff.draw(screen);
		  			}
		  			else{
		  				spriteQuarterBorder.setx(320);
			  			spriteQuarterBorder.sety(listNote[i].getStaffy());
			  			spriteQuarterBorder.draw(screen);
			  		
			  			spriteNoteStaffi.setframe(listNote[i].getDuration()-1);
			  			spriteNoteStaffi.setx(listNote[i].getStaffx());
			  			spriteNoteStaffi.sety(listNote[i].getStaffy());
			  			spriteNoteStaffi.draw(screen);
		  			}	
		  		//pintado de bemoles y sostenidos
		  			if (Midi.isBlack(listNote[i].getNote())){
		  				
		  				if (Midi.isFlat(listNote[i].getNote())){
			  				spriteNoteFlat.setx(listNote[i].getStaffx()-20);
			  				spriteNoteFlat.sety(listNote[i].getStaffy());
			  				spriteNoteFlat.draw(screen);
		  				}
		  				else{
		  					spriteNoteSharp.setx(listNote[i].getStaffx()-20);
			  				spriteNoteSharp.sety(listNote[i].getStaffy());
			  				spriteNoteSharp.draw(screen);
		  				}
		  			}
		  			
		  		}		  		
	  		}
		}
		catch(Exception e){
			System.out.println("Error play.drawstaffnotes "+e);
		}
	}
	public void drawkeyboard(SDLSurface screen){
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
		}
		catch(Exception e){
			System.out.println("Error play.drawkeyboard "+e);
		}
	}
	public void drawlines(SDLSurface screen){
		try{
			spriteLine.sety(10);
			spriteLine.setx(-5);
			
			spritehorizontalline.sety(450);
			spritehorizontalline.setx(20);
			spritehorizontalline.draw(screen);
		
		  	for (int l=0;l<=14;l++){ //bucle pintar lineas para las blancas
		  		
		  		spriteLine.setx(spriteLine.getx() + spriteKey.getw());
		  		spriteLine.draw(screen); //pinta lineas
		  		
		  	}
		  	spriteLine.setx(20);
		  	for (int l=0;l<=12;l++){ //bucle pintar lineas para las negras
		  	
		  		spriteLine.setx(spriteLine.getx() + spriteKey.getw());
		  		
		  		if ( (l != 2) && 
		  				(l != 6) && 
		  				(l != 9) ){ //no pintes donde no hay
		  			
		  			spriteLine.draw(screen); //pinta lineas 
		  		}
		  		
		  	}
		}
		catch(Exception e){
			System.out.println("Error play.drawlines");
		}
	}
	
	public void drawlinesnotes(SDLSurface screen){
		try
		{
		for (int i=0;i<maxnotes;i++){ //pintar las notas
		  		
		  		if ((listNote[i].getPush() ==1 ) &&  
		  				(listNote[i].getNote() >=60 )&& (listNote[i].getNote() <=84) ){ 
		  			//no se pintan las notas de liberacion = numero de notas disponibles / 2

	  				for (int j = 5;j>=listNote[i].getDuration();j--){
	  					
	  					if ((listNote[i].gety()+((j-5)*15) > 30)&&
	  							(listNote[i].gety()+((j-5)*15) < 500) ){
	  						
	  						if (listNote[i].isFailed()){
	  							spritenotebar.setframe(1);
	  						}
	  						else{
	  							spritenotebar.setframe(0);
	  						}
	  						
		  					spritenotebar.setx(listNote[i].getx());
			  				spritenotebar.sety(listNote[i].gety()+((j-5)*15) );
			  				spritenotebar.draw(screen);
	  					}
	  				}
		  		}
		  	}	
		}
		catch(Exception e){
			System.out.println("Error play.drawlinesnotes "+e);
		}
	}

	public void drawhud(SDLSurface screen){
		try{
			
			spritehud.setx(0);
			spritehud.setframe(0);
			spritehud.draw(screen);
			
			spritehud.setx(575);
			spritehud.setframe(1);
			spritehud.draw(screen);
	
			int seconds = Math.max(midi.getDurationRemaining((int) time - interval)/1000,0);
			int miliseconds = Math.max(midi.getDurationRemaining((int) time - interval)%1000,0);
			this.textArial15.drawtext(Language.getString("Play.Score"), screen, 10, 5); //pintamos un texto de prueba con el tiempo
		  	
			this.textArial15.drawtext(Language.getString("Play.TimeRemaining"), screen, 600, 5); //pintamos un texto de prueba con el tiempo
	
		  	this.textArial15.drawtext(Language.getString("Play.Multiplicator"), screen, 150, 5); //pintamos un texto de prueba con el tiempo
	
			this.textCalculator40.drawtext(seconds + "' "+ miliseconds+"''" , screen, 600, 30); //pintamos un texto de prueba con el tiempo

		  	this.textCalculator40.drawtext(String.valueOf(score) , screen, 30, 30); //pintamos un texto de prueba con el tiempo
		  	this.textCalculator40.drawtext(String.valueOf(combo), screen, 190, 30); //pintamos un texto de prueba con el tiempo
		
		  	spritePendulumBase.setx(475);
		  	spritePendulumBase.sety(5);
		  	spritePendulumBase.draw(screen);
		  	
		  	int time = (60000/player.gettempo()/5);
		  	spritePendulum.changeframeReverse(time);
		  	
		  	spritePendulum.setx(475);
		  	spritePendulum.sety(10);
		  	if (Options.isMetronome()) spritePendulum.draw(screen);
		  	
		}
		catch(Exception e){
			System.out.println("Error play.drawhud "+e);
		}
	}
	public void draw(SDLSurface screen){
		
		if (staff == null || staff.equals("off")){
			
			this.drawlines(screen);
	  		this.drawlinesnotes(screen);

			
		}		//Options.staffmode==1
		else{
			
			this.drawstaff(screen);
	  		this.drawstaffnotes(screen);

		}
	  	
		this.drawkeyboard(screen);
		
		this.drawhud(screen);
		
	  	try {
		  	for (int i = 0;i<10;i++){
		    	if (spritePrecision[i].isActive()){
		    		spritePrecision[i].draw(screen);	//Pintamos el acierto de las notas
		    	}
		  	}
	
		  	screen.flip(); //Pintamos la pantalla
		   
		  	screen.fillRect(0); //borramos la pantalla anterior

	  	}
	  	catch(Exception e){
	  		System.out.println("Error play.draw  "+e);
	  	}

	}
	public void getnote() throws MidiUnavailableException, InvalidMidiDataException { //obtiene la siguiente nota si el tiempo es mayor que el tick
		
		int track=0; //music	
		if ((midi.getNextTick(track) != -1) &&
			(time >= midi.getNextTick(track)*(1000/(double) midi.getresolution() )*(60/(double)player.gettempo()))){
	
			 listNote[lastNote] = midi.getNextNote(track);
			 
			 if( (listNote[lastNote].isActive()) && (listNote[lastNote].getPush()==1) &&
					 (listNote[lastNote].getNote() >=60 )&& (listNote[lastNote].getNote() <=84) ){
			    	//System.out.println("nota "  + listNote[lastNote].getNote());
			    	lastNote++;
			    	
			 }
		}
		track=1; //metronome	   
		if ((midi.getNextTick(track) != -1) &&
				(time >= midi.getNextTick(track)*(1000/(double) midi.getresolution() )*(60/(double)player.gettempo()))){

				 listMetronome[lastMetronome] = midi.getNextNote(track);
				 
				 if((listMetronome[lastMetronome].isActive()) && (listMetronome[lastMetronome].getPush()==1) ){
	  			    	lastMetronome++;
	  			    	
	  			 }
			}
				
	
	}
	public int getlowernote(int note){
	
		int y= 0;
		int lower=-1;
		for (int i = 0 ; i<this.maxnotes;i++){ 
			
			if ((listNote[i].getNote() == note) && (listNote[i].isActive())){
				
				if (y < listNote[i].gety()){
					
					y=listNote[i].gety();
					lower= i ;
					
				}
			}
		}
		return lower;
			
	}

	public void constant(SDLSurface screen) { //resto de procedimientos constantes del menu 1
	
		try{

		  	
			if (midi.getDurationRemainingseconds((int) time -interval)  < 0 ){
		
				if (!Options.auto){
					DbScores dbscores = new DbScores();
					FileScores filescores = new FileScores();
					
					java.util.Date utilDate = new java.util.Date();
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
					if (Options.isOnline()){
						dbscores.addScore(this.song, User.getUser().getName(), sqlDate,  score);
			
					}
					else{
						filescores.addScore(this.song, User.getUser().getName(), sqlDate,  score);
					}
				}
				this.end= true;
				
			}
			
			if (maxnotes -2<= lastNote){
				lastNote = 0;
			}
			if (maxnotes -2<= lastMetronome){
				lastMetronome = 0;
			}
			if (maxprecision -2<= lastPrecision){
				lastPrecision = 0;
			}
			
			if (maxchannel < channel ){
				channel = 0;
			}
			  for (int i=0;i<maxnotes;i++){ //bucle desaparecedor de notas, si no hemos pulsado saldra un bad
					
				  if ((listMetronome[i].isActive()) && (listMetronome[i].getPush()==1)){
					  
					 // System.out.println(listNote[i].getStaffx());
					  listMetronome[i].setStaffx(listMetronome[i].getStaffx()-verticalSpeed); //velocidad horizontal
					  
				  }
				
				listMetronome[i].sety(listMetronome[i].gety()+verticalSpeed); //velocidad verticalproyect
					
				if (
		  			((listMetronome[i].gety() > limitp1 - spritenotebar.geth()) && (listMetronome[i].isActive())) ){
		  			
		  			if ( Options.isMetronome() ) player.metronomeOn();
	
		  			if (this.spritePendulum.isBackward()){
		  				spritePendulum.setframe(0);
		  			}
		  			listMetronome[i].setActive(false);
		  		
		  		}
			  }
			  
		
			  for (int i=0;i<maxnotes;i++){ //bucle desaparecedor de notas, si no hemos pulsado saldra un bad
				
				  if ((listNote[i].isActive()) && (listNote[i].getPush()==1)){
					  
					 // System.out.println(listNote[i].getStaffx());
					  listNote[i].setStaffx(listNote[i].getStaffx()-verticalSpeed); //velocidad horizontal
					  
				  }
				
				listNote[i].sety(listNote[i].gety()+verticalSpeed); //velocidad verticalproyect
					
		  		if ((Options.auto) &&
		  			((listNote[i].gety() > limitp2 - spritenotebar.geth()) && (listNote[i].isActive())) ){
		  			
		  			player.noteOn(listNote[i].getNote());
		  			combo++;
		  			addScore("perfect");
		  			
		  			listNote[i].setActive(false);
		  		
		  		}
	    	  	if( (listNote[i].gety() > limitp4 - spritenotebar.geth()) && (listNote[i].isActive()) ){
	    	  		
					spritePrecision[lastPrecision].setframe(1);
	  				
	  				spritePrecision[lastPrecision].setx(listNote[i].getx());
	  				spritePrecision[lastPrecision].sety(450);
	  				spritePrecision[lastPrecision].setActive();
	  				spritePrecision[lastPrecision].setTimeactive();
	  				
	  				lastPrecision++;
	  				
	  				listNote[i].setFailed(true);
	  				combo = 1;
	    	  		
	    	  		listNote[i].setActive(false);
	
	  			}
			} //END for
		  	
		  	for (int i = 0;i<10;i++){
		    	if (spritePrecision[i].getTimeactive() == 0){
		    		spritePrecision[i].setinActive();
		    		
		    	}
		    	else{
		    		
		    		spritePrecision[i].reducetimeactive();
		    	}
		  	}
		
		  	today = Calendar.getInstance();
			time =today.getTimeInMillis() -inittime;
		}
		catch(Exception e){
			System.out.println("Error Play.constant "+e);
		}
	}

	public void evaluateMouse(){
		int posx = -30;
		int posy = 510;
		int h= spriteKey.geth();
		int w= spriteKey.getw();
		int note = 0;
		for (int i=0;i<=14;i++){ 
		 	
			posx = posx + w;
			note = Piano.array2note(i);
	  		if (event.isMouseClicked(posx, w, posy, h)){
	  			piano.setPressed(note-60, true);
		  		piano.setUsed(note-60, false);
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
		 	
			posx = posx + spriteKey.getw();
			note = Piano.arrayblack2note(i);
	  		if (event.isMouseClicked(posx, w, posy, h)){
	  			piano.setPressed(note-60, true);
		  		piano.setUsed(note-60, false);
		  		player.noteOn(note);
	  		}
	  		if (event.isMouseReleased()){
	  			piano.setPressed(note-60, false);
	  			player.noteOff(note);	
	  		}
	  	}
	}
	@SuppressWarnings("static-access")
	public void evaluateKeyboard() { //comprueba si se acierta la nota con el teclado
	
		if (event.isPressed() ){
			int note = 0;
			int sym = event.getSym();
			//System.out.println((char) sym +" : "+sym  );
			
			note = event.symtonote(sym);
			
			try{
				//SWIG_SDLMixer.SWIG_Mix_PlayChannel(channel, sounds[note-60], 0);
		  		//channel++;
			}
			catch(Exception e){
				
			}
	  		piano.setPressed(note-60, true);
	  		piano.setUsed(note-60, false);
	  		player.noteOn(note);
		}
		if (event.isReleased() ){
			int note = 0;
			int sym = event.getSym();

			note = event.symtonote(sym);

			try {
				piano.setPressed(note-60, false);
				piano.setUsed(note-60, false);
				player.noteOff(note);
			}
			catch(Exception e){
				
			}
		}
	}
	public void evaluatePrecision() { //comprueba si se acierta la nota con el teclado
		
		try {
			
			if (true){
				
				int note = 0;
				int i = 0;
				int min = 0;
				for (int j = 0;j<=numnotes;j++){
					
					if (piano.getPressed(j) && !piano.isUsed(j)){
	
						piano.setUsed(j, true);
						note = j+60;
						
				  		while((i<this.numnotes) ){
							//if ((listNote[i].getNote() == note) && (listNote[i].isActive()) ){
								
							min = this.getlowernote(note);
							try{
								if ((listNote[min].gety() + spritenotebar.geth() > limitp2 )&& (listNote[min].gety() + spritenotebar.geth()< limitp3   )){ // y si esa nota esta por debajo de una altitud
				  					//perfect
									combo++;
				  					addScore("perfect");
				  					
				  					spritePrecision[lastPrecision].setframe(2);
				  					
			    	  				spritePrecision[lastPrecision].setx(listNote[min].getx());
			    	  				spritePrecision[lastPrecision].sety(450);
			    	  				spritePrecision[lastPrecision].setActive();
			    	  				spritePrecision[lastPrecision].setTimeactive();
			    	  				
			    	  				lastPrecision++;
			    	  				listNote[min].setinactive(); //la desactivamos
			    	  				
			    	  		
				  				}  
				  				else if ((listNote[min].gety() +spritenotebar.geth()> limitp1  )&& (listNote[min].gety() +spritenotebar.geth() < limitp4   )){
				  					//good
				  					combo++;
				  					addScore("good");
				  					
				  					spritePrecision[lastPrecision].setframe(0);
				  					
			    	  				spritePrecision[lastPrecision].setx(listNote[min].getx());
			    	  				spritePrecision[lastPrecision].sety(450);
			    	  				spritePrecision[lastPrecision].setActive();
			    	  				spritePrecision[lastPrecision].setTimeactive();
			    	  				
			    	  				lastPrecision++;
			    	  				listNote[min].setinactive(); //la desactivamos
			    	  			
				  				}
				  				else if ((listNote[min].gety() +spritenotebar.geth() > limitp1 -10  )&& (listNote[min].gety() +spritenotebar.geth()< limitp4  +10 ) ){
				  					//bad
				  					combo = 1;
				  					
				  					spritePrecision[lastPrecision].setframe(1);
			    	  				spritePrecision[lastPrecision].setx(listNote[min].getx());
			    	  				spritePrecision[lastPrecision].sety(450);
			    	  				spritePrecision[lastPrecision].setActive();
			    	  				spritePrecision[lastPrecision].setTimeactive();
			    	  				
			    	  				lastPrecision++;
			    	  				
			    	  				listNote[min].setinactive();
			    	  				listNote[min].setFailed(true);
			    	  				
			    	  			
				  				}
						//	}
							}
							catch(Exception e){
							}
						i++;
						}
					}
				}
			}//end if
		}//end try
		catch(Exception e){
			
		}
	}

	public void keyboard(SDLSurface screen) {
		
		//System.out.println(piano.getpush());
		//System.out.println(piano);
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
				
			sym = event.getSym();
			
			switch (sym){
				case 27:
	
					today = Calendar.getInstance();
					long timeesc = today.getTimeInMillis();
					Popup menuesc = new Popup();
					menuesc.set(Language.getString("Popup.Exit"),
							Language.getString("Popup.Exit.msg"),
							Language.getString("Popup.No"),
							Language.getString("Popup.Yes"));
					this.end = (1 ==  menuesc.go(screen));
					
					today = Calendar.getInstance();
					inittime += today.getTimeInMillis()-timeesc;

					break;
				
			}
		}
	}
	
	int go (SDLSurface screen,String song){
		
		//System.out.println("inicio play");
		this.song = song;

		this.player.init(song);
		this.initsong(song);

		//midi[0].getMidiInformation();
		this.end = false;

	    
		while (!this.end){
			
			try {
		
				initfps = System.currentTimeMillis();
				
	        	if (event.isMouseClicked(this.spritePendulumBase)){
	        		Options.setMetronome(!Options.isMetronome());
	        	}
	        	
	    		this.getnote();
	    		
	    		this.evaluateMouse();
	    		this.evaluatePrecision();
	    		this.evaluateKeyboard();

	    		this.constant(screen);
	        	
		        this.draw(screen);
		        
		        this.keyboard(screen);
		        
		        endfps = System.currentTimeMillis();
		        fps = endfps - initfps;

		        try{
		        	Thread.sleep(17-fps);
		        }
		        catch(Exception e){
		        	
		        }
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.close();

		return 0 ;
	}
	public void addScore(String s){
		if (s == "good"){
			score = score + 5 *combo;
		}
		if (s == "perfect"){
			score = score + 10*combo;
		}
		
	}
	public void close(){
		this.listNote = null;
		
		this.midi.close();
		
		this.piano.close();
		this.player.close();
	}
	
}
