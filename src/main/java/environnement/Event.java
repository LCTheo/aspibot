package environnement;

/**
* La Classe Event permet de catégoriser les différents types d'évènements se produisant dans l'environnement
* Chaque évènement est donc unique, permettant à l'environnement de mettre à jour l'affichage graphique selon l'évènement donné.  
*
*/
public class Event {
    private int id;

    //Evenement de déplacement de l'agent
    public static final Event move = new Event();

    //Evenement de ramassage de diamant de l'agent
    public static final Event gather = new Event();

    //Evenement de nettoyage de l'agent
    public static final Event clean = new Event();
    
    //Evenement d'aout de diamant de l'environnement
    public static final Event addJewel = new Event();
    
    //Evenement d'aout de poussiere de l'environnement
    public static final Event addDust = new Event();
    
    //Evenement d'initialisation de la position de l'agent
    public static final Event initpos = new Event();

    /**
    * Constructeur vide de la classe Event
    */
    private Event() {

    }
}
