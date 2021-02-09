package agent;

import environnement.Position;
import environnement.Room;

public class State {

    private Room[][] map;

    private Position agentPosition;

    public State(Room[][] map, Position agentPosition) {
        this.map = map;
        this.agentPosition = agentPosition;
    }

    public Room[][] getMap() {
        return map;
    }

    public void setMap(Room[][] map) {
        this.map = map;
    }

    public Room getRoom(int i, int j){
        return this.map[i][j];
    }

    public void updateRoom(int i, int j, Room room) {
        this.map[i][j] = room;
    }

    public Position getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(Position agentPosition) {
        this.agentPosition = agentPosition;
    }

    public State copy(){
        return new State(this.map, this.agentPosition);
    }

}
