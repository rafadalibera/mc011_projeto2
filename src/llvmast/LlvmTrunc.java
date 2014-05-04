package llvmast;
import  java.util.*;
public class LlvmTrunc extends LlvmInstruction{
    public LlvmValue lhs;
    public LlvmValue source;
    public LlvmType toType;

    public LlvmTrunc(LlvmValue lhs, LlvmValue source, LlvmType toType){
	this.lhs = lhs;
	this.source = source;
	this.toType = toType;
    }
    
    public String toString(){
    	return "  " + lhs + " = trunc " + source.type + " " + source +" to " + toType;
    }

}
