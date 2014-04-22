package llvmast;

import syntaxtree.Statement;

public class LlvmBlock extends LlvmInstruction{
	public LlvmRegister _exp;
	public LlvmType _type;
	public util.List<Statement> _body;
	
	public LlvmBlock (LlvmRegister exp, LlvmType type, util.List<Statement> body) {
		_exp = exp;
		_type = type;
		_body = body;
	}
	
	public String toString() {
		String s = null;
		return s;
	}

}
