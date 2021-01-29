package environnement;

/**
 * @henri
 * classe permetant l'affichage de l'environement et de l'agent pour suivre les actions réalisé.
 * des indicateurs comme les scores de poussiere et bijou serons aussi affiché
 * seul l'environement fait appel a display
 * d'autre methode peuvent etre créé si besoin
 * pour tester le déplacement de l'agent tu peux le faire bouger arbitraiaremnt en appelant agentMove dans agent.run
 *  pour une représentation photoréaliste cf src/main/ressources/ui.png
 */
public class Display {

    private static Display display;

    private Display() {

    }

    /**
     * initialise l'affichage et lance la fenetre
     */
    public static void init() {
        display = new Display();
    }

    /**
     * met a jour les éléments de la fenetre
     * @param event
     * @param position
     */
    public static void render(Event event, Position position){

    }
}
