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
    private Terminal terminal;

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
        this.terminal = lexical.prochainTerminal();
        ElemAST racine = null;
        racine = E(false);
        return racine;
    }


// Methode pour chaque symbole non-terminal de la grammaire retenue

    public ElemAST E(boolean parentheses) {
        String chaine = null;
        ElemAST e = null;
        ElemAST n1 = null;
        ElemAST n2 = null;

        n1 = F();
        if (!lexical.resteTerminal()) {
            e = n1;
            return e;
        }

        chaine = terminal.getChaine();
        switch (chaine) {
            case "+":
                terminal = lexical.prochainTerminal();
                n2 = E(parentheses);
                e = new NoeudAST(n1, n2, "+", parentheses);
                break;
            case "-":
                terminal = lexical.prochainTerminal();
                n2 = E(parentheses);
                e = new NoeudAST(n1, n2, "-", parentheses);
                break;
            default:
                e = n1;
                break;
        }

        return e;
    }

    public ElemAST F() {
        String chaine = null;
        ElemAST e = null;
        ElemAST n1 = null;
        ElemAST n2 = null;

        n1 = G();
        if (!lexical.resteTerminal()) {
            e = n1;
            return e;
        }

        terminal = lexical.prochainTerminal();
        chaine = terminal.getChaine();
        switch (chaine) {
            case "*":
                terminal = lexical.prochainTerminal();
                n2 = F();
                e = new NoeudAST(n1, n2, "*");
                break;
            case "/":
                terminal = lexical.prochainTerminal();
                n2 = F();
                e = new NoeudAST(n1, n2, "/");
                break;
            default:
                e = n1;
                break;
        }

        return e;
    }

    public ElemAST G() {
        String chaine = null;
        ElemAST e = null;
        ElemAST n1 = null;
        ElemAST n2 = null;
        boolean parentheses;

        chaine = terminal.getChaine();

        if (chaine.equals("(")) {
            parentheses = true;
            terminal = lexical.prochainTerminal();
            e = E(parentheses);
            chaine = terminal.getChaine();
            if (chaine.equals(")")) {

            } else {
                throw new Error("Pas de parenthese fermante");
            }
        } else {
            e = T();
        }

        return e;
    }

    public ElemAST T() {
        FeuilleAST feuille = null;
        String chaine = terminal.getChaine();
        if (!chaine.equals("")) {
            try {
                feuille = new FeuilleAST(Integer.parseInt(chaine));
            } catch (Exception e) {
                feuille = new FeuilleAST(chaine);
            }

        } else {
            feuille = new FeuilleAST(0);
        }
        return feuille;

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
        String toWritePostfix = "";

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
            toWritePostfix += "Evaluation postfix de l'AST trouve: " + RacineAST.postfixAST();
            System.out.println(toWritePostfix);
            Writer w = new Writer(args[1], toWriteLect + toWriteEval); // Ecriture de toWrite dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }

}

