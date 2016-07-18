
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiDevice.Info;

public class Piano
{
	private MidiDevice md;
	private Receptor receptor;
	
	private static int deviceSelected = 1;
	//MidiDevice md = MidiSystem.getMidiDevice(m[Integer.parseInt(args[0])]);
	
	public static int getDeviceSelected() {
		return deviceSelected;
	}
	public static void setDeviceSelected(int deviceSelected) {
		Piano.deviceSelected = deviceSelected;
	}
	private Transmitter tr ;
	
	private boolean[] used;
	
	private boolean[] pressed;
	protected final int numnotes=25;
	
	public Piano(){

		pressed = new boolean[numnotes];
		used = new boolean[numnotes];
	}
	public boolean getPressed(int i){
		
		return pressed[i];
	}
	public void setPressed(int i,boolean b){
		try{
			pressed[i] = b;
		}
		catch(Exception e){
			//nota no válida
		}
	}
	public static int arrayblack2note(int key){
		
		int note = 0;
		switch(key){
		
			case 0: note =61 ;
				break;
			case 1: note =63 ;
				break;
			case 3: note =66 ;
				break;
			case 4: note =68 ;
				break;
			case 5: note =70 ;
				break;
			case 7: note =73;
				break;
			case 8: note =75;
				break;
			case 10: note =78;
				break;
			case 11: note =80;
				break;
			case 12: note =82;
				break;
		}
		return note;
	}
	public static int array2note(int key){
		
		int note = 0;
		switch(key){
		
			case 0: note =60 ;
				break;
			case 1: note =62 ;
				break;
			case 2: note =64 ;
				break;
			case 3: note =65 ;
				break;
			case 4: note =67 ;
				break;
			case 5: note =69;
				break;
			case 6: note =71;
				break;
			case 7: note =72;
				break;
			case 8: note =74;
				break;
			case 9: note =76;
				break;
			case 10: note = 77;
				break;
			case 11: note =79;
				break;
			case 12: note = 81;
				break;
			case 13: note = 83;
				break;
			case 14: note = 84;
				break;
		}
		return note;
	}
	
	public void setUsed(int i,boolean b){
		try{
			used[i] = b;
		}
		catch(Exception e){
			//nota no válida;
		}
	}
	public boolean isUsed(int i){
		return used[i];
	}

	public void init () 
	{
		try{
			
			//MidiDevice md = MidiSystem.getMidiDevice(m[Integer.parseInt(args[0])]);
			md = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[deviceSelected]);
			tr = md.getTransmitter();
			receptor = new Receptor();
			tr.setReceiver(receptor);
			
			md.open();
		}
		catch(Exception e){
			System.out.println("Error Piano.init al inicializar el dispositivo MIDI "+deviceSelected);
			if (md != null && md.isOpen()){
				md.close();
			}
			tr.close();
			receptor.close();
		}
	}
	public void close(){
		try{
			if (md != null && md.isOpen()){
				md.close();
			}
			tr.close();
			receptor.close();
		}
		catch(Exception e){
			System.out.println("Error Piano.close al cerrar receptores/transmisores "+e);
		}
	}
	public static void changeDevice(){
		
		if (deviceSelected +1 >= Piano.getDevicesNumber()){
			deviceSelected = 0;
		}
		else{
			deviceSelected++;
		}
	}
	public static int getDevicesNumber(){
		
		return MidiSystem.getMidiDeviceInfo().length;
		
	}

	public static Info getDeviceInfo(int i){
		
		Info info = null; 
		try{
			info = MidiSystem.getMidiDeviceInfo()[i];
		
		}
		catch(Exception e)
		{
			System.out.println("Error Piano.getDeviceInfo no existe el dispositivo "+i);
			
		}	
		return info;
	}
	
	public class Receptor implements Receiver
	{
		MidiPlayer player;
		public Receptor(){
			player = new MidiPlayer(); //clase MidiPlayer para reproducir el sonido del piano
		}

		public void send(MidiMessage message, long timeStamp)
		{
			boolean press=false;
			int push = 0;
			int note = 0;
			int velocity = 0;
			int i=0;
			for (byte b: message.getMessage())
			{
				if (i==0){
					push = b;
					
					if (push ==-112){
						
						press = true;
					}
					else if (push ==-128){
						push =0;
						press = false;
					}
					
				}
				if (i==1){
					note = b;
				}
				if (i==2){
					velocity = b;
					if (velocity == 0){
						press = false;
					}
				}
				i++;
			}
			try{
				if (press == true){
					player.noteOn(note);							
				}
				else{							
					player.noteOff(note);
				}
				pressed[note-60] = press;
				used[note-60] = false;
			}
			catch(Exception e){
				//Error piano.send array pressed excedido
			}
		}

		public void close() {
			player.close();
		}

	}

}
           