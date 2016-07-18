package database;

import java.util.Date;

public class TableScores {

	
		private String song;
		private String user;
		private Date added;
		private int score;
		
		public void setSong(String name) {
			this.song = name;
		}
		public String getSong() {
			return song;
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
		public void setScore(int multi) {
			this.score = multi;
		}
		public int getScore() {
			return score;
		}
		
	
}
