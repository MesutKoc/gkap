package algorithmen;


import java.io.File;
import java.util.*;
import org.eclipse.jdt.annotation.NonNull;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import io.GraphReader;
import org.graphstream.graph.Edge;


/**
 * Created by MattX7 on 23.10.2016.
 */
public class BFS {
    public static boolean preview = true;
    public int steps = -1;
    private Graph graph;
    private Node source;
    private Node target;

    public void init(Graph graph) {
        this.graph = graph;
        setSourceAndTarget(graph.getNode(0), graph.getNode(graph.getNodeCount() - 1));
    }

    public void compute() {
        if (graph == null || source == null || target == null) // have to be set
            throw new IllegalArgumentException();
        
        reset();
     
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(tag(source, -1)); // start with source
        source.setAttribute("ui.class", "markBlue");
        while (!queue.isEmpty()) {
            Node next = queue.peek();
            next.setAttribute("ui.class", "markRed");
            queue.addAll(getUntaggedNeighborsAndTagThem(next));
            if (isTargetTagged()) {
                steps = target.getAttribute("steps");
                break;
            }
            next.setAttribute("ui.class", "markBlue");
            queue.remove(next);
        }
    }

    public List<Node> getShortestPath() {
        if (target.getAttribute("steps").equals(-1))
            throw new IllegalArgumentException("do compute before this method");
        LinkedList<Node> shortestWay = new LinkedList<Node>();
        for (Node node : graph.getEachNode()) {
            node.setAttribute("ui.class", "");
        }
        shortestWay.add(target);
        target.setAttribute("ui.class", "markRed");
        while (!shortestWay.getLast().getAttribute("steps").equals(0)) { // TODO noch eine Abbruchbedingung
            Node next = getShortestNode(shortestWay.getLast()); // TODO Nullable
            next.setAttribute("ui.class", "markRed");
            shortestWay.add(next);
        }
        return shortestWay;
    }

    @NonNull
    private Node getShortestNode(Node node) {
        Iterator<Node> nodeIterator = node.getNeighborNodeIterator();
        while (nodeIterator.hasNext()) {
            Node next = nodeIterator.next();
            if (next.getAttribute("steps").equals((((Integer) node.getAttribute("steps")) - 1))) {
                return next;
            }
        }
        return null;
    }

    /**
     * Sets source and target before compute()
     *
     * @param s source node
     * @param t target node
     */ // TODO methode in eine Klasse AlgoHelper oder so auslagern
    public void setSourceAndTarget(@NonNull Node s, @NonNull Node t) {
        if (this.source != null && this.source.hasAttribute("title"))
            this.source.removeAttribute("title");
        if (this.target != null && this.target.hasAttribute("title"))
            this.target.removeAttribute("title");
        this.source = s;
        this.target = t;
        source.setAttribute("title", "source");
        target.setAttribute("title", "target");
    }

    // ====== PRIVATE =========

    /**
     * All untagged shortestWay that ar neighbors.
     *
     * @param node
     * @return isEmpty if no neighbors are left.
     */
    @NonNull
    private List<Node> getUntaggedNeighborsAndTagThem(@NonNull Node node) {
        List<Node> newTaggedNeighbors = new ArrayList<Node>();
        Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
        while (edgeIterator.hasNext()) {
            Edge nextEdge = edgeIterator.next();
            Node nextNode;
            if (node != nextEdge.getNode1())
                nextNode = nextEdge.getNode1();
            else
                nextNode = nextEdge.getNode0();
            if (nextNode.getAttribute("steps").toString().equals("-1")) {
                newTaggedNeighbors.add(
                        tag(nextNode, Integer.valueOf(node.getAttribute("steps").toString())));
                nextNode.setAttribute("ui.class", "markBlue");
            }

        }
        return newTaggedNeighbors;
    }

    private void reset() {
        this.steps = -1;
        for (Node node : graph.getEachNode()) {
            node.setAttribute("steps", -1);
        }
    }

    /**
     * tag Node
     *
     * @param node
     * @param steps
     * @return the node param for inline use
     */
    @NonNull
    private Node tag(@NonNull Node node, @NonNull Integer steps) {
        node.setAttribute("steps", steps + 1);
        node.setAttribute("ui.label", node.getAttribute("ui.label") + " | " + (steps + 1) + " |");
        return node;
    }

    @NonNull
    private Boolean isTargetTagged() {
        return (!target.getAttribute("steps").equals(-1));
    }


    public static void main(String[] args) throws Exception {
    	GraphReader reader = new GraphReader();
//        Graph graph = IOGraph.fromFile("MyGraph", new File("src/main/resources/input/BspGraph/graph02.gka"));
    	Graph graph = reader.openFile(new File("bspGraphen/graph01.gka"));

        BFS bfs = new BFS();
        bfs.init(graph);
        bfs.setSourceAndTarget(graph.getNode("a"),graph.getNode("c"));
    
        bfs.compute();
        System.out.println(bfs.getShortestPath().toString());
        System.out.println("Steps" + bfs.steps);

    }
}