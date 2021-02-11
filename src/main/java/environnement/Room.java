package environnement;

/**
* Classe permettant de modéliser les attributs de chacune des cases du tableau de l'environnement.
* Chaque Case est en fait une instance de la classe Room et possède deux attribut pour savoir si la case contient une poussière ou un diamant (ou les deux)
*/
public class Room {
    
    //Booléen pour savoir si la case possède une poussière
    private boolean dust;
    
    //Booléen pour savoir si la case possède un diamant
    private boolean Jewel;

    /**
    * Constructeur de la classe. Par défaut, une case ne contient ni de poussière ni de diamant.
    * C'est l'environnement qui gère l'édition des booléens pour ajouter ou supprimer des éléments des différentes cases selon les actions de l'agent. 
    */
    public Room() {
        this.dust = false;
        this.Jewel = false;
    }

    /**
    * Getter pour savoir si de la poussière est présente sur la case
    * return dust : true si poussière présente, falses sinon
    */
    public boolean isDust() {
        return dust;
    }
    
    /**
    * Setter pour éditer l'attribut poussière de la case
    * @param dust : true pour ajouter de la poussière, false pour supprimer
    */
    public void setDust(boolean dust) {
        this.dust = dust;
    }
    
    /**
    * Getter pour savoir si un diamant est présent sur la case
    * return Jewel : true si diamant présent, false sinon
    */
    public boolean isJewel() {
        return Jewel;
    }
    
    /**
    * Setter pour éditer l'attribut diamant de la case
    * @param Jewel : true pour ajouter un diamant, false pour supprimer
    */
    public void setJewel(boolean bijou) {
        this.Jewel = bijou;
    }
}
