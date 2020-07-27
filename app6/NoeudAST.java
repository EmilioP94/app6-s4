package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  private final ElemAST enfantGauche;
  private final ElemAST enfantDroite;
  private final String op;
  private boolean parentheses = false;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST enfantGauche, ElemAST enfantDroite, String op, boolean parentheses) { // avec parentheses
    this.enfantGauche = enfantGauche;
    this.enfantDroite = enfantDroite;
    this.op = op;
    this.parentheses = parentheses;
  }

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST enfantGauche, ElemAST enfantDroite, String op) { // sans parentheses
    this.enfantGauche = enfantGauche;
    this.enfantDroite = enfantDroite;
    this.op = op;
  }

  public String getOp() {
    return this.op;
  }

  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
    switch (op) {
      case "+":
        return enfantGauche.EvalAST() + enfantDroite.EvalAST();
      case "*":
        return enfantGauche.EvalAST() * enfantDroite.EvalAST();
      case "-":
        return enfantGauche.EvalAST() - enfantDroite.EvalAST();
      case "/":
        return enfantGauche.EvalAST() / enfantDroite.EvalAST();
      default:
        throw new Error("Operation non supportee: " + op);
    }
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
      return  "(" + enfantGauche.LectAST() + op + enfantDroite.LectAST() + ")";
  }

  public String postfixAST() {
    return enfantGauche.postfixAST() + enfantDroite.postfixAST() + op + " ";
  }

}


