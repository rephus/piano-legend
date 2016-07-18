
	public class Note {
		
		private int posStaffx, posStaffy;

		private int posx,posy;
		
		private int note;

		private int duration;
		
		private int push;
		
		private boolean black;
		
		private int tempo ;
		
		private boolean active;
		
		private boolean failed;

	   	public Note(){ //inicializa la clase
	   		posy=0;
	   		posx=0;
	   		posStaffx=0;
	   		posStaffy=0;
	   		
	   		//active=0;
	   		active = false;
	   	}
	   	public void init(){
	   		posy=0;
	   		posx=0;
	   		
	   		posStaffx=0;
	   		posStaffy=0;
	   	}
	   	
	//control variables de posicion

	   	public void setx(int x){posx = x;} ;
	   	public void sety(int y){posy = y;};

	   	public int getx(){return posx;}
	   	public int gety(){return posy;}

	   	public boolean isActive(){ return active;}
	   	public void setActive(boolean b){ active = b;}
	   	public void setinactive(){ active = false;}
	   	
		public void setNote(int note) {
			this.note = note;
		}
		public int getNote() {
			return note;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public int getDuration() {
			return duration;
		}
		public void setPush(int push) {
			this.push = push;
		}
		public int getPush() {
			return push;
		}
		public void setBlack(boolean black) {
			this.black = black;
		}
		public boolean isBlack() {
			return black;
		}
		public void setTempo(int tempo) {
			this.tempo = tempo;
		}
		public int getTempo() {
			return tempo;
		}
		public void setStaffx(int posStaffx) {
			this.posStaffx = posStaffx;
		}
		public int getStaffx() {
			return posStaffx;
		}
		public void setStaffy(int posStaffy) {
			this.posStaffy = posStaffy;
		}
		public int getStaffy() {
			return posStaffy;
		}
		public void setFailed(boolean failed) {
			this.failed = failed;
		}
		public boolean isFailed() {
			return failed;
		}

		public String toString(){
			return "note "+this.note +",duration "+duration +",push "+this.push;
		}
}
