import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyGraph {
    private int numVertices;
    private int numEdges;
    private HashMap<Vertex, ArrayList<GraphPairing>> graph;

    public MyGraph() {
        numEdges = 0;
        numVertices = 0;
        graph = new HashMap<>(); //don't know order, vertex, graphing pair
    }

    public int NumVertices() {
        return numVertices;
    }

    public int numEdges() {
        return numEdges;
    }

    public void insertVertex(String name) {
        Vertex temp = new Vertex(name);
        //assume for now, the given name does not exist in map
        graph.put(temp, null); //assume new vertex with no edges
        numVertices++;
    }

    public void print() {
        Set<Vertex> vertexArrayList = graph.keySet();

        for (Vertex v: vertexArrayList) {
            System.out.print(v + ": ");
            System.out.println(graph.get(v));
        }
    }

    //THis is O(2n) can we make this 0 (1n)
    //assumes v1 and v2 exist
    public void insertEdge(String v1, String v2, String edgeName) {
        Set<Vertex> vertices = graph.keySet();
        Vertex vertex1 = null;
        Vertex vertex2 = null;
        Edge edge = new Edge(edgeName);

        //add in // v != null && // to each if to prevent null pointer exception
        for (Vertex v: vertices) {
            if (v.compareTo(new Vertex(v1)) == 0)
                vertex1 = v;
            if (v.compareTo(new Vertex(v2)) == 0)
                vertex2 = v;
        }

        //System.out.println("Contains v1: " + graph.containsKey(vertex1));
        //System.out.println("Contains v2: " + graph.containsKey(vertex1));
        ArrayList<GraphPairing> v1EdgeList = graph.get(vertex1);
        if (v1EdgeList == null)
            graph.put(vertex1, new ArrayList<>());
        graph.get(vertex1).add(new GraphPairing(vertex2, edge));

        ArrayList<GraphPairing> v2EdgeList = graph.get(vertex2);
        if (v2EdgeList == null)
            graph.put(vertex2, new ArrayList<>());
        graph.get(vertex2).add(new GraphPairing(vertex1, edge));

        numEdges++;
    }

    public Set<Edge> edges() {
        Set<Vertex> vertices = vertices();
        Set<Edge> edges = new HashSet<>();
        for (Vertex v: vertices) {
            for (int i = 0; i < graph.get(v).size(); i++) {//goes through the graph pairings
                Edge tempE = graph.get(v).get(i).getE();//gets graph object for vertice,then gets edge
                edges.add(tempE);
            }
        }
        return edges;
    }

    public Set<Vertex> vertices() {
        return graph.keySet();
    }

    public Edge getEdge(Vertex v1, Vertex v2) {
        Set<Vertex> vertices = vertices();
        for (Vertex vertex: vertices) {
            if (vertex.equals(v1)) {//found vertex in set of keys
                for (int i = 0; i < graph.get(vertex).size(); i++) {//loop through key graph objects
                    Vertex gVert = graph.get(vertex).get(i).getV();
                    if (gVert.getName().equals(v2.getName()))
                        return graph.get(vertex).get(i).getE();
                }
            }
        }
        return null;
    }

    //add in // v != null && // to each if to prevent null pointer exception
    public Edge getEdge(String u, String v) {
        Vertex v1 = getVertex(u);
        Vertex v2 = getVertex(v);
        Set<Vertex> vertices = vertices();
        for (Vertex vertex: vertices) {
            if (vertex.equals(v1)) {//found vertex in set of keys
                for (int i = 0; i < graph.get(vertex).size(); i++) {//loop through key graph objects
                    Vertex gVert = graph.get(vertex).get(i).getV();
                    if (gVert.getName().equals(v2.getName()))
                        return graph.get(vertex).get(i).getE();
                }
            }
        }
        return null;
    }


    public Vertex[] endVertices(Edge e) {
        Vertex[] endVertices  = new Vertex[2];
        Set<Vertex> vertices = vertices();

        for (Vertex v: vertices) {
            for (int i = 0; i < graph.get(v).size(); i++) {
                if (graph.get(v).get(i).getE().equals(e)) {//found edge pairing in graph object
                    endVertices[0] = v;
                    endVertices[1] = graph.get(v).get(i).getV();
                    return endVertices;//don't want to run again
                }
            }
        }
        return endVertices;//will return empty array
    }

    public Edge getEdge(String name) {
        Set<Vertex> vertices = vertices();
        for (Vertex v: vertices) {
            for (int i = 0; i < graph.get(v).size(); i++) {
                Edge e = graph.get(v).get(i).getE();
                if (e.getName().equals(name))
                    return e;
            }
        }
        return null;
    }

    //same as in degree
    public int outDegree(Vertex v) {
        ArrayList<GraphPairing> list = graph.get(v);
        return list.size();
    }
    public int outDegree(String v) {
        Vertex v1 = getVertex(v);
        ArrayList<GraphPairing> list = graph.get(v1);
        return list.size();
    }
    public Vertex getVertex(String u) {
        Set<Vertex> vertices = vertices();
        for (Vertex v: vertices) {
            if (v.getName().equals(u))
                return v;
        }
        return null;
    }


}
