package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;
public class Graph {
    private HashTable vertex; 
   
    public Graph(int tableSize) {
        vertex = new HashTable(tableSize); 
    }

   
    public Graph() {
        vertex = new HashTable(55); 
    }

    public void addVertex1(Vertex vertex) {
        TableEntry entry = new TableEntry(); 
        entry.header.add(vertex.getName()); 
        entry.dist = Integer.MAX_VALUE; 
        entry.path = null; // No initial path
       
    }

    public void addEdge(Edge edge) {
        edges.add(edge); // Add the edge to the list of edges
        edge.getSource().adjacentVertices.add(edge); // Add the edge to the source vertex's adjacent list
    }

   
    public TableEntry getTableEntry(String name) {
        return vertex.get(name); // Retrieve the TableEntry from the HashTable by vertex name
    }

    public Vertex getVertex1(String name) {
        TableEntry entry = getTableEntry(name);
        if (entry != null) {
            // Assuming the vertex name is in the header, return the corresponding vertex
            return new Vertex(entry.header.get(0), 0, 0); // Replace 0, 0 with actual coordinates if available
        }
        return null; // Return null if the vertex is not found
    }

    // Retrieves all vertices in the graph
    public Collection<Vertex> getVertices1() {
        List<Vertex> vertexList = new ArrayList<>();
      
        
        return vertexList; // Return the list of all vertices
    }

	
	
	    private Map<String, Vertex> vertices = new HashMap<>();

	    public void addVertex(Vertex vertex) {
	        vertices.put(vertex.getName(), vertex);
	    }
	    private List<Edge> edges = new ArrayList<>();


	    public boolean hasEdge(String source, String target) {
	        // Check if the source vertex exists in the graph
	        Vertex sourceVertex = vertices.get(source); // Assuming `vertices` is a Map<String, Vertex>
	        if (sourceVertex == null) {
	            return false;
	        }
	        // Check if the target vertex is in the adjacency list of the source vertex
	        for (Edge edge : sourceVertex.adjacentVertices) { // Assuming `getAdjacencyList()` returns List<Edge>
	            if (edge.getTarget().getName().equals(target)) { // Assuming `getTarget()` returns the target Vertex
	                return true;
	            }
	        }
	        return false; // No edge found
	    }


	   	    public Vertex getVertex(String name) {
	        return vertices.get(name);
	    }

	    public Collection<Vertex> getVertices() {
	        return vertices.values();
	    }
	}
