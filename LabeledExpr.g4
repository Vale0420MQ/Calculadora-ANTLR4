// Nombre de la gramatica


grammar LabeledExpr;


// Regla inicial del programa
prog:   stat+ ;

stat

// una expresión seguida de salto de línea
// se etiqueta como #printExpr para que el Visitor genere un método específico

    :   expr NEWLINE            # printExpr

// una asignación (variable = expresión) seguida de salto de línea
// se etiqueta como #assign
    |   ID '=' expr NEWLINE     # assign

// una línea en blanco
// Se etiqueta como #blank
    |   NEWLINE                 # blank
    ;


// Regla que define las expresiones matemáticas
expr

    // Multiplicación o división
    :   expr op=(MUL|DIV) expr  # MulDiv
    // Suma o resta
    |   expr op=(ADD|SUB) expr  # AddSub
    // Número entero
    |   INT                     # int
    // Identificador (variable)
    |   ID                      # id
    // Expresión entre paréntesis
    |   '(' expr ')'            # parens
    ;

// definición explicita de los operadores como tokens
// esto permite referenciarlos luego como constantes en el Visitor
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

ID  : [a-zA-Z]+ ;
INT : [0-9]+ ;
NEWLINE : '\r'? '\n' ;
WS  : [ \t]+ -> skip ;
