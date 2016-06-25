/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author mlardeux
 */
public class JeuId3 {

    public Node racine;
    public ArrayList<String[]> question; // intitulé/question 

    public JeuId3(Node racine) {
        this.racine = racine;
        this.question = new ArrayList<String[]>();
    }

    public Node getRacine() {
        return racine;
    }

    public ArrayList<String[]> getQuestion() {
        return question;
    }

    public void setRacine(Node racine) {
        this.racine = racine;
    }

    public void setQuestion(ArrayList<String[]> question) {
        this.question = question;
    }

    /**
     * methode qui prend en argument le nom du fichier ou se situe la base de
     * question et modifie la base de question de this
     */
    public void lireFichierQ(String nomFichier) {
        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier + ".csv"));
            while (fichier.ready()) {
                String ligne = fichier.readLine();
                String[] mots = ligne.split(";").clone();
                this.question.add(mots);
            }
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode appelé par la fenetre menu pour le lancement du jeu methode qui
     * lance le jeu
     */
    public void jouerId3(String nomFichierBase, String nomFicherQ, String musique) {
        BaseDD maBase = new BaseDD();
        maBase.lireFichier(nomFichierBase);
        Node noeud = new Node(maBase);
        noeud.creeArbre();
        this.lireFichierQ(nomFicherQ);
        this.setRacine(noeud);
        InputStream music;
        try {
            music = new FileInputStream(new File(musique));
            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
            this.racine.poseQ(this.getQuestion());
            AudioPlayer.player.stop(audios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    /**
     * methode lancant la fenetre menu du jeu methode qui demarre le jeu
     */
    public static void jeu() {
        ImageIcon img2 = new ImageIcon("akinator_defi2.png");
        JOptionPane.showMessageDialog(null,
                "Bonjour, je suis Akiµtor, le génie de l'ENSMM." + '\n'
                + "En temps normal, je pourrais t'exhausser trois voeux." + '\n'
                + "Mais la j'ai trop pas le temps, alors, je trouverais juste à qui tu penses.",
                " ", JOptionPane.INFORMATION_MESSAGE,
                img2);
        FenetreMenu menu = new FenetreMenu();
        menu.lancerJeu();
    }

}
