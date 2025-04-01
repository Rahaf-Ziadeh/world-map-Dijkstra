package application;
//edgge class 
public class Edge {
	Vertex source;
	Vertex target;
	double distance=Double.MAX_VALUE;
	double cost;
	double time;


	public Vertex getSource() {
		return source;
	}

	public Vertex getTarget() {
		return target;
	}

	public double getDistance() {
		return distance;
	}

	public double getCost() {
		return cost;
	}

	public double getTime() {
		return time;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public Edge(Vertex source, Vertex target, double cost, double time, double distance) {
		super();
		this.source = source;
		this.target = target;
		this.cost = cost;
		this.time = time;
		this.distance = distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
