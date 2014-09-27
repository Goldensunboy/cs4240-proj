parser grammar Tiger;

tiger-program : type-declaration-list funct-declaration-list main-function ;

funct-declaration-list : (funct-declaration funct-declaration-list)?;

funct-declaration : ret-type 'function' 'id' '('param-list')' 'begin' block-list 'end' ';';

main-function : 'void' 'main' '(' ')' 'begin' block-list 'end' ';';

ret-type : type-id | 'void';

type-id : base-type | 'id';

base-type : 'int' | 'fixedpt';

param : 'id' ':' type-id;

param-list : (param param-list-tail)?;

param-list-tail : (',' param param-list-tail)?;

block-list : (block block-tail)+; // block-list and block-tail are combined

block : 'begin' declaration-segment stat-seq 'end'';';

type-declaration-list : (type-declaration type-declaration-list)?;

var-declaration-list : (var-declaration var-declaration-list)?;

type-declaration : 'type' 'id' '=' type ';';

type : ('array' '[' INTLIT ']'('['INTLIT']')? 'of')? base-type;

var-declaration : 'var' id-list ':' type-id optional-init ';';

id-list : 'id' (',' id-list)?;

optional-init : (':=' const)?;

stat-seq : stat+;

stat : (value ':=' expr | 'if' expr 'then' stat-seq ('else' stat-seq)? 'endif' | 'while' expr 'do' stat-seq 'enddo' | 'for' id ':=' index-expr 'to' index-expr 'do' stat-seq 'enddo' |  opt-prefix 'id' '(' expr-list ')' | 'break' | 'return' expr | block-list) ';'; 

opt-prefix : (value ':=')? ;

expr : const | value | expr binary-operator expr | '(' expr ')'; 

const : FIXEDPTLIT | INTLIT;

value : 'id' value-tail;

value-tail : ('[' index-expr ']'('[' index-expr ']')?)?;

index-expr : INTLIT | 'id' | index-expr index-oper index-expr;

index-oper : '+' | '-' | '*';

binary-operator : '+' | '-' | '*' | '/' | '=' | '<>' | '<' | '>'| '<=' | '>=' | '&' | '|'; 

declaration-segment : type-declaration-list var-declaration-list;

expr-list : (expr expr-list-tail)?;

expr-list-tail : (',' expr expr-list-tail)?;

