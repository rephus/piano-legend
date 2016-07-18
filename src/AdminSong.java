
import java.io.File;

import file.FileBrowser;
import file.FileSongs;
import sdljava.video.SDLSurface;

public class AdminSong {

	//---------------------------------------------------------    	  	
	//DECLARAR VARIABLES
	//---------------------------------------------------------

	private boolean end ; //determina cuando salir del programa

	private Text textHand20;
	private FileSongs filesongs;
	//private Language language ;
	
	private CFrame frameBackgroundNotebook;
	private CSprite spriteBackgroundNotebook;

	private CFrame framepencil;
	private CSprite spritepencil;
	
	private CFrame frameadded;
	private CSprite spriteadded;
	
	private CFrame frameup;
	private CFrame framedown;
	private CSprite spritemove;
	
	private Midi[] midi;
	
	private  String path;
    
	private  int maxtracks;
    
	private   MidiPlayer player;

	private Event event;
	
	private String[] files;

	private int selectsong;
	private int numsongs ;
	
	
	private final int maxsongs = 14;
	private int showsongs ;
	
	public AdminSong(){
		this.initsprites();
		this.initgame();
		
		
		this.updatesongs();
		player.play(files[selectsong].substring(0,(int) (files[selectsong].length()-4)));
	}
	 public void initsprites() {
			
		//init sprites

		 try {
			 frameBackgroundNotebook = new CFrame();
			spriteBackgroundNotebook = new CSprite() ;
			
			framepencil = new CFrame();
		    spritepencil  = new CSprite();
		    
		    frameadded = new CFrame();
		    spriteadded = new CSprite();
		    
		    frameup = new CFrame();
		    framedown = new CFrame();
		    spritemove = new CSprite();
		    
		    frameup.load("sprites"+  File.separator +"up.png");
		    framedown.load("sprites"+  File.separator +"down.png");
		    spritemove.addframe(frameup);
		    spritemove.addframe(framedown);
		    
		    frameadded.load("sprites"+  File.separator +"added.png");
		    spriteadded.addframe(frameadded);
			
			frameBackgroundNotebook.load("sprites"+  File.separator +"notebook.png");
		    this.spriteBackgroundNotebook.addframe(this.frameBackgroundNotebook);
			
		    framepencil.load("sprites"+  File.separator +"pencil.png");
		    spritepencil.addframe(framepencil);
		 }
		 catch(Exception e){
			 System.out.println("Error selectsong.initsprites "+e);
		 }
	
	}
	 public void initgame() { //inicializa
			
		 try{
	
			 showsongs = 0;
			 numsongs = 0;
			 selectsong = 0;
			 
			 event = new Event();
			
			 filesongs = new FileSongs();
			 
			player = new MidiPlayer();
	
			end = false;

	  	  textHand20 = new Text();
		    textHand20.init("fonts"+  File.separator + "brigitte.ttf",20);

		    textHand20.setcolor(0,0,0);
			   
		 }
		 catch(Exception e){
			 
		 }
	}
	 public void initsong(String song){
		try{
			path = "music" + File.separator;
		    for (int i=0;i<maxtracks;i++){
		    	midi[i] = new Midi();
		    	midi[i].initFile(path + song);
		    }
		}
		catch(Exception e){
			System.out.println("Error AdminSong.initsong "+path +song+" : "+e);
		}
	    
	}
		
	public void draw(SDLSurface screen) {
		
		try{

			int namelength = 0;
		    spriteBackgroundNotebook.draw(screen);
			
		    textHand20.drawtext(Language.getString("AdminSong.Add"), screen, 320, 70 );
		    textHand20.drawtext(Language.getString("AdminSong.Added"), screen, 600, 70 );

		    for (int i = 0;i<Math.min(numsongs,maxsongs);i++){
		    	
	    		textHand20.drawtext(files[i+showsongs], screen,330, 133
    				+(i*32)); 
	    		if (filesongs.getSong(files[i+showsongs].substring(0,(int) (files[i+showsongs].length()-4)))!= null){
	    			spriteadded.setx(650);
	    			spriteadded.sety(143+(i*32));
	    			spriteadded.draw(screen);
	    		}
		    }	

	    	if (selectsong == -1){
				spritepencil.sety(80);
				spritepencil.setx(400);
			}
			else{
				namelength = files[selectsong].length();
				
				spritepencil.setx(namelength*12+350);
				spritepencil.sety( ( selectsong-showsongs) *32 + 143);
			
			}
	    	if(showsongs>0){
	    		spritemove.setframe(0);
	    		spritemove.setx(300);
	    		spritemove.sety(150);
	    		spritemove.draw(screen);
	    		
	    	}
	    	if(maxsongs+showsongs <numsongs){
	    		spritemove.setframe(1);
	    		spritemove.setx(300);
	    		spritemove.sety(550);
	    		spritemove.draw(screen);
	    	}
	    	
			spritepencil.draw(screen);
	
		    screen.flip(); //Pintamos la pantalla
	    	    	
		}
		catch(Exception e){
			System.out.println("Error Adminsong.draw "+e);
		}
	  	//screen.fillRect(0); //borramos la pantalla anterior
		
	}
		
	public void keyboard(SDLSurface screen){
			
		int sym = 0;
		event.setEvent();
		
		if (event.isPressed()){
				
			sym = event.getSym();
			switch (sym){
			
		    	case 27 : 
		    		
		    		this.end = true;
		
		    		player.stop();
		    		
		    		break;
	    	
		    	case 273:
		    	
	    			if (selectsong > 0 ){
		    			selectsong --;
		    			player.close();
		    		
			    		player.play( files[selectsong].substring(0,(int) (files[selectsong].length()-4))  );	
		    		}
	    			if ((showsongs > 0) && (selectsong <showsongs)){
		    			showsongs--;
		    		}
		    		break;
		    	case 274:
	    		
		    		if (selectsong  < numsongs-1){
	    				selectsong++;
	    				
	    				//player.stop();
	    				player.close();
	    	    		
	    				player.play( files[selectsong].substring(0,(int) (files[selectsong].length()-4)));
				    		
		    		}
		    		if (selectsong >= maxsongs+showsongs){
		    			showsongs++;
		    		}
		    		break;

		    	case 13:
		    	
		    		if (filesongs.getSong(files[selectsong].substring(0,(int) (files[selectsong].length()-4)))!= null){
		    			//deletesong
		    			//filesongs.addSong(files[selectsong].substring(0,(int) (files[selectsong].length()-4)));
		    			filesongs.deleteSong(files[selectsong].substring(0,(int) (files[selectsong].length()-4)));
		    			
		    		}
		    		else{
		    			//deletesong
		    			filesongs.addSong(files[selectsong].substring(0,(int) (files[selectsong].length()-4)));

		    		}
			    	
		    		break;
	    	}
			this.draw(screen);
		}
		
	}
	public void updatesongs(){
		
		files = FileBrowser.getmidinames(FileBrowser.getFolderSongs());
		numsongs = files.length;
		
	}

	public int go(SDLSurface screen){

		this.draw(screen);
			//this.initsong(song);
		
		this.end = false;
		
		while (!this.end){
			
			try {
				
				this.keyboard(screen);
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 

		}
		this.close();
		return 0;
	}
	public void close()
	
	{
		this.player.close();
	}
	
}
