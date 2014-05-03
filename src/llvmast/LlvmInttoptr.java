package llvmast;
import  java.util.*;
public class LlvmInttoptr extends LlvmInstruction{
    public LlvmValue lhs;
    public LlvmValue source;
    public LlvmType toType;

    public LlvmInttoptr(LlvmValue lhs, LlvmValue source, LlvmType toType){
	this.lhs = lhs;
	this.source = source;
	this.toType = toType;
    }
    
    public String toString(){
    	return "  " + lhs + " = inttoptr " + source.type + " " + source +" to " + toType;
    }

}
