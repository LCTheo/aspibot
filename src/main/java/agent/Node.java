package agent;

import environnement.Room;
import environnement.Position;

public class Node {

    private State state;
    private Node parent;
    private Action action;
    private int depth;
    private int pathCost;





    public Node(State state, Node parent, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        this.pathCost = pathCost;
    }

    public Node() {
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public int getPathCost() {
        return pathCost;
    }

    public Action getAction() {
        return action;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
