// Importamos las clases principales del runtime de ANTLR.
// Estas permiten trabajar con el flujo de entrada, lexer, parser y arbol sintactico.

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calc {

    // metodo principal
    // recibe argumentos desde la linea de comandos 

    public static void main(String[] args) throws Exception {

        // se declara el flujo de caracteres de entrada
        // aca ANTLR lee el texto que se quiere analizar
        CharStream input;

        // si el usuario pasa un archivo como argumento, entonces se lee desde ese archivo
        if (args.length > 0) {
            input = CharStreams.fromFileName(args[0]);

        // si no se pasa ningun archivo, se lee desde la entrada estándar
        } else {
            input = CharStreams.fromStream(System.in);
        }

        // se crea el lexer a partir del flujo de entrada
        // en el lexer se encarga de dividir el texto en tokens
        LabeledExprLexer lexer = new LabeledExprLexer(input);

        // se crea un stream de tokens a partir del lexer
        // este stream sera usado por el parser
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // se crea el parser usando los tokens generados por el lexer
        // el parser construye el árbol sintactico basado en la gramatica
        LabeledExprParser parser = new LabeledExprParser(tokens);

        // llamamos a la regla inicial de la gramatica (prog)
        // esto genera el arbol sintáctico completo del programa
        ParseTree tree = parser.prog();

        // creamos una instancia de nuestro visitor personalizado
        // este visitor es el que realmente evalua las expresiones
        EvalVisitor eval = new EvalVisitor();

        // se recorre el arbol sintactico usando el visitor
        // Aqui se ejecutan los calculos
        eval.visit(tree);

    }
}
