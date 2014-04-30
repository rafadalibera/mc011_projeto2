package llvmast;
public  class LlvmTimes extends LlvmInstruction{
	public LlvmRegister _lhs;
	public LlvmType _type;
	public LlvmValue _op1;
	public LlvmValue _op2;
	
	
    public LlvmTimes(LlvmRegister lhs, LlvmType type, LlvmValue op1, LlvmValue op2){
    	_lhs = lhs;
    	_type = type;
    	_op1 = op1;
    	_op2 = op2;
    }

    public String toString(){
		return "  " + _lhs + " = mul " + _type + " " + _op1 + ", " + _op2;
    }
}