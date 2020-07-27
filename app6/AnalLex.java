package app6;

/** @author Ahmed Khoumsi */




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
  private int pointeurLecture;
  private int state;
  private Etat etat;
  private final String validCharRegEx = "[0-9a-zA-Z_]+";
  private final String numberRegEx = "[0-9]+";
  private final String wordRegEx = "[A-Z](_?[a-zA-Z])*";

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
      printState();
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
              return terminal;
            }
            break;
          } else if (this.operator.contains(Character.toString(nextChar))) {
            etat = Etat.UNIT_END;
            break;
          } else {
            ErreurLex("you tried to add the character: " + nextChar + "to a number at position" + pointeurLecture);
            break;
          }
        case UNIT_END:
          terminal.setChaine(unit);
          etat = Etat.INIT;
          return terminal;
        case OP:
          //pas besoin de valider si operateur valide car deja validé dans etat INIT
          unit = Character.toString(nextChar);
          terminal.setChaine(unit);
          pointeurLecture ++;
          etat = Etat.INIT;
          return terminal;
        case VARIABLE:
          if(this.uppercase.contains(Character.toString(nextChar)) || this.lowercase.contains(Character.toString(nextChar))) {
            unit += nextChar;
            pointeurLecture ++;
            if (!resteTerminal()) {
              terminal.setChaine(unit);
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

  public void printState() {
    if (this.etat == Etat.INIT)
      System.out.println("Init");
    if (this.etat == Etat.NUMBER)
      System.out.println("Number");
    if (this.etat == Etat.VARIABLE)
      System.out.println("Variable");
    if (this.etat == Etat.OP)
      System.out.println("Op");
    if (this.etat == Etat.UNDERSCORE)
      System.out.println("Underscore");
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
      toWrite += t.getChaine() + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
