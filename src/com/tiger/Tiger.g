grammar Tiger;

options { k = 1; backtrack = no; output = AST; }


@lexer::header
{
package com.antlr.generated;
}

@parser::header
{
package com.antlr.generated;

import java.util.Map;
import java.util.TreeMap;
}

@lexer::members{
  private boolean errorFlag = false;

  public void reportError(RecognitionException re) {
    errorFlag = true;
    super.reportError(re);
  }
  
  public boolean getErrorFlag() {
    return errorFlag;
  } 
}

@parser::members{
  
  private Map<String, Attribute> variableNames = new TreeMap<String, VariableNameAttribute>();
  private Map<String, Attribute> definedConstants = new TreeMap<String, DefinedConstantAttribute>();
  private Map<String, Attribute> typeNames = new TreeMap<String, TypeAttribute>();
  private Map<String, Attribute> functionNames = new TreeMap<String, FunctionNameAttribute>();
  private Map<String, Attribute> stringConstantLiteralNames = new TreeMap<String, LiteralConstantAndStringAttribute>();
  // TODO private Map<String, Attribute> srcTxtLabels= new TreeMap<StringAttribute>();
  // TODO private Map<String, Object> compilerGeneratedTemps = new TreeMap<String, Object>();

  private void putVariableNames(String variableName, String type, String declaringFunctionName) {
    VariableNameAttribute variableNameAttribute = new VaribleNameAttribute(variableName, type declaringFunctionName);
    variableNames.put(variableName, VariableNameAttribute);
  }

  private void putTypeNames(){
  }
  
  private boolean errorFlag = false;

  public void reportError(RecognitionException re) {
    errorFlag = true;
    super.reportError(re);
  } 
  
  public boolean getErrorFlag() {
    return errorFlag;
  } 
}

tigerProgram :
	typeDeclarationList funcNext
;

funcCurrent :
	 funcDeclaration funcNext
;

funcNext :
  ((typeId funcCurrent) | (KEY_VOID (funcCurrent | mainFunction)))
;

funcDeclaration :
  KEY_FUNCTION ID OP_LPAREN paramList OP_RPAREN KEY_BEGIN blockList KEY_END OP_SCOLON
;

mainFunction:
  KEY_MAIN OP_LPAREN OP_RPAREN KEY_BEGIN blockList KEY_END OP_SCOLON EOF
;

retType :
	typeId | KEY_VOID
;

typeId :
	baseType | ID
;

baseType :
	KEY_INT | KEY_FIXEDPT
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
	typeDeclaration*
;

varDeclarationList :
	varDeclaration*
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
	(OP_ASSIGN constant)?
;

statSeq :
	stat+
;

stat :
	(
		ID (valueTail OP_ASSIGN expr | OP_LPAREN exprList OP_RPAREN) |
		KEY_IF expr KEY_THEN statSeq (KEY_ELSE statSeq)? KEY_ENDIF |
		KEY_WHILE expr KEY_DO statSeq KEY_ENDDO |
		KEY_FOR ID OP_ASSIGN indexExpr KEY_TO indexExpr KEY_DO statSeq KEY_ENDDO |
		KEY_BREAK |
		KEY_RETURN expr
	)	OP_SCOLON |
		block
; 

optPrefix :
	(value OP_ASSIGN)?
;

expr :
  binOp1 ((
    OP_AND |
    OP_OR
  ) expr)?
;

binOp1 :
  binOp2 ((
    OP_LEQ |
    OP_GEQ |
    OP_LTHAN |
    OP_GTHAN |
    OP_NEQ |
    OP_EQUAL
  ) binOp1)?
;

binOp2 :
  binOp3 ((
    OP_MINUS |
    OP_PLUS
  ) binOp2)?
;

binOp3 :
  binOp4 ((
    OP_DIV |
    OP_MULT
  ) binOp3)?
;

binOp4 :
  constant |
  OP_LPAREN expr OP_RPAREN |
  ID (
    valueTail |
    OP_LPAREN expr OP_RPAREN
  )
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

constant :
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

declarationSegment :
  typeDeclarationList varDeclarationList
;

exprList :
  (expr exprListTail)?
;

exprListTail :
  (OP_COMMA expr exprListTail)?
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


INTLIT :
	'0' |
	(('1'..'9') (DIGIT)*)
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