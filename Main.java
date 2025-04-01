package application;

//this class contains the GUI design and the dijkstra methods to find the shortest path of two chosen cities.
import javafx.application.Application;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Main extends Application {
	Vertex vertex[] = null;
	Edge edges[] = null;
	double width = 0;
	double height = 0;
	Pane overlayPane = new Pane();
	Graph graph = new Graph();
	HashTable table = new HashTable();

	@Override
	public void start(Stage primaryStage) {
		try {

			Image bgImage = new Image("file:/C:/Users/hp elite 840/Documents/Algo/map.jpg");
			ImageView bgImageView = new ImageView(bgImage);
			ComboBox<String> source = new ComboBox<String>();
			ComboBox<String> target = new ComboBox<String>();
			ComboBox<String> filter = new ComboBox<String>();
			BorderPane root = new BorderPane();
			source.setEditable(true);
			target.setEditable(true);
			Scene scene = new Scene(root);
			source.getItems().sorted();

			File file = new File("C:\\Users\\hp elite 840\\Documents\\Algo\\cities.txt");

			int c = 0;
			int c1 = 0;
			if (file.length() != 0) {

				try {
					Scanner scanner = new Scanner(file);// scan to read the file
					while (scanner.hasNext()) {
						String data = scanner.nextLine();

						String[] tokens = data.split(",");
						if (tokens.length == 2) {// to read first line of the sizes
							vertex = new Vertex[Integer.parseInt(tokens[0])];
							edges = new Edge[Integer.parseInt(tokens[1])];
						}

						if (tokens.length == 3) {// to read cities names and x,y coordinates
							Vertex v = new Vertex(tokens[0], Double.parseDouble(tokens[1]),
									Double.parseDouble(tokens[2]));
							vertex[c] = v;
							c++;
						}

						if (tokens.length == 4) {// to read edges and the price and time for each
							Vertex sourceq = getVertexByName(tokens[0]);
							Vertex targetq = getVertexByName(tokens[1]);
							if (sourceq != null && targetq != null) {
								final int EARTH_RADIUS = 6371;
								double lat1Rad = Math.toRadians(sourceq.getLatitude());
								double lat2Rad = Math.toRadians(targetq.getLatitude());
								double deltaLat = Math.toRadians(targetq.getLatitude() - sourceq.getLatitude());
								double deltaLon = Math.toRadians(targetq.getLongitude() - sourceq.getLongitude());
								// Haversine formula to caluculate distance between two coordinates
								double dis = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1Rad)
										* Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
								double ce = 2 * Math.atan2(Math.sqrt(dis), Math.sqrt(1 - dis));// central angle
								double distance = EARTH_RADIUS * ce;

								Edge edge = new Edge(sourceq, targetq, Double.parseDouble(tokens[2]),
										Double.parseDouble(tokens[3]), distance);
								edges[c1] = edge;
								c1++;
								// check if adgacents are already add
								System.out.println(
										"Checking edge from " + sourceq.getName() + " to " + targetq.getName());
								if (!sourceq.adjacentVertices.contains(edge)) {
									sourceq.adjacentVertices.add(edge);
									System.out.println(
											"Added edge from " + sourceq.getName() + " to " + targetq.getName());
								} else {
									System.out.println("Edge already exists from " + sourceq.getName() + " to "
											+ targetq.getName());
								}
							} else {
								System.out.println("Source or target vertex is null for edge: " + data);
							}
						}
					}
					scanner.close();

				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			}
			for (int i = 0; i < vertex.length; i++) {
				graph.addVertex(vertex[i]);
			}

			Label lbSource = new Label("Source");
			lbSource.setFont(Font.font(17));
			lbSource.setFont(Font.font(null, FontWeight.BOLD, lbSource.getFont().getSize()));
			Label lbTarget = new Label("Target");
			lbTarget.setFont(Font.font(17));
			lbTarget.setFont(Font.font(null, FontWeight.BOLD, lbTarget.getFont().getSize()));
			Label lbFilter = new Label("Filter");
			lbFilter.setFont(Font.font(17));
			lbFilter.setFont(Font.font(null, FontWeight.BOLD, lbFilter.getFont().getSize()));

			target.setMaxSize(200, 100);
			target.setStyle("-fx-font-size: 15pt;");
			source.setMaxSize(200, 100);
			source.setStyle("-fx-font-size: 15pt;");
			filter.setMaxSize(200, 100);
			filter.setStyle("-fx-font-size: 15pt;");

			Button btRun = new Button("Run");
			btRun.setFont(Font.font(15));
			btRun.setFont(Font.font(null, FontWeight.BOLD, btRun.getFont().getSize()));
			btRun.setMaxSize(100, 100);
			HBox h2 = new HBox();
			Label lbPath = new Label("Path:");
			lbPath.setFont(Font.font(17));
			lbPath.setFont(Font.font(null, FontWeight.BOLD, lbPath.getFont().getSize()));
			TextArea tPath = new TextArea();
			tPath.setMaxSize(250, 250);
			tPath.setFont(Font.font(20));
			h2.getChildren().addAll(lbPath, tPath);

			Label lbDistance = new Label("Distance:");
			lbDistance.setFont(Font.font(17));
			lbDistance.setFont(Font.font(null, FontWeight.BOLD, lbDistance.getFont().getSize()));

			TextField tfDistance = new TextField();
			tfDistance.setFont(Font.font(20));

			Label lbCost = new Label("Cost:");
			lbCost.setFont(Font.font(17));
			lbCost.setFont(Font.font(null, FontWeight.BOLD, lbCost.getFont().getSize()));

			TextField tfCost = new TextField();
			tfCost.setFont(Font.font(20));

			Label lbTime = new Label("Time:");
			lbTime.setFont(Font.font(17));
			lbTime.setFont(Font.font(null, FontWeight.BOLD, lbTime.getFont().getSize()));

			TextField tfTime = new TextField();
			tfTime.setFont(Font.font(20));

			Button b3 = new Button("Clear");
			b3.setFont(Font.font(15));
			b3.setFont(Font.font(null, FontWeight.BOLD, b3.getFont().getSize()));
			b3.setMaxSize(100, 100);

			GridPane g = new GridPane();
			g.setPadding(new Insets(5, 5, 5, 5));
			// g.setHgap(2);
			g.setVgap(10);

			g.add(lbSource, 0, 1);
			g.add(source, 1, 1);

			g.add(lbTarget, 0, 2);
			g.add(target, 1, 2);

			g.add(lbFilter, 0, 3);
			g.add(filter, 1, 3);

			g.add(btRun, 1, 4);

			g.add(lbPath, 0, 5);
			g.add(tPath, 1, 5);

			g.add(lbDistance, 0, 6);
			g.add(tfDistance, 1, 6);

			g.add(lbCost, 0, 7);
			g.add(tfCost, 1, 7);

			g.add(lbTime, 0, 8);
			g.add(tfTime, 1, 8);

			// g.add(b3,2,9);

			g.setAlignment(Pos.CENTER_LEFT);
			root.setRight(g);
			root.setCenter(bgImageView);

			root.setCenter(new StackPane(bgImageView, overlayPane)); // Combine image and labels
			//Circle[] button = new Circle[vertex.length];
			
			width = bgImage.getWidth();
			height = bgImage.getHeight();
			System.out.println(width);
			System.out.println(height);
			// Collect items into a list
			List<String> items = new ArrayList<>();
			for (int k = 0; k < vertex.length; k++) {
				items.add(vertex[k].getName());
				// some calculations to make the labels fit the image
				double latitude = vertex[k].getLatitude();
				double longitude = vertex[k].getLongitude();

				double y = (height - (latitude + 90) / 180 * height + 134);
				double x = (longitude + 220) / 360 * width - 25;
			//	Circle circle = new Circle(x, y, 5, Color.BLACK);
			//	button[k] = new Circle(x, y, 3.5, Color.BLACK);
				Label lbName = new Label(vertex[k].getName());
				lbName.setStyle(
						"-fx-font-family: 'Open Sans'; -fx-font-size: 8pt; -fx-font-weight: bold; -fx-text-fill: black; -fx-effect: dropshadow(gaussian, white, 3, 0.8, 0, 0);");

				lbName.setLayoutX(x);
				lbName.setLayoutY(y);
				lbName.setOnMouseClicked(e -> {
					System.out.println(lbName.getText());
					if (source.getValue() == null) {
						source.setValue(lbName.getText());
					}
					else if (target.getValue() == null) {
						target.setValue(lbName.getText());
					}
				});

				
				overlayPane.getChildren().addAll(lbName);
				
				
			}

			// Sort the items alphabetically
			Collections.sort(items);

			// Add sorted items to ComboBox
			source.getItems().addAll(items);
			target.getItems().addAll(items);

			// Make ComboBox editable
			source.setEditable(true);
			target.setEditable(true);

//
//

			btRun.setOnAction(e -> {
				String src = source.getValue();
				String tgt = target.getValue();
				String flt = filter.getValue();
				if (src == null || tgt == null || flt == null) {
					tPath.setText("Please select all required fields.");
					return;
				}
				if (!source.getItems().contains(src)) {
					tPath.setText("Source vertex \"" + src + "\" is not available.");
					return;
				}
				if (!source.getItems().contains(src)) {
					tPath.setText("Target vertex \"" + tgt + "\" is not available.");
					return;
				}

				initializeTable(getVertexByName(src), graph, table);
				if (flt.equals("Time")) {
					dijkstraT(graph, table);
				} else if (flt.equals("Cost")) {
					dijkstraCost(graph, table);
				} else if (flt.equals("Distance")) {
					dijkstraDes(graph, table, "Distance");
				}

				TableEntry targetEntry = table.get(tgt);
				if (targetEntry.dist == Double.MAX_VALUE) {
					tPath.setText("No path available from " + src + " to " + tgt + ".");
					tfDistance.setText("");
					tfCost.setText("");
					tfTime.setText("");
					return;
				}

				printShortestPath(table, tgt, tPath);

				tfDistance.setText(String.format("%.2f", getTotalDistance(graph, table, tgt)) + " km");
				tfCost.setText(String.format("%.2f", getTotalCost(graph, table, tgt)) + " $");
				tfTime.setText(String.format("%.2f", getTotalTime(graph, table, tgt)) + " mins");

				// Draw the path on the canvas
				Vertex[] path = getPathVertices(graph, table, tgt);
				drawPathGraph(vertex, edges, path, overlayPane);

				System.out.println("Is Cairo connected to Amman? " + graph.hasEdge("Cairo", "Amman"));
				System.out.println("Is Amman connected to Abu Dhabi? " + graph.hasEdge("Amman", "Abu Dhabi"));
				System.out.println("Is Cairo connected to Abu Dhabi? " + graph.hasEdge("Cairo", "Abu Dhabi"));
			});

			filter.getItems().addAll("Time", "Distance", "Cost");
			filter.setValue("Distance");

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// .getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			primaryStage.setTitle("Shortest Path world map");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vertex getVertexByName(String name) {
		for (Vertex v : vertex) {
			if (v.getName().equalsIgnoreCase(name)) {
				return v;
			}
		}
		return null; // Return null if no vertex with the given name is found
	}

	private Vertex[] getPathVertices(Graph graph, HashTable table, String target) {
		List<Vertex> pathList = new ArrayList<>();
		String step = target;
		while (step != null) {
			pathList.add(graph.getVertex(step));
			step = table.get(step).path;
		}
		Collections.reverse(pathList); // Reverse to get the path from source to target
		return pathList.toArray(new Vertex[0]);
	}

	public double getTotalDistance(Graph graph, HashTable table, String target) {
		double totalDistance = 0.0;
		String step = target;
		while (step != null && table.get(step).path != null) {
			Vertex source = graph.getVertex(table.get(step).path);
			Vertex destination = graph.getVertex(step);
			totalDistance += getEdgeDistance(source, destination);
			step = table.get(step).path;
		}
		return totalDistance;
	}

	public double getTotalCost(Graph graph, HashTable table, String target) {
		double totalCost = 0.0;
		String step = target;
		while (step != null && table.get(step).path != null) {
			Vertex source = graph.getVertex(table.get(step).path);
			Vertex destination = graph.getVertex(step);
			totalCost += getEdgeCost(source, destination);
			step = table.get(step).path;
		}
		return totalCost;
	}

	public double getTotalTime(Graph graph, HashTable table, String target) {
		double totalTime = 0.0;
		String step = target;
		while (step != null && table.get(step).path != null) {
			Vertex source = graph.getVertex(table.get(step).path);
			Vertex destination = graph.getVertex(step);
			totalTime += getEdgeTime(source, destination);
			step = table.get(step).path;
		}
		return totalTime;
	}

	private double getEdgeDistance(Vertex source, Vertex destination) {
		for (Edge edge : source.adjacentVertices) {
			if (edge.getTarget().equals(destination)) {
				return edge.getDistance();
			}
		}
		return 0.0;
	}

	private double getEdgeCost(Vertex source, Vertex destination) {
		for (Edge edge : source.adjacentVertices) {
			if (edge.getTarget().equals(destination)) {
				return edge.getCost();
			}
		}
		return 0.0;
	}

	private double getEdgeTime(Vertex source, Vertex destination) {
		for (Edge edge : source.adjacentVertices) {
			if (edge.getTarget().equals(destination)) {
				return edge.getTime();
			}
		}
		return 0.0;
	}

	public void readGraph(Graph g, HashTable T) {
		System.out.println("Reading graph with " + g.getVertices().size() + " vertices.");
		for (Vertex vertex : g.getVertices()) {
			System.out.println("Processing vertex: " + vertex.getName());
			TableEntry entry = new TableEntry();
			entry.header.add(vertex.getName());
			for (Edge edge : vertex.adjacentVertices) {
				System.out.println("Adding edge from " + vertex.getName() + " to " + edge.getTarget().getName());
				entry.header.add(edge.getTarget().getName());
			}
			T.add(vertex.getName(), entry);
		}
	}

	public void initializeTable(Vertex start, Graph g, HashTable T) {
		readGraph(g, T);

		for (int i = 0; i < T.size; i++) {
			SingleLinkedList list = T.table[i];
			Node current = list.getFirst();
			while (current != null) {
				TableEntry entry = (TableEntry) current.getData();
				entry.known = false;
				entry.dist = Double.MAX_VALUE;
				entry.path = null;
				current = current.getNext();
			}
		}

		int startIndex = T.hash(start.getName());
		SingleLinkedList startList = T.table[startIndex];
		Node startNode = startList.getFirst();
		while (startNode != null) {
			TableEntry startEntry = (TableEntry) startNode.getData();
			if (startEntry.header.get(0).equalsIgnoreCase(start.getName())) {
				startEntry.dist = 0;
				break;
			}
			startNode = startNode.getNext();
		}
	}

	public void dijkstraDes(Graph graph, HashTable table, String factor) {
		while (true) {
			String vName = smallestUnknownDistanceVertex(table);
			if (vName == null)
				break;

			TableEntry vEntry = table.get(vName);
			vEntry.known = true;
			Vertex v = graph.getVertex(vName);

			if (v != null) {
				System.out.println("Processing vertex: " + vName);
				for (Edge edge : v.adjacentVertices) {
					Vertex w = edge.getTarget();
					TableEntry wEntry = table.get(w.getName());

					double newDist = vEntry.dist;
					if (factor.equals("Time")) {
						newDist += edge.getTime();
					} else if (factor.equals("Cost")) {
						newDist += edge.getCost();
					} else if (factor.equals("Distance")) {
						newDist += edge.getDistance();
					}

					System.out.println(
							"Considering edge from " + vName + " to " + w.getName() + " with newDist: " + newDist);

					if (!wEntry.known && newDist < wEntry.dist) {
						System.out.println(
								"Updating distance for " + w.getName() + " from " + wEntry.dist + " to " + newDist);
						wEntry.dist = newDist;
						wEntry.path = vName;
					}
				}
			} else {
				System.out.println("Vertex " + vName + " not found in graph.");
			}
		}
	}

	public void dijkstraCost(Graph graph, HashTable table) {
		while (true) {
			String vName = smallestUnknownDistanceVertex(table);
			if (vName == null)
				break;

			TableEntry vEntry = table.get(vName);
			vEntry.known = true;
			Vertex v = graph.getVertex(vName);

			if (v != null) {
				System.out.println("Processing vertex: " + vName);
				for (Edge edge : v.adjacentVertices) {
					Vertex w = edge.getTarget();
					TableEntry wEntry = table.get(w.getName());

					double newCost = vEntry.dist + edge.getCost();

					System.out.println(
							"Considering edge from " + vName + " to " + w.getName() + " with newCost: " + newCost);

					if (!wEntry.known && newCost < wEntry.dist) {
						System.out.println(
								"Updating cost for " + w.getName() + " from " + wEntry.dist + " to " + newCost);
						wEntry.dist = newCost;
						wEntry.path = vName;
					}
				}
			} else {
				System.out.println("Vertex " + vName + " not found in graph.");
			}
		}
	}

	public void dijkstraT(Graph graph, HashTable table) {
		while (true) {
			String vName = smallestUnknownDistanceVertex(table); // Get vertex with smallest unknown distance (time in
																	// this case)
			if (vName == null)
				break;

			TableEntry vEntry = table.get(vName);
			vEntry.known = true;
			Vertex v = graph.getVertex(vName);

			if (v != null) {
				System.out.println("Processing vertex: " + vName);
				for (Edge edge : v.adjacentVertices) {
					Vertex w = edge.getTarget();
					TableEntry wEntry = table.get(w.getName());

					double newDist = vEntry.dist + edge.getTime(); // Only consider time for calculation

					System.out.println(
							"Considering edge from " + vName + " to " + w.getName() + " with newDist: " + newDist);

					if (!wEntry.known && newDist < wEntry.dist) {
						System.out.println(
								"Updating distance for " + w.getName() + " from " + wEntry.dist + " to " + newDist);
						wEntry.dist = newDist;
						wEntry.path = vName;
					}
				}
			} else {
				System.out.println("Vertex " + vName + " not found in graph.");
			}
		}
	}

	private String smallestUnknownDistanceVertex(HashTable table) {
		double minDist = Double.MAX_VALUE;
		String minVertex = null;

		for (int i = 0; i < table.size(); i++) {
			SingleLinkedList list = table.table[i];
			Node current = list.getFirst();
			while (current != null) {
				TableEntry entry = (TableEntry) current.getData();
				if (!entry.known && entry.dist < minDist) {
					minDist = entry.dist;
					minVertex = entry.header.get(0); // Assuming the first header entry is the vertex name
				}
				current = current.getNext();
			}
		}

		System.out.println("Smallest unknown vertex: " + minVertex + " with distance: " + minDist);
		return minVertex;
	}

	public void printShortestPath(HashTable table, String target, TextArea tPath) {
		StringBuilder pathBuilder = new StringBuilder();
		String step = target;
		while (step != null) {
			pathBuilder.insert(0, " -> " + step);
			step = table.get(step).path;
		}

		// Remove the leading arrow and space if the pathBuilder is not empty
		if (pathBuilder.length() > 0) {
			pathBuilder.delete(0, 4);
		}

		// Display the path
		tPath.setText(pathBuilder.toString());
	}

	public void drawPathGraph(Vertex[] vertex, Edge[] edges, Vertex[] path, Pane overlayPane) {
		// Clear the previous path elements only
		overlayPane.getChildren().removeIf(node -> "path-element".equals(node.getStyleClass().get(0)));

		// Draw the path
		for (int i = 0; i < path.length - 1; i++) {
			if (path[i] != null && path[i + 1] != null) {
				double x1 = (path[i].getLongitude() + 220) / 360 * width - 25;
				double y1 = (height - (path[i].getLatitude() + 90) / 180 * height + 134);
				double x2 = (path[i + 1].getLongitude() + 220) / 360 * width - 25;
				double y2 = (height - (path[i + 1].getLatitude() + 90) / 180 * height + 134);

				Line line = new Line(x1, y1, x2, y2);
				line.setStroke(Color.BLACK); // Set the color of the line (path)
				line.setStrokeWidth(2); // Set the thickness of the line
				line.getStyleClass().add("path-element"); // Add style class
				overlayPane.getChildren().add(line);
			}
		}

		// Optionally, add circles to highlight the path vertices
		for (int i = 0; i < path.length; i++) {
			if (path[i] != null) {
				double x = (path[i].getLongitude() + 220) / 360 * width - 25;
				double y = (height - (path[i].getLatitude() + 90) / 180 * height + 134);
				Circle circle = new Circle(x, y, 5, Color.BLUE); // Use red to highlight path vertices
				circle.getStyleClass().add("path-element"); // Add style class
				overlayPane.getChildren().add(circle);
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
