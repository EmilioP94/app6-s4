package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  private int valeur;
  private String texte;


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(int valeur ) {  // avec arguments
    this.valeur = valeur;
    this.texte = null;
  }

  public FeuilleAST(String texte) {
      this.texte = texte;
      this.valeur = 0;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
    return valeur;
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      String returnValue = null;
      if (texte != null) {
          returnValue = texte;
      } else returnValue = Integer.toString(this.valeur);
    return returnValue;
  }

}
