grammar Tiger;

tigerProgram :
	typeDeclarationList functDeclarationList mainFunction
;

functDeclarationList :
	(functDeclaration functDeclarationList)?
;

functDeclaration :
	retType KEY_FUNCTION ID OP_LPAREN paramList OP_RPAREN KEY_BEGIN blockList KEY_END OP_SCOLON
;

mainFunction :
	KEY_VOID KEY_MAIN OP_LPAREN OP_RPAREN KEY_BEGIN blockList KEY_END OP_SCOLON
;

retType :
	typeId | KEY_VOID
;

typeId :
	baseType | ID
;

baseType :
	INTLIT | FIXEDPTLIT
;

param :
	ID OP_COLON typeId
;

paramList :
	(param paramListTail)?
;

paramListTail :
	(OP_COMMA param paramListTail)?
;

blockList :
	block+ // block-list and block-tail are combined
;

block :
	KEY_BEGIN declarationSegment statSeq KEY_END OP_SCOLON
;

typeDeclarationList :
	(typeDeclaration typeDeclarationList)?
;

varDeclarationList :
	(varDeclaration varDeclarationList)?
;

typeDeclaration :
	KEY_TYPE ID OP_EQUAL type OP_SCOLON
;

type :
	(KEY_ARRAY OP_LBRACK INTLIT OP_RBRACK
		(OP_LBRACK INTLIT OP_RBRACK)? KEY_OF)?
	baseType
;

varDeclaration :
	KEY_VAR idList OP_COLON typeId optionalInit OP_SCOLON
;

idList :
	ID (OP_COMMA idList)?
;

optionalInit :
	(OP_ASSIGN const)?
;

statSeq :
	stat+
;

stat :
	(
		value OP_ASSIGN (expr | ID OP_LPAREN exprList OP_RPAREN) |
		KEY_IF expr KEY_THEN statSeq (KEY_ELSE statSeq)? KEY_ENDIF |
		KEY_WHILE expr KEY_DO statSeq KEY_ENDDO |
		KEY_FOR ID OP_ASSIGN indexExpr KEY_TO indexExpr KEY_DO statSeq KEY_ENDDO |
		ID OP_LPAREN exprList OP_RPAREN |
		KEY_BREAK |
		KEY_RETURN expr |
		blockList
	) OP_SCOLON
; 

optPrefix :
	(value OP_ASSIGN)?
;

expr :
	(const | value | OP_LPAREN expr OP_RPAREN)
	(binaryOperator (const | value | OP_LPAREN expr OP_RPAREN))*
; 

const :
	FIXEDPTLIT |
	INTLIT
;

value :
	ID valueTail
;

valueTail :
	(OP_LBRACK indexExpr OP_RBRACK
		(OP_LBRACK indexExpr OP_RBRACK)?
	)?
;

indexExpr :
	(INTLIT | ID)
	(indexOper (INTLIT | ID))*
;

indexOper :
	OP_PLUS  |
	OP_MINUS |
	OP_MULT
;

binaryOperator :
	OP_PLUS  |
	OP_MINUS |
	OP_MULT  |
	OP_DIV   |
	OP_EQUAL |
	OP_NEQ   |
	OP_LEQ   |
	OP_GEQ   |
	OP_LTHAN |
	OP_GTHAN |
	OP_AND   |
	OP_OR
; 

declarationSegment :
  typeDeclarationList varDeclarationList
;

exprList :
  (expr exprListTail)?
;

KEY_FUNCTION : 'function' ;
KEY_BEGIN    : 'begin'    ;
KEY_END      : 'end'      ;
KEY_VOID     : 'void'     ;
KEY_MAIN     : 'main'     ;
KEY_TYPE     : 'type'     ;
KEY_ARRAY    : 'array'    ;
KEY_OF       : 'of'       ;
KEY_INT      : 'int'      ;
KEY_FIXEDPT  : 'fixedpt'  ;
KEY_VAR      : 'var'      ;
KEY_IF       : 'if'       ;
KEY_THEN     : 'then'     ;
KEY_ENDIF    : 'endif'    ;
KEY_ELSE     : 'else'     ;
KEY_WHILE    : 'while'    ;
KEY_DO       : 'do'       ;
KEY_ENDDO    : 'enddo'    ;
KEY_FOR      : 'for'      ;
KEY_TO       : 'to'       ;
KEY_BREAK    : 'break'    ;
KEY_RETURN   : 'return'   ;

OP_LPAREN  : '('  ;
OP_RPAREN  : ')'  ;
OP_SCOLON  : ';'  ;
OP_COLON   : ':'  ;
OP_EQUAL   : '='  ;
OP_COMMA   : ','  ;
OP_ASSIGN  : ':=' ;
OP_LBRACK  : '['  ;
OP_RBRACK  : ']'  ;
OP_PLUS    : '+'  ;
OP_MINUS   : '-'  ;
OP_MULT    : '*'  ;
OP_DIV     : '/'  ;
OP_NEQ     : '<>' ;
OP_LEQ     : '<=' ;
OP_GEQ     : '>=' ;
OP_LTHAN   : '<'  ;
OP_GTHAN   : '>'  ;
OP_AND     : '&'  ;
OP_OR      : '|'  ;
OP_UNDER   : '_'  ;
OP_PERIOD  : '.'  ;


exprListTail :
	(OP_COMMA expr exprListTail)?
;

INTLIT :
	'0' |
	'1'..'9' DIGIT*
;

FIXEDPTLIT :
	INTLIT OP_PERIOD DIGIT (DIGIT? DIGIT)?
;

ID :
  ALPHANUM (ALPHANUM | DIGIT | OP_UNDER)*
;


fragment
DIGIT :
  '0'..'9'
;

fragment
ALPHANUM :
  'a'..'z' |
  'A'..'Z'
;

COMMENT :
  '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
;

WS :
  ( ' '
  | '\t'
  | '\r'
  | '\n'
  ) {$channel=HIDDEN;}
;