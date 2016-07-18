
import java.io.IOException;

import sdljava.SDLException;
import sdljava.video.SDLVideo;

public class CFrame {

	
	  private sdljava.video.SDLSurface img;
	  
	  public void load(String path) throws SDLException, IOException {
		  
		  img = sdljava.image.SDLImage.load("./" + path);
		 
		  img.setColorKey(SDLVideo.SDL_SRCCOLORKEY, 0x00FF00); //donde #00FF00 es el color verde

	}
	 
	  
	  public void unload() throws SDLException{
		  
		  img.freeSurface();
	  }
	  	
	  public sdljava.video.SDLSurface getimg(){return img;}

	  public void setimg(sdljava.video.SDLSurface imagen){img =imagen;}	
	

}
