/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_quiestce;

/**
 *
 * @author clecann
 */
public class TestBase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String nomFichier = "quiestce";
        BaseDD maBase = new BaseDD();
        maBase.lireFichier(nomFichier);
        //System.out.println(maBase);
        maBase.jouer();
    }
    
}
