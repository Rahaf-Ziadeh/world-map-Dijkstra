package application;
//vertex class to store vertices with the attributes latitude,longtitude,visited,name and the adgacent list
import java.util.LinkedList;
import java.util.ArrayList;

public class Vertex {
	String name;
//	Vertex previous;
	double latitude;
	double longitude;
	boolean visited;
	
	public Vertex path;
	ArrayList<Edge> adjacentVertices = new ArrayList<>();

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

//	int num;
//	double distance=Integer.MAX_VALUE;
//	boolean visited;
//
//	LinkedList<edges> e = new LinkedList<edges>();
//
	public Vertex(String name, double latitude, double longitude) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
		// TODO Auto-generated constructor stub
	}

//		this.num = number;
//}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public double getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//
//	public double getLongitude() {
//		return longitude;
//	}
//
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}
//
//	public int getNum() {
//		return num;
//	}
//
//	public void setNum(int num) {
//		this.num = num;
//	}
//
//	public double getDistance() {
//		return distance;
//	}
//
//	public void setDistance(int distance) {
//		this.distance = distance;
//	}
//
//	public LinkedList<edges> getE() {
//		return e;
//	}
//
//	public void setE(LinkedList<edges> e) {
//		this.e = e;
//	}
//
//	public boolean FindEdge(String ss) {
//
//		for (int i = 0; i < e.size(); i++) {
//			if (e.get(i).getD().getName().compareToIgnoreCase(ss) == 0)
//				return true;
//		}
//		return false;
//	}
//
//	public String ttoString() {
//		String r = name+":";
//		for (int i = 0; i < e.size(); i++) {
//			r = r + e.get(i).d.name + ",";
//		}
//		return r;
//	}

//}