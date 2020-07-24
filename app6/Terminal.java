package app6;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {


// Constantes et attributs
//  ....
  private String chaine;


/** Un ou deux constructeurs (ou plus, si vous voulez)
  *   pour l'initalisation d'attributs 
 */	
  public Terminal(String chaine) {   // arguments possibles
     this.chaine = chaine;
  }

  public Terminal() {   // arguments possibles
    this.chaine = "";
  }

  public String getChaine() {
    return this.chaine;
  }

  public void setChaine(String chaine) {
    this.chaine = chaine;
  }

}
