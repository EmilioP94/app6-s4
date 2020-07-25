package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  private final ElemAST enfant_gauche;
  private final ElemAST enfant_droite;
  private final String op;
  private boolean parentheses = false;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST enfant_gauche, ElemAST enfant_droite, String op, boolean parentheses) { // avec parentheses
    this.enfant_gauche = enfant_gauche;
    this.enfant_droite = enfant_droite;
    this.op = op;
    this.parentheses = parentheses;
  }

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST enfant_gauche, ElemAST enfant_droite, String op) { // sans parentheses
    this.enfant_gauche = enfant_gauche;
    this.enfant_droite = enfant_droite;
    this.op = op;
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
    switch (op) {
      case "+":
        return enfant_gauche.EvalAST() + enfant_droite.EvalAST();
      case "*":
        return enfant_gauche.EvalAST() * enfant_droite.EvalAST();
      case "-":
        return enfant_gauche.EvalAST() - enfant_droite.EvalAST();
      case "/":
        return enfant_gauche.EvalAST() / enfant_droite.EvalAST();
      default:
        throw new Error("Operation non supportee: " + op);
    }
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
    if (parentheses) {
      return  "(" + enfant_gauche.LectAST() + op + enfant_droite.LectAST() + ")";
    } else {
      return  enfant_gauche.LectAST() + op + enfant_droite.LectAST();
    }

  }

}


