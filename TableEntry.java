package application;
import java.util.Arrays;
import java.util.*;

public class TableEntry {
	List<String> header;
	    boolean known;
	    double dist;
	    String path;
	    private HashMap<String, TableEntry> entries;

	 

	    public List<String> getHeader() {
			return header;
		}



		public boolean isKnown() {
			return known;
		}



		public double getDist() {
			return dist;
		}



		public String getPath() {
			return path;
		}



		public HashMap<String, TableEntry> getEntries() {
			return entries;
		}



		public void setHeader(List<String> header) {
			this.header = header;
		}



		public void setKnown(boolean known) {
			this.known = known;
		}



		public void setDist(double dist) {
			this.dist = dist;
		}



		public void setPath(String path) {
			this.path = path;
		}



		public void setEntries(HashMap<String, TableEntry> entries) {
			this.entries = entries;
		}



		public TableEntry() {
	        this.header = new ArrayList<>();
	        this.known = false;
	        this.dist = Integer.MAX_VALUE;
	        this.path = null; // Represents notVertex
	    }
}
