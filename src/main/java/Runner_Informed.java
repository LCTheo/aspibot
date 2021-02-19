import agent.Agent;
import agent.InformedAgent;
import agent.UninformedAgent;
import environnement.Display;
import environnement.Environment;

import java.util.Locale;

public class Runner_Informed {

    public static void main(String[] args) {
        Environment env = new Environment();
        Agent agent = new UninformedAgent(env);

        Thread thread_env = new Thread(env);
        Thread thread_agent = new Thread(agent);

        thread_env.start();
        thread_agent.start();

        Display.init(env, "uninformed");
    }
}
