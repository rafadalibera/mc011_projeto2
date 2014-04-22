/*****************************************************
Esta classe Codegen ������ a respons������vel por emitir LLVM-IR. 
Ela possui o mesmo m������todo 'visit' sobrecarregado de
acordo com o tipo do par������metro. Se o par������mentro for
do tipo 'While', o 'visit' emitir������ c������digo LLVM-IR que 
representa este comportamento. 
Alguns m������todos 'visit' j������ est������o prontos e, por isso,
a compila������������o do c������digo abaixo j������ ������ poss������vel.

class a{
    public static void main(String[] args){
    	System.out.println(1+2);
    }
}

O pacote 'llvmast' possui estruturas simples 
que auxiliam a gera������������o de c������digo em LLVM-IR. Quase todas 
as classes est������o prontas; apenas as seguintes precisam ser 
implementadas: 

// llvmasm/LlvmBranch.java
// llvmasm/LlvmIcmp.java
// llvmasm/LlvmMinus.java
// llvmasm/LlvmTimes.java


Todas as assinaturas de m������todos e construtores 
necess������rias j������ est������o l������. 


Observem todos os m������todos e classes j������ implementados
e o manual do LLVM-IR (http://llvm.org/docs/LangRef.html) 
como guia no desenvolvimento deste projeto. 

****************************************************/
package llvm;

import semant.Env;
import syntaxtree.*;
import llvmast.*;

import java.util.*;

public class Codegen extends VisitorAdapter{
	private List<LlvmInstruction> assembler;
	private Codegen codeGenerator;

  	private SymTab symTab;
	private ClassNode classEnv; 	// Aponta para a classe atualmente em uso em symTab
	private MethodNode methodEnv; 	// Aponta para a metodo atualmente em uso em symTab


	public Codegen(){
		assembler = new LinkedList<LlvmInstruction>();
	}

	// M������todo de entrada do Codegen
	public String translate(Program p, Env env){	
		codeGenerator = new Codegen();
		
		// Preenchendo a Tabela de S������mbolos
		// Quem quiser usar 'env', apenas comente essa linha
		// codeGenerator.symTab.FillTabSymbol(p);
		
		// Formato da String para o System.out.printlnijava "%d\n"
		codeGenerator.assembler.add(new LlvmConstantDeclaration("@.formatting.string", "private constant [4 x i8] c\"%d\\0A\\00\""));	

		// NOTA: sempre que X.accept(Y), ent������o Y.visit(X);
		// NOTA: Logo, o comando abaixo ir������ chamar codeGenerator.visit(Program), linha 75
		p.accept(codeGenerator);

		// Link do printf
		List<LlvmType> pts = new LinkedList<LlvmType>();
		pts.add(new LlvmPointer(LlvmPrimitiveType.I8));
		pts.add(LlvmPrimitiveType.DOTDOTDOT);
		codeGenerator.assembler.add(new LlvmExternalDeclaration("@printf", LlvmPrimitiveType.I32, pts)); 
		List<LlvmType> mallocpts = new LinkedList<LlvmType>();
		mallocpts.add(LlvmPrimitiveType.I32);
		codeGenerator.assembler.add(new LlvmExternalDeclaration("@malloc", new LlvmPointer(LlvmPrimitiveType.I8),mallocpts)); 


		String r = new String();
		for(LlvmInstruction instr : codeGenerator.assembler)
			r += instr+"\n";
		return r;
	}

	public LlvmValue visit(Program n){
		n.mainClass.accept(this);

		for (util.List<ClassDecl> c = n.classList; c != null; c = c.tail)
			c.head.accept(this);

		return null;
	}
//testando
//testando2
	public LlvmValue visit(MainClass n){
		
		// definicao do main 
		assembler.add(new LlvmDefine("@main", LlvmPrimitiveType.I32, new LinkedList<LlvmValue>()));
		assembler.add(new LlvmLabel(new LlvmLabelValue("entry")));
		LlvmRegister R1 = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		assembler.add(new LlvmAlloca(R1, LlvmPrimitiveType.I32, new LinkedList<LlvmValue>()));
		assembler.add(new LlvmStore(new LlvmIntegerLiteral(0), R1));

		// Statement ������ uma classe abstrata
		// Portanto, o accept chamado ������ da classe que implementa Statement, por exemplo,  a classe "Print". 
		n.stm.accept(this);  

		// Final do Main
		LlvmRegister R2 = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmLoad(R2,R1));
		assembler.add(new LlvmRet(R2));
		assembler.add(new LlvmCloseDefinition());
		return null;
	}
	
	public LlvmValue visit(Plus n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmPlus(lhs,LlvmPrimitiveType.I32,v1,v2));
		return lhs;
	}
	
	public LlvmValue visit(Print n){

		LlvmValue v =  n.exp.accept(this);

		// getelementptr:
		LlvmRegister lhs = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I8));
		LlvmRegister src = new LlvmNamedValue("@.formatting.string",new LlvmPointer(new LlvmArray(4,LlvmPrimitiveType.I8)));
		List<LlvmValue> offsets = new LinkedList<LlvmValue>();
		offsets.add(new LlvmIntegerLiteral(0));
		offsets.add(new LlvmIntegerLiteral(0));
		List<LlvmType> pts = new LinkedList<LlvmType>();
		pts.add(new LlvmPointer(LlvmPrimitiveType.I8));
		List<LlvmValue> args = new LinkedList<LlvmValue>();
		args.add(lhs);
		args.add(v);
		assembler.add(new LlvmGetElementPointer(lhs,src,offsets));

		pts = new LinkedList<LlvmType>();
		pts.add(new LlvmPointer(LlvmPrimitiveType.I8));
		pts.add(LlvmPrimitiveType.DOTDOTDOT);
		
		// printf:
		assembler.add(new LlvmCall(new LlvmRegister(LlvmPrimitiveType.I32),
				LlvmPrimitiveType.I32,
				pts,				 
				"@printf",
				args
				));
		return null;
	}
	
	public LlvmValue visit(IntegerLiteral n){
		return new LlvmIntegerLiteral(n.value);
	};
	
	// Todos os visit's que devem ser implementados
	
	//Function CLASSDECLSIMPLE:
	public LlvmValue visit(ClassDeclSimple n){
		util.List<MethodDecl> methodList = n.methodList;
		util.List<VarDecl> varList = n.varList;
		Identifier name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassDeclSimple(exp,LlvmPrimitiveType.I32,name, varList, methodList));
		return exp;
	}
	//Function CLASSDECLEXTENDS:
	public LlvmValue visit(ClassDeclExtends n){
		Identifier superClass = n.superClass;
		Identifier name = n.name;
		util.List<VarDecl> varList = n.varList;
		util.List<MethodDecl> methodList = n.methodList;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassDeclExtends(exp,LlvmPrimitiveType.I32, superClass, name, varList, methodList));
		return exp;		
	}
	//Function VARDECL:
	public LlvmValue visit(VarDecl n){
		LlvmValue type = n.type.accept(this);
		Identifier name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassVarDecl(exp,LlvmPrimitiveType.I32, type, name));
		return exp;
	}
	//Function METHODDECL:
	public LlvmValue visit(MethodDecl n){
		LlvmValue returnType = n.returnType.accept(this);
		Identifier name = n.name;
		Exp returnExp = n.returnExp;
		util.List<Formal> formals = n.formals;
		util.List<Statement> body = n.body;
		util.List<VarDecl> locals = n.locals;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassMethodDecl(exp,LlvmPrimitiveType.I32, returnType, name, formals, locals, body, returnExp));
		return exp;
	}
	//Function FORMAL:
	public LlvmValue visit(Formal n){
		LlvmValue type = n.type.accept(this);
		Identifier name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassFormal(exp,LlvmPrimitiveType.I32, type, name));
		return exp;
	}
	//Function INTARRAYTYPE:
	public LlvmValue visit(IntArrayType n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIntArrayType(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function BOOLEANTYPE:
	public LlvmValue visit(BooleanType n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmBooleanType(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function INTEGERTYPE:
	public LlvmValue visit(IntegerType n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIntegerType(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function IDENTIFIERTYPE:
	public LlvmValue visit(IdentifierType n){
		String name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIdentifierType(exp,LlvmPrimitiveType.I32, name));
		return exp;
	}
	//Function BLOCK:
	public LlvmValue visit(Block n){
		util.List<Statement> body = n.body;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmBlock(exp,LlvmPrimitiveType.I32, body));
		return exp;
	}
	//Function IF:
	public LlvmValue visit(If n){
		Exp condition = n.condition;
		Statement thenClause = n.thenClause;
		Statement elseClause = n.elseClause;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIf(exp,LlvmPrimitiveType.I32, condition, thenClause, elseClause));
		//assembler.add(new LlvmIf(exp,LlvmPrimitiveType.I32, condition, thenClause));
		return exp;
	}
	//Function WHILE:
	public LlvmValue visit(While n){
		Exp condition = n.condition;
		Statement body = n.body;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmWhile(exp,LlvmPrimitiveType.I32, condition, body));
		return exp;
	}
	//Function ASSIGN:
	public LlvmValue visit(Assign n){
		Identifier var = n.var;
		Exp exp = n.exp;
		LlvmRegister exp2 = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmAssign(exp,LlvmPrimitiveType.I32, var, exp));
		return exp2;
	}
	//Function ARRAYASSIGN
	public LlvmValue visit(ArrayAssign n){
		Identifier var = n.var;
		Exp index = n.index;
		Exp value = n.value;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayAssign(exp,LlvmPrimitiveType.I32, var, index, value));
		return exp;
	}
	// Function AND:
	public LlvmValue visit(And n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmAnd(lhs,LlvmPrimitiveType.I32,v1,v2));
		return lhs;		
	}
	//Function LESSTHAN:
	public LlvmValue visit(LessThan n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmLessThan(lhs,LlvmPrimitiveType.I32,v1,v2));
		return lhs;
	}
	//Function EQUAL:
	public LlvmValue visit(Equal n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmEqual(lhs,LlvmPrimitiveType.I32,v1,v2));
		return lhs;
	}
	//Function MINUS:
	public LlvmValue visit(Minus n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmMinus(lhs,LlvmPrimitiveType.I32,v1,v2));
		return lhs;	
	}
	//Function TIMES:
	public LlvmValue visit(Times n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmTimes(lhs, LlvmPrimitiveType.I32, v1, v2));
		return lhs;
	}
	//Function ARRAYLOOKUP:
	public LlvmValue visit(ArrayLookup n){
		Exp array = n.array;
		Exp index = n.index;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayLookup(exp, LlvmPrimitiveType.I32, array, index));
		return exp;
	}
	//Function ARRAYLENGTH:
	public LlvmValue visit(ArrayLength n){
		Exp array = n.array;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayLength(exp, LlvmPrimitiveType.I32, array));
		return exp;
	}
	//Function CALL:
	public LlvmValue visit(Call n){
		Exp object = n.object;
		Identifier method = n.method;
		util.List<Exp> actuals = n.actuals;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmCall(exp, LlvmPrimitiveType.I32, object, method, actuals));
		return exp;
	}
	//Function TRUE:
	public LlvmValue visit(True n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmTrue(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function FALSE:
	public LlvmValue visit(False n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmFalse(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function IDENTIFIEREXP
	public LlvmValue visit(IdentifierExp n){
		Identifier name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIdentifierExp(exp,LlvmPrimitiveType.I32, name));
		return exp;
	}
	//Function THIS:
	public LlvmValue visit(This n){
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmThis(exp,LlvmPrimitiveType.I32));
		return exp;
	}
	//Function NEWARRAY:
	public LlvmValue visit(NewArray n){
		Exp size = n.size;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmNewArray(exp,LlvmPrimitiveType.I32, size));
		return exp;
	}
	//Function NEWOBJECT:
	public LlvmValue visit(NewObject n){
		Identifier className = n.className;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmNewObject(exp,LlvmPrimitiveType.I32, className));
		return exp;
	}
	//Function NOT:
	public LlvmValue visit(Not n) {
		LlvmValue v = n.exp.accept(this);
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmNot(exp,LlvmPrimitiveType.I32,v));
		return exp;
	}
	//Function IDENTIFIER:
	public LlvmValue visit(Identifier n){
		String s = n.s;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIdentifier(exp,LlvmPrimitiveType.I32, s));
		return exp;
	}
}


/**********************************************************************************/
/* === Tabela de S������mbolos ==== 
 * 
 * 
 */
/**********************************************************************************/

class SymTab extends VisitorAdapter{
    public Map<String, ClassNode> classes;     
    private ClassNode classEnv;    //aponta para a classe em uso

    public LlvmValue FillTabSymbol(Program n){
	n.accept(this);
	return null;
	}
	public LlvmValue visit(Program n){
		n.mainClass.accept(this);
	
		for (util.List<ClassDecl> c = n.classList; c != null; c = c.tail)
			c.head.accept(this);
	
		return null;
	}
	
	public LlvmValue visit(MainClass n){
		classes.put(n.className.s, new ClassNode(n.className.s, null, null));
		return null;
	}
	
	public LlvmValue visit(ClassDeclSimple n){
		List<LlvmType> typeList = null;
		// Constroi TypeList com os tipos das vari������veis da Classe (vai formar a Struct da classe)
		
		List<LlvmValue> varList = null;
		// Constroi VarList com as Vari������veis da Classe
	
		classes.put(n.name.s, new ClassNode(n.name.s, 
											new LlvmStructure(typeList), 
											varList)
	      			);
	    	// Percorre n.methodList visitando cada m������todo
		return null;
	}
	
		public LlvmValue visit(ClassDeclExtends n){return null;}
		public LlvmValue visit(VarDecl n){return null;}
		public LlvmValue visit(Formal n){return null;}
		public LlvmValue visit(MethodDecl n){return null;}
		public LlvmValue visit(IdentifierType n){return null;}
		public LlvmValue visit(IntArrayType n){return null;}
		public LlvmValue visit(BooleanType n){return null;}
		public LlvmValue visit(IntegerType n){return null;}
}

class ClassNode extends LlvmType {
	ClassNode (String nameClass, LlvmStructure classType, List<LlvmValue> varList){
	}
}

class MethodNode {
}




