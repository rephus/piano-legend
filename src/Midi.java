
import java.io.File;
import java.io.IOException;
import java.net.URL;


import javax.sound.midi.InvalidMidiDataException;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import javax.sound.midi.Sequence;

import javax.sound.midi.Track;

import database.DbSettings;
import file.FileBrowser;

public class Midi {

	private Note tempNote ;
	private Sequence seq;
	private Track [] tracks;
	private int[] numevent;
	
	MidiEvent mevent;
	
	MidiMessage mmess;
	
	
	public static boolean isBlack (int note){
		
		int[] blacks = {61,63,66,68,70,73,75,78,80,82};
		boolean isblack = false;
		
		for (int i=0;i<blacks.length;i++){
			if (blacks[i]==note){
				isblack = true;
			}
		}
	
		return isblack;
	
	}
	public static boolean isFlat (int note){//es bemol
		
		int[] flats = {63,70,75,82};
		
		boolean isflat = false;
		
		for (int i=0;i<flats.length;i++){
			if (flats[i]==note){
				isflat = true;
			}
		}
	
		return isflat;
	}
	public static int note2posStaff (int note ){ //transforma la nota en posiciones horizontales
		
		int pos = 0;
		int desp = 50;
	
		switch(note){
			
			//clave de sol 
			case 85: 
			case 86: pos = 0;
				break;
			case 84: pos = 15;
				break;
			case 83: 
			case 82: pos = 30;
				break;
			case 81: pos = 45;
				break;
			case 80:
			case 79: pos = 60; //
				break;
			case 78: //fa sostenido
			case 77: pos = 75; //fa
				break;
			case 76: pos =90; //mi
				break;
			case 75: //Re sostenido
			case 74: pos = 105; //re
				break;
			case 73: //do sostenido
			case 72: pos = 120; //do 
				break;
					
			case 71: //si sostenido
			case 70: pos = 135; //si 
				break;
			case 68: //la sostenido
			case 69:pos = 150; //la 
				break;
			case 67: pos = 165; //sol 
				break;
			case 66: //fa sostenido
			case 65: pos = 180; //fa
				break;
			case 63: //mi sostenido
			case 64: pos = 195; //mi 
				break;
			
			case 62: pos = 210; //re
				break;
			case 61: // do sostenido
			case 60: pos = 225; //do
				break;

			default : pos = 0 ;
		}
		pos = pos+desp;
		
		return pos;

	}
	public int note2pos (int note){ //transforma la nota en posiciones verticales
		
		int pos = 0;
		int desp = 0;

		if ((note >= 60 ) && (note <= 84 ) ){
			if (note > 64){
				desp = 25;
			}
			if (note > 71){
				desp = desp + 25;
			}
			if (note > 76){
				desp = desp + 25;
			}
			if (note > 83){
				desp = desp + 25;
			}
			pos = ((note -59 )*25) + desp;
		
		}
		else {
			pos = 0 ;
		}
		
		return pos;
		
	}

	public int time2Duration(int time) throws MidiUnavailableException, InvalidMidiDataException{
	
		int duration = 0;
		int resolution = 0;
		resolution = getresolution();

		if (time == resolution ){
			//negra
			duration = 3;
			
		}
		if (time > resolution  ){
			//blanca
			duration = 2;
		}
		if (time > resolution *2  ){
			// redonda
			duration = 1;
		}
		
		if (time < resolution ){
			//corchea 
			duration = 4;
		}
		if (time < resolution/2 ){
			// semicorchea
			duration = 5;
		}
	
		return duration;
		
	}

	public int getresolution () throws MidiUnavailableException, InvalidMidiDataException{

		return seq.getResolution();
		
	}
	public int getDurationTotal () throws MidiUnavailableException, InvalidMidiDataException{
		
		return (int) seq.getMicrosecondLength()/1000;
		
	}
	public int getDurationTotalseconds () throws MidiUnavailableException, InvalidMidiDataException{
		
		return (int) seq.getMicrosecondLength()/1000000;
		
	}
	public int getDurationTotalmiliseconds () throws MidiUnavailableException, InvalidMidiDataException{
		
		return (int) (seq.getMicrosecondLength()/1000 )%1000 ;
		
	}
	public int getDurationRemaining (int time) throws MidiUnavailableException, InvalidMidiDataException{
		
		return (int) ((int) seq.getMicrosecondLength()/1000 - time);
		
	}
	public int getDurationRemainingseconds (int time) throws MidiUnavailableException, InvalidMidiDataException{
		
		return (int) ((int) seq.getMicrosecondLength()/1000 - time)/1000;
		
	}
	public int getDurationRemainingmiliseconds (int time) throws MidiUnavailableException, InvalidMidiDataException{
	
		return (int) ((int) (seq.getMicrosecondLength()/1000)- time) %1000;
	
	}
	
	public void initFile (String song) throws InvalidMidiDataException, IOException, MidiUnavailableException {

		if (Options.isOnline()){  // From URL
			//sequence = MidiSystem.getSequence(new URL("http://89.128.145.208/piano/songs/campanitas.mid"));
			song = song.replaceAll(" ", "%20");
			URL u = new URL( DbSettings.getString("songsfolder") +File.separator+ FileBrowser.getFolderSongs()+File.separator+ song +".mid");
			
			seq = MidiSystem.getSequence(u);
			//MidiFileFormat format = MidiSystem.getMidiFileFormat(new File(dir));
		}
		else{ //From FILE
			// From file Sequence sequence = MidiSystem.getSequence(new FileInputStream(song));
	    
			this.seq = MidiSystem.getSequence( new File(FileBrowser.getFolderSongs()+ File.separator+song+".mid"));
		}
	
		tracks = seq.getTracks();
		unifyTracks();
		deleteTracks();
		addMetronome();
		tracks = seq.getTracks();
		numevent  = new int[tracks.length];
		
	}
	private void addMetronome(){
		Track trackmetronome = seq.createTrack();
		ShortMessage messageOn = new ShortMessage();
		ShortMessage messageOff = new ShortMessage();

		MidiEvent me ;
		try {
			messageOn.setMessage(ShortMessage.NOTE_ON, 0,70, 98);
			messageOn.setMessage(ShortMessage.NOTE_OFF,0, 70, 98);

		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		long maxlength = seq.getTickLength();
		long timestamp =0;
		long newlength =seq.getTickLength();
		long resolution = seq.getResolution();
		while (maxlength == newlength){
			
			me = new MidiEvent(messageOn,timestamp*resolution);
			trackmetronome.add(me);
			me = new MidiEvent(messageOff,timestamp*resolution + 10);
			trackmetronome.add(me);
			newlength = seq.getTickLength();
			timestamp++;
		}
	}
	private void unifyTracks(){
		Track track0= tracks[0];
		for (int i=1;i< seq.getTracks().length;i++){
			MidiEvent mevent;
			
			for (int j=0;j<tracks[i].size();j++){
				mevent = tracks[i].get(j);
				track0.add(mevent);
			}
		}
	}
	private void deleteTracks(){
	
		for (int i=1;i<tracks.length;i++){
			seq.deleteTrack(tracks[i]);
		}
	}

	public int getNextTick(int tracknumber){

		int tick = 0;
		tempNote = new Note();

		Track t = tracks[tracknumber];

		if (numevent[tracknumber] < t.size())
		{
			
			MidiEvent mevent = t.get(numevent[tracknumber]);		
			tick = ((int) mevent.getTick());
			
		}
		else{
			tick = -1;
		}
		
		return tick;
	}
		
	public int gettracks(){

		return tracks.length ;
	}
	
	@SuppressWarnings("static-access")
	public Note getNextNote(int tracknumber){
		
		tempNote = new Note();

		int duration = 0 ;
			
		int nota ;
		int type = 0;
		
		Track t = tracks[tracknumber];
		
		MidiEvent mevent = t.get(numevent[tracknumber]);				
		MidiMessage mmess = mevent.getMessage();
		
		//tick = (int) mevent.getTick();
		type =  mmess.getMessage()[0];

		
		if( (type == -112) ||
			(type == -128) )
		try{
			//free = mmess.getMessage()[2];
		
			tempNote.setTempo((int) mevent.getTick());
	
			nota = mmess.getMessage()[1];
				
			tempNote.setx(this.note2pos(nota));

			tempNote.setStaffy(this.note2posStaff(nota));
			
			if (tempNote.getx() > 0 ){
				
				tempNote.setStaffx(700);
				
				tempNote.setActive(true);//setactive();
				
				tempNote.setNote(nota);
				tempNote.sety(50);
				
				duration = getFree(tracknumber, nota)- (int) mevent.getTick();
				try {
					tempNote.setDuration(this.time2Duration(duration) );
					
				} catch (Exception e) {
					
				}

				if (tempNote.isBlack()){
					
					tempNote.sety(0);
				}
			}
			else{
				tempNote.setinactive();
				
			}
			
			if (type == -128){ //si es 127 es una liberacion
				tempNote.setPush(0);
			
			}
			else{
				tempNote.setPush(1);
	
			}
		}
		catch(Exception e){
			
		}
		
		numevent[tracknumber]++;

		return tempNote;

	}
	
	public int getFree(int tracknumber, int note){

		boolean find = false ;
		
		int eventnumaux =numevent[tracknumber]+1; //buscamos a partir del siguiente frame
		int bucle = 0;
		
		MidiEvent mevent ;
		MidiMessage mmess ;
		
		Track track = tracks[tracknumber];
		mevent = track.get(eventnumaux);
		
		while ( (!find) && (eventnumaux < track.size()) ){

			mevent = track.get(eventnumaux);				
			mmess = mevent.getMessage();
			
			bucle=0;
			for (byte b: mmess.getMessage())
			{
				
				if (bucle==1 ){

					if (b == note){
						
						find = true ;
					}
				}
				bucle++;
			}
			
			eventnumaux++;
		}
		
		return (int)mevent.getTick() ;
	
	}
	public void getMidiInformation(){
		// Print track information
		Track [] tracks = this.seq.getTracks();
		if ( tracks != null ) {
		   for ( int i = 0; i < tracks.length; i++ ) {
		      System.out.println( "Track " + i + ":" );
		      Track track = tracks[ i ];
		      for ( int j = 0; j < track.size(); j++ ) {
			       MidiEvent event = track.get( j );
			       try{
			    	   System.out.println( 
				    		   " num " + j +
				    		   " tick "+ event.getTick() + 
				    		   " type "+       event.getMessage().getMessage()[0]+  
				    		   " mensaje "+	   event.getMessage().getMessage()[1] +
				    		   " mensaje2 "+	   event.getMessage().getMessage()[2] );
			      
			       }
			       catch(Exception e){
			    	   System.out.println( 
				    		   " num " + j +
				    		   " tick "+ event.getTick() + 
				    		   " type "+       event.getMessage().getMessage()[0]+  
				    		   " mensaje "+	   event.getMessage().getMessage()[1] );
			       }
			    } // for
		    } // for
		} // if
	}
	public void close(){
		this.seq = null;
		this.mevent = null;
		this.tracks = null;
		tempNote = null;
	}
	
}
