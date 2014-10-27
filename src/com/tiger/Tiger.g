grammar Tiger;

options { k = 1; backtrack = no; output = AST; }


@lexer::header
{
package com.antlr.generated;
}

@parser::header
{
package com.antlr.generated;

/***************myImport***************/
import java.util.Map;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Map.Entry;
import com.attribute.Attribute;
import com.attribute.VariableNameAttribute;
import com.symbol_table.SymbolTableManager;
import com.symbol_table.Symbol;
import com.symbol_table.Scope;
import com.symbol_table.IdType;
import com.symbol_table.NameSpaceManager;
import com.attribute.FunctionNameAttribute;
import com.attribute.FunctionNameAttribute.ParamType;
import com.attribute.FunctionNameAttribute.ReturnType;
import com.compiler.TempVarFactory;
/**************************************/

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
  private NameSpaceManager nameSpaceManager = new NameSpaceManager();
  // TODO private Map<String, Attribute> definedConstants = new TreeMap<String, DefinedConstantAttribute>();
  // TODO private Map<String, Attribute> typeNames = new TreeMap<String, TypeAttribute>();
  // TODO private Map<String, Attribute> functionNames = new TreeMap<String, FunctionNameAttribute>();
  // TODO private Map<String, Attribute> stringConstantLiteralNames = new TreeMap<String, LiteralConstantAndStringAttribute>();
  // TODO private Map<String, Attribute> srcTxtLabels= new TreeMap<StringAttribute>();
  // TODO private Map<String, Object> compilerGeneratedTemps = new TreeMap<String, Object>();
  private ArrayList<String> IRList = new ArrayList<String>();
  private TempVarFactory tvf = new TempVarFactory();

  private void putVariableNameAttributeMap(List<String> variableNameList, String type, String declaringFunctionName) {
    for (String variableName : variableNameList) {
        VariableNameAttribute variableNameAttribute = new VariableNameAttribute(variableName, type, declaringFunctionName);
        attributeMap.put(variableName, variableNameAttribute);
    }
  }
  
  private void putFunctionNameAttributeMap(String functionName, String returnType, List<String> parameters) {
    FunctionNameAttribute functionNameAttribute = new FunctionNameAttribute(functionName, returnType, parameters);
    attributeMap.put(functionName, functionNameAttribute);
  }
  

  public void printAttributeMap() {
    
    for (Entry<String, List<Symbol>> attr : symbolTableManager.getSymbolTable().entrySet()){
      for (Symbol symbol : attr.getValue() ) {
        System.out.println(symbol + " :: Have access to: " + showAllReachableAttribute(symbol.getScope())); // TODO debug, delete me
      }
    }
  }

  public String showAllReachableAttribute(Scope scope) {
    String temp = "";
    while (scope != null) {
      for(Entry<String, List<Symbol>> symbolListEntry : scope.getSymbolMap().entrySet()) {
        temp += symbolListEntry.getKey() + ", ";
      }
      scope = scope.getEnclosingScope();
    }
    return temp;
  }

  
  public void printTheNameSpace() {
    System.out.println(nameSpaceManager.toString());
  }
  
  public void printTheIRCode() {
    System.out.println("IR code:\n**********");
    for(String s : IRList) {
      System.out.println(s);
    }
    System.out.println("**********");
  }

  private boolean errorFlag = false;

  public void reportError(RecognitionException re) {
    errorFlag = true;
    super.reportError(re);
  } 
  
  public boolean getErrorFlag() {
    return errorFlag;
  }
  
  public List<String> getIRList() {
    return IRList;
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

funcDeclaration 
scope{List<String> myParams;}
@init{$funcDeclaration::myParams = new ArrayList<>();}
:
  KEY_FUNCTION myFunctionName=id[IdType.FUNCTION_NAME] OP_LPAREN paramList OP_RPAREN key_begin blockList[$myFunctionName.text] key_end OP_SCOLON
  {putFunctionNameAttributeMap($myFunctionName.text, null /*ReturnType returnType*/, $funcDeclaration::myParams);}
;

mainFunction
  :
  a=KEY_MAIN OP_LPAREN OP_RPAREN key_begin blockList[$a.text] key_end OP_SCOLON EOF
;

retType :
	typeId | KEY_VOID
;

typeId :
	baseType | id[IdType.NIY]
;

baseType :
	KEY_INT | KEY_FIXEDPT
;

param:
	id[IdType.NIY] OP_COLON typeId
	{$funcDeclaration::myParams.add($typeId.text);}
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
	key_begin declarationSegment[functionName] statSeq[functionName] key_end OP_SCOLON
;

typeDeclarationList :
	typeDeclaration*
;

varDeclarationList[String functionName] :
	varDeclaration[functionName]*
;

typeDeclaration :
  
	KEY_TYPE id[IdType.NIY] OP_EQUAL type OP_SCOLON
	
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
	KEY_VAR idList[IdType.VAR_NAME] OP_COLON myTypeId=typeId optionalInit OP_SCOLON
	{putVariableNameAttributeMap($varDeclaration::aggregatedMyIdList, $myTypeId.text, $functionName);}
;

idList[IdType idType] : 
  myId=id[idType] (OP_COMMA idList[idType])?
	{
	$varDeclaration::aggregatedMyIdList.add($myId.text);
	}
;

optionalInit :
	(OP_ASSIGN constant)?
;

statSeq[String functionName] :
	stat[functionName]+
;

stat[String functionName] : 
	(
		s1=id[IdType.NIY]
		(
		  s2=valueTail OP_ASSIGN s3=expr
		  {
		    // Assignment statement
		    IRList.add("assign, " + $s1.exp + $s2.exp + ", " + $s3.exp);
		  }
		  | OP_LPAREN exprList OP_RPAREN
		  {
		    // Function call
		    
		  }
		)
		| KEY_IF expr KEY_THEN statSeq[functionName]
		(
		  KEY_ELSE statSeq[functionName]
		)?
		KEY_ENDIF
		| KEY_WHILE expr KEY_DO statSeq[functionName] KEY_ENDDO
		| KEY_FOR id[IdType.NIY] OP_ASSIGN indexExpr KEY_TO indexExpr KEY_DO statSeq[functionName] KEY_ENDDO
		| KEY_BREAK
		| KEY_RETURN expr
	)
	OP_SCOLON
	| block[functionName]
; 

optPrefix :
	(
	  value OP_ASSIGN
	)?
;

expr returns [String exp]:
  s1=binOp1
  (
    (
      s2=OP_AND
      | OP_OR
    )
    s3=expr
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("and, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("or, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

binOp1 returns [String exp]:
  s1=binOp2
  (
    (
      s2=OP_LEQ
      | s3=OP_GEQ
      | s4=OP_LTHAN
      | s5=OP_GTHAN
      | s6=OP_NEQ
      | OP_EQUAL
    )
    s7=binOp1
  )?
  {
    if($s7.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("leq, "    + $s1.exp + ", " + $s7.exp + ", " + temp);
      } else if(s3 != null) {
        IRList.add("geq, "    + $s1.exp + ", " + $s7.exp + ", " + temp);
      } else if(s4 != null) {
        IRList.add("lthan, "  + $s1.exp + ", " + $s7.exp + ", " + temp);
      } else if(s5 != null) {
        IRList.add("gthan, "  + $s1.exp + ", " + $s7.exp + ", " + temp);
      } else if(s6 != null) {
        IRList.add("neq, "    + $s1.exp + ", " + $s7.exp + ", " + temp);
      } else {
        IRList.add("equals, " + $s1.exp + ", " + $s7.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

binOp2 returns [String exp]:
  s1=binOp3
  (
    (
      s2=OP_PLUS
      | OP_MINUS
    )
    s3=binOp2
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

binOp3 returns [String exp]:
  s1=binOp4
  (
    (
      s2=OP_DIV
      | OP_MULT
    )
    s3=binOp3
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("div, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("mult, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

binOp4 returns [String exp]:
  s1=constant                   {$exp = $s1.exp;}
  | OP_LPAREN s2=expr OP_RPAREN {$exp = $s2.exp;}
  | s3=id[IdType.NIY]
  (
    s4=valueTail                      {$exp = $s3.exp + $s4.exp;}
    | OP_LPAREN s5=exprList OP_RPAREN {$exp = $s3.exp + $s5.exp;}
  )
;

constant returns [String exp]:
	FIXEDPTLIT {$exp = $FIXEDPTLIT.text;}
	| INTLIT   {$exp = $INTLIT.text;}
;

value returns [String exp]:
	s1=id[IdType.NIY] s2=valueTail {$exp = $s1.exp + $s2.exp;}
;

valueTail returns [String exp]:
	(
	  OP_LBRACK s1=indexExpr OP_RBRACK
    (
      OP_LBRACK s2=indexExpr OP_RBRACK
    )?
	)?
	{
	  if($s2.exp != null) {
	    $exp = "[" + $s1.exp + "][" + $s2.exp + "]";
	  } else if($s1.exp != null) {
	    $exp = "[" + $s1.exp + "]";
	  } else {
	    $exp = "";
	  }
	}
;

indexExpr returns [String exp]:
  s1=indexExpr2
  (
    OP_MULT s2=indexExpr
  )?
  {
    if($s2.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      IRList.add("mult, " + $s1.exp + ", " + $s2.exp + ", " + temp);
      $exp = temp;
    }
  }
;

indexExpr2 returns [String exp]:
  s1=indexExpr3
  (
    (
      s2=OP_PLUS
      | OP_MINUS
    )
    s3=indexExpr2
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
    } else {
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

indexExpr3 returns [String exp]:
  INTLIT           {$exp = $INTLIT.text;}
  | id[IdType.NIY] {$exp = $id.exp;}
;

declarationSegment[String functionName] :
  typeDeclarationList varDeclarationList[functionName]
;

exprList returns [String exp]:
  (
    s1=expr s2=exprListTail
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = $s1.exp + $s2.exp;
    }
  }
;

exprListTail returns [String exp]:
  (
    OP_COMMA s1=expr s2=exprListTail
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = ", " + $s1.exp + $s2.exp;
    }
  }
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

id[IdType idType] returns [String exp]:
  ID
  {
    nameSpaceManager.manageNameSpace(idType, $ID.text);
    $exp = $ID.text;
  }
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