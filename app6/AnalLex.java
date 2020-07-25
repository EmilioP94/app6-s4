package app6;

/** @author Ahmed Khoumsi */




/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
private final String stringToAnalyse;
private int pointeurLecture;
private int state;
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
      char nextChar = nextCharValue();
      this.pointeurLecture++;
      switch (state) {
        case 0:
          if( nextChar == '+' ||
              nextChar == '-' ||
              nextChar == '*' ||
              nextChar == '/' ||
              nextChar == '(' ||
              nextChar == ')'){
            unit = Character.toString(nextChar);
            terminal.setChaine(unit);
            return terminal;
          }
          else if (!String.valueOf(nextChar).matches(validCharRegEx)) {
            ErreurLex("not a valid character");
          }
          else {
            unit += nextChar;
            this.state = 1;
            break;
          }

        case 1:
          if (String.valueOf(nextChar).matches(validCharRegEx)){
            unit += nextChar;
            if (!resteTerminal())
              terminal.setChaine(unit);
          }
          else {
            boolean matchesNumber = unit.matches(numberRegEx);
            boolean matchesWord = unit.matches(wordRegEx);
            if ( matchesNumber || matchesWord ){
              terminal.setChaine(unit);
            } else {
              ErreurLex("Invalid string");
            }
            this.state = 0;
            if (!unit.equals("(") || !unit.equals(")")){
              this.pointeurLecture--;
            }
            return terminal;
          }
      }
    }
  return terminal;
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) {	
     throw new Error(s);
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
