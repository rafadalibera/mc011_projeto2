package llvmast;
public  class LlvmIcmp extends LlvmInstruction{
	LlvmRegister _lhs;
	int _conditionCode;
	LlvmType _type; 
	LlvmValue _op1; 
	LlvmValue _op2;
    
    public LlvmIcmp(LlvmRegister lhs,  int conditionCode, LlvmType type, LlvmValue op1, LlvmValue op2){
    	_lhs = lhs;
    	_conditionCode = conditionCode;
    	_type = type;
    	_op1 = op1;
    	_op2 = op2;
    }

    public String toString(){
    	switch(_conditionCode) {
    	//and
    	case 0:
    		return "  " +_lhs + " = and " + _type + " " + _op1 + ", " + _op2;
    	//equal
    	case 1:
    		return "  " +_lhs + " = icmp eq " + _type + " " + _op1 + ", " + _op2;
    	//lessThan
    	case 2:
    		return "  " +_lhs + " = icmp slt " + _type + " " + _op1 + ", " + _op2;
    	//not
    	case 3:
    		return "  " + _lhs + " =  xor " + _op1 + ", under";
    	default:
    		return null;
    	}
    }
}