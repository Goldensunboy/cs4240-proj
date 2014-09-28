grammar Tiger;

tigerProgram :
	typeDeclarationList functDeclarationList mainFunction
;

functDeclarationList :
	(functDeclaration functDeclarationList)?
;

functDeclaration :
	retType 'function' 'id' '('paramList')' 'begin' blockList 'end' ';'
;

mainFunction :
	'void' 'main' '(' ')' 'begin' blockList 'end' ';'
;

retType :
	typeId | 'void'
;

typeId :
	baseType | 'id'
;

baseType :
	'int' | 'fixedpt'
;

param :
	'id' ':' typeId
;

paramList :
	(param paramListTail)?
;

paramListTail :
	(',' param paramListTail)?
;

blockList :
	block+ // block-list and block-tail are combined
;

block :
	'begin' declarationSegment statSeq 'end' ';'
;

typeDeclarationList :
	(typeDeclaration typeDeclarationList)?
;

varDeclarationList :
	(varDeclaration varDeclarationList)?
;

typeDeclaration :
	'type' 'id' '=' type ';'
;

type :
	('array' '[' INTLIT ']'
		('['INTLIT']')? 'of')?
	baseType
;

varDeclaration :
	'var' idList ':' typeId optionalInit ';'
;

idList :
	'id' (',' idList)?
;

optionalInit :
	(':=' const)?
;

statSeq :
	stat+
;

stat :
	(
		value ':=' (expr | 'id' '(' exprList ')') |
		'if' expr 'then' statSeq ('else' statSeq)? 'endif' |
		'while' expr 'do' statSeq 'enddo' |
		'for' 'id' ':=' indexExpr 'to' indexExpr 'do' statSeq 'enddo' |
		'id' '(' exprList ')' |
		'break' |
		'return' expr |
		blockList
	) ';'
; 

optPrefix :
	(value ':=')?
;

expr :
	(const | value | '(' expr ')')
	(binaryOperator (const | value | '(' expr ')'))*
; 

const :
	FIXEDPTLIT |
	INTLIT
;

value :
	'id' valueTail
;

valueTail :
	('[' indexExpr ']'
		('[' indexExpr ']')?
	)?
;

indexExpr :
	(INTLIT | 'id')
	(indexOper (INTLIT | 'id'))*
;

indexOper :
	'+' |
	'-' |
	'*'
;

binaryOperator :
	'+' |
	'-' |
	'*' |
	'/' |
	'=' |
	'<>' |
	'<=' |
	'>=' |
	'<' |
	'>' |
	'&' |
	'|'
; 

declarationSegment :
	typeDeclarationList varDeclarationList
;

exprList :
	(expr exprListTail)?
;

exprListTail :
	(',' expr exprListTail)?
;

INTLIT :
	'0' |
	'1'..'9' ('0'..'9')*
;

FIXEDPTLIT :
	INTLIT '.' ('0'..'9') (('0'..'9')? '0'..'9')?
;
