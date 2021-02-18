package environnement;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;

/**
 * Classe principale de l'environnement.
 * Permet de gérer les évènements et des répercuter les actions de l'agent sur l'environnement et par conséquent sur l'affichage graphique.
 */
public class Environment implements Runnable{

    //Carte des pieces avec les éléments : tableau en 2D d'instance de la classe "Room"
    private final Room[][] map = new Room[5][5];
    
    // Position de l'agent
    private Position agentPosition;
    
    //Score de recolte des bijoux. +1 si ramassé, -1 si aspiré. 
    private int Jewelscore;
    
    //Score de ramassage, = poussiere_ramassé/poussiere_total
    private float DustScore;

    //Cout en électricté pour la mesure de performance de l'agent
    private int electricityCost = -1;
    private boolean started;

    /**
     * constructeur de la classe, initialise la map et les scores,
     * La position de départ est donnée par l'agent
     */
    public Environment() {
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
        started = false;
    }

    /**
     * Processus de l'environnement qui génère les nouvelles poussieres et bijoux de façon aléatoire
     */
    @Override
    public void run() {
    while (true){
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
            //Création d'un timer pour générer les poussières et les diamants selon une certaine fréquence
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ajoute un bijou dans l'environnement
     * Met à jour l'affichage graphique avec l'appel à Display.Render(Event, Position)
     */
    private void addJewel(){
         int x = (int) (Math.random()*(4-0+1)+0);
         int y = (int) (Math.random()*(4-0+1)+0);
         Position position = new Position(x, y);
      
         this.map[position.getX()][position.getY()].setJewel(true);
        //Mise à jour graphique
         Display.render(Event.addJewel, position);
    }

    /**
     * Ajoute une poussiere dans l'environnement
     * Met à jour l'affichage graphique avec l'appel à Display.Render(Event, Position)
     */
    private void addDust(){
        int x = (int) (Math.random()*(4-0+1)+0);
        int y = (int) (Math.random()*(4-0+1)+0);
        Position position = new Position(x, y);
      
        this.map[position.getX()][position.getY()].setDust(true);
        //Mise à jour graphique
        Display.render(Event.addDust, position);
    }

    /**
     * Repercute l'action de ramassage de l'agent sur l'environnement
     * @param position : position à laquelle l'évènement se produit
     */
    public void gather(Position position){
        //Display.render(Event.gather, position);
        //Mise à jour de l'environnement
        this.map[position.getX()][position.getY()].setJewel(false);
        //Mise à jour graphique
        Display.render(Event.gather, position);
        //Mise à jour des scores
        this.Jewelscore++;
        this.electricityCost++;
        //Mise à jour graphique des scores
        Display.updateScore(this.Jewelscore, this.electricityCost, 1);
    }

    /**
     * Repercute l'action de nettoyage de l'agent sur l'environnement
     * @param position : position à laquelle l'évènement se produit
     */
    public void clean(Position position){
        //Display.render(Event.clean, position);
        //Mise à jour de l'environnement
        this.map[position.getX()][position.getY()].setDust(false);
        //Mise à jour graphique
        Display.render(Event.clean, position);
        //à modifier
        //Mise à jour des scores
        //Mise à jour graphique des scores
        this.DustScore++;
        this.electricityCost++;
        Display.updateScore((int)this.DustScore, this.electricityCost, 2);
    }

    /**
     * Repercute l'action de déplacement de l'agent sur l'environnement
     * @param positionToGoTO : position vers laquelle l'agent se dirige
     * @param id : identifiant pour connaitre la position précédente de l'agent
     */
    public void agentMove(Position positionToGoTO, int id){
        //Mise à jour graphique pour supprimer l'agent de sa case précédente
        if (id == 0){
            this.agentPosition = positionToGoTO;
            return;
        }
        if(id==1){
            Position oldposition = new Position(positionToGoTO.getX(), positionToGoTO.getY()-1);
            Display.render(Event.delBot, oldposition);
        } else if(id==2){
            Position oldposition = new Position(positionToGoTO.getX()+1, positionToGoTO.getY());
            Display.render(Event.delBot, oldposition);
        } else if(id==3){
            Position oldposition = new Position(positionToGoTO.getX()-1, positionToGoTO.getY());
            Display.render(Event.delBot, oldposition);
        } else if (id==4){
            Position oldposition = new Position(positionToGoTO.getX(), positionToGoTO.getY()+1);
            Display.render(Event.delBot, oldposition);
        }
        //Mise à jour de l'environnement
        this.agentPosition = positionToGoTO;
        //Mise à jour graphique
        Display.render(Event.move, positionToGoTO);
        //Mise à jour du cout de l'agent
        this.electricityCost++;
        //Mise à jour graphique du cout de l'agent
        Display.updateScore(0, this.electricityCost, 3);
    }

    /**
    * Getter pour obtenir la carte de l'environnement (tableau 2D de Room)
    * return map : la carte de l'environnement (tableau 2D de Room)
    */
    public Room[][] getMap() {
        return map;
    }
    
    /**
    * Getter pour obtenir le score de diamant de l'agent
    * return Jewelscore : le score de diamant de l'agent
    */
    public int getJewelscore() {
        return Jewelscore;
    }
    
    /**
    * Getter pour obtenir le score de poussière de l'agent
    * return DustScore : le score de poussière de l'agent
    */
    public float getDustScore() {
        return DustScore;
    }
    
    /**
    * Getter pour obtenir la mesure de performance (cout d'electricité) de l'agent
    * return electricityCost : la mesure de performance (cout d'electricité) de l'agent
    */
    public int getElectricityCost() {
        return electricityCost;
    }
    
    /**
    * Getter pour obtenir la position de l'agent 
    * return this.agentPosition : la position de l'agent 
    */
    public Position getAgentPosition(){
        return this.agentPosition;
    }

    public void setAgentPosition(Position position){
        this.agentPosition = position;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
