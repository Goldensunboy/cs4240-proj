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
import java.util.Hashtable;
import java.util.TreeMap;
import com.attribute.Attribute;
import com.attribute.VariableNameAttribute;
import java.util.Map.Entry;
import com.symbol_table.SymbolTableManager;
import com.symbol_table.Symbol;
import com.symbol_table.Scope;
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
  
  private SymbolTableManager symbolTableManager = new SymbolTableManager();
  private Map<String, Attribute> attributeMap = new Hashtable<>();
  // TODO private Map<String, Attribute> definedConstants = new TreeMap<String, DefinedConstantAttribute>();
  // TODO private Map<String, Attribute> typeNames = new TreeMap<String, TypeAttribute>();
  // TODO private Map<String, Attribute> functionNames = new TreeMap<String, FunctionNameAttribute>();
  // TODO private Map<String, Attribute> stringConstantLiteralNames = new TreeMap<String, LiteralConstantAndStringAttribute>();
  // TODO private Map<String, Attribute> srcTxtLabels= new TreeMap<StringAttribute>();
  // TODO private Map<String, Object> compilerGeneratedTemps = new TreeMap<String, Object>();

  private void putAttributeMap(List<String> variableNameList, String type, String declaringFunctionName) {
    for (String variableName : variableNameList) {
//        System.out.println("Variable Name: " + variableName + " Type: " + type + " Function: " + declaringFunctionName); //TODO debug, delete later
        VariableNameAttribute variableNameAttribute = new VariableNameAttribute(variableName, type, declaringFunctionName);
        attributeMap.put(variableName, variableNameAttribute);
	  }
  }

  public void printAttributeMap() {
    
    for (Entry<String, Symbol> attr : symbolTableManager.getSymboTable().entrySet())
      System.out.println(attr.getKey() + " Scope ID: " + showAllReachableAttribute(attr.getValue().getScope()));
  }

  public String showAllReachableAttribute(Scope scope) {
    String temp = "";
    while (scope != null) {
      for(Entry<String, Symbol> symbolEntry : scope.getSymbolMap().entrySet()) {
        temp += symbolEntry.getKey() + ", ";
      }
      scope = scope.getEnclosingScope();
    }
    return temp;
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
  KEY_FUNCTION a=ID OP_LPAREN paramList OP_RPAREN key_begin blockList[$a.text] key_end OP_SCOLON
;

mainFunction
  :
  a=KEY_MAIN OP_LPAREN OP_RPAREN key_begin blockList[$a.text] key_end OP_SCOLON EOF
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

blockList[String functionName] :
	block[functionName]+ // block-list and block-tail are combined
;

block[String functionName] :
	key_begin declarationSegment[functionName] statSeq key_end OP_SCOLON
;

typeDeclarationList :
	typeDeclaration*
;

varDeclarationList[String functionName] :
	varDeclaration[functionName]*
;

typeDeclaration :
  
	KEY_TYPE ID OP_EQUAL type OP_SCOLON
	
;

type :
	(KEY_ARRAY OP_LBRACK INTLIT OP_RBRACK
		(OP_LBRACK INTLIT OP_RBRACK)? KEY_OF)?
	baseType
;

varDeclaration[String functionName]
scope {List<String> aggregatedMyIdList;}
@init {$varDeclaration::aggregatedMyIdList = new ArrayList<>();}
 :
	KEY_VAR idList OP_COLON myTypeId=typeId optionalInit OP_SCOLON
	{putAttributeMap($varDeclaration::aggregatedMyIdList, $myTypeId.text, $functionName);}
;

idList: 
  myId=ID (OP_COMMA idList)?
	{
	$varDeclaration::aggregatedMyIdList.add($myId.text);
	}
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
		block[""]
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
    OP_LPAREN exprList OP_RPAREN
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

declarationSegment[String functionName] :
  typeDeclarationList varDeclarationList[functionName]
;

exprList :
  (expr exprListTail)?
;

exprListTail :
  (OP_COMMA expr exprListTail)?
;

key_begin
  @init{
  symbolTableManager.makeNewScope(attributeMap);
  attributeMap.clear();
  }
  : 
  'begin'    
;

key_end
  @init{
  symbolTableManager.goToEnclosingScope(attributeMap);
  attributeMap.clear();
  }
  : 'end'
;

KEY_FUNCTION : 'function' ;
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