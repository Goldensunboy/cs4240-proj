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
import java.util.Set;
import java.util.HashSet;
import com.attribute.Attribute;
import com.attribute.VariableNameAttribute;
import com.symbol_table.SymbolTableManager;
import com.symbol_table.Symbol;
import com.symbol_table.Scope;
import com.symbol_table.IdType;
import com.attribute.FunctionNameAttribute;
import com.attribute.FunctionNameAttribute.ParamType;
import com.compiler.TempVarFactory;
import com.compiler.ReturnType;
import com.compiler.VarType;
import com.exception.InvalidTypeException;
import com.exception.InvalidInvocationException;
import com.exception.UndeclaredFunctionException;
import com.exception.UndeclaredVariableException;
import com.exception.ExceptionHandler;
import com.exception.TypeMismatchException;
import com.exception.NameSpaceConflictException;
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
  private Map<String, Attribute> attributeMap = new Hashtable<String, Attribute>();
  private ArrayList<String> IRList = new ArrayList<String>();
  private TempVarFactory tvf = new TempVarFactory();
  private String enclosingFunctionName;
  private ExceptionHandler exceptionHandler = new ExceptionHandler();
  
  private Set<String> varNamespace = new HashSet<>();
  private Set<String> typeNamespace = new HashSet<>();
  private Set<String> functionNamespace = new HashSet<>();
  
  public static final String VAR_NAMESPACE = "varNameSpcae";
  public static final String TYPE_NAMESPACE = "typeNameSpcae";
  public static final String FUNCTION_NAMESPACE = "functionNameSpcae";
  
  private void putVariableNameAttributeMap(List<String> variableNameList, String type, String declaringFunctionName) {
    for (String variableName : variableNameList) {
        VariableNameAttribute variableNameAttribute = new VariableNameAttribute(variableName, type, declaringFunctionName);
        attributeMap.put(variableName, variableNameAttribute);
    }
  }
  
  private void putFunctionNameAttributeMap(String functionName, ReturnType returnType, List<String> parameters) {
    FunctionNameAttribute functionNameAttribute = new FunctionNameAttribute(functionName, returnType, parameters);
    attributeMap.put(functionName, functionNameAttribute);
  }

  public void putTypeAttribute(String attributeName, VarType type) {
    
  }

  public void printAttributeMap() {
    for (Entry<String, List<Symbol>> attr : symbolTableManager.getSymbolTable().entrySet()){
      for (Symbol symbol : attr.getValue() ) {
        System.out.println(symbol + " :: Have access to: " + showAllReachableAttributes(symbol.getScope())); // TODO debug, delete me
      }
    }
  }
  
  public String showAllReachableAttributes(Scope scope) {
    String temp = "";
    while (scope != null) {
      for(Entry<String, List<Symbol>> symbolListEntry : scope.getSymbolMap().entrySet()) {
        temp += symbolListEntry.getKey() + ", ";
      }
      scope = scope.getEnclosingScope();
    }
    return temp;
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
  
  private int getLineNumber(ParserRuleReturnScope token) {
    return getLineNumber(token.start);
  }
    
  private int getLineNumber(Token token) {
    return token.getLine();
  }
  
  private void makeNewScope() {
    Map<String, Set<String>> unregisteredNamespaceMap = getUnregisteredNamespacesMap();
    
    symbolTableManager.makeNewScope(attributeMap, enclosingFunctionName, unregisteredNamespaceMap);
	  
	  clearMapsAndSets();
  }
  
  private void goToEnclosingScope() {
    Map<String, Set<String>> unregisteredNamespaceMap = getUnregisteredNamespacesMap();
    
    symbolTableManager.goToEnclosingScope(attributeMap, unregisteredNamespaceMap);
    
    clearMapsAndSets();
  }
  
  private void clearMapsAndSets() {
    attributeMap.clear();
    varNamespace.clear();
    typeNamespace.clear();
    functionNamespace.clear();
  }
  
  private Map<String, Set<String>> getUnregisteredNamespacesMap() {
    Map<String, Set<String>> unregisteredNameSpaces = new Hashtable<>();
    
    unregisteredNameSpaces.put(FUNCTION_NAMESPACE, functionNamespace);
	  unregisteredNameSpaces.put(VAR_NAMESPACE, varNamespace);
	  unregisteredNameSpaces.put(TYPE_NAMESPACE, typeNamespace);
  
	  return unregisteredNameSpaces;
  }
  
}

tigerProgram :
	typeDeclarationList funcNext
;

funcNext:
  typeId funcCurrent[$typeId.type]
  | KEY_VOID
  (
    funcCurrent[ReturnType.VOID]
    | mainFunction[ReturnType.VOID]
  )
;

funcCurrent[ReturnType returnType] :
	 funcDeclaration[returnType] funcNext
;

funcDeclaration[ReturnType returnType]
scope
{
  List<String> myParams;
}
@init
{
  $funcDeclaration::myParams = new ArrayList<String>();
}
:
  KEY_FUNCTION myFunctionName=id[IdType.FUNCTION_NAME, true]
  OP_LPAREN paramList OP_RPAREN afterBegin[$myFunctionName.text, returnType]
;

afterBegin[String myFunctionName, ReturnType returnType]
@init
{
  putFunctionNameAttributeMap(myFunctionName,
                              returnType,
                              $funcDeclaration::myParams);
  enclosingFunctionName = myFunctionName;
}
:
  key_begin blockList[myFunctionName] 
  {
    if(!symbolTableManager.returnStatementSatisfied(myFunctionName)) {
      String customMessage = "Mismatch return statement for function " + myFunctionName;
      ReturnType expectedReturnType = symbolTableManager.getReturnType();
      ReturnType actualReturnType = symbolTableManager.getCurrentScopeReturnType();
      exceptionHandler.handleException(null, customMessage, expectedReturnType.getName(), 
                                       actualReturnType.getName(), TypeMismatchException.class);
    }
  }
  key_end OP_SCOLON
;

mainFunction [ReturnType returnType]:
  a=KEY_MAIN OP_LPAREN OP_RPAREN 
  {
	  putFunctionNameAttributeMap($KEY_MAIN.text,
	                              ReturnType.VOID,
	                              new ArrayList<String>());
	  enclosingFunctionName = $KEY_MAIN.text;
  }
  key_begin blockList[$a.text] key_end OP_SCOLON EOF
;

retType :
	typeId
	| KEY_VOID
;

typeId returns[ReturnType type]:
	baseType {$type=$baseType.type;}
	| id[IdType.NIY, false] {$type=ReturnType.OTHER;}
	
	
;

baseType returns[ReturnType type]:
	KEY_INT {$type=ReturnType.INT;}
	| KEY_FIXEDPT {$type=ReturnType.FIXPT;}
;

param :
	id[IdType.NIY, false] OP_COLON typeId
	{
	  $funcDeclaration::myParams.add($typeId.text);
	}
;

paramList :
	(
	  param paramListTail
	)?
;

paramListTail :
	(
	  OP_COMMA param paramListTail
	)?
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
  
	KEY_TYPE myId=id[IdType.TYPE_NAME, true] OP_EQUAL type OP_SCOLON
;

type :
	(
	  KEY_ARRAY OP_LBRACK INTLIT OP_RBRACK
		(
		  OP_LBRACK INTLIT OP_RBRACK
		)?
		KEY_OF
	)?
	baseType
;

varDeclaration[String functionName]
scope
{
  List<String> aggregatedMyIdList;
}
@init
{
  $varDeclaration::aggregatedMyIdList = new ArrayList<String>();
}
:
	KEY_VAR idList[IdType.VAR_NAME, true] OP_COLON myTypeId=typeId optionalInit OP_SCOLON
	{
	  putVariableNameAttributeMap($varDeclaration::aggregatedMyIdList,
	                              $myTypeId.text,
	                              $functionName);
	}
;

idList[IdType idType, boolean testNamespace] : 
  myId=id[idType, testNamespace]
  (
    OP_COMMA idList[idType, testNamespace]
  )?
	{
	  $varDeclaration::aggregatedMyIdList.add($myId.text);
	}
;

optionalInit :
	(
	  OP_ASSIGN constant
	)?
;

statSeq[String functionName] 
:
	stat[functionName]+
;

stat[String functionName] returns [ReturnType statReturnType]
@init
{
  List<String> paramList = new ArrayList<String>();
}
: 
	(
		s1=id[IdType.NIY, false]
		(
		  s2=valueTail OP_ASSIGN s3=expr
		  {
		    // Verify that the assignment is valid
		    Attribute att = symbolTableManager.getAttributeInCurrentScope($s1.exp, attributeMap);
		    if(!$s3.exp.contains("#")) {
		      // Expr assignment
		      if(att == null) {
		        // Variable not declared yet
		        throw new UndeclaredVariableException("Line " +
		          (($s1.start != null)? $s1.start.getLine() : -1) +
		          ": Assignment to undeclared variable: " + $s1.exp);
		      } else if($s1.type == ReturnType.INT && $s3.type == ReturnType.FIXPT) {
		        // Illegal assignment (fixpt to int)
		        throw new InvalidTypeException("Line " +
		          (($s1.start != null)? $s1.start.getLine() : -1) +
		          ": Assignment of fixedpt expression " + $s3.exp + " to int variable " + $s1.exp);
		      }
		      //Cannot assign a conditional to a variable
		      if($s3.myIsBool) {
	          int lineNumber = getLineNumber(s3);
	          String customMessage = "Boolean values cannot be assigned to a variable.";
	          exceptionHandler.handleException(lineNumber, customMessage, null, 
	                                          null,InvalidTypeException.class);
		      }
		      // Assignment statement
          IRList.add("assign, " + $s1.exp + $s2.exp + ", " + $s3.exp);
		    } else {
		      // Function assignment
		      String[] parts = $s3.exp.split("#");
		      ReturnType rettype = symbolTableManager.getFunctionReturnType(parts[0]);
		      if($s1.type == ReturnType.INT && rettype == ReturnType.FIXPT) {
		        // (fixpt to int)
            throw new InvalidTypeException("Line " +
              (($s1.start != null)? $s1.start.getLine() : -1) +
              ": Assignment of fixedpt function " + parts[0] + " to int variable " + $s1.exp);
		      }
		      IRList.add("callr, " + $s1.exp + ", " + parts[0] + ", " + parts[1]);
		    }
		  }
		  | OP_LPAREN s4=funcExprList[paramList] OP_RPAREN
		  {
		    // Lone function call
		    if("".equals($s4.exp)) {
		      // Parameterless
		      IRList.add("call, " + $s1.exp);
		    } else {
		      // With params
		      IRList.add("call, " + $s1.exp + ", " + $s4.exp);
		    }
		    // Verify that the function exists
		    Attribute att = symbolTableManager.getAttributeInGlobalScope($s1.exp);
        if(att == null) {
          // Function not declared yet
          throw new UndeclaredFunctionException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Invocation of undeclared function: " + $s1.exp);
        }
        // Verify that the function params match with the type for the function
        List<String> params = symbolTableManager.getFunctionParameters($s1.exp);
        if(params.size() != paramList.size()) {
          String expected = params.size() == 0 ? "[void]" : params.toString();
          List<String> foundList = new ArrayList<String>();
          for(int i = params.size() - 1; i >= 0; --i) {
            foundList.add(paramList.get(i));
          }
          String found = paramList.size() == 0 ? "[void]" : foundList.toString();
          throw new InvalidInvocationException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Invalid invocation of function: [" + $s1.exp + "] Expected: " + expected + " Found: " + found);
        }
        for(int i = 0; i < params.size(); ++i) {
          if("int".equals(params.get(i)) && "fixedpt".equals(paramList.get(params.size() - i - 1))) {
            String expected = params.size() == 0 ? "[void]" : params.toString();
            List<String> foundList = new ArrayList<String>();
            for(int j = params.size() - 1; j >= 0; --j) {
              foundList.add(paramList.get(j));
            }
            String found = paramList.size() == 0 ? "[void]" : foundList.toString();
            throw new InvalidInvocationException("Line " +
              (($s1.start != null)? $s1.start.getLine() : -1) +
              ": Invalid invocation of function: [" + $s1.exp + "] Expected: " + expected + " Found: " + found);
          }
        }
		  }
		)
		| KEY_IF 
		{
		  ReturnType ifReturnType=ReturnType.VOID, elseReturnType=ReturnType.VOID;
		  makeNewScope();
		}
		myIfCond=expr
		  {
		    if(!$myIfCond.myIsBool) {
			    int lineNumber = getLineNumber(myReturnValue);
			    String customMessage = "If statement conditions must resolve to a boolean value";
	        exceptionHandler.handleException(lineNumber, customMessage, null, 
                                          null,InvalidTypeException.class);
		    } 
		  }
      KEY_THEN statSeq[functionName]
      {
        ifReturnType = symbolTableManager.getCurrentScopeReturnType();
      }
    (
      {
        goToEnclosingScope();
      }
      KEY_ELSE 
      {
        makeNewScope();
      }
      statSeq[functionName]
      {
        elseReturnType = symbolTableManager.getCurrentScopeReturnType();
      }
    )?
    KEY_ENDIF
    {
      goToEnclosingScope();
      symbolTableManager.setCurrentScopeReturnType(ifReturnType == elseReturnType? ifReturnType : ReturnType.VOID);
    }
		| KEY_WHILE myWhileCond=expr {if(!$myWhileCond.myIsBool) throw new InvalidTypeException("Line: " + (($myWhileCond.start != null)? $myWhileCond.start.getLine() : -1) +". while conditional statements must resolve to a boolean value.");} 
      KEY_DO statSeq[functionName] KEY_ENDDO
		| KEY_FOR id[IdType.NIY, false] OP_ASSIGN indexExpr KEY_TO indexExpr KEY_DO statSeq[functionName] KEY_ENDDO
		| KEY_BREAK
		{
		  // Cannot be in the function-level scope
		  
		}
		| KEY_RETURN myReturnValue=expr
		{
		  ReturnType expectedReturnType = symbolTableManager.getReturnType();
		  ReturnType actualReturnType = $myReturnValue.type;
		  if(actualReturnType != expectedReturnType || $myReturnValue.myIsBool) {
		    int lineNumber = getLineNumber(myReturnValue);
		    String customMessage = "Type doesn't match the expected return type";
		    exceptionHandler.handleException(lineNumber, customMessage, 
		                                      expectedReturnType.getName(), 
		                                      ($myReturnValue.myIsBool)? "boolean":actualReturnType.getName(), 
		                                      TypeMismatchException.class);
		  } else {
		    symbolTableManager.setCurrentScopeReturnType(actualReturnType);
		  }
		}
	)
	OP_SCOLON
	| block[functionName]
; 

optPrefix :
	(
	  value OP_ASSIGN
	)?
;

expr returns [String exp, ReturnType type, boolean myIsBool, boolean myIsFunc]:
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
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
    } else {
      if($s1.myIsBool == false || $s3.myIsBool == false){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot use '&' on non-boolean values.");
        } else {
          throw new InvalidTypeException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot use '|' on non-boolean values.");
        }
      } else if($s1.myIsFunc || $s3.myIsFunc) {
        throw new InvalidInvocationException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot perform operations on a function lvalue.");
      }
      $myIsBool = true;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("and, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("or, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

funcExpr returns [String exp, ReturnType type, boolean myIsBool]:
  s1=funcBinOp1
  (
    (
      s2=OP_AND
      | OP_OR
    )
    s3=funcExpr
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
    } else {
      if($s1.myIsBool == false || $s3.myIsBool == false){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot use '&' on non-boolean values.");
        } else {
          throw new InvalidTypeException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot use '|' on non-boolean values.");
        }
      }
      $myIsBool = true;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("and, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("or, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

binOp1 returns [String exp, ReturnType type, boolean myIsBool, boolean myIsFunc]:
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
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      if($s1.myIsBool == true || $s7.myIsBool == true){
        throw new InvalidTypeException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot compare using a boolean value.");
      } else if($s1.myIsFunc || $s7.myIsFunc) {
        throw new InvalidInvocationException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot perform operations on a function lvalue.");
      }
      $myIsBool = true;
      $myIsFunc = false;
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
      if($s1.type == ReturnType.FIXPT || $s7.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

funcBinOp1 returns [String exp, ReturnType type, boolean myIsBool]:
  s1=funcBinOp2
  (
    (
      s2=OP_LEQ
      | s3=OP_GEQ
      | s4=OP_LTHAN
      | s5=OP_GTHAN
      | s6=OP_NEQ
      | OP_EQUAL
    )
    s7=funcBinOp1
  )?
  {
    if($s7.exp == null) {
      $exp = $s1.exp;
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
    } else {
      if($s1.myIsBool == true || $s7.myIsBool == true){
        throw new InvalidTypeException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot compare using a boolean value.");
      }
      $myIsBool = true;
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
      if($s1.type == ReturnType.FIXPT || $s7.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

binOp2 returns [String exp, ReturnType type, boolean myIsBool, boolean myIsFunc]:
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
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      if($s1.myIsBool == true || $s3.myIsBool == true){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot add using a boolean value.");
        } else {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot subtract using a boolean value.");
        }
      } else if($s1.myIsFunc || $s3.myIsFunc) {
        throw new InvalidInvocationException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot perform operations on a function rvalue.");
      }
      $myIsBool = false;
      $myIsFunc = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

funcBinOp2 returns [String exp, ReturnType type, boolean myIsBool]:
  s1=funcBinOp3
  (
    (
      s2=OP_PLUS
      | OP_MINUS
    )
    s3=funcBinOp2
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
    } else {
      if($s1.myIsBool == true || $s3.myIsBool == true){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot add using a boolean value.");
        } else {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot subtract using a boolean value.");
        }
      }
      $myIsBool = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

binOp3 returns [String exp, ReturnType type, boolean myIsBool, boolean myIsFunc]:
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
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      if($s1.myIsBool || $s3.myIsBool){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot divide using a boolean value.");
        } else {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot multiply using a boolean value.");
        }
      } else if($s1.myIsFunc || $s3.myIsFunc) {
        throw new InvalidInvocationException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Cannot perform operations on a function rvalue.");
      }
      $myIsBool = false;
      $myIsFunc = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("div, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("mult, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

funcBinOp3 returns [String exp, ReturnType type, boolean myIsBool]:
  s1=funcBinOp4
  (
    (
      s2=OP_DIV
      | OP_MULT
    )
    s3=funcBinOp3
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
      $type = $s1.type;
      $myIsBool = $s1.myIsBool;
    } else {
      if($s1.myIsBool || $s3.myIsBool){
        if(s2 != null) {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot divide using a boolean value.");
        } else {
          throw new InvalidTypeException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Cannot multiply using a boolean value.");
        }
      }
      $myIsBool = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.add("div, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.add("mult, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if($s1.type == ReturnType.FIXPT || $s3.type == ReturnType.FIXPT) {
        $type = ReturnType.FIXPT;
      } else {
        $type = ReturnType.INT;
      }
    }
  }
;

binOp4 returns [String exp, ReturnType type, boolean myIsBool, boolean myIsFunc]
@init
{
  List<String> paramList = new ArrayList<String>();
  $myIsBool = false;
  $myIsFunc = false;
}
:
  s1=constant                   {$exp = $s1.exp; $type = $s1.type;}
  | OP_LPAREN s2=expr OP_RPAREN {$exp = $s2.exp; $type = $s2.type; $myIsBool = $expr.myIsBool;}
  | s3=id[IdType.NIY, false]
  (
    s4=valueTail                                     {$exp = $s3.exp + $s4.exp; $type = $s3.type;}
    | OP_LPAREN s5=funcExprList[paramList] OP_RPAREN {$exp = $s3.exp + "#" + $s5.exp; $type = $s3.type; $myIsFunc = true;}
  )
  {
    Attribute att = symbolTableManager.getAttributeInCurrentScope($s3.exp, attributeMap);
    if(att == null && $s5.exp == null) {
      // Variable not declared yet
      throw new UndeclaredVariableException("Line " +
        (($s1.start != null)? $s1.start.getLine() : -1) +
        ": Use of undeclared variable: " + $s3.exp);
    } else if($s5.exp != null) {
      // Verify that the function exists
      att = symbolTableManager.getAttributeInGlobalScope($s3.exp);
      if(att == null) {
        // Function not declared yet
        throw new UndeclaredFunctionException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Invocation of undeclared function: " + $s3.exp);
      }
      // Verify that the function params match with the type for the function
      List<String> params = symbolTableManager.getFunctionParameters($s3.exp);
      if(params.size() != paramList.size()) {
        String expected = params.size() == 0 ? "[void]" : params.toString();
        List<String> foundList = new ArrayList<String>();
        for(int i = paramList.size() - 1; i >= 0; --i) {
          foundList.add(paramList.get(i));
        }
        String found = paramList.size() == 0 ? "[void]" : foundList.toString();
        throw new InvalidInvocationException("Line " +
          (($s1.start != null)? $s1.start.getLine() : -1) +
          ": Invalid invocation of function: [" + $s3.exp + "] Expected: " + expected + " Found: " + found);
      }
      for(int i = 0; i < params.size(); ++i) {
        if("int".equals(params.get(i)) && "fixedpt".equals(paramList.get(params.size() - i - 1))) {
          String expected = params.size() == 0 ? "[void]" : params.toString();
          List<String> foundList = new ArrayList<String>();
          for(int j = params.size() - 1; j >= 0; --j) {
            foundList.add(paramList.get(j));
          }
          String found = paramList.size() == 0 ? "[void]" : foundList.toString();
          throw new InvalidInvocationException("Line " +
            (($s1.start != null)? $s1.start.getLine() : -1) +
            ": Invalid invocation of function: [" + $s3.exp + "] Expected: " + expected + " Found: " + found);
        }
      }
    }
  }
;

funcBinOp4 returns [String exp, ReturnType type, boolean myIsBool]:
  s1=constant                      {$exp = $s1.exp; $type = $s1.type; $myIsBool = false;}
  | OP_LPAREN s2=expr OP_RPAREN    {$exp = $s2.exp; $type = $s2.type; $myIsBool = $expr.myIsBool;}
  | s3=id[IdType.NIY, false] s4=valueTail {$exp = $s3.exp + $s4.exp; $type = $s3.type; $myIsBool = false;}
  {
    Attribute att = symbolTableManager.getAttributeInCurrentScope($s3.exp, attributeMap);
    if(att == null) {
      // Variable not declared yet
      throw new UndeclaredVariableException("Line " +
        (($s1.start != null)? $s1.start.getLine() : -1) + 
        ": Use of undeclared variable: " + $s3.exp);
    }
  }
;

constant returns [String exp, ReturnType type]:
	FIXEDPTLIT {$exp = $FIXEDPTLIT.text; $type = ReturnType.FIXPT;}
	| INTLIT   {$exp = $INTLIT.text;     $type = ReturnType.INT;  }
;

value returns [String exp, ReturnType type]:
	s1=id[IdType.NIY, false] s2=valueTail
	{
	  $exp = $s1.exp + "#" + $s2.exp;
	  $type = $id.type;
	}
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
  INTLIT {$exp = $INTLIT.text;}
  | myId=id[IdType.NIY, false]
  {
    $exp = $id.exp;
    Attribute att = symbolTableManager.getAttributeInCurrentScope($id.exp, attributeMap);
    if(att == null) {
      // Variable not declared yet
      int lineNumber = getLineNumber(myId);
      String customMessage = "Use of undeclared variable: \"" + $id.exp + "\""; 
      exceptionHandler.handleException(lineNumber, customMessage, null, null, 
                                       UndeclaredVariableException.class);
    }
    if(!"int".equals($id.type)) {
      // Invalid type (must be int)
      int lineNumber = getLineNumber(myId);
      String customMessage = "Use of fixedpt variable in array index expression: \"" + $id.exp + "\""; 
      exceptionHandler.handleException(lineNumber, customMessage, null, null, 
                                       InvalidTypeException.class);
    }
  }
;

declarationSegment[String functionName] :
  typeDeclarationList varDeclarationList[functionName]
;

exprList[List<String> paramList] returns [String exp]:
  (
    s1=expr s2=exprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = $s1.exp + $s2.exp;
      paramList.add($s1.type == ReturnType.INT ? "int" : "fixedpt");
    }
  }
;

funcExprList[List<String> paramList] returns [String exp]:
  (
    s1=funcExpr s2=funcExprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = $s1.exp + $s2.exp;
      if($s1.myIsBool) {
          int lineNumber = getLineNumber(s1);
          String customMessage = "Cannot pass in a boolean value as a parameter";
          exceptionHandler.handleException(lineNumber, customMessage, null, 
                                          null,InvalidTypeException.class);
      }
      paramList.add($s1.type == ReturnType.INT ? "int" : "fixedpt");
    }
  }
;

exprListTail[List<String> paramList] returns [String exp]:
  (
    OP_COMMA s1=expr s2=exprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = ", " + $s1.exp + $s2.exp;
      paramList.add($s1.type == ReturnType.INT ? "int" : "fixedpt");
    }
  }
;

funcExprListTail[List<String> paramList] returns [String exp]:
  (
    OP_COMMA s1=funcExpr s2=funcExprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = ", " + $s1.exp + $s2.exp;
      paramList.add($s1.type == ReturnType.INT ? "int" : "fixedpt");
    }
  }
;

key_begin
  @init{
    makeNewScope();
  }
  : 
  'begin'    
;

key_end
  @init{
    goToEnclosingScope();
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

id[IdType idType, boolean testNamespace] returns [String exp, ReturnType type]:
  myId=ID
  {
    
    if (testNamespace) {
	    Map<String, Set<String>> unregisteredNameSpaceMap = getUnregisteredNamespacesMap();
	    if(symbolTableManager.doesNameSpaceConflict(idType, $myId.text, unregisteredNameSpaceMap)) {
	      int lineNumber = getLineNumber($myId);
	      String customMessage = idType.getName() + " \"" + $myId.text + "\" is already in the namespace"; 
	      exceptionHandler.handleException(lineNumber, customMessage, null, null, NameSpaceConflictException.class);
	    }
	    
	    if(idType == IdType.FUNCTION_NAME) {
	      functionNamespace.add($myId.text);
	    }
	    if(idType == IdType.VAR_NAME) {
	      varNamespace.add($myId.text);
	    }
	    if(idType == IdType.TYPE_NAME) {
		    typeNamespace.add($myId.text);
	    }
    }
    
    $exp = $ID.text;
    Attribute att = symbolTableManager.getAttributeInCurrentScope($ID.text, attributeMap);
    if(att != null) {
      $type = ReturnType.INT.getName().equals(att.getType())   ? ReturnType.INT   :
              ReturnType.FIXPT.getName().equals(att.getType()) ? ReturnType.FIXPT :
                                              ReturnType.OTHER ;
    } else {
      $type = ReturnType.OTHER;
    }
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
