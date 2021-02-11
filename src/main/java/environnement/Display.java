package environnement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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
    //Panel pour afficher les évènements de l'environnement
    private JScrollPane pan_events;
    //Affichage graphique de l'environnement : tableau 2D de 5 par 5. Chaque Panel représente une case ("Room")
    private JPanel[][] tab_panels = new JPanel[5][5];
    //Score 
    private int score;
    //Évènement
    private String events;
    //Label pour le score
    private JLabel scorelabel;
    //TextArea pour afficher les labels des différents évènements générés par l'environnement
    private JTextArea eventlabel;

    //Images pour l'affichage graphique
    private ImageIcon dust = new ImageIcon("src/main/resources/dust.png");
    private ImageIcon jewel = new ImageIcon("src/main/resources/jewel.png");
    private ImageIcon vacuum = new ImageIcon("src/main/resources/aspibot.png");

    /**
    * Constructeur de la classe
    * Le constructeur va en réaliter construire l'ensemble de l'affichage graphique
    **/
    private Display() {
        //Création de la fenetre principale
        JFrame frame = new JFrame();
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
                this.panel.add(panel);
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
        this.scorelabel = new JLabel("", SwingConstants.CENTER);
        this.scorelabel.setPreferredSize(new Dimension(100,50));
        this.scorelabel.setBorder(border);
        this.pan_score.add(scorelabel);
        frame.add(this.pan_score, BorderLayout.SOUTH);

        //Ajout de la case "Events"
        this.eventlabel = new JTextArea();
        this.eventlabel.setEditable(false);
        this.eventlabel.setBorder(border);
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
     * @param environmnet : instance de l'environnement pour récupérer la position initiale donnée par l'agent
     */
    public static void init(environment environment) {
       display.render(Event.initpos,environment.getAgentPosition());

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
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(1);
            label.setIcon(null);
            //Evenement de nettoyage de l'agent
        } else if (event.equals(Event.clean)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Nettoyage d'une poussière en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour du score
            display.score++;
            display.scorelabel.setText(Integer.toString(display.score));
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(0);
            label.setIcon(null);
            //Evenement de déplacement de l'agent
        } else if (event.equals(Event.move)){
            JPanel panel = display.tab_panels[position.getX()][position.getY()];
            //Affichage de l'évènement
            display.eventlabel.append("Déplacement de l'agent en: \t"+position.getX()+", "+position.getY()+"\n");
            //Mise à jour de l'affichage
            JLabel label = (JLabel) panel.getComponent(2);
            label.setIcon(display.vacuum);
        }

    }
}
