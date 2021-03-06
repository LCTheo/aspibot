package environnement;

import agent.Agent;
import agent.InformedAgent;
import agent.UninformedAgent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * Classe permettant l'affichage de l'environnement et de l'agent pour suivre les actions réalisées.
 * Des indicateurs comme les scores de poussiere et bijoux sont aussi affichés
 * L'environnement fait appel a display pour faire le lien entre l'interface graphique et le coeur du programme interne.
 */
public class Display {

    private static Display display = new Display();
    //Panel principal de la fenêtre graphique
    private JPanel panel;
    //Panel pour afficher le score de l'agent
    private JPanel pan_score;

    //Panel pour afficher les boutons de gestion
    private JFrame frame;

    //Panel footer
    private JPanel south = new JPanel();

    //Panel pour afficher les évènements de l'environnement
    private JScrollPane pan_events;
    //Affichage graphique de l'environnement : tableau 2D de 5 par 5. Chaque Panel représente une case ("Room")
    private JPanel[][] tab_panels = new JPanel[5][5];
    //Score 
    private int score;
    //Évènement
    private Position agentPosition;
    //Labels pour les scores
    private JLabel scorelabelJewel;
    private JLabel scorelabelDust;
    private JLabel costElec;
    //TextArea pour afficher les labels des différents évènements générés par l'environnement
    private JTextArea eventlabel;

    private final ImageIcon dust;
    private final ImageIcon jewel;
    private final ImageIcon vacuum;

    /**
    * Constructeur de la classe
    * Le constructeur va en réaliter construire l'ensemble de l'affichage graphique
    **/
    private Display() {

        //Images pour l'affichage graphique
        BufferedImage dustImg = getImage("/image/dust.png");
        BufferedImage jewelImg = getImage("/image/jewel.png");
        BufferedImage aspibotImg = getImage("/image/aspibot.png");
        this.dust = new ImageIcon(dustImg);
        this.jewel = new ImageIcon(jewelImg);
        this.vacuum = new ImageIcon(aspibotImg);

        //Création de la fenetre principale
        frame = new JFrame();
        this.panel = new JPanel();
        //Création d'une bordure pour délimiter les cases.
        Border border = BorderFactory.createLineBorder(Color.black,2);

        //Création du tableau de cases. Chaque Panel du tableau de JPanel contient 3 JLabels, pour afficher soit une poussière, soit un diamant soit l'agent
        int i = 0;
        int j = 0;
        while(i<5){
            while(j<5){
                JPanel panel = new JPanel();
                //Label pour la poussière
                JLabel label1 = new JLabel("", JLabel.CENTER);
                //Label pour le diamant
                JLabel label2 = new JLabel("", JLabel.CENTER);
                //Label pour l'agent
                JLabel label3 = new JLabel("", JLabel.CENTER);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                this.tab_panels[i][j] = panel;
                panel.setSize(50, 50);
                panel.setBackground(Color.gray);
                panel.setBorder(border);
                j++;
            }
            j=0;
            i++;
        }

        i = 0;
        j = 0;
        while(i<5){
            while(j<5){
                this.panel.add(this.tab_panels[j][i]);
                j++;
            }
            j=0;
            i++;
        }
        //Affichage des cases selon une grille de 5 par 5
        this.panel.setLayout(new GridLayout(5,5));
        frame.add(this.panel);

        //Ajout de la case "Score"
        this.pan_score = new JPanel();
        this.scorelabelJewel = new JLabel("", SwingConstants.CENTER);
        this.scorelabelJewel.setPreferredSize(new Dimension(120,50));
        this.scorelabelJewel.setBorder(new TitledBorder(BorderFactory.createTitledBorder(border, "Score Diamant")));
        this.scorelabelDust = new JLabel("", SwingConstants.CENTER);
        this.scorelabelDust.setPreferredSize(new Dimension(120,50));
        this.scorelabelDust.setBorder(new TitledBorder(BorderFactory.createTitledBorder(border, "Score Poussière")));
        this.costElec = new JLabel("", SwingConstants.CENTER);
        this.costElec.setPreferredSize(new Dimension(120,50));
        this.costElec.setBorder(new TitledBorder(BorderFactory.createTitledBorder(border, "Cout Electricité")));
        this.pan_score.add(scorelabelJewel);
        this.pan_score.add(scorelabelDust);
        this.pan_score.add(costElec);
        this.south.add(this.pan_score, BorderLayout.CENTER);



        frame.add(this.south, BorderLayout.SOUTH);

        //Ajout de la case "Events"
        this.eventlabel = new JTextArea();
        this.eventlabel.setEditable(false);
        this.eventlabel.setBorder(new TitledBorder(BorderFactory.createTitledBorder(border, "Evénements")));
        this.pan_events = new JScrollPane(this.eventlabel);
        this.pan_events.setPreferredSize(new Dimension(360,650));
        
        //Autoscroll pour l'affichage des évènements
        DefaultCaret caret = (DefaultCaret)this.eventlabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.pan_events.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(this.pan_events, BorderLayout.EAST);

        //Caractéristiques globales de la fenêtre principale
        frame.setPreferredSize(new Dimension(1200, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Aspibot");
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);




    }

    /**
     * Initialise l'affichage de l'agent
     * @param environment : instance de l'environnement pour récupérer la position initiale donnée par l'agent
     * @param agentType : type d'agent utilisé
     */
    public static void init(Environment environment, String agentType) {
       render(Event.initpos, environment.getAgentPosition());
       display.frame.setTitle("Aspibot : "+agentType+" agent");

    }

    /**
     * Met a jour les éléments de la fenêtre
     * @param event : un évènement précis permettant de savoir quelle action effectuer pour l'affichage
     * @param position : posisiton de l'évènement pour mettre à jour la case associée
     */
    public static void render(Event event, Position position){
            //Evenement d'initialisation de la position de l'agent
        if(event.equals(Event.initpos)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Initialisation de l'agent en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(2);
            label.setIcon(display.vacuum);
            //Evenement d'aout de poussiere de l'environnement
        } else if(event.equals(Event.addDust)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'une poussière en : \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(0);
            label.setIcon(display.dust);
            //Evenement d'aout de diamant de l'environnement
        } else if (event.equals(Event.addJewel)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Ajout d'un diamant en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(display.jewel);
            //Evenement de ramassage de diamant de l'agent
        } else if (event.equals(Event.gather)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Récupération d'un diamant en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour du score
            display.score++;
            //display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(null);
            //Evenement de nettoyage de l'agent
        } else if (event.equals(Event.clean)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Nettoyage de la pièce en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour du score
            display.score++;
            //display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(null);
            label = (JLabel) panel.getComponent(0);
            label.setIcon(null);
            //Evenement pour supprimer la position de l'agent sur une case donnée
        } else if (event.equals(Event.delBot)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(2);
            label.setIcon(null);
            //Evenement de déplacement de l'agent
        } else if (event.equals(Event.move)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            if(display.agentPosition == null){
                //Affichage de l'évènement
                //Mise à jour de l'affichage
                JLabel label = (JLabel) panel.getComponent(2);
                label.setIcon(display.vacuum);
            }else {
                //Affichage de l'évènement
                display.eventlabel.append("Déplacement de l'agent en: \t"+position.getX()+", "+position.getY()+"\n");
                //Mise à jour de l'affichage
                JLabel label = (JLabel) panel.getComponent(2);
                label.setIcon(display.vacuum);
            }
            display.agentPosition = position ;
        }

    }

    /**
     * Permet de mettre à jour graphiquement le score géré par l'environnement
     * @param score : score à mettre à jour (soit le score des poussière, soit celui des diamants)
     * @param elec : cout d'electricité à mettre à jour
     * @param id : numéro d'identification de la mise à jour. Soit une mise à jour de poussière, soit une mise à jour de diamant, soit d'électricité
     */
    public static void updateScore(int score, int elec, int id){
        if(id==1){
            display.scorelabelJewel.setText(Integer.toString(score));
            display.costElec.setText(Integer.toString(elec));
        } else if(id==2){
            display.scorelabelDust.setText(Integer.toString(score));
            display.costElec.setText(Integer.toString(elec));
        } else if(id==3){
            display.costElec.setText(Integer.toString(elec));
        }
    }

    /**
     * from : https://stackoverflow.com/questions/19447104/load-image-from-a-filepath-via-bufferedimage
     * @param filename : chemain vers l'image à charger
     * @return buffer d'image
     */
    private BufferedImage getImage(String filename) {
        try {
            // Grab the InputStream for the image.
            InputStream in = getClass().getResourceAsStream(filename);

            // Then read it.
            return ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("The image was not loaded.");
        }

        return null;
    }
}
