package agent;

import environnement.Room;
import environnement.Position;

public class Node {

    private final Room[][] map;
    private final Position position;
    private final Node parent;
    private final int depth;
    private final int pathCost;

    public Node(Room[][] map, Position position, Node parent, int depth, int pathCost) {
        this.map = map;
        this.position = position;
        this.parent = parent;
        this.depth = depth;
        this.pathCost = pathCost;
    }

    public Room[][] getMap() {
        return map;
    }

    public Position getPosition() {
        return position;
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
}
