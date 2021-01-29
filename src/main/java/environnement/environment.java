package environnement;

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
    private void addJewel(){}

    /**
     * ajoute une poussiere dans l'environement
     */
    private void addDust(){}

    public Room[][] getMap() {
        return map;
    }

    /**
     * repercute l'action de ramassage sur l'environement
     * @param position
     */
    public void gather(Position position){}

    /**
     * repercute l'action de nettoyage sur l'environement
     * @param position
     */
    public void clean(Position position){}

    /**
     * repercute l'action de déplacement de l'agent sur l'environement
     * @param position
     */
    public void agentMove(Position position){}

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
