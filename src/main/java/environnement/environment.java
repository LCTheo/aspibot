package environnement;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;

/**
 * @henri
 */
public class environment implements Runnable{

    /**
     * carte des piece avec les éléments
     */
    private final Room[][] map = new Room[5][5];

    private Position agentPosition;

    /**
     * score de recolte des bijou. +1 si ramassé, -1 si aspiré.
     */
    private int Jewelscore;

    /**
     * score de ramassage, = poussiere_ramassé/poussire_total
     */
    private float DustScore;


    private int electricityCost;

    /**
     * constructeur de la classe, initialise la map et les scores,
     * la position de départ est donné par l'agent
     */
    public environment() {
        //Création du tableau de cases
        int i = 0;
        int j = 0;
        while(i<5){
            while(j<5){
                Room room = new Room();
                this.map[i][j] = room;
                j++;
            }
            j=0;
            i++;
        }

        //Ici on génère 4 poussière et 1 diamant
        int n = 0;
        while(n<4){
            addDust();
            n++;
        }
        addJewel();

    }

    /**
     * processus de l'envinonement qui génère les nouvelles poussiere et bijou
     */
    @Override
    public void run() {
        while(true){

            //Création d'un timer pour générer les poussières et les diamants selon une certaine fréquence
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Probabilité d'avoir une nouvelle poussière (1 chance sur 2)
            double probD = Math.random();
            if(probD>=0.5){
                addDust();
            }
            //Probabilité d'avoir un nouveau bijou perdu (1 chance sur 3)
            double probJ = Math.random();
            if(probJ<=0.33){
                addJewel();
            }
        }

    }

    /**
     * ajoute un bijou dans l'environement
     */
    private void addJewel(){
         Position position = new Position();
         position.setX((int) (Math.random()*(4-0+1)+0));
         position.setY((int) (Math.random()*(4-0+1)+0));
         this.map[position.getX()][position.getY()].setJewel(true);
         Display.render(Event.addJewel, position);
    }

    /**
     * ajoute une poussiere dans l'environement
     */
    private void addDust(){
        Position position = new Position();
        position.setX((int) (Math.random()*(4-0+1)+0));
        position.setY((int) (Math.random()*(4-0+1)+0));
        this.map[position.getX()][position.getY()].setDust(true);
        Display.render(Event.addDust, position);
    }

    /**
     * repercute l'action de ramassage sur l'environement
     * @param position
     */
    public void gather(Position position){
        Display.render(Event.gather, position);
        this.map[position.getX()][position.getY()].setJewel(false);
        this.Jewelscore++;
        this.electricityCost++;
    }

    /**
     * repercute l'action de nettoyage sur l'environement
     * @param position
     */
    public void clean(Position position){
        Display.render(Event.clean, position);
        this.map[position.getX()][position.getY()].setDust(false);
        this.DustScore++;
        this.electricityCost++;
    }

    /**
     * repercute l'action de déplacement de l'agent sur l'environement
     * @param position
     */
    public void agentMove(Position position){

        Display.render(Event.move, position);
        this.electricityCost++;
    }

    public Room[][] getMap() {
        return map;
    }

    public int getJewelscore() {
        return Jewelscore;
    }

    public float getDustScore() {
        return DustScore;
    }

    public int getElectricityCost() {
        return electricityCost;
    }


}
