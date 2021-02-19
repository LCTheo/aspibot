package agent;

/**
 * extension de la classe node rajoutant le cout théorique restant, pour les besoin de l'algorithme informé A*.
 */
public class InformedNode extends Node{

    // cout théorique restant avant d'atteindre le but
    private int heuristicCost;

    //parent du node
    private InformedNode parent;


    /**
     * constructeur de la classe Node
     * @param state état du node
     * @param parent parent du node
     * @param depth profondeur du node
     * @param pathCost cout pour arriver dans l'état du node
     * @param heuristicCost cout théorique restant
     */
    public InformedNode(State state, Node parent, int depth, int pathCost, int heuristicCost) {
        super(state, parent, depth, pathCost);
        this.heuristicCost = heuristicCost;
    }
    /**
     * constructeur vide de la classe InformedNode
     */
    public InformedNode() {
        super();
    }

    /**
     * setter pour le cout théorique restant
     * @param heuristicCost nouveau coup théorique restant
     */
    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    /**
     * setter pour le parent du node
     * @param parent nouveau parent du node
     */
    public void setParent(InformedNode parent) {
        this.parent = parent;
    }

    @Override
    public InformedNode getParent() {
        return parent;
    }

    /**
     * retourne le cout total théorique jusqu'au but
     * @return cout théorique plus le cout pour arriver dans cette état
     */
    public int getTotalCost(){
        return heuristicCost + getPathCost();
    }
}
