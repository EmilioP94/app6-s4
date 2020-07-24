package app6;


/**
 * @author Ahmed Khoumsi
 */

import static java.lang.Integer.parseInt;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    private Reader reader;
    private AnalLex lexical;

    /** Constructeur de DescenteRecursive :
     - recoit en argument le nom du fichier contenant l'expression a analyser
     - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String in) {
        this.reader = new Reader(in);
        this.lexical = new AnalLex(reader.toString());
    }


    /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     *    Elle retourne une reference sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() {
        ElemAST racine = null;
        racine = E();
        return racine;
    }


// Methode pour chaque symbole non-terminal de la grammaire retenue

    public ElemAST E() {
        Terminal t = null;
        String chaine = null;
        ElemAST e = null;
        ElemAST n1 = null;
        ElemAST n2 = null;

        t = lexical.prochainTerminal();
        n1 = T(t);
        if (!lexical.resteTerminal()) {
            e = n1;
        }

        t = lexical.prochainTerminal();
        chaine = t.getChaine();
        switch (chaine) {
            case "+":
                n2 = E();
                e = new NoeudAST(n1, n2, "+");
            case "-":
                n2 = E();
                e = new NoeudAST(n1, n2, "-");
            case "*":
                n2 = E();
                e = new NoeudAST(n1, n2, "*");
            case "/":
                n2 = E();
                e = new NoeudAST(n1, n2, "/");
        }

        return e;
    }

    public FeuilleAST T(Terminal terminal) {
        return new FeuilleAST(Integer.parseInt(terminal.getChaine()));
    }


    /** ErreurSynt() envoie un message d'erreur syntaxique
     */
    public void ErreurSynt(String s) {
        throw new Error(s);
    }


    //Methode principale a lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {
        String toWriteLect = "";
        String toWriteEval = "";

        System.out.println("Debut d'analyse syntaxique");
        if (args.length == 0) {
            args = new String[2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatSyntaxique.txt";
        }
        DescenteRecursive dr = new DescenteRecursive(args[0]);
        try {
            ElemAST RacineAST = dr.AnalSynt();
            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
            System.out.println(toWriteLect);
            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);
            Writer w = new Writer(args[1], toWriteLect + toWriteEval); // Ecriture de toWrite
            // dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }

}

