package agent;

import environnement.Position;
import environnement.Room;

/**
 * Classe représentant un état du probleme.
 * Cela comprend un tableau de piece qui contient un indicateur de poussiere et de bijou et la position de l'agent.
 */
public class State {

    //tableau de piece représentent l'environement
    private final Room[][] map;

    //position de l'agent dans cette état
    private final Position agentPosition;

    /**
     * Constructeur de la classe State
     * @param map tableau de piece
     * @param agentPosition position de l'agent
     */
    public State(Room[][] map, Position agentPosition) {
        this.map = new Room[5][5];
        int i = 0;
        int j = 0;
        while(i<5){
            while(j<5){
                Room room = new Room();
                room.setJewel(map[i][j].isJewel());
                room.setDust(map[i][j].isDust());
                this.map[i][j] = room;
                j++;
            }
            j=0;
            i++;
        }
        this.agentPosition = agentPosition;
    }

    /**
     * getter du tableau de piece
     * @return tableau de piece
     */
    public Room[][] getMap() {
        return map;
    }

    /**
     * retourne les indicateurs de présence pour une piece donnée
     * @param i coordonné x de la piece
     * @param j coordonné y de la piece
     * @return une piece
     */
    public Room getRoom(int i, int j){
        return this.map[i][j];
    }

    /**
     * permet de modifier les indicateurs de présence pour une piece donnée
     * @param i coordonné x de la piece
     * @param j coordonné y de la piece
     * @param room indicateur de présence de la piece
     */
    public void updateRoom(int i, int j, Room room) {
        this.map[i][j].setDust(room.isDust());
        this.map[i][j].setJewel(room.isJewel());
    }

    /**
     * getter de la position de l'agent
     * @return
     */
    public Position getAgentPosition() {
        return agentPosition;
    }

    /**
     * setter de la position de l'agent
     * @param x nouvelle coordonné x
     * @param y nouvelle coordonné y
     */
    public void setAgentPosition(int x, int y) {
        this.agentPosition.setX(x);
        this.agentPosition.setY(y);
    }

    /**
     * affiche le tableau de piece avec les indicateurs de présence dans la console
     */
    public void printMap(){
        System.out.println("+--+--+--+--+--+");
        System.out.print("|" +(this.map[0][0].isDust() ? 1 : 0)+""+(this.map[0][0].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[0][1].isDust() ? 1 : 0)+""+(this.map[0][1].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[0][2].isDust() ? 1 : 0)+""+(this.map[0][2].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[0][3].isDust() ? 1 : 0)+""+(this.map[0][3].isJewel() ? 1 : 0)+"|");
        System.out.println((this.map[0][4].isDust() ? 1 : 0)+""+(this.map[0][4].isJewel() ? 1 : 0)+"|");
        System.out.println("+--+--+--+--+--+");
        System.out.print("|" +(this.map[1][0].isDust() ? 1 : 0)+""+(this.map[1][0].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[1][1].isDust() ? 1 : 0)+""+(this.map[1][1].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[1][2].isDust() ? 1 : 0)+""+(this.map[1][2].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[1][3].isDust() ? 1 : 0)+""+(this.map[1][3].isJewel() ? 1 : 0)+"|");
        System.out.println((this.map[1][4].isDust() ? 1 : 0)+""+(this.map[1][4].isJewel() ? 1 : 0)+"|");
        System.out.println("+--+--+--+--+--+");
        System.out.print("|" +(this.map[2][0].isDust() ? 1 : 0)+""+(this.map[2][0].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[2][1].isDust() ? 1 : 0)+""+(this.map[2][1].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[2][2].isDust() ? 1 : 0)+""+(this.map[2][2].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[2][3].isDust() ? 1 : 0)+""+(this.map[2][3].isJewel() ? 1 : 0)+"|");
        System.out.println((this.map[2][4].isDust() ? 1 : 0)+""+(this.map[2][4].isJewel() ? 1 : 0)+"|");
        System.out.println("+--+--+--+--+--+");
        System.out.print("|" +(this.map[3][0].isDust() ? 1 : 0)+""+(this.map[3][0].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[3][1].isDust() ? 1 : 0)+""+(this.map[3][1].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[3][2].isDust() ? 1 : 0)+""+(this.map[3][2].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[3][3].isDust() ? 1 : 0)+""+(this.map[3][3].isJewel() ? 1 : 0)+"|");
        System.out.println((this.map[3][4].isDust() ? 1 : 0)+""+(this.map[3][4].isJewel() ? 1 : 0)+"|");
        System.out.println("+--+--+--+--+--+");
        System.out.print("|" +(this.map[4][0].isDust() ? 1 : 0)+""+(this.map[4][0].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[4][1].isDust() ? 1 : 0)+""+(this.map[4][1].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[4][2].isDust() ? 1 : 0)+""+(this.map[4][2].isJewel() ? 1 : 0)+"|");
        System.out.print((this.map[4][3].isDust() ? 1 : 0)+""+(this.map[4][3].isJewel() ? 1 : 0)+"|");
        System.out.println((this.map[4][4].isDust() ? 1 : 0)+""+(this.map[4][4].isJewel() ? 1 : 0)+"|");
        System.out.println("+--+--+--+--+--+");
    }

}
