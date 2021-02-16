import agent.Agent;
import agent.InformedAgent;
import agent.UninformedAgent;
import environnement.Display;
import environnement.environment;

public class Runner {

    public static void main(String[] args) {

        environment env = new environment();
        Agent uninformedAgent = new UninformedAgent(env);
        Agent informedAgent = new InformedAgent(env);

        Thread thread_env = new Thread(env);
        Thread thread_agent1 = new Thread(uninformedAgent);
        Thread thread_agent2 = new Thread(informedAgent);

        thread_env.start();
        thread_agent2.start();
        Display.init(env);
    }
}
