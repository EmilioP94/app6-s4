package app6;

/** @authors Emile Paquette, Jeff Miller-Chauvin, Élodie Amar */


/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  enum Etat {
    INIT,
    NUMBER,
    UNIT_END,
    OP,
    VARIABLE,
    UNDERSCORE
  }

  private String lowercase = "abcdefghijklmnopqrstuvwxyz";
  private String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYS";
  private String underscore = "_";
  private String number = "0123456789";
  private String operator = "+-*/()";

  // Attributs
  private final String stringToAnalyse;

  public int getPointeurLecture() {
    return pointeurLecture;
  }

  private int pointeurLecture;
  private int state;
  private Etat etat;
  private Etat etatPrecedent;

  /** Constructeur pour l'initialisation d'attribut(s)
   * @param stringToAnalyse le string à analyser
   */
  public AnalLex(String stringToAnalyse) {  // arguments possibles
    this.stringToAnalyse = stringToAnalyse;
    this.pointeurLecture = 0;
    this.state = 0;
    this.etat = Etat.INIT;
  }


  /** resteTerminal() retourne :
   false  si tous les terminaux de l'expression arithmetique ont ete retournes
   true s'il reste encore au moins un terminal qui n'a pas ete retourne
   */
  public boolean resteTerminal( ) {
    return this.pointeurLecture < this.stringToAnalyse.length();
  }

  /** Retourne la valeur du prochain caractère
   */
  private char nextCharValue() {
    return this.stringToAnalyse.charAt(this.pointeurLecture);
  }

  /** prochainTerminal() retourne le prochain terminal
   Cette methode est une implementation d'un AEF
   */
  public Terminal prochainTerminal( ) {
    Terminal terminal = new Terminal();
    String unit = "";
    while (resteTerminal()) {
      char nextChar = nextCharValue();
      while (nextChar == ' ') {
        pointeurLecture ++;
        nextChar = nextCharValue();
      }
      switch (etat) {
        case INIT:
          if(this.uppercase.contains(Character.toString(nextChar))) {
            etat = Etat.VARIABLE;
            break;
          } else if (this.number.contains(Character.toString(nextChar))) {
            etat = Etat.NUMBER;
            break;
          } else if (this.operator.contains(Character.toString(nextChar))) {
            etat = Etat.OP;
            break;
          } else if (this.lowercase.contains(Character.toString(nextChar))) {
            ErreurLex("Variables must start with an uppercase letter: character '" + nextChar + "' at position " + pointeurLecture + " is lowercase.");
          } else {
            ErreurLex("invalid first character: " + nextChar);
            break;
          }
        case NUMBER:
          if(this.number.contains(Character.toString(nextChar))) {
            unit += nextChar;
            this.pointeurLecture++;
            if (!resteTerminal()) {
              terminal.setChaine(unit);
              terminal.setType("Number");
              return terminal;
            }
            break;
          } else if (this.operator.contains(Character.toString(nextChar))) {
            etatPrecedent = Etat.NUMBER;
            etat = Etat.UNIT_END;
            break;
          } else {
            ErreurLex("you tried to add the character: " + nextChar + "to a number at position" + pointeurLecture);
            break;
          }
        case UNIT_END:
          terminal.setChaine(unit);
          terminal.setType(getEtatPrecedent());
          etat = Etat.INIT;
          return terminal;
        case OP:
          unit = Character.toString(nextChar);
          terminal.setChaine(unit);
          terminal.setType("Operateur");
          pointeurLecture ++;
          etat = Etat.INIT;
          return terminal;
        case VARIABLE:
          if(this.uppercase.contains(Character.toString(nextChar)) || this.lowercase.contains(Character.toString(nextChar))) {
            unit += nextChar;
            pointeurLecture ++;
            if (!resteTerminal()) {
              terminal.setChaine(unit);
              terminal.setType("Variable");
              return terminal;
            }
            break;
          }
          else if (this.underscore.contains(Character.toString(nextChar))) {
            unit += nextChar;
            etat = Etat.UNDERSCORE;
            pointeurLecture ++;
            break;
          } else if (this.operator.contains(Character.toString(nextChar))) {
            etatPrecedent = Etat.NUMBER;
            etat = Etat.UNIT_END;
            break;
          } else {
            ErreurLex("Invalid character: " + nextChar + "at position " + pointeurLecture + ". expected a letter or an underscore.");
            break;
          }
        case UNDERSCORE:
          if (this.underscore.contains(Character.toString(nextChar))) {
            ErreurLex("Two underscores in a row at position: " + pointeurLecture);
          } else if (this.lowercase.contains(Character.toString(nextChar)) || this.uppercase.contains(Character.toString(nextChar))) {
            etat = Etat.VARIABLE;
            break;
          } else {
          ErreurLex("Invalid character " + nextChar + " after underscore at position " + pointeurLecture + ", expected a letter.");
          }
      }
    }
    if (etat == Etat.UNDERSCORE) {
      ErreurLex("Cannot end expression with an underscore.");
    }
    return terminal;
  }


  /** ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) {
    throw new Error(s);
  }

  /**
   * Retourne l'état précedent
   * @return String etatPrecedent: l'état précedent
   */
  public String getEtatPrecedent() {
    switch (etatPrecedent){
      case NUMBER:
        return "Number";
      case VARIABLE:
        return "Variable";
      case OP:
        return "Op";
    }
    return null;
  }

  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite += t.getChaine() + "\t" + t.getType() + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
