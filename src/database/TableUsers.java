package database;

import java.security.MessageDigest;
import java.util.Date;

public class TableUsers {
	
		private String name;
		private String pass;
		private Date added;
		private String email;
		private String verif;
		
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public String getPass() {
			return pass;
		}
		public void setAdded(Date added) {
			this.added = added;
		}
		public Date getAdded() {
			return added;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getEmail() {
			return email;
		}
		public void setVerif(String verif) {
			this.verif = verif;
		}
		public String getVerif() {
			return verif;
		}
		public boolean isVerif(){
			
			return this.verif.equalsIgnoreCase("1");
		}
		public static String getHash(String message) {
		      MessageDigest md;
		      byte[] buffer;
		      byte[] digest;
		      String hash = "";
		      
		      try{
			        
			        buffer = message.getBytes();
			        md = MessageDigest.getInstance("SHA1");
			        md.update(buffer);
			        digest = md.digest();
	
			        for(byte aux : digest) {
			            int b = aux & 0xff;
			            if (Integer.toHexString(b).length() == 1) hash += "0";
			            hash += Integer.toHexString(b);
			        }
		      }
		      catch(Exception e){
		    	  
		    	  
		      }

		        return hash;
		 }
		
		
	
}
