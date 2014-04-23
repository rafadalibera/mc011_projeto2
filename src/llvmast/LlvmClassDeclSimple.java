package llvmast;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import syntaxtree.Identifier;
import syntaxtree.MethodDecl;
import syntaxtree.VarDecl;

import util.List;

public class LlvmClassDeclSimple extends LlvmInstruction{
	public LlvmRegister _exp;
	public LlvmType _type;
	public List<MethodDecl> _methodList;
	public List<VarDecl> _varList;
	public Identifier _name;
	
	public LlvmClassDeclSimple(LlvmRegister exp, LlvmType type, Identifier name, List<VarDecl> varList,
			List<MethodDecl> methodList) {
		_exp = exp;
		_type = type;
		_methodList = methodList;
		_varList = varList.it
		_name = name;	
	}
	
	public String toString() {
		
		
		String variaveis = "";
		for(VarDecl v : _varList){
			variaveis += v.toString() + " ";
		}
		
		String s = "%" + _name + " = type { " + variaveis + " }";
		
		return s;
    }

}
