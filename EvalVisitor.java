// se importa HashMap y Map para poder almacenar variables y sus valores
// esto permite tener memoria para la calculadora

import java.util.HashMap;
import java.util.Map;

// esta clase hereda de LabeledExprBaseVisitor<Integer>
// el <Integer> indica que cada metodo visit devolvera un numero entero

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {

        // mapa que actua como la memoria de la calculadora
    Map<String, Integer> memory = new HashMap<String, Integer>();

        // se ejecuta cuando se encuentra una asignacion 
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {

        // obtenemos el nombre de la variable (lado izquierdo del '=')
        String id = ctx.ID().getText();
        // evaluamos la expresion que esta a la derecha del '='
        int value = visit(ctx.expr());
        // se guarda el resultado en la memoria
        memory.put(id, value);
        return value;
    }

    // se ejecuta cuando la regla corresponde a una expresion sola seguida de salto de línea, o sea, cuando se imprime el resultado

    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {

        // se evalua la expresion
        Integer value = visit(ctx.expr());
        // se muestra el resultado en pantalla
        System.out.println(value);
        return 0;
    }

    // se ejecuta cuando encuentra un numero entero
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {

        // Convierte el texto del token INT en un Integer
        return Integer.valueOf(ctx.INT().getText());
    }


    // se ejecuta cuando encuentra una variable (ID)
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {

        // se obtiene el nombre de la variable
        String id = ctx.ID().getText();
        // si la variable existe en memoria se devuelve su valor
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }


    // se ejecuta para multiplicacion o division
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {

        // se evalua la subexpresion izquierda
        int left = visit(ctx.expr(0));

        // se evaluamos la subexpresion derecha
        int right = visit(ctx.expr(1));

        // se verifica qué operador fue usado
        // y si es multiplicacion se retorna left * right.
        if (ctx.op.getType() == LabeledExprParser.MUL) return left * right;

        // si no es MUL, entonces es division
        return left / right;
    }

    // se ejecuta para suma o resta
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.ADD) return left + right;
        // si no es ADD, entonces es resta
        return left - right;
    }

    // se ejecuta cuando la expresion esta entre parentesis
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}
