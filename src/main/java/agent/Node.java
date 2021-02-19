package agent;

import environnement.Room;
import environnement.Position;

public class Node {

    //état correspondant au node
    private State state;
    //parent du noeud
    private Node parent;
    //action correspondant au node
    private Action action;
    //profondeur du node
    private int depth;
    //cout pour arriver dans l'état du node
    private int pathCost;


    /**
     * constructeur de la classe Node
     * @param state état du node
     * @param parent parent du node
     * @param depth profondeur du node
     * @param pathCost cout pour arriver dans l'état du node
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
     * getter de l'état correspondant au node
     * @return this.state
     */
    public State getState() {
        return state;
    }

    /**
     * getter du parent du node
     * @return this.parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * getter de la profondeur du node
     * @return this.depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * getter du cout pour arriver à ce node
     * @return this.pathCost
     */
    public int getPathCost() {
        return pathCost;
    }

    /**
     * getter de l'action associé au node
     * @return this.action
     */
    public Action getAction() {
        return action;
    }

    /**
     * setter pour l'état du node
     * @param state nouvelle état du node
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * setter pour le parent du node
     * @param parent nouveau parent du node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * setter pour la profondeur du node
     * @param depth nouvelle profondeur du node
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * setter pour le cout du node
     * @param pathCost nouveau cout du node
     */
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * setter pour l'action associé au node
     * @param action nouvelle action associé du node
     */
    public void setAction(Action action) {
        this.action = action;
    }
}
