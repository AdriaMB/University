package libraries.dataStructures.graphs;

import libraries.dataStructures.models.Queue;
import libraries.dataStructures.models.ListPOI;
import libraries.dataStructures.linear.ArrayQueue;

// IN THE SECOND SESSION: include the following import statements:
import libraries.dataStructures.models.UFSet;
import libraries.dataStructures.hierarchical.ForestUFSet;
import libraries.dataStructures.models.PriorityQueue;
import libraries.dataStructures.hierarchical.BinaryHeap;

/** Abstract Graph class: basis for the Graph hierarchy, which
 *  defines a graph's behaviour.<br>
 *  It isn't an interface because it includes both the code for the operations
 *  of a graph that are independent both of its type as of their implementation.<br>
 *
 *  @version December 2018
 */

public abstract class Graph {

    protected boolean isDirected; // Indicates whether a graph is Directed or not
    protected int[] visited;      // Nodes visited in a traversal
    protected int visitOrder;     // Order in which nodes are visited in a traversal
    protected Queue<Integer> q;   // Queue for a BFS traversal

    /** Creates and empty graph, Directed if directed is true
     *  or Undirected otherwise.
     *
     * @param directed The type of graph, Directed or Undirected
     */
    public Graph(boolean directed) { isDirected = directed; }

    /** Checks whether a graph is directed or not.
     *
     * @return boolean true if the graph is Directed, false if it is Undirected
     */
    public boolean isDirected() { return isDirected; }

    /** Returns the number of vertices in a graph.
     *
     * @return int number of vertices
     */
    public abstract int numVertices();

    /** Returns the number of edges in a graph.
     *
     * @return int number of edges
     */
    public abstract int numEdges();

    /** Checks whether the edge (i, j) belongs to a graph.
     *
     * @param i    Source vertex
     * @param j    Target vertex
     * @return boolean true if (i, j) appears in the graph, false otherwise.
     */
    public abstract boolean containsEdge(int i, int j);

    /** Returns the weight of the edge (i, j) in a graph, 0 if that edge
     *  is not contained in the graph.
     *
     * @param i    Source vertex
     * @param j    Target vertex
     * @return double Weight of the edge (i, j), or 0 if it doesn't exist.
     */
    public abstract double edgeWeight(int i, int j);

    /** If it isn't in the graph, adds the edge (i, j) to an Unweighted graph.
     *
     * @param i    Source vertex
     * @param j    Target vertex
     */
    public abstract void addEdge(int i, int j);

    /** If it isn't in the graph, adds the edge (i, j) with weight w to a Weighted graph.
     *
     * @param i    Source vertex
     * @param j    Target vertex
     * @param w    Weight of the edge (i, j)
     */
    public abstract void addEdge(int i, int j, double w);

    /** Returns a ListPOI that contains vertex i's adjacent vertices.
     *
     * @param i Vertex from which adjacent vertices are looked up.
     * @return ListPOI with the vertices adjacent to i
     */
    public abstract ListPOI<Adjacent> adjacentTo(int i);

    /** Returns a String with each of the vertices of a graph
     *  and their adjacent vertices, in insertion order.
     *
     * @return  String representing a Graph
     */
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int  i = 0; i < numVertices(); i++) {
            res.append("Vertex: ").append(i);
            ListPOI<Adjacent> l = adjacentTo(i);
            res.append(l.isEmpty() ? " without" : " with").append(" adjacents");
            for (l.begin(); !l.isEnd(); l.next()) {
                res.append(" ").append(l.get());
            }
            res.append("\n");
        }
        return res.toString();
    }

    /** Returns an array with each of the vertices of a graph and their
     *  adjacent vertices, in BFS order.
     *
     * @return  Vertex array visited in a BFS traversal
     */
    public int[] toArrayBFS() {
        int[] res = new int[numVertices()];
        visited = new int[numVertices()];
        visitOrder = 0;
        q = new ArrayQueue<>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visited[i] == 0) {
                toArrayBFS(i, res);
            }
        }
        //We return the array with the order with which each node has been visited
        return res;
    }

    // BFS traversal from the source vertex, which stores its result in res
    protected void toArrayBFS(int origin, int[] res) {
        res[visitOrder++] = origin;
        visited[origin] = 1;
        q.enqueue(origin);
        while (!q.isEmpty()) {
            int u = q.dequeue();
            ListPOI<Adjacent> l = adjacentTo(u);
            for (l.begin(); !l.isEnd(); l.next()) {
                Adjacent a = l.get();
                //Adjacent returns a node that is adjacent to the current one. In this case, the
                //ListPOI l contains the adjacent nodes to origin: in case of origin = 0, the adjacent
                //list would contain [1, 5, 2]
                
                //a.target just returns the value of the adjacent node.
                if (visited[a.target] == 0) {
                    res[visitOrder++] = a.target; // We add to the order visited array the node that
                                                  // we have arrived at
                    visited[a.target] = 1; //  We mark that the adjacent node has been reached
                    q.enqueue(a.target);  // IMPORTANT: now we include in the Queue q the node. Now we make
                    // sure that after visiting all children nodes of the parent, we will do the same
                    // with the children nodes of each child.
                }
            }
        }
    }

    /** PRECONDITION: !this.isDirected()
     *  Returns a subset of edges that connect all vertices in an Undirected
     *  and Connected graph, or null if the graph is Unconnected.
     *
     * @return Edge[], array with the numV - 1 edges that connect the numV
     *         vertices of the graph, or null if the graph is Unconnected.
     */
    public Edge[] bfsSpanningTree() {
        // Result is now and array of Edges. How many?
        Edge[] res = new Edge[numVertices()-1]; // The res array will store the order in which the edges are visited
        visited = new int[numVertices()]; // We also need to mark which nodes have been visited
        visitOrder = 0;
        q = new ArrayQueue<>();
        // We want a Spanning Tree, which needs to be connected. With the previous implementation, the for() loop made sure that EVERY vertice, independently if it was connected to the rest or not, was visited
        // Now, we only make 1 invocation to this method, which means that all unconnected vertices won't be traversed, which implies there will be 0 in the visited[] array
        bfsSpanningTree(visited[0], res);
        // After the invocation, we chech wether all vertices have been visited.
        for(int i = 0; i < numVertices(); i++){
            if(visited[i] == 0){
                return null;
            }
        }
        return res;
    }

    // BFS traversal from the source vertex, which stores its result in res
    protected void bfsSpanningTree(int origin, Edge[] res) {
        visited[origin] = 1; // Checks that this node origin has been visited
        q.enqueue(origin);   // We put in the "pending for visiting" queue this node
        while (!q.isEmpty()) {
            int u = q.dequeue(); // We take the next node to visit
            ListPOI<Adjacent> l = adjacentTo(u); // We obtain the adjacent nodes
            for (l.begin(); !l.isEnd(); l.next()) {
                Adjacent a = l.get(); 
                if (visited[a.target] == 0) {
                    Edge nextEdge = new Edge(u, a.target, a.weight);
                    res[visitOrder++] = nextEdge;       // We add this edge to the res array
                    visited[a.target] = 1;              // Consider the adjacent node as result
                    q.enqueue(a.target);                // We put in the "pending to visit" queue the adjacent node
                }
            }
        }
    }

    /** PRECONDITION: !this.isDirected()
     * Returns a subset of edges that, with minimal cost, connect all the
     * vertices of an Undirected and Connected graph, or null if the graph
     * is Unconnected.
     *
     * @return Edge[], array with the numV - 1 edges that connect the numV
     *         vertices with minimum cost, or null if the graph is Unconnected
     */
    public Edge[] kruskal() {
        // The result will have the size == numVertices() - 1 
        // WHY? The number of edges of the minima
        Edge[] res = new Edge[numVertices()-1];
        
        // First: implement a binary heap (priority queue) that will store all the edges of the 
        // graph, such that those edges with less weight will have more priority
        PriorityQueue<Edge> feasibleEdges = new BinaryHeap<Edge>();
                
        for(int i = 0; i < numVertices(); i++){
            ListPOI<Adjacent> aux = this.adjacentTo(i);
            
            for(aux.begin(); !aux.isEnd(); aux.next()){
                
                Adjacent a = aux.get(); // We get the adjacent to node i. Adjacent contains the target node and the weigth of the edge
                Edge e = new Edge(i, a.getTarget(), a.getWeight()); // We create the object edge
                feasibleEdges.add(e); // We add the edge to the feasibleEdges
                
                System.out.println(e);
            } 
        }
        
        // Second: implement the UF-Set using the UFSet and ForestUFSet classes
        // This structure will check whether an edge extracted from feasibleEdges creates a cycle
        // This check will be implemented by representing the connected components of the subgraph
        // defined by E' with a UF-Set of size N(numVertices())
        
        // Initially, as E' is an empty set, each vertex is alone in its own connected component
        UFSet uf = new ForestUFSet(numVertices());
        int index = 0;  //Cardinality 
        
        System.out.println("Now we will do array traversal");
        //feasibleEdges.isEmpty()!!!!!!!!!!!!!!
        //feasibleEdges won't be null NEVER. However, it might not have elements
        while(index < numVertices() - 1 && !feasibleEdges.isEmpty()){
            //At each iteration we take an edge from feasibleEdges
            //We must check whether this edge creates or not a cycle. MST are acycled
            Edge aux = feasibleEdges.removeMin();
            System.out.println("We want to add: " + aux);
            int ufS = uf.find(aux.getSource());          // ufSource
            int ufT = uf.find(aux.getTarget());          // ufTarget
            
            System.out.println("The source group: " + ufS + " and the target group: " + ufT);
            if(ufS != ufT){
                System.out.println("It doesn't create a cylce. We add it");
                // The vertices belong to different groups. Thus, the cycle is not created
                uf.union(ufS, ufT);     // We join both edges to the same group. Now we know that 
                                        // those edges are connnected via an edge. Any other edge
                                        // trying to connect 2 nodes of the same group will create
                                        // a cycle
                                        
                res[index] = aux;     // We add the edge to the results
                index++;
            }
            // If ufS == ufT, we know they belong to the same group. Connecting them with an edge will
            // create a cycle
            else{
                System.out.println("It creates a cycle. Do not add it" + '\n');
            }
        }        
        
        
        // The function must return the array of edges with minimal cost IF THE GRAPH IS CONNECTED
        // Otherwise, the graph should return null
        
        if(index != numVertices()-1) return null;
        return res;
    }
}
