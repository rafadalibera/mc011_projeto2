package llvmast;
import  java.util.*;
public class LlvmZext extends LlvmInstruction{
    public LlvmValue lhs;
    public LlvmValue source;
    public LlvmType toType;

    public LlvmZext(LlvmValue lhs, LlvmValue source, LlvmType toType){
	this.lhs = lhs;
	this.source = source;
	this.toType = toType;
    }
    
    public String toString(){
    	return "  " + lhs + " = zext " + source.type + " " + source +" to " + toType;
    }

}
