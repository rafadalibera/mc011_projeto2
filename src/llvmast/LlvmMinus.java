package llvmast;

public  class LlvmMinus extends LlvmInstruction{
    public LlvmRegister _lhs;
    public LlvmType _type;
    public LlvmValue _op1, _op2;
    
	public LlvmMinus(LlvmRegister lhs, LlvmType type, LlvmValue op1, LlvmValue op2){
		_lhs = lhs;
		_op1 = op1;
		_op2 = op2;
		_type = type;
    }

    public String toString(){
    	return "  " + _lhs + " = sub " + _type + " " + _op1 + ", " + _op2;
    }
}
