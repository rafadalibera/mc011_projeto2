package llvmast;

import syntaxtree.Identifier;
import syntaxtree.Type;

public class LlvmVarDecl extends LlvmInstruction {
	public LlvmRegister _exp;
	public LlvmType _llvmtype;
	public Type _type;
	public Identifier _name;
	
	public LlvmVarDecl (LlvmRegister exp, LlvmType llvmtype, Type type, Identifier name) {
		_exp = exp;
		_llvmtype = llvmtype;
		_type = type;
		_name = name;
		
	}
	
	public String toString() {
		String s = "";
		
		s = "%" + _name + "= alloca " + _llvmtype;
		
		return s;
	}

}
