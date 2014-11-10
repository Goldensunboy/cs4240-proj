package com.attribute;

import com.compiler.Type;
import com.exception.ShouldNotHappenException;

public class TypeAttribute implements Attribute{
	private boolean isArray = false;
	private String aliasName;
	private Type type, typeOfArray;
	private ArrayTypeSpecific arrayTypeSpecific;
	
	public TypeAttribute() {
		type = Type.TEMPORARY;
	}
	
	public TypeAttribute(String aliasName, Type type, Type typeOfArray, 
			ArrayTypeSpecific arrayTypeSpecific) {
		this.aliasName = aliasName;
		this.type = type;
		this.isArray = type == Type.ARRAY;
		this.typeOfArray = typeOfArray;
		this.arrayTypeSpecific = arrayTypeSpecific;
	}
	
	public boolean isTwoDimensionalArray() {
		return arrayTypeSpecific == null  ? false : 
			arrayTypeSpecific.isTwoDimensionalArray();
	}

	public Type getType() {
		return type;
	}

	public int getDim1() {
		return arrayTypeSpecific == null ? -1 : arrayTypeSpecific.getDim1();
	}

	public void setDim1(int dim1) {
		arrayTypeSpecific.setDim1(dim1);
	}

	public int getDim2() {
		return arrayTypeSpecific == null ? -1 : arrayTypeSpecific.getDim2();
	}

	public void setDim2(int dim2) {
		arrayTypeSpecific.setDim2(dim2);
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
	
	public boolean isArray() {
		return isArray;
	}
	
	public String toString() {
		String retVal = "aliasName: " + aliasName;
		retVal += ", type: " + type;
		retVal += ", isArray: " + isArray;
		if(arrayTypeSpecific != null) {
			retVal += ", isTwoDimensionalArray: " + arrayTypeSpecific.isTwoDimensionalArray();
			retVal += ", typeOfArray: " + typeOfArray;
			retVal += ", dim1: " + arrayTypeSpecific.getDim1();
			retVal += ", dim2: " + arrayTypeSpecific.getDim2();			
		}
		
		return retVal;
	}

	@Override
	public String getTypeName() {
		return aliasName;
	}

	public boolean isPrimitive() {
		return aliasName.equals(type.getName());
	}
	
	public boolean assignableBy(TypeAttribute secondTypeAttribute, ArrayTypeSpecific secondArrayTypeSpecific) {

		// assigning to array is not legal
		if(isArray && arrayTypeSpecific == null) {
			return false;
		}
		// assigning to the elements of an array is legal
		else if(isArray && arrayTypeSpecific != null){
			if(!secondTypeAttribute.isPrimitive()) {
				return false;
			}
			// check for use of dimension. e.g. if 2D and is using only 1D of it, it's illegal
			if(isTwoDimensionalArray() != arrayTypeSpecific.isTwoDimensionalArray()) {
				return false;
			}
			return typeOfArray == secondTypeAttribute.getType();
		}
		
		// TODO make sure return myArray and assign myArray = myArray2 is taken care of
		if(secondTypeAttribute.isArray) {
			// can't assign something to an array
			if(secondArrayTypeSpecific == null) {
				return false;
			}
			
			if (secondTypeAttribute.isTwoDimensionalArray() != 
					secondArrayTypeSpecific.isTwoDimensionalArray()) {
				return false;
			}
			
			return type == secondTypeAttribute.getTypeOfArray();
		}

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
}