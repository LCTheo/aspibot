package environnement;

/**
 * @henri
 */
public class environment implements Runnable{



    /**
     * carte des piece avec les éléments
     */
    private Room[][] map;

    private Position agentPosition;

    /**
     * score de recolte des bijou. +1 si ramassé, -1 si aspiré.
     */
    private int Jewelscore;

    /**
     * score de ramassage, = poussiere_ramassé/poussire_total
     */
    private float DustScore;


    private int electricityCost;

    /**
     * constructeur de la classe, initialise la map et les scores,
     * la position de départ est donné par l'agent
     */
    public environment() {

    }

    /**
     * processus de l'envinonement qui génère les nouvelles poussiere et bijou
     */
    @Override
    public void run() {


       // Display.render();
    }

    /**
     * ajoute un bijou dans l'environement
     */
    private void addJewel(){

    }

    /**
     * ajoute une poussiere dans l'environement
     */
    private void addDust(){

    }

    /**
     * repercute l'action de ramassage sur l'environement
     * @param position
     */
    public void gather(Position position){

        // Display.render();
    }

    /**
     * repercute l'action de nettoyage sur l'environement
     * @param position
     */
    public void clean(Position position){

        // Display.render();
    }

    /**
     * repercute l'action de déplacement de l'agent sur l'environement
     * @param position
     */
    public void agentMove(Position position){

        // Display.render();
    }

    public Room[][] getMap() {
        return map;
    }

    public int getJewelscore() {
        return Jewelscore;
    }

    public float getDustScore() {
        return DustScore;
    }

    public int getElectricityCost() {
        return electricityCost;
    }


}
