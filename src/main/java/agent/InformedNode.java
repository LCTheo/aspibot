package agent;

/**
 * extension de la classe noeud rajoutant le cout théorique restant, pour les besoin de l'algorithme informé A*.
 */
public class InformedNode extends Node{

    // cout théorique restant avant d'atteindre le but
    private int heuristicCost;

    //parent du noeud
    private InformedNode parent;


    /**
     * constructeur de la classe Node
     * @param state état du noeud
     * @param parent parent du noeud
     * @param depth profondeur du noeud
     * @param pathCost cout pour arriver dans l'état du noeud
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
     * setter pour le parent du noeud
     * @param parent nouveau parent du noeud
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
     * @return cout théorique plus le cout pour arriver dans cet état
     */
    public int getTotalCost(){
        return heuristicCost + getPathCost();
    }
}
