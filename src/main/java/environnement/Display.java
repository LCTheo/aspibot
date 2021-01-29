package environnement;

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
     * met a jour les élément de la fenetre
     * @param event
     * @param position
     */
    public static void render(Event event, Position position){

    }
}
