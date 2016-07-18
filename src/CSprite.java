
import sdljava.SDLException;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;


public class CSprite {
	
	private int posx,posy;
	private int actualframe;
	private int maxframes;
	private int numframes;
	
	private boolean active;

	private	double zoom;
	
	private int timeactive;

	private	int type; //0 para floor
	
	private CFrame[] sprite;
	
	private long time;
	private boolean backward;
	
	public boolean isBackward() {
		return backward;
	}
	public void setBackward(boolean backward) {
		this.backward = backward;
	}
	public CSprite(int nf){
		   sprite=new CFrame[nf];
		   maxframes=nf;
		   numframes=0;
			actualframe = 0;
			zoom = 1;
			type = 100;
			}
	public   	CSprite(){
		int   nf=20;
		   sprite=new CFrame[nf];
		   maxframes=nf;
		   numframes=0;
			actualframe = 0;
			zoom = 1;
			type = 100;
		}
	public   	void finalize(){
		 int i;
		   for (i=0 ; i<=maxframes-1 ; i++){
		    //  sprite[i].unload();
		   }
		}

	//operaciones con los frames frames

	public 	void addframe(CFrame frame){
		   if (numframes<maxframes) {
			      sprite[numframes]=frame;
			      numframes = numframes +1;
			   }
			}		//añade un frame al sprite actual, incrementa en 1 numframes
	public   	void setframe(int nf){
		   if (nf<=numframes) {
			      actualframe=nf;
			   }
			}		//selecciona el frame indicado del sprite (<numframes)

	public	int getactualframe(){
		return actualframe;
	}
	public	int getnumframes(){
		return numframes;
	}

	public boolean nextframe(){		
		boolean b = true;
		if (actualframe == numframes-1){
			b = false;
		}
		else {
			b = true;
			setframe(actualframe+1);
		}	
		return b;
	}		//cambia al siguiente frame en la lista, si no hay, vuelve a empezar
	public boolean previousframe(){		
		boolean b = true;
		if (actualframe == 0){
			b = false;
		}
		else {
			b = true;
			setframe(actualframe-1);
		}	
		return b;
	}		//cambia al siguiente frame en la lista, si no hay, vuelve a empezar

	public boolean changeframe(int t, boolean repeat){
		
		boolean finished = false;
		if (time + t< System.currentTimeMillis() ){
			
			time = System.currentTimeMillis();
			if (!this.nextframe() && repeat){
				this.setframe(0);
			}
		}
		return finished;
	}
	public boolean changeframeReverse(int t){
		
		boolean finished = false;
		if (time + t< System.currentTimeMillis() ){
			
			time = System.currentTimeMillis();
			
			if (this.backward){
				if (!this.previousframe()){
					this.backward = false;
				}
			}
			else{
				if (!this.nextframe() ){
					this.backward = true;
				}
			}
		}
		return finished;
	}
	
	public boolean changeframe(int t,boolean repeat ,int begin,int end ){
		boolean finished = false;
		if (time + t< System.currentTimeMillis() ){
			
			time = System.currentTimeMillis();
			
			if (!this.nextframe() && repeat){
				
				this.setframe(begin);
			}
			else if ( (this.getactualframe()>end)  && repeat){
				this.setframe(begin);
			}
		}
		return finished;
	}
		
	//operaciones de posicion
	  
	public   	void setx(int f) {posx=f;}
	public   	void sety(int y) {posy=y;}
	public   	void addx(float c) {posx+=c;}
	public   	void addy(float c) {posy+=c;}
	public   	int getx() {return posx;}
	public   	int gety() {return posy;}
	public   	int getw() {return sprite[actualframe].getimg().getWidth();}
	public   	int geth() {return sprite[actualframe].getimg().getHeight();}

	public	float getbottom(){
		
		return (posy+geth() );
	} //devuelve y + h
	public	float getmiddle(){

		return ((posx+getw() ) /2 ); 

	} //devuelve x+w / 2

	//pintar sprites
	public   	void draw(SDLSurface screen) throws SDLException{
		  sdljava.video.SDLRect dest = new SDLRect(posx,posy);;
		   
		  // 	blitsurface(sprite[actualframe].getimg(),null,superficie,dest);
		   	sprite[actualframe].getimg().blitSurface(screen,dest);//sdljava.video.SDLSurface.blitSurface(sprite[actualframe].getimg(),null,superficie,dest);//SDL_BlitSurface(sprite[actualframe].img,NULL,superficie,&dest);

		}
	public   	void drawCenter(SDLSurface screen, int minx, int maxx, int posy){
		try{
			int middle = (minx + maxx) / 2;
			int middlewith = this.getw()/2;
			int initx = middle-middlewith;
			
		  sdljava.video.SDLRect dest = new SDLRect(initx,posy);;
		   
		  // 	blitsurface(sprite[actualframe].getimg(),null,superficie,dest);
		   	sprite[actualframe].getimg().blitSurface(screen,dest);//sdljava.video.SDLSurface.blitSurface(sprite[actualframe].getimg(),null,superficie,dest);//SDL_BlitSurface(sprite[actualframe].img,NULL,superficie,&dest);

		}
		catch(Exception e){
			System.out.println("Error CSprite.drawCenter "+e);
		}
	}
	

	//operaciones de colision

	public	int collision(CSprite sp){

		float w1,h1,w2,h2,x1,y1,x2,y2;
		 	x1=getx();        // pos. X del sprite1
		 	y1=gety();        // pos. Y del sprite1
		 	w1=getw();        // ancho del sprite1
		   	h1=geth();        // altura del sprite1

		   	w2=sp.getw();     // ancho del sprite2
		   	h2=sp.geth();     // alto del sprite2
		   	x2=sp.getx();     // pos. X del sprite2
		   	y2=sp.gety();     // pos. Y del sprite2

		   if (((x1+w1)>x2)&&((y1+h1)>y2)&&((x2+w2)>x1)&&((y2+h2)>y1)) {
		      return 1;
		                                           
		   } else {
		       return 0;
		   }
		}

	//public   	int collisionbottom(CSprite sp);

	//public	int collisiontop(CSprite sp);

	//public	int collisionright(CSprite sp);

	//public	int collisionleft(CSprite sp);

	//operaciones con lso tipos de sprites

	public	void settype(int n){ type = n;}
	public	int gettype(){ return type; }

	//operaciones con zoom
	public	void addzoom(double t) throws SDLException{
		
		zoom = zoom +t;
		
	}	
	public	void restorezoom(){
		
		//sprite[actualframe].setimg(rotozoomSurface(sprite[actualframe].getimg(),0,1/zoom,0)) ;
	
	}
	public void setActive() {
		this.active = true;
	}
	public void setinActive() {
		this.active = false;
	}
	public boolean isActive() {
		return active;
	}
	
	public void setTimeactive() {
		this.timeactive =200;
	}
	public int getTimeactive() {
		return timeactive;
	}
	public void reducetimeactive(){
		
		timeactive = timeactive-1;
	}
	
	@SuppressWarnings("static-access")
	public void animate(SDLSurface screen){
		
		for (int i = 1;i<this.getnumframes();i++){
			
			try{
						
				this.nextframe();
				Thread.currentThread().sleep(25);
				this.draw(screen);
			
				//screen.blitSurface(dstSurface)
				screen.flip(); 
			//	screen.fillRect(0);
			}
			catch(Exception e){
				
			}
		}
	}
	
}
