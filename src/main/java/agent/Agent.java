package agent;

import environnement.environment;
import environnement.Room;
import environnement.Position;

import java.util.Queue;

public abstract class Agent implements Runnable {

    protected Sensor sensor;
    protected Effector effector;
    protected Room[][] map;
    protected Position position;

    private Queue<Action> plan;

    protected Agent(environment environment){

    }

    @Override
    public void run() {

        // Display.render();
    }

    private void move (){

    }

    private void gather(){

    }

    private void learning(){

    }

    private void updateState(){

    }

    protected abstract void planning();
}
