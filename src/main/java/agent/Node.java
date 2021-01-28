package agent;

import environnement.Room;
import environnement.Position;

public class Node {

    private final Room[][] carte;
    private final Position position;
    private final Node parent;
    private final int profondeur;
    private final int cout_chemain;

    public Node(Room[][] carte, Position position, Node parent, int profondeur, int cout_chemain) {
        this.carte = carte;
        this.position = position;
        this.parent = parent;
        this.profondeur = profondeur;
        this.cout_chemain = cout_chemain;
    }

    public Room[][] getCarte() {
        return carte;
    }

    public Position getPosition() {
        return position;
    }

    public Node getParent() {
        return parent;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public int getCout_chemain() {
        return cout_chemain;
    }
}
