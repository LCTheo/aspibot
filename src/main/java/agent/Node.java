package agent;

/**
 * classe représentant les noeuds de l'arbre de recherche
 */
public class Node {

    //état correspondant au noeud
    private State state;
    //parent du noeud
    private Node parent;
    //action correspondant au noeud
    private Action action;
    //profondeur du noeud
    private int depth;
    //cout pour arriver dans l'état du noeud
    private int pathCost;


    /**
     * constructeur de la classe Node
     * @param state état du noeud
     * @param parent parent du noeud
     * @param depth profondeur du noeud
     * @param pathCost cout pour arriver dans l'état du noeud
     */
    public Node(State state, Node parent, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.depth = depth;
        this.pathCost = pathCost;
    }

    /**
     * constructeur vide de la classe Node
     */
    public Node() {
    }

    /**
     * getter de l'état correspondant au noeud
     * @return this.state
     */
    public State getState() {
        return state;
    }

    /**
     * getter du parent du noeud
     * @return this.parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * getter de la profondeur du noeud
     * @return this.depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * getter du cout pour arriver à ce noeud
     * @return this.pathCost
     */
    public int getPathCost() {
        return pathCost;
    }

    /**
     * getter de l'action associé au noeud
     * @return this.action
     */
    public Action getAction() {
        return action;
    }

    /**
     * setter pour l'état du noeud
     * @param state nouvelle état du noeud
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * setter pour le parent du noeud
     * @param parent nouveau parent du noeud
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * setter pour la profondeur du noeud
     * @param depth nouvelle profondeur du noeud
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * setter pour le cout du noeud
     * @param pathCost nouveau cout du noeud
     */
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * setter pour l'action associé au noeud
     * @param action nouvelle action associé du noeud
     */
    public void setAction(Action action) {
        this.action = action;
    }
}
