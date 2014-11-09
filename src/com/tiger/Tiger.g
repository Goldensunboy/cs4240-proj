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
import java.util.LinkedList;
import java.util.Collections;
import com.attribute.Attribute;
import com.attribute.VariableAttribute;
import com.attribute.FunctionAttribute;
import com.attribute.TypeAttribute;
import com.symbol_table.SymbolTableManager;
import com.symbol_table.Symbol;
import com.symbol_table.Scope;
import com.symbol_table.IdType;
import com.compiler.TempVarFactory;
import com.compiler.LabelFactory;
import com.compiler.Type;
import com.exception.InvalidTypeException;
import com.exception.InvalidInvocationException;
import com.exception.UndeclaredFunctionException;
import com.exception.UndeclaredVariableException;
import com.exception.ShouldNotHappenException;
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
  private LinkedList<String> IRList = new LinkedList<String>();
  private TempVarFactory tvf = new TempVarFactory();
  private LabelFactory lf = new LabelFactory();
  private String enclosingFunctionName;
  private ExceptionHandler exceptionHandler = new ExceptionHandler();
  private TypeAttribute VOID_TYPE_ATTRIBUTE = symbolTableManager.getTypeAttributeInCurrentScope(Type.VOID.getName(), attributeMap);
  private TypeAttribute INT_TYPE_ATTRIBUTE = symbolTableManager.getTypeAttributeInCurrentScope(Type.INT.getName(), attributeMap);
  private TypeAttribute FIXEDPT_TYPE_ATTRIBUTE = symbolTableManager.getTypeAttributeInCurrentScope(Type.FIXPT.getName(), attributeMap);
  
  private Set<String> varNamespace = new HashSet<String>();
  private Set<String> typeNamespace = new HashSet<String>();
  private Set<String> functionNamespace = new HashSet<String>();
  
  public static final String VAR_NAMESPACE = "varNameSpace";
  public static final String TYPE_NAMESPACE = "typeNameSpace";
  public static final String FUNCTION_NAMESPACE = "functionNameSpace";
  
  private void putVariableAttributeMap(List<String> variableNameList, 
      String typeName, TypeAttribute typeAttribute, String declaringFunctionName) {
    for (String variableName : variableNameList) {
        VariableAttribute variableAttribute = new VariableAttribute(variableName, typeName, declaringFunctionName);
        attributeMap.put(variableName, variableAttribute);
    }
  }
  
  private void putFunctionAttributeMap(String functionName,  TypeAttribute returnTypeAttribute, List<String> parameters) {
    String typeName = returnTypeAttribute.getAliasName();
    FunctionAttribute functionAttribute = new FunctionAttribute(functionName, typeName, parameters);
    attributeMap.put(functionName, functionAttribute);
  }

  public void putTypeAttributeMap(int lineNumber ,String aliasName, Type type,
      Type typeOfArray, boolean isTwoDimensionalArray, int dim1, int dim2) {
    TypeAttribute typeAttribute = new TypeAttribute(aliasName, type, typeOfArray,
      isTwoDimensionalArray, dim1, dim2);
    //System.out.println("Line: " + lineNumber + " :: " + typeAttribute);
    attributeMap.put(aliasName, typeAttribute); 
  }

  public void printAttributeMap() {
    for (Entry<String, List<Symbol>> attr : symbolTableManager.getSymbolTable().entrySet()){
      for (Symbol symbol : attr.getValue() ) {
        System.out.println(symbol + " :: Have access to: " +
          showAllReachableAttributes(symbol.getScope()));
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
  
  public List<String> getIRCode() {
    Collections.reverse(IRList);
    return IRList;
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
  skip["File"]
	| typeDeclarationList funcNext
;

funcNext:
  myTypeId=typeId[IdType.RETURN_TYPE]
  funcCurrent[$myTypeId.text, $typeId.typeAttribute]
  | KEY_VOID
  ( 
    funcCurrent[VOID_TYPE_ATTRIBUTE.getAliasName(), VOID_TYPE_ATTRIBUTE]
    | mainFunction[VOID_TYPE_ATTRIBUTE]
  )
;

funcCurrent[String typeName, TypeAttribute returnTypeAttribute] :
	 funcDeclaration[typeName, returnTypeAttribute] funcNext
;

funcDeclaration[String typeName, TypeAttribute returnTypeAttribute]
scope
{
  List<String> myParams;
  List<VariableAttribute> parameterList;
}
@init
{
  $funcDeclaration::myParams = new ArrayList<String>();
  $funcDeclaration::parameterList = new ArrayList<VariableAttribute>();
}
:
  KEY_FUNCTION myFunctionName=id[IdType.FUNCTION_NAME]
	{
	  IRList.addFirst("FUNC_" + $id.text + ":");
	}
  OP_LPAREN paramList[$myFunctionName.text] OP_RPAREN afterBegin[$myFunctionName.text, typeName, returnTypeAttribute]
;

afterBegin[String myFunctionName, String typeName, TypeAttribute returnTypeAttribute]
@init
{
  putFunctionAttributeMap(myFunctionName,
                              returnTypeAttribute,
                              $funcDeclaration::myParams);
  enclosingFunctionName = myFunctionName;
}
:
  myKey_begin=key_begin 
  {
    for(VariableAttribute attribute : $funcDeclaration::parameterList) {
      attributeMap.put(attribute.getVariableName(), attribute);
    }
  }
  blockList[myFunctionName] 
  {
    if(!symbolTableManager.returnStatementSatisfied(myFunctionName)) {
      String customMessage = "Mismatch return statement for function " + myFunctionName;
      TypeAttribute expectedReturnType = symbolTableManager.getReturnType();
      TypeAttribute actualReturnType = symbolTableManager.getCurrentScopeReturnType();
      exceptionHandler.handleException(myKey_begin, customMessage, expectedReturnType.getAliasName(), 
                                       actualReturnType.getAliasName(), TypeMismatchException.class);
    }
  }
  key_end OP_SCOLON
;

mainFunction [TypeAttribute returnTypeAttribute]:
  a=KEY_MAIN OP_LPAREN OP_RPAREN 
  {
	  putFunctionAttributeMap($KEY_MAIN.text,
	                              returnTypeAttribute,
	                              new ArrayList<String>());
	  enclosingFunctionName = $KEY_MAIN.text;
	  IRList.addFirst("FUNC_main:");
  }
  key_begin blockList[$a.text] key_end OP_SCOLON EOF
;

typeId[IdType idType] returns[TypeAttribute typeAttribute]:
	baseType {$typeAttribute=$baseType.typeAttribute;}
	| id[idType] {$typeAttribute=$id.typeAttribute;}
;

baseType returns[TypeAttribute typeAttribute]:
	KEY_INT 
	{
	  $typeAttribute=
	          symbolTableManager.getTypeAttributeInCurrentScope(Type.INT.getName(), attributeMap);
	}
	| KEY_FIXEDPT 
	{
	  $typeAttribute=
	          symbolTableManager.getTypeAttributeInCurrentScope(Type.FIXPT.getName(), attributeMap);
	}
;

param[String declaringFunctionName] :
	id[IdType.FUNCTION_PARAMETER] OP_COLON typeId[IdType.VARIABLE_TYPE]
	{
    $funcDeclaration::myParams.add($typeId.text);
    
	  VariableAttribute variableAttribute = new VariableAttribute($id.text, $typeId.text, declaringFunctionName);
    $funcDeclaration::parameterList.add(variableAttribute);
	}
;

paramList[String declaringFunctionName] :
	(
	  param[declaringFunctionName] paramListTail[declaringFunctionName]
	)?
;

paramListTail[String declaringFunctionName] :
	(
	  OP_COMMA param[declaringFunctionName] paramListTail[declaringFunctionName]
	)?
;

blockList[String functionName] :
	block[functionName, ""]+ // block-list and block-tail are combined
;

block[String functionName, String endLoop] :
	key_begin declarationSegment[functionName] statSeq[functionName, endLoop] key_end OP_SCOLON
;

typeDeclarationList :
	typeDeclaration*
;

varDeclarationList[String functionName] :
	varDeclaration[functionName]*
;

typeDeclaration :
	KEY_TYPE myId=id[IdType.USER_DEFINED_TYPE] OP_EQUAL myType=type OP_SCOLON
	{
	  putTypeAttributeMap($myId.start.getLine(), $myId.text, $myType.type,
	    $myType.typeOfArray, $myType.isTwoDimensionalArray, $myType.dim1, $myType.dim2);
	}
;

type returns [Type type, Type typeOfArray, boolean isTwoDimensionalArray, int dim1, int dim2]
@init{
  boolean myIsTwoDimensional_var = false;
  boolean myIsTypeArray_var = false;
  int myDim2_var = -1;
  $dim1 = -1;
  $dim2 = -1;
}
:
	(
	  KEY_ARRAY OP_LBRACK myDim1=INTLIT OP_RBRACK
		(
		  OP_LBRACK myDim2=INTLIT OP_RBRACK
		  {
			  $isTwoDimensionalArray = true;
			  myDim2_var= Integer.valueOf($myDim2.text);
		  }
		)?
		KEY_OF
		{
		  $type = Type.ARRAY;
		  $dim1 = Integer.valueOf($myDim1.text);
			$dim2 = myDim2_var;	
			myIsTypeArray_var = true;		  
		}
	)?
	myBaseType=baseType
	{
	  if(myIsTypeArray_var) {
	    $typeOfArray = $myBaseType.typeAttribute.getType();
	  } else {
	    $type = $myBaseType.typeAttribute.getType(); 
	  }
	}
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
	KEY_VAR idList[IdType.VARIABLE_DECLARATION] OP_COLON myTypeId=typeId[IdType.VARIABLE_TYPE]
	{
	  putVariableAttributeMap($varDeclaration::aggregatedMyIdList,
	                          $myTypeId.text, $myTypeId.typeAttribute,
	                          $functionName);
	}
  optionalInit[$varDeclaration::aggregatedMyIdList] OP_SCOLON
;

idList[IdType idType] : 
  myId=id[idType]
  (
    OP_COMMA idList[idType]
  )?
	{
	  $varDeclaration::aggregatedMyIdList.add($myId.text);
	}
;

optionalInit[List<String> varNames] :
	(
	  OP_ASSIGN s1=constant
	  {
	    for(String varName : varNames) {
	      TypeAttribute typeAttribute = symbolTableManager.getTypeAttributeInCurrentScope(varName, attributeMap);
	      
	      if(typeAttribute.isPrimitive()) {
	        IRList.addFirst("assign, " + varName + ", " + $s1.exp);
	      } else {
	        // TODO 1D or 2D array? Must also get sizes
	        IRList.addFirst("assign, " + varName + ", ***PLACEHOLDER ARRAY ASSIGN***, " + $s1.exp);
	      }
	    }
	  }
	)?
;

statSeq[String functionName, String endLoop] :
	stat[functionName, endLoop]+
;

stat[String functionName, String endLoop] returns [Type statReturnType]
@init
{
  List<String> paramList = new ArrayList<String>();
  
}
: 
	(
		s1=id[IdType.VARIABLE_NAME] 
		(
		  s2=valueTail OP_ASSIGN s3=expr[null, null]
		  {
		    // Verify that the assignment is valid
		    Attribute att = symbolTableManager.getAttributeInCurrentScope($s1.exp, attributeMap);
		    TypeAttribute s1TypeAttribute = symbolTableManager.getTypeAttributeInCurrentScope($s1.text, attributeMap);
        TypeAttribute s3TypeAttribute = $s3.typeAttribute;
        if(!$s3.exp.contains("#")) {
		      // Expr assignment
		      if(att == null) {
		        // Variable not declared yet
		        String customMessage = "Assignment to undeclared variable: " + $s1.exp;
		        exceptionHandler.handleException(s1, customMessage, null, null, UndeclaredVariableException.class);
		      } else if(!s1TypeAttribute.assignableBy(s3TypeAttribute)) {
		        // Illegal assignment (fixpt to int)
            String customMessage = "Can't assign " + $s3.text + " to " + $s1.text;
            exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
		      }
		      // Cannot assign a conditional to a variable
		      if($s3.myIsBool) {
	          String customMessage = "Boolean values cannot be assigned to a variable.";
	          exceptionHandler.handleException(s3, customMessage, null, null,InvalidTypeException.class);
		      }
		      // Assignment statement
		      if("".equals($s2.exp)) {
            IRList.addFirst("assign, " + $s1.exp + ", " + $s3.exp);
          } else {
            String[] parts = $s2.exp.substring(1, $s2.exp.length() - 1).split("\\]\\[");
            if(parts.length == 1) {
              IRList.addFirst("array_store, " + $s1.exp + ", " + parts[0] + ", " + $s3.exp);
            } else {
              IRList.addFirst("array_store, " + $s1.exp + ", " + parts[0] + ", " + parts[1] + ", " + $s3.exp);
            }
          }
		    } else {
		      if(att == null) {
          // Variable not declared yet
            String customMessage = "Assignment to undeclared variable: " + $s1.exp;
            exceptionHandler.handleException(s1, customMessage, null, null, UndeclaredVariableException.class);
          }
		      // Function assignment
		      String[] parts = $s3.exp.split("#");
		      TypeAttribute rettype = symbolTableManager.getFunctionReturnType(parts[0]);
		      if(!$s1.typeAttribute.assignableBy(rettype)) {
		        // (fixpt to int)
		        String customMessage = "Can't assign function \"" + parts[0] 
		          +"\"\'s return value with the type: \"" + rettype.getAliasName() 
		          + "\" to \"" + $s1.exp + "\" with the type: \"" + $s1.typeAttribute.getAliasName()+"\"";
            exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
		      }
		      IRList.addFirst("callr, " + $s1.exp + ", FUNC_" + parts[0] +
		        // Be careful not to reference parts[1] which is out
		        // of bounds for parameterless functions
		        (parts.length == 1 ? "" : ", " + parts[1]));
		    }
		  }
		  | OP_LPAREN s4=funcExprList[paramList] OP_RPAREN
		  {
		    // Lone function call
		    if("".equals($s4.exp)) {
		      // Parameterless
		      IRList.addFirst("call, FUNC_" + $s1.exp);
		    } else {
		      // With params
		      IRList.addFirst("call, FUNC_" + $s1.exp + ", " + $s4.exp);
		    }
		    // Verify that the function exists
		    Attribute att = symbolTableManager.getAttributeInGlobalScope($s1.exp);
        if(att == null) {
          // Function not declared yet
          String customMessage = "Invocation of undeclared function: " + $s1.exp;
          exceptionHandler.handleException(s1, customMessage, null, null, UndeclaredFunctionException.class); 
        }
        // Verify that the function params match with the type for the function
        List<String> params = symbolTableManager.getFunctionParameters($s1.exp);
        if(params.size() != paramList.size()) {
          String expected = params.size() == 0 ? "[void]" : params.toString();
          List<String> foundList = new ArrayList<String>();
          
          int offset = Math.abs(params.size() - paramList.size());
          for(int i = params.size() - offset - 1; i >= 0; --i) {
            foundList.add(paramList.get(i));
            }
          String actual = paramList.size() == 0 ? "[void]" : foundList.toString();
          
          String customMessage = "Invalid invocation of function: [" + $s1.exp + "]";
          exceptionHandler.handleException(s1, customMessage, expected, actual,
            InvalidInvocationException.class);
        } else {
	        for(int i = 0; i < params.size(); ++i) {
	          if("int".equals(params.get(i)) && "fixedpt".equals(paramList.get(params.size() - i - 1))) {
	            String expected = params.size() == 0 ? "[void]" : params.toString();
	            List<String> foundList = new ArrayList<String>();
	            System.out.println(paramList + " paramList");
	            for(int j = params.size() - 1; j >= 0; --j) {
	              foundList.add(paramList.get(j));
	            }
	            String actual = paramList.size() == 0 ? "[void]" : foundList.toString();
	
		          String customMessage = "Invalid invocation of function: [" + $s1.exp + "]";
		          exceptionHandler.handleException(s1, customMessage, expected, actual,
		            InvalidInvocationException.class);
	          }
	        }
	      }
		  }
		)
		| KEY_IF 
		{
		  TypeAttribute ifReturnType=VOID_TYPE_ATTRIBUTE;
		  TypeAttribute elseReturnType=VOID_TYPE_ATTRIBUTE;
		  makeNewScope();
		  String ifLabel = lf.nextLabel("IF_START");
		  String elseLabel = lf.nextLabel("ELSE_BEGIN");
		}
		myIfCond=expr[ifLabel, elseLabel]
		  {
		    if(!$myIfCond.myIsBool) {
			    String customMessage = "If statement conditions must resolve to a boolean value";
	        exceptionHandler.handleException(myIfCond, customMessage, null, 
                                          null,InvalidTypeException.class);
		    }
		  }
      KEY_THEN statSeq[functionName, endLoop]
      {
        ifReturnType = symbolTableManager.getCurrentScopeReturnType();
        String elseLabel2 = lf.nextLabel("ELSE_END");
        IRList.addFirst("goto, " + elseLabel2);
        IRList.addFirst(elseLabel + ":");
      }
    (
      {
        goToEnclosingScope();
      }
      s5=KEY_ELSE
      {
        makeNewScope();
      }
      statSeq[functionName, endLoop]
      {
        elseReturnType = symbolTableManager.getCurrentScopeReturnType();
      }
    )?
    KEY_ENDIF
    {
      goToEnclosingScope();
      symbolTableManager.setCurrentScopeReturnType(ifReturnType.equals(elseReturnType)? ifReturnType : VOID_TYPE_ATTRIBUTE);
      IRList.addFirst(elseLabel2 + ":");
    }
		|
		{
		  String whileTop = lf.nextLabel("WHILE_TOP");
		  String whileBegin = lf.nextLabel("WHILE_BEGIN");
		  String endSubLoopWhile = lf.nextLabel("WHILE_END");
		  IRList.addFirst(whileTop + ":");
		}
		KEY_WHILE myWhileCond=expr[whileBegin, endSubLoopWhile]
		{
		  if(!$myWhileCond.myIsBool) {
        String customMessage = "while conditional statements must resolve to a boolean value";
        exceptionHandler.handleException(myWhileCond, customMessage, null, null,
          InvalidTypeException.class);
		  }
		}
      KEY_DO statSeq[functionName, endSubLoopWhile] KEY_ENDDO
    {
      IRList.addFirst("goto, " + whileTop);
      IRList.addFirst(endSubLoopWhile + ":");
    }
		|
		{
		  String endSubLoop = lf.nextLabel("LOOP_END");
		}
		sym_for=key_for s6=id[IdType.VARIABLE_DECLARATION]
		  {
		    List<String> indexVarList = new ArrayList<String>();
		    indexVarList.add($s6.exp);
		    putVariableAttributeMap(indexVarList,
                                $s6.text, INT_TYPE_ATTRIBUTE,
                                $functionName);
		  }
		  OP_ASSIGN s7=indexExpr KEY_TO s8=indexExpr
		  {
		    String forTop = lf.nextLabel("FOR_START");
		    String endSubLoopFor = lf.nextLabel("FOR_END");
		    // Store upper bound
		    String upperBoundTemp = tvf.nextTemp();
		    IRList.addFirst("assign, " + upperBoundTemp + ", " + $s8.exp);
		    // Generate index variable
		    ArrayList<String> varList = new ArrayList<String>();
		    varList.add($s6.text);
		    putVariableAttributeMap(varList, Type.INT.getName() , INT_TYPE_ATTRIBUTE, $functionName);
        IRList.addFirst("assign, " + $s6.text + ", " + $s7.exp);
        // Begin loop here
        IRList.addFirst(forTop + ":");
        IRList.addFirst("brgt, " + $s6.text + ", " + upperBoundTemp + ", " + endSubLoopFor);
		  }
		  KEY_DO statSeq[functionName, endSubLoopFor] KEY_ENDDO
		  {
		    // Increment index variable and return to top
		    String incTemp = tvf.nextTemp();
		    IRList.addFirst("add, " + $s6.text + ", 1, " + incTemp);
		    IRList.addFirst("assign, " + $s6.text + ", " + incTemp);
		    IRList.addFirst("goto, " + forTop);
		    IRList.addFirst(endSubLoopFor + ":");
		    goToEnclosingScope();
		  }
		| brk=KEY_BREAK
		{
		  // Cannot be in the function-level scope
		  if("".equals(endLoop)) {
		    String customMessage = "Must be within a loop's scope to use break";
		    exceptionHandler.handleException(brk, customMessage, 
		                                      null, null,
		                                      ShouldNotHappenException.class);
		  } else {
		    IRList.addFirst("goto, " + endLoop);
		  }
		}
		| KEY_RETURN myReturnValue=expr[null, null]
		{
		  TypeAttribute expectedReturnType = symbolTableManager.getReturnType();
		  TypeAttribute actualReturnType = $myReturnValue.typeAttribute;
		  if(actualReturnType != expectedReturnType || $myReturnValue.myIsBool) {
		    String customMessage = "Type doesn't match the expected return type";
		    exceptionHandler.handleException(myReturnValue, customMessage, 
		                                      expectedReturnType.getAliasName(), 
		                                      ($myReturnValue.myIsBool)? "boolean":actualReturnType.getAliasName(), 
		                                      TypeMismatchException.class);
		  } else {
		    IRList.addFirst("return, " + $myReturnValue.exp);
		    symbolTableManager.setCurrentScopeReturnType(actualReturnType);
		  }
		}
	)
	OP_SCOLON
	| block[functionName, endLoop]
;
 
key_for :
  KEY_FOR
  {
    makeNewScope();
  }
;

expr[String startLabel, String endLabel] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool, boolean myIsFunc] :
  s1=binOp1[startLabel, endLabel]
  (
    (
      s2=OP_AND
      | OP_OR
    )
    {
      // Left side check for boolean value
      if(!$s1.myIsBool) {
        String customMessage = "Cannot use " + (s2 != null ? "'&'" : "'|'") + " on non-boolean values";
        exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
      } else if($s1.myIsFunc) {
        String customMessage = "Cannot perform operations on a function lvalue";
        exceptionHandler.handleException(s1, customMessage, null, null, InvalidInvocationException.class);
      } else {
      // Convert default branch to start for OR
        if(s2 == null) {
          String[] parts = IRList.pop().split(", ");
          switch(parts[0].hashCode()) {
            case 0x02E44FC: parts[0] = "brneq"; break;
            case 0x02E453D: parts[0] = "brleq"; break;
            case 0x02E45D8: parts[0] = "brgeq"; break;
            case 0x59A6103: parts[0] = "brlt" ; break;
            case 0x59A73C8: parts[0] = "brgt" ; break;
            case 0x59A7B4A: parts[0] = "breq" ; break;
            default: exceptionHandler.handleException(s1, "Internal error in popping branch for OR operation",
                       null, null, ShouldNotHappenException.class);
          }
          IRList.addFirst(parts[0] + ", " + parts[1] + ", " + parts[2] + ", " + startLabel);
        }
      }
    }
    s3=expr[startLabel, endLabel]
  )?
  {
    if($s3.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = $s1.typeAttribute;
      $myIsBool = $s1.myIsBool;
    } else {
      if(!$s3.myIsBool) {
	      String customMessage = "Cannot use " + (s2 != null ? "'&'" : "'|'") + " on non-boolean values";
	      exceptionHandler.handleException(s3, customMessage, null, null, InvalidTypeException.class);
      } else if($s3.myIsFunc) {
        String customMessage = "Cannot perform operations on a function lvalue";
        exceptionHandler.handleException(s3, customMessage, null, null, InvalidInvocationException.class);
      }
      $myIsBool = true;
      IRList.addFirst(startLabel + ":");
    }
  }
;

funcExpr[IdType idType] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool]:
  s1=funcBinOp1[idType]
  {
    $exp = $s1.exp;
    $typeAttribute = $s1.typeAttribute;
    $myIsBool = $s1.myIsBool;
  }
;

binOp1[String startLabel, String endLabel] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool, boolean myIsFunc]:
  s1=binOp2[startLabel, endLabel]
  (
    (
      s2=OP_LEQ
      | s3=OP_GEQ
      | s4=OP_LTHAN
      | s5=OP_GTHAN
      | s6=OP_NEQ
      | OP_EQUAL
    )
    s7=binOp1[startLabel, endLabel]
  )?
  {
    TypeAttribute s1TypeAttribute = $s1.typeAttribute;
    TypeAttribute s7TypeAttribute;
    if($s7.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = s1TypeAttribute;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      s7TypeAttribute = $s7.typeAttribute;
      if(!s1TypeAttribute.canBeInOperationWith(s7TypeAttribute)) {
        String customMessage = $s1.text + " and " + $s7.text + " are not comparable";
        exceptionHandler.handleException(s1, customMessage, null, null, TypeMismatchException.class);
      }
      
      if($s1.myIsBool == true || $s7.myIsBool == true){
         String customMessage = "Cannot compare using a boolean value";
         exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
      } else if($s1.myIsFunc || $s7.myIsFunc) {
        String customMessage = "Cannot perform operations on a function lvalue";
        exceptionHandler.handleException(s1, customMessage, null, null, InvalidInvocationException.class);
      }
      $myIsBool = true;
      $myIsFunc = false;
      if(s2 != null) {
        IRList.addFirst("brgt, "  + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      } else if(s3 != null) {
        IRList.addFirst("brlt, "  + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      } else if(s4 != null) {
        IRList.addFirst("brgeq, " + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      } else if(s5 != null) {
        IRList.addFirst("brleq, " + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      } else if(s6 != null) {
        IRList.addFirst("breq, "  + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      } else {
        IRList.addFirst("brneq, " + $s1.exp + ", " + $s7.exp + ", " + endLabel);
      }
      $exp = "";
      if(s1TypeAttribute.getType() == Type.FIXPT || (s7TypeAttribute == null ? false : s7TypeAttribute.getType() == Type.FIXPT)) {
        $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;
      } else {
        $typeAttribute = INT_TYPE_ATTRIBUTE;
      }
    }
  }
;

funcBinOp1[IdType idType] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool]:
  s1=funcBinOp2[idType]
  {
    $exp = $s1.exp;
    $typeAttribute = $s1.typeAttribute;
    $myIsBool = $s1.myIsBool;
  }
;

binOp2[String startLabel, String endLabel] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool, boolean myIsFunc]:
  s1=binOp3[startLabel, endLabel]
  (
    (
      s2=OP_PLUS
      | OP_MINUS
    )
    s3=binOp2[startLabel, endLabel]
  )?
  {
    TypeAttribute s1TypeAttribute = $s1.typeAttribute;
    TypeAttribute s3TypeAttribute;
    if($s3.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = s1TypeAttribute;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      s3TypeAttribute = $s3.typeAttribute;
      String addSubtract = s2 == null ? "subtracted" : "added" ;
      if(!s1TypeAttribute.canBeInOperationWith(s3TypeAttribute)) {
        String customMessage = $s1.text + " and " + $s3.text + " cannot be " + addSubtract;
        exceptionHandler.handleException(s1, customMessage, null, null, TypeMismatchException.class);
      }
      
      if($s1.myIsBool == true || $s3.myIsBool == true){
          String customMessage = "Boolean values cannot be " + addSubtract;
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
      } else if($s1.myIsFunc || $s3.myIsFunc) {
          String customMessage = "Cannot perform operations on a function rvalue";
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidInvocationException.class);
      }
      $myIsBool = false;
      $myIsFunc = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.addFirst("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.addFirst("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if(s1TypeAttribute.getType() == Type.FIXPT || (s3TypeAttribute == null ? false : s3TypeAttribute.getType() == Type.FIXPT)){
        $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;
      } else {
        $typeAttribute = INT_TYPE_ATTRIBUTE;
      }
    }
  }
;

funcBinOp2[IdType idType] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool]:
  s1=funcBinOp3[idType]
  (
    (
      s2=OP_PLUS
      | OP_MINUS
    )
    s3=funcBinOp2[IdType.NIY] //most likely it's idType
  )?
  {
    TypeAttribute s1TypeAttribute = $s1.typeAttribute;
    TypeAttribute s3TypeAttribute;
    if($s3.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = $s1.typeAttribute;
      $myIsBool = $s1.myIsBool;
    } else {
      s3TypeAttribute = $s3.typeAttribute;
      if($s1.myIsBool == true || $s3.myIsBool == true){
        if(s2 != null) {
          String customMessage = "Cannot add using a boolean value";
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
        } else {
          String customMessage = "Cannot subtract using a boolean value";
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
        }
      }
      $myIsBool = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.addFirst("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.addFirst("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if(s1TypeAttribute.getType() == Type.FIXPT || s3TypeAttribute == null ? false : s3TypeAttribute.getType() == Type.FIXPT) {
        $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;
      } else {
        $typeAttribute = INT_TYPE_ATTRIBUTE;
      }
    }
  }
;

binOp3[String startLabel, String endLabel] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool, boolean myIsFunc]:
  s1=binOp4[startLabel, endLabel]
  (
    (
      s2=OP_DIV
      | OP_MULT
    )
    s3=binOp3[startLabel, endLabel]
  )?
  {
    TypeAttribute s1TypeAttribute = $s1.typeAttribute;
    TypeAttribute s3TypeAttribute;
    if($s3.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = s1TypeAttribute;
      $myIsBool = $s1.myIsBool;
      $myIsFunc = $s1.myIsFunc;
    } else {
      s3TypeAttribute = $s3.typeAttribute;
      String divMult = s2 == null ? "multiplied" : "divided";
      if(!s1TypeAttribute.canBeInOperationWith(s3TypeAttribute)) {
        String customMessage = $s1.text + " and " + $s3.text + " cannot be " + divMult;
        exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
      }
      
      if($s1.myIsBool || $s3.myIsBool){
	      String customMessage = "Boolean values cannot be " + divMult;
	      exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
      } else if($s1.myIsFunc || $s3.myIsFunc) {
        String customMessage = "Cannot perform operations on a function rvalue";
        exceptionHandler.handleException(s1, customMessage, null, null, InvalidInvocationException.class);
      }
      $myIsBool = false;
      $myIsFunc = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.addFirst("div, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.addFirst("mult, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if(s1TypeAttribute.getType() == Type.FIXPT || (s3TypeAttribute == null ? false : s3TypeAttribute.getType() == Type.FIXPT)) {
        $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;
      } else {
        $typeAttribute = INT_TYPE_ATTRIBUTE;
      }
    }
  }
;

funcBinOp3[IdType idType] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool]:
  s1=funcBinOp4[idType]
  (
    (
      s2=OP_DIV
      | OP_MULT
    )
    s3=funcBinOp3[IdType.NIY] //most likely it's idType
  )?
  {
    TypeAttribute s1TypeAttribute  = $s1.typeAttribute;
    TypeAttribute s3TypeAttribute;
    if($s3.exp == null) {
      $exp = $s1.exp;
      $typeAttribute = $s1.typeAttribute;
      $myIsBool = $s1.myIsBool;
    } else {
      s3TypeAttribute = $s3.typeAttribute;
      if($s1.myIsBool || $s3.myIsBool){
        if(s2 != null) {
          String customMessage = "Cannot divide using a boolean value";
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
        } else {
          String customMessage = "Cannot multiply using a boolean value";
          exceptionHandler.handleException(s1, customMessage, null, null, InvalidTypeException.class);
        }
      }
      $myIsBool = false;
      String temp = tvf.nextTemp();
      if(s2 != null) {
        IRList.addFirst("div, "  + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.addFirst("mult, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
      if(s1TypeAttribute.getType() == Type.FIXPT || s3TypeAttribute == null ? false : s3TypeAttribute.getType() == Type.FIXPT) {
        $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;
      } else {
        $typeAttribute = INT_TYPE_ATTRIBUTE;
      }
    }
  }
;

binOp4[String startLabel, String endLabel] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool, boolean myIsFunc]
@init
{
  List<String> paramList = new ArrayList<String>();
  $myIsBool = false;
  $myIsFunc = false;
}
:
  s1=constant {$exp = $s1.exp; $typeAttribute = $s1.typeAttribute;}
  | OP_LPAREN s2=expr[startLabel, endLabel] OP_RPAREN {$exp = $s2.exp; $typeAttribute = $s2.typeAttribute; $myIsBool = $s2.myIsBool;}
  | s3=id[IdType.VARIABLE_NAME]
  (
    s4=valueTail
    {
      $typeAttribute = $s3.typeAttribute;
      if("".equals($s4.exp)) {
        $exp = $s3.exp;
      } else {
        String[] parts = $s4.exp.substring(1, $s4.exp.length() - 1).split("\\]\\[");
        String arrTempVar = tvf.nextTemp();
        if(parts.length == 1) {
          IRList.addFirst("array_load, " + arrTempVar + ", " + $s3.exp + ", " + parts[0]);
        } else {
          IRList.addFirst("array_load, " + arrTempVar + ", " + $s3.exp + ", " + parts[0] + ", " + parts[1]);
        }
        $exp = arrTempVar;
      }
    }
    | OP_LPAREN s5=funcExprList[paramList] OP_RPAREN 
    {
      $exp = $s3.exp + "#" + $s5.exp; 
      $typeAttribute = $s3.typeAttribute; 
      $myIsFunc = true;
    }
  )
  {
    Attribute att = symbolTableManager.getAttributeInCurrentScope($s3.exp, attributeMap);
    if(att == null && $s5.exp == null) {
      // Variable not declared yet
        String customMessage = "Use of undeclared variable: " + $s3.exp;
        exceptionHandler.handleException(s3, customMessage, null, null, UndeclaredVariableException.class);
    } else if($s5.exp != null) {
      // Verify that the function exists
      att = symbolTableManager.getAttributeInGlobalScope($s3.exp);
      if(att == null) {
        // Function not declared yet
        String customMessage = "Invocation of undeclared function: " + $s3.exp;
        exceptionHandler.handleException(s3, customMessage, null, null, UndeclaredFunctionException.class);
      }
      // Verify that the function params match with the type for the function
      List<String> params = symbolTableManager.getFunctionParameters($s3.exp);
      if(params.size() != paramList.size()) {
        String expected = params.size() == 0 ? "[void]" : params.toString();
        List<String> foundList = new ArrayList<String>();
        
        int offset = Math.abs(params.size() - paramList.size());
        for(int i = paramList.size() - offset - 1; i >= 0; --i) {
          foundList.add(paramList.get(i));
        }
        String actual = paramList.size() == 0 ? "[void]" : foundList.toString();
        String customMessage = "Invalid invocation of function: [" + $s3.exp + "]";
        exceptionHandler.handleException(s3/*TODO s1*/, customMessage, expected, actual, InvalidInvocationException.class);
      }
      for(int i = 0; i < params.size(); ++i) {
        if("int".equals(params.get(i)) && "fixedpt".equals(paramList.get(params.size() - i - 1))) {
          String expected = params.size() == 0 ? "[void]" : params.toString();
          List<String> foundList = new ArrayList<String>();
          for(int j = params.size() - 1; j >= 0; --j) {
            foundList.add(paramList.get(j));
          }
          String actual = paramList.size() == 0 ? "[void]" : foundList.toString();
          String customMessage = "Invalid invocation of function: [" + $s3.exp + "]";
          exceptionHandler.handleException(s3 /*TODO s1*/, customMessage, expected, actual, InvalidInvocationException.class);
        }
      }
    }
  }
;

funcBinOp4[IdType idType] returns [String exp, TypeAttribute typeAttribute, boolean myIsBool]:
  s1=constant                      {$exp = $s1.exp; $typeAttribute = $s1.typeAttribute; $myIsBool = false;}
  | OP_LPAREN s2=funcExpr[IdType.NIY]/*most likely it's idType*/ OP_RPAREN    {$exp = $s2.exp; $typeAttribute = $s2.typeAttribute; $myIsBool = $s2.myIsBool;}
  | s3=id[idType] s4=valueTail {$exp = $s3.exp + $s4.exp; $typeAttribute = $s3.typeAttribute; $myIsBool = false;}
  {
    Attribute att = symbolTableManager.getAttributeInCurrentScope($s3.exp, attributeMap);
    if(att == null) {
      // Variable not declared yet
      String customMessage = "Use of undeclared variable: " + $s3.exp;
      exceptionHandler.handleException(s3, customMessage, null, null, UndeclaredVariableException.class);
    }
  }
;

constant returns [String exp, TypeAttribute typeAttribute]:
	FIXEDPTLIT {$exp = $FIXEDPTLIT.text; $typeAttribute = FIXEDPT_TYPE_ATTRIBUTE;}
	| INTLIT   {$exp = $INTLIT.text;     $typeAttribute = INT_TYPE_ATTRIBUTE;  }
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
      IRList.addFirst("mult, " + $s1.exp + ", " + $s2.exp + ", " + temp);
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
        IRList.addFirst("add, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      } else {
        IRList.addFirst("sub, " + $s1.exp + ", " + $s3.exp + ", " + temp);
      }
      $exp = temp;
    }
  }
;

indexExpr3 returns [String exp]:
  INTLIT {$exp = $INTLIT.text;}
  | myId=id[IdType.NIY]
  {
    $exp = $id.exp;
    Attribute att = symbolTableManager.getAttributeInCurrentScope($id.exp, attributeMap);
    if(att == null) {
      // Variable not declared yet
      String customMessage = "Use of undeclared variable: \"" + $id.exp + "\""; 
      exceptionHandler.handleException(myId, customMessage, null, null, 
                                       UndeclaredVariableException.class);
    }
    if(!"int".equals($id.typeAttribute.getType().getName())) {
      // Invalid type (must be int)
      String customMessage = "Use of fixedpt variable in array index expression: \"" + $id.exp + "\""; 
      exceptionHandler.handleException(myId, customMessage, null, null, 
                                       InvalidTypeException.class);
    }
  }
;

declarationSegment[String functionName] :
  typeDeclarationList varDeclarationList[functionName]
;

exprList[List<String> paramList] returns [String exp]:
  (
    s1=expr[null, null] s2=exprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = $s1.exp + $s2.exp;
      paramList.add($s1.typeAttribute.getType() == Type.INT ? Type.INT.getName() : Type.FIXPT.getName());
    }
  }
;

funcExprList[List<String> paramList] returns [String exp]:
  (
    s1=funcExpr[IdType.FUNCTION_ARGUMENT] s2=funcExprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = $s1.exp + $s2.exp;
      if($s1.myIsBool) {
          String customMessage = "Cannot pass in a boolean value as a parameter";
          exceptionHandler.handleException(s1, customMessage, null, 
                                          null,InvalidTypeException.class);
      }
      paramList.add($s1.typeAttribute.getType() == Type.INT ? "int" : "fixedpt");
    }
  }
;

exprListTail[List<String> paramList] returns [String exp]:
  (
    OP_COMMA s1=expr[null, null] s2=exprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = ", " + $s1.exp + $s2.exp;
      paramList.add($s1.typeAttribute.getType() == Type.INT ? Type.INT.getName() : Type.FIXPT.getName());
    }
  }
;

funcExprListTail[List<String> paramList] returns [String exp]:
  (
    OP_COMMA s1=funcExpr[IdType.NIY] s2=funcExprListTail[paramList]
  )?
  {
    if($s1.exp == null) {
      $exp = "";
    } else {
      $exp = ", " + $s1.exp + $s2.exp;
      paramList.add($s1.typeAttribute.getType() == Type.INT ? "int" : "fixedpt");
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

skip[String testCaseName]
:
  SKIP
  {System.out.println(testCaseName + " skipped");}  
;
SKIP:
  'SKIP'
;
id_replacement [IdType idType] returns [String exp, Type type]
:
  myId=id[idType]
  {
    $exp = $myId.exp;
    $type = $myId.typeAttribute.getType();
  } 
;

id[IdType idType] returns [String exp, TypeAttribute typeAttribute]:
  myId=ID
  {
    TypeAttribute testType;
    $exp = $ID.text;
    boolean isAlreadyDeclared = idType.isAlreadyDeclared();
    //If not declared, check to see it would be valid in namespace or not 
    if (!isAlreadyDeclared) {
	    Map<String, Set<String>> unregisteredNameSpaceMap = getUnregisteredNamespacesMap();
	    if(symbolTableManager.doesNameSpaceConflict(myId.getLine(), idType, $myId.text, unregisteredNameSpaceMap)) {
	      String customMessage = idType.getName() + " \"" + $myId.text + "\" is already in the namespace"; 
	      exceptionHandler.handleException($myId, customMessage, null, null, NameSpaceConflictException.class);
	    }
	    
	    if(idType == IdType.FUNCTION_NAME) {
	      functionNamespace.add($myId.text);
	    }
	    else if(idType == IdType.VARIABLE_DECLARATION ||
	            idType == IdType.FUNCTION_PARAMETER) {
	      varNamespace.add($myId.text);
	    }
	    else if(idType == IdType.USER_DEFINED_TYPE) {
		    typeNamespace.add($myId.text);
	    }
	    
	    TypeAttribute tempTypeAttribute = new TypeAttribute();
      $typeAttribute = tempTypeAttribute;
    } else if(idType == IdType.VARIABLE_TYPE) {
      if(!symbolTableManager.isValidType($myId.text, attributeMap)) {
        String customMessage = "Type " + $myId.text + " is not defined";
        exceptionHandler.handleException(myId, customMessage, null, null, InvalidTypeException.class);
      }
    } else {
	    TypeAttribute attribute = symbolTableManager
	                            .getTypeAttributeInCurrentScope($myId.text, attributeMap);
      $typeAttribute = attribute;
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

COMMENT2 :
  'SKIP_S' ( options {greedy=false;} : . )* 'SKIP_E' {$channel=HIDDEN;}
;

WS :
  ( ' '
  | '\t'
  | '\r'
  | '\n'
  ) {$channel=HIDDEN;}
;
