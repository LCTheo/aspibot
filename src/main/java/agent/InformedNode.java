package agent;

public class InformedNode extends Node{
    private int heuristicCost;

    private InformedNode parent;


    public InformedNode(State state, Node parent, int depth, int pathCost, int heuristicCost) {
        super(state, parent, depth, pathCost);
        this.heuristicCost = heuristicCost;
    }
    public InformedNode() {
        super();
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public void setParent(InformedNode parent) {
        this.parent = parent;
    }

    @Override
    public InformedNode getParent() {
        return parent;
    }

    public int getTotalCost(){
        return heuristicCost + getPathCost();
    }
}
