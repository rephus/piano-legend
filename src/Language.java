import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Language {

	public Language(){
		
	}
	public static String getString (String str){
		
		ResourceBundle rb = ResourceBundle.getBundle("language.language");
		
		try {
			return rb.getString(str);
		} catch (MissingResourceException e) {
			return '!' + str + '!';
		}
	}
}
