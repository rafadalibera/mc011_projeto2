package llvmast;
public  class LlvmBranch extends LlvmInstruction{
	LlvmLabelValue _label;
	LlvmValue _cond;
	LlvmLabelValue _brTrue;
	LlvmLabelValue _brFalse;
	boolean _simple  = false;

    public LlvmBranch(LlvmLabelValue label){
    	_label = label;
    	_simple = true; 
    }
    
    public LlvmBranch(LlvmValue cond,  LlvmLabelValue brTrue, LlvmLabelValue brFalse){
    	_cond = cond;
    	_brTrue = brTrue;
    	_brFalse = brFalse;

    }

    public String toString(){
    	if (_simple == false) {
    		return "  " + "br i1 " + _cond + ", label %" + _brTrue + ", label %" + _brFalse;
    	} else {
    		return "  " + "br label %" + _label;
    	}
    }
}