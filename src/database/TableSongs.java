package database;

import java.util.Date;

public class TableSongs {

	
		private String name;
		private String user;
		private Date added;
		private int multi;
		
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getUser() {
			return user;
		}
		public void setAdded(Date added) {
			this.added = added;
		}
		public Date getAdded() {
			return added;
		}
		public void setMulti(int multi) {
			this.multi = multi;
		}
		public int getMulti() {
			return multi;
		}
		
	
}
