package agent;

import environnement.Display;
import environnement.Environnement;
import environnement.Piece;

import java.util.Queue;

public abstract class Agent implements Runnable {

    protected Capteur capteur;
    protected Actionneur actionneur;
    protected Piece[][] carte;
    protected int[] position = new int[2];

    private Queue<Action> plan;

     protected Agent(Environnement environnement){

     }



    private void bouger (int i, int j){

    }

    private void ramasser(){}

    protected abstract void planification();



    private void apprentisage(){}


}
