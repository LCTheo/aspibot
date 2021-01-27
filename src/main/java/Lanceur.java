import agent.Agent;
import environnement.Environnement;

public class Lanceur {

    public static void main(String[] args) {

        Environnement env = new Environnement();
        Agent agent = new Agent(env);

        Thread thread_env = new Thread(env);
        Thread thread_agent = new Thread(agent);

        thread_env.start();
        thread_agent.start();
    }
}
