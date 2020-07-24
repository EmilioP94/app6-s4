package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  private int valeur;


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(int valeur ) {  // avec arguments
    this.valeur = valeur;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
    return valeur;
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return Integer.toString(this.valeur);
  }

}
