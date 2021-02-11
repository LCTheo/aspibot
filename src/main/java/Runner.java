import agent.Agent;
import agent.UninformedAgent;
import environnement.Display;
import environnement.environment;

public class Runner {

    public static void main(String[] args) {

        environment env = new environment();
        Agent agent = new UninformedAgent(env);

        Thread thread_env = new Thread(env);
        Thread thread_agent = new Thread(agent);

        thread_env.start();
        thread_agent.start();
        Display.init(env);
    }
}
