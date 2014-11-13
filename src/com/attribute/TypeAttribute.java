package com.attribute;

import com.compiler.Type;
import com.exception.ShouldNotHappenException;

public class TypeAttribute implements Attribute{
	private boolean isArray = false;
	private String aliasName;
	private Type type, typeOfArray;
	private ArrayTypeSpecific expectedArrayTypeSpecific;
	private ArrayTypeSpecific receivedArrayTypeSpecific;
	private int dim1;
	private int dim2;
	
	public TypeAttribute() {
		this(null, Type.TEMPORARY);
	}

	public TypeAttribute(String aliasName, Type type) {
		this(aliasName, type, -1, -1);
	}
	
	public TypeAttribute(String aliasName, Type type, int dim1, int dim2) {
		this.aliasName = aliasName;
		this.type = type;
		this.isArray = Type.ARRAY == type;
		this.dim1 = dim1; 
		this.dim2 = dim2;
	}
	
	public int getDim1() {
		return dim1;
	}
	
	public int getDim2() {
		return dim2;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Type getTypeOfArray() {
		return typeOfArray;
	}

	public void setTypeOfArray(Type typeOfArray) {
		this.typeOfArray = typeOfArray;
	}

	public void setReceivedArrayTypeSpecific(ArrayTypeSpecific receivedArrayTypeSpecific) {
		this.receivedArrayTypeSpecific = receivedArrayTypeSpecific;
	}
	
	public void setExpectedArrayTypeSpecific(ArrayTypeSpecific expectedArrayTypeSpecific) {
		this.expectedArrayTypeSpecific = expectedArrayTypeSpecific;
	}

	public ArrayTypeSpecific getReceivedArrayTypeSpecific() {
		return receivedArrayTypeSpecific;
	}
	
	public ArrayTypeSpecific getExpectedArrayTypeSpecific() {
		return expectedArrayTypeSpecific;
	}
	
	public boolean isArray() {
		return isArray;
	}

	@Override
	public String getTypeName() {
		return aliasName;
	}

	public boolean isPrimitive() {
		return aliasName.equals(type.getName());
	}
	
	private boolean hasProperDimension() {
		return expectedArrayTypeSpecific.getDim1() == 
				receivedArrayTypeSpecific.getDim1() && 
				expectedArrayTypeSpecific.getDim2() == 
				receivedArrayTypeSpecific.getDim2();
	}

	private TypeAttribute manipulateArrayType(TypeAttribute typeAttribute) {
		if(typeAttribute == null) {
			return null;
		}
		if(typeAttribute.isArray()) {
			if(typeAttribute.hasProperDimension()) {
				Type secondTypeOfArray = typeAttribute.getTypeOfArray();
				String aliasName = secondTypeOfArray.getName();
				typeAttribute = new TypeAttribute(aliasName ,secondTypeOfArray);
			} else if(typeAttribute.getReceivedArrayTypeSpecific().hasDimension()) {
				//System.out.println(typeAttribute);
				return null;
			}
		}
		return typeAttribute;
	}
	
	private boolean assignability(TypeAttribute secondTypeAttribute) {
		// If RHS or LHS is user defined, but not both. RHS is not assignable to LHS
		if(this.isPrimitive() == !secondTypeAttribute.isPrimitive()) {
			return false;
		}
		
		// If they are both user defined types, all matters is the alias names
		if(!this.isPrimitive() && !secondTypeAttribute.isPrimitive()) {
			return aliasName.equals(secondTypeAttribute.aliasName);
		}
		
		// If they are both primitives 
		if(this.isPrimitive() && secondTypeAttribute.isPrimitive()) {
			// make sure INT is assigned to INT. Don't accept FIXPT on RHS for an INT LHS
			if(type == Type.INT) {
				return secondTypeAttribute.type == Type.INT;
			} 
			// If not INT, it should be FIXPT. Therefore both INT and FIXPT is legal for LHS
			return true;
		}
		
		
		throw new ShouldNotHappenException(
				"Didn't consider an edge case for assignments");
	}
	
	public boolean doReturnValuesMatch(TypeAttribute secondTypeAttribute) {
		secondTypeAttribute = manipulateArrayType(secondTypeAttribute);
	      
		
		if(secondTypeAttribute == null) {
			return false;
		}
		
		if(isArray) {
			return aliasName.equals(secondTypeAttribute.getAliasName());
		}
		
		return assignability(secondTypeAttribute);
	}
	
	public boolean isProperParameter(TypeAttribute secondTypeAttribute) {
		
		if(secondTypeAttribute == null) {
			return false;
		}
		
		if(isArray && secondTypeAttribute.isArray() && !hasProperDimension() && !secondTypeAttribute.hasProperDimension()) {
			if(getExpectedArrayTypeSpecific().getDim1() == secondTypeAttribute.getExpectedArrayTypeSpecific().getDim1()  && 
					getExpectedArrayTypeSpecific().getDim2() == secondTypeAttribute.getExpectedArrayTypeSpecific().getDim2() ){
				return true;
			}
		}
				
		return assignableBy(secondTypeAttribute);
	}

	public boolean assignableBy(TypeAttribute secondTypeAttribute) {
		secondTypeAttribute = manipulateArrayType(secondTypeAttribute);
		
		if(secondTypeAttribute == null) {
			return false;
		}
		
		if(isArray) {

			if(hasProperDimension()) {

				// can't assign non-primitive to arrays
				if(!secondTypeAttribute.isPrimitive()) {
					return false;
				}
				
				if(typeOfArray == Type.FIXPT) {
					return true;
				}
				
				return typeOfArray == secondTypeAttribute.getType();
			} else {
				return false;
			}
		}
		
		return assignability(secondTypeAttribute);
	}
	
	public boolean canBeInitializedBy(TypeAttribute secondTypeAttribute) {
		
		if(isArray) {
			// can't assign an array to another array
			if(!secondTypeAttribute.isPrimitive()) {
				return false;
			}
			// if array type is fixed point, we can accept fixedpt or int
			if(typeOfArray == Type.FIXPT) {
				return true;
			} 
			// if array type is int, then we only can assign int to it
			if (typeOfArray == Type.INT) {
				return secondTypeAttribute.type == Type.INT;				
			}
			
			throw new ShouldNotHappenException(
					"Didn't consider an array assignment edge case");
		}
		
		// If they are both user defined types, all matters is the alias names
		if(!this.isPrimitive() && !secondTypeAttribute.isPrimitive()) {
			return aliasName.equals(secondTypeAttribute.aliasName);
		}
		
		// If RHS is user defined, and LHS is not
		if(!secondTypeAttribute.isPrimitive()) {
			return false;
		}
		
		
		/* If LHS is user defined
		* 
		* TODO can combine this if and else statement. But keep it for now,
		* I feel like I'm missing something
		*/
		if(!this.isPrimitive()) {
			
			// if it is int, only int primitive would be accepted on RHS
			if(type == Type.INT) {
				return secondTypeAttribute.type == Type.INT;
			}
			
			// if it is fixedpt, both int and fixedpt primitives are acceptable on RHS
			else if (type == Type.FIXPT) {
				return true;
			}
			
			throw new ShouldNotHappenException(
					"Didn't consider an edge case for assignment of user defined types");
			
		} 
		
		// If type is primitive
		else {
			// if type is int only int is acceptable on RHS
			if(type == Type.INT) {
				return secondTypeAttribute.type == Type.INT;
			}
			// If type is fixed point any primitive is acceptable on LHS
			else if(type == Type.FIXPT) {
				return true; 
			}
			throw new ShouldNotHappenException(
					"Didn't consider an edge case for assignment of primitives");
		}
	}
	
	public boolean canBeInOperationWith(TypeAttribute secondTypeAttribute) {
		
		// can't have an array in an operation
		if(isArray || secondTypeAttribute.isArray) {
			return false;
		}
		
		// If they are both user defined types, all matters is the alias names
		if(!this.isPrimitive() && !secondTypeAttribute.isPrimitive()) {
			return aliasName.equals(secondTypeAttribute.aliasName);
		}
		
		// if one is primitive and the other not 
		if(this.isPrimitive()==!secondTypeAttribute.isPrimitive()) {
			return false;
		}
		
		// If types are primitives
		return true;
	}
	
	public String toString() {
		String retVal = "aliasName: " + aliasName;
		retVal += ", type: " + type;
		retVal += ", isArray: " + isArray;
		retVal += isArray ? ", typeOfArray: " + typeOfArray : "";
		if(expectedArrayTypeSpecific != null) {
			retVal += ", Expected Array Type Specific";
			retVal += ", isTwoDimensionalArray: " + expectedArrayTypeSpecific.isTwoDimensionalArray();
			retVal += ", dim1: " + expectedArrayTypeSpecific.getDim1();
			retVal += ", dim2: " + expectedArrayTypeSpecific.getDim2();			
		}
		if(receivedArrayTypeSpecific != null) {
			retVal += ", Received Array Type Specific";
			retVal += ", isTwoDimensionalArray: " + receivedArrayTypeSpecific.isTwoDimensionalArray();
			retVal += ", dim1: " + receivedArrayTypeSpecific.getDim1();
			retVal += ", dim2: " + receivedArrayTypeSpecific.getDim2();
		}
		
		return retVal;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TypeAttribute typeAttribute = new TypeAttribute(aliasName, type);
		typeAttribute.setExpectedArrayTypeSpecific(expectedArrayTypeSpecific);
		typeAttribute.setReceivedArrayTypeSpecific(receivedArrayTypeSpecific);
		typeAttribute.setTypeOfArray(typeOfArray);
		return typeAttribute;
	}

	@Override
	public int getScopeId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Dereference an array type to its contained type
	 */
	public void dereference() {
		if(!isArray) {
			throw new ShouldNotHappenException("Dereferenced a non-array type");
		} else {
			isArray = false;
			dim1 = dim2 = -1;
			type = typeOfArray;
			typeOfArray = null;
			expectedArrayTypeSpecific = receivedArrayTypeSpecific = null;
			aliasName = type.getName();
		}
	}
}
