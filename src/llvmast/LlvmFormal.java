package llvmast;

import syntaxtree.Identifier;
import syntaxtree.Type;

public class LlvmFormal extends LlvmInstruction {
	public LlvmRegister _exp;
	public LlvmType _llvmtype;
	public Type _type;
	public Identifier _name;
	
	public LlvmFormal (LlvmRegister exp, LlvmType llvmtype, Type type, Identifier name) {
		_exp = exp;
		_llvmtype = llvmtype;
		_type = type;
		_name = name;
	}
	
	public String toString() {
		String s = null;
		return s;
	}
	
}

