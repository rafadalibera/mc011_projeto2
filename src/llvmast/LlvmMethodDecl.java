package llvmast;

import syntaxtree.Exp;
import syntaxtree.Formal;
import syntaxtree.Identifier;
import syntaxtree.Statement;
import syntaxtree.VarDecl;

public class LlvmMethodDecl extends LlvmInstruction{
	public LlvmRegister _exp;
	public LlvmType _type;
	public LlvmValue _returnType;
	public Identifier _name;
	public Exp _returnExp;
	public util.List<Formal> _formals;
	public util.List<Statement> _body;
	public util.List<VarDecl> _locals;
	
	public LlvmMethodDecl (LlvmRegister exp, LlvmType type, LlvmValue returnType, 
			Identifier name, util.List<Formal> formals, util.List<VarDecl> locals,
			util.List<Statement> body, Exp returnExp) {
		_exp = exp;
		_type = type;
		_returnType = returnType;
		_name = name;
		_returnExp = returnExp;
		_formals = formals;
		_body = body;
		_locals = locals;
	}
	
	public String toString() {
		String s = "";
		return s;
	}
}
