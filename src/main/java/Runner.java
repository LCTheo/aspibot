import agent.Agent;
import agent.InformedAgent;
import agent.UninformedAgent;
import environnement.Display;
import environnement.Environment;

import java.util.Locale;

public class Runner {

    public static void main(String[] args) {
        Environment env = new Environment();
        Agent agent;
        String type;
        if (args.length > 0){
            if (args[0].toLowerCase(Locale.ROOT).equals("informed")){
                agent = new InformedAgent(env);
                type = "informed";
            }
            else {
                agent = new UninformedAgent(env);
                type = "uninformed";
            }
        }else {
            agent = new UninformedAgent(env);
            type = "uninformed";
        }

        Thread thread_env = new Thread(env);
        Thread thread_agent = new Thread(agent);

        thread_env.start();
        thread_agent.start();

        Display.init(env, type);
    }
}
