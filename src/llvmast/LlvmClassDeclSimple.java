package llvmast;

import syntaxtree.Identifier;
import syntaxtree.MethodDecl;
import syntaxtree.VarDecl;
import util.List;

public class LlvmClassDeclSimple extends LlvmInstruction{
	public LlvmRegister _exp;
	public LlvmType _type;
	public util.List<MethodDecl> _methodList;
	public util.List<VarDecl> _varList;
	public Identifier _name;
	
	public LlvmClassDeclSimple(LlvmRegister exp, LlvmType type, Identifier name, List<VarDecl> varList,
			List<MethodDecl> methodList) {
		_exp = exp;
		_type = type;
		_methodList = methodList;
		_varList = varList;
		_name = name;	
	}
	
	public String toString() {
		String s = "%" + _name + " = type { " + _type + " }";
		/*
		for (int i = 0; i < _methodList.size(); i++) {
			System.out.println(_methodList.get(i));
		}
		//s = s + 
		return " " + _lhs + " = mul " + _type + " " + _op1 + ", " + _op2;
		*/
		return s;
    }

}
