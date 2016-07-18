
import java.io.File;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import database.DbSettings;

import file.FileBrowser;

import java.net.URL;

public class MidiPlayer implements MetaEventListener {

 	Synthesizer synth;
    MidiChannel channel;
    Patch patch;
    Instrument instrument;
    
    MidiChannel channelMetronome;
    Patch patchMetronome;
    Instrument instrumentMetronome;

    public MidiPlayer(){
        try {

            //Se obtiene el sintetizador midi
            synth = MidiSystem.getSynthesizer();
            //Se abre el sintetizador para poder reproducir sonidos
            synth.open();
            //Se obtienen los canales del sintetizador
            channel = synth.getChannels()[0];
            //se carga el banco de sonidos
            //se carga la lista de sonidos
            synth.loadAllInstruments(synth.getDefaultSoundbank());
            //Se cargan los instrumentos
            instrument = synth.getLoadedInstruments()[0]; //piano = 0
            patch = instrument.getPatch();
            channel.programChange(patch.getBank(), patch.getProgram());
            
            channelMetronome = synth.getChannels()[1];
            synth.loadAllInstruments(synth.getDefaultSoundbank());
            instrumentMetronome = synth.getLoadedInstruments()[115]; //caja madera = 115;
            patchMetronome = instrumentMetronome.getPatch();
            channelMetronome.programChange(patchMetronome.getBank(), patchMetronome.getProgram());
        } catch (Exception exc) {
           System.out.println("No se ha podido inicializar MidiPlayer "+exc);
        }
    }
  public void metronomeOn() {
        
        try{
           channelMetronome.noteOn(70, 50);
  
        } catch (Exception exc) {
            System.out.println("No se puede reproducir el metronomo " );
        }
    }
  public void noteOn(int note) {
        
        try{
           channel.noteOn(note, 100);
  
        } catch (Exception exc) {
            System.out.println("No se puede reproducir la nota "+note );
        }
    }
	public void noteOff(int note){
		
		channel.noteOff(note);
	}

	
	private Sequencer sequencer;
	private Sequence sequence;
	private boolean pause;

  	public void play(String song) {
  	  
	    // load a sequence
  		try{
  			if (Options.isOnline()){  // From URL
  				//sequence = MidiSystem.getSequence(new URL("http://89.128.145.208/piano/songs/campanitas.mid"));

  				song = song.replaceAll(" ", "%20");
				URL u = new URL( DbSettings.getString("songsfolder") +File.separator+ FileBrowser.getFolderSongs()+File.separator+ song +".mid");
  				
  				sequence = MidiSystem.getSequence(u);
  			}
  			else{ //From FILE
  				// From file Sequence sequence = MidiSystem.getSequence(new FileInputStream(song));
  		    
  				this.sequence = MidiSystem.getSequence( new File(FileBrowser.getFolderSongs()+ File.separator+song+".mid"));
  			}
  		   
  		    // Create a sequencer for the sequence
  		    this.sequencer = MidiSystem.getSequencer();
  		    this.sequencer.open();
  		    this.sequencer.setSequence(sequence);

  		    // Start playing
  		   
  		    sequencer.start();
  		
  		}
  		catch(Exception e){
  			System.out.println("Error MidiPlayer.play " +e);
  		}
  	}


  	public int gettempo(){
  		int tempo = 0;
  		//sequencer = player.getSequencer();
  		tempo = (int)sequencer.getTempoInBPM();
  		//System.out.println("midiplayer->gettempo"+ tempo);
  		return tempo;
  	}

  	public void init(String song) {
  	// load a sequence
  		try{
  			if (Options.isOnline()){  // From URL
  				//sequence = MidiSystem.getSequence(new URL("http://89.128.145.208/piano/songs/campanitas.mid"));
  				song = song.replaceAll(" ", "%20");
				URL u = new URL( DbSettings.getString("songsfolder") +File.separator+ FileBrowser.getFolderSongs()+File.separator+ song +".mid");
  				
  				sequence = MidiSystem.getSequence(u);

  			}
  			else{ //From FILE
  				// From file Sequence sequence = MidiSystem.getSequence(new FileInputStream(song));
  		    
  				this.sequence = MidiSystem.getSequence( new File(FileBrowser.getFolderSongs()+ File.separator+song+".mid"));
  			}
  		   
  		    // Create a sequencer for the sequence
  		    this.sequencer = MidiSystem.getSequencer();
  		    this.sequencer.open();
  		    this.sequencer.setSequence(sequence);

  		    // Start playing
  		   // sequencer.start();
  		
  		}
  		catch(Exception e){
  			System.out.println("Error MidiPlayer.init " +e);
  		}

  	}

	public void stop() {
	    if (sequencer != null && sequencer.isOpen()) {
	      sequencer.stop();
	      sequencer.setMicrosecondPosition(0);
	    }
	}
	public boolean isPaused() {
		return this.pause;
	}
	public void setPaused(boolean pause) {
	    if (this.pause != pause && sequencer != null && sequencer.isOpen()) {
	      this.pause = pause;
	      if (pause) {
	        sequencer.stop();
	      } else {
	        sequencer.start();
	      }
	    }
	}
	public Sequencer getSequencer() {
		return sequencer;
	}
	public void close() {
	    if (sequencer != null && sequencer.isOpen()) {
	      sequencer.close();
	    }
	    if (synth != null && synth.isOpen()) {
	    	synth.close();
	    }
	    channel = null;
	    patch = null;
	    instrument = null;
	    channelMetronome = null;
	    patchMetronome = null;
	    instrumentMetronome = null;
	}

	public void meta(MetaMessage event) {
	    /*if (event.getType() == END_OF_TRACK_MESSAGE) {
	      if (sequencer != null && sequencer.isOpen() && loop) {
	        sequencer.start();
	      }
	    }*/
	}

}

           