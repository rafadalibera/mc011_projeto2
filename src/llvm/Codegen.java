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

import java.sql.Time;
import java.util.*;

public class Codegen extends VisitorAdapter{
	private List<LlvmInstruction> assembler;
	private Codegen codeGenerator;

  	private SymTab symTab = new SymTab();
	private ClassNode classEnv; 	// Aponta para a classe atualmente em uso em symTab
	private MethodNode methodEnv; 	// Aponta para a metodo atualmente em uso em symTab

	private int indexLabel = -1;
	
	public int getIndexLabel(){
		
		return indexLabel + 1;
	}

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
		symTab.FillTabSymbol(n);
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
		
		symTab.SetClassInUse(n.name.s);
		
		assembler.add(new LlvmConstantDeclaration("%class."+n.name.s, 
				new LlvmStructure(symTab.GetClassInUse()._classStructure.typeList).toString()));

		for (util.List<MethodDecl> m = n.methodList; m != null; m = m.tail){
			m.head.accept(this);
		}
		
		return null;

	}
	//Function CLASSDECLEXTENDS:
	public LlvmValue visit(ClassDeclExtends n){
		/*
		Identifier superClass = n.superClass;
		Identifier name = n.name;
		util.List<VarDecl> varList = n.varList;
		util.List<MethodDecl> methodList = n.methodList;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmClassDeclExtends(exp,LlvmPrimitiveType.I32, superClass, name, varList, methodList));
		return exp;		
		*/
		return null;
	}
	//Function VARDECL:
	public LlvmValue visit(VarDecl n){
		assembler.add(new LlvmAlloca(n.name.accept(this), n.type.accept(this).type, new LinkedList<LlvmValue>()));
		return null;
	}
	//Function METHODDECL:
	public LlvmValue visit(MethodDecl n){
		symTab.SetMethodInUse(n.name.s);
		List<LlvmValue> listaArgs = new ArrayList<LlvmValue>();
		
		listaArgs.add(new LlvmNamedValue("%this", new LlvmPointer(symTab.GetClassInUse())));
		
		for (util.List<Formal> c = n.formals; c != null; c = c.tail)
		{
		
				listaArgs.add(c.head.accept(this));
			
		}
		LlvmInstruction definition = new LlvmDefine("@__" + n.name.toString() + "_" + symTab.GetClassInUse()._name , n.returnType.accept(this).type, listaArgs);
		assembler.add(definition);
		
		boolean vai = false;
		for(LlvmValue v : listaArgs){
			if(vai){
				LlvmRegister addr = new LlvmRegister(v.type);
				assembler.add(new LlvmAlloca(addr, v.type, new LinkedList<LlvmValue>()));
				assembler.add(new LlvmStore(v, addr));
			}
			vai = true;
		}
		
		for(util.List<VarDecl> v = n.locals; v != null; v = v.tail){
			v.head.accept(this);
		}
		
		for(util.List<Statement> s = n.body; s != null; s = s.tail){
			s.head.accept(this);
		}
		
		assembler.add(new LlvmRet(n.returnExp.accept(this)));
		
		return null;
	}
	//Function FORMAL:
	public LlvmValue visit(Formal n){
		LlvmValue addr = new LlvmNamedValue(n.name.toString(), n.type.accept(this).type);
		//assembler.add(new LlvmAlloca(addr, n.type.accept(this).type, new LinkedList<LlvmValue>()));
		return addr;
	}
	//Function INTARRAYTYPE:
	public LlvmValue visit(IntArrayType n){
		//return new LlvmNamedValue("", LlvmPrimitiveType.I32);
		return null;
	}
	//Function BOOLEANTYPE:
	public LlvmValue visit(BooleanType n){
		return new LlvmNamedValue("", LlvmPrimitiveType.I1);
	}
	//Function INTEGERTYPE:
	public LlvmValue visit(IntegerType n){
		return new LlvmNamedValue("", LlvmPrimitiveType.I32);
	}
	//Function IDENTIFIERTYPE:
	public LlvmValue visit(IdentifierType n){
		return new LlvmNamedValue(n.name, LlvmPrimitiveType.I32);
	}
	//Function BLOCK:
	public LlvmValue visit(Block n){
		
		for(util.List<Statement> s = n.body; s != null; s = s.tail){
			s.head.accept(this);
		}		

		return null;
	}
	//Function IF:
	public LlvmValue visit(If n){
		//LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		
		LlvmLabelValue labelThan = (new LlvmLabelValue("label" + getIndexLabel()));
		LlvmLabelValue labelElse = (new LlvmLabelValue("label" + getIndexLabel()));
		LlvmLabelValue labelEnd = (new LlvmLabelValue("label" + getIndexLabel()));
		
		assembler.add(new LlvmBranch(n.condition.accept(this), labelThan, labelElse));
		
		assembler.add(new LlvmLabel(labelThan));
		n.thenClause.accept(this);
		assembler.add(new LlvmBranch(labelEnd));
		
		if (n.elseClause != null) {
			assembler.add(new LlvmLabel(labelEnd));
			n.elseClause.accept(this);
			assembler.add(new LlvmBranch(labelEnd));
		}
		
		assembler.add(new LlvmLabel(labelEnd));
		
		return null;
	}
	//Function WHILE:
	public LlvmValue visit(While n){
		
		LlvmLabelValue labelLoop = (new LlvmLabelValue("label" + getIndexLabel()));
		LlvmLabelValue labelBegin = (new LlvmLabelValue("label" + getIndexLabel()));
		LlvmLabelValue labelEnd = (new LlvmLabelValue("label" + getIndexLabel()));
		
		assembler.add(new LlvmLabel(labelLoop));
		
		assembler.add(new LlvmBranch(n.condition.accept(this), labelBegin, labelEnd));
		n.body.accept(this);
		assembler.add(new LlvmBranch(labelLoop));
		
		assembler.add(new LlvmLabel(labelEnd));

		return null;
	}
	//Function ASSIGN:
	public LlvmValue visit(Assign n){
		assembler.add(new LlvmStore(n.exp.accept(this), n.var.accept(this)));
		return null;
	}
	//Function ARRAYASSIGN
	public LlvmValue visit(ArrayAssign n){
		
		/*
		Identifier var = n.var;
		Exp index = n.index;
		Exp value = n.value;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayAssign(exp,LlvmPrimitiveType.I32, var, index, value));
		return exp;
		*/
		return null;
	}
	// Function AND:
	public LlvmValue visit(And n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmIcmp(lhs, 0, LlvmPrimitiveType.I1,  v1, v2));

		return lhs;	
	}
	//Function LESSTHAN:
	public LlvmValue visit(LessThan n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmIcmp(lhs, 2, LlvmPrimitiveType.I32,  v1, v2));

		return lhs;
	}
	//Function EQUAL:
	public LlvmValue visit(Equal n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmIcmp(lhs, 1, LlvmPrimitiveType.I32,  v1, v2));

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
		/*
		Exp array = n.array;
		Exp index = n.index;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayLookup(exp, LlvmPrimitiveType.I32, array, index));
		return exp;
		*/
		return null;
	}
	//Function ARRAYLENGTH:
	public LlvmValue visit(ArrayLength n){
		
		
		/*
		Exp array = n.array;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmArrayLength(exp, LlvmPrimitiveType.I32, array));
		return exp;
		*/
		return null;
	}
	//Function CALL:
	public LlvmValue visit(Call n){
		
		
		/*
		Exp object = n.object;
		Identifier method = n.method;
		util.List<Exp> actuals = n.actuals;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmCall(exp, LlvmPrimitiveType.I32, object, method, actuals));
		return exp;
		*/
		return null;
	}
	//Function TRUE:
	public LlvmValue visit(True n){
		return new LlvmBool(1);
	}
	//Function FALSE:
	public LlvmValue visit(False n){
		return new LlvmBool(0);
	}
	//Function IDENTIFIEREXP
	public LlvmValue visit(IdentifierExp n){
		/*
		Identifier name = n.name;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmIdentifierExp(exp,LlvmPrimitiveType.I32, name));
		return exp;
		*/
		return null;
	}
	//Function THIS:
	public LlvmValue visit(This n){
		LlvmPointer t = new LlvmPointer(symTab.GetClassInUse());
		LlvmRegister exp = new LlvmRegister(t.content);
		return exp;
	}
	//Function NEWARRAY:
	public LlvmValue visit(NewArray n){
		/*
		Exp size = n.size;
		LlvmRegister exp = new LlvmRegister(LlvmPrimitiveType.I32);
		//assembler.add(new LlvmNewArray(exp,LlvmPrimitiveType.I32, size));
		return exp;
		*/
		return null;
	}
	//Function NEWOBJECT:
	public LlvmValue visit(NewObject n){
		LlvmValue sizeValue = new LlvmIntegerLiteral(symTab.classes.get(n.className.s)._classStructure.sizeByte);
		LlvmRegister lhsMalloc = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I8));
		List<LlvmValue> args = new LinkedList<LlvmValue>();
		args.add(sizeValue);
		assembler.add(new LlvmCall(lhsMalloc, lhsMalloc.type, "@malloc", args));
		LlvmRegister lhsBitCast = new LlvmRegister(n.className.accept(this).type);
		assembler.add(new LlvmBitcast(lhsBitCast, lhsMalloc, lhsBitCast.type));
		
		return lhsBitCast;
	}
	//Function NOT:
	public LlvmValue visit(Not n) {
		LlvmValue v = n.exp.accept(this);
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmTimes(lhs, LlvmPrimitiveType.I32, v, v));
		return lhs;
	}
	//Function IDENTIFIER:
	public LlvmValue visit(Identifier n){
		
		String s = n.s;
		LlvmRegister exp = new LlvmRegister("%"+n.s+"_temp", LlvmPrimitiveType.I32);
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
    public Map<String, ClassNode> classes = new HashMap<String, ClassNode>();     
    private ClassNode classEnv;    //aponta para a classe em uso
    private MethodNode methodEnv;
    
    void SetClassInUse(String className){
    	classEnv = classes.get(className);
    }
    
    public ClassNode GetClassInUse(){
    	return classEnv;
    }
    
    public void SetMethodInUse(String methodName){
    	methodEnv = classEnv._methodList.get(methodName);
    }
    
    public MethodNode GetMethodInUse(){
    	return methodEnv;
    }
    
    
    
    //--------------------------------------//
    
    public LlvmValue FillTabSymbol(Program n){
	n.accept(this);
	return null;
	}
	public LlvmValue visit(Program n){
		n.mainClass.accept(this);
	
		for (util.List<ClassDecl> c = n.classList; c != null; c = c.tail)
		{	
			c.head.accept(this);
		}
	
		return null;
	}
	
	public LlvmValue visit(MainClass n){
		classes.put(n.className.s, new ClassNode(n.className.s));
		SetClassInUse(n.className.s);
		
		return null;
	}
	
	public LlvmValue visit(ClassDeclSimple n){
		
		
		// Constroi TypeList com os tipos das vari������veis da Classe (vai formar a Struct da classe)
		
		
		// Constroi VarList com as Vari������veis da Classe
	
		classes.put(n.name.s, new ClassNode(n.name.s));
		SetClassInUse(n.name.s);
		
		List<LlvmType> listaTipos = new LinkedList<LlvmType>();
		
		for(util.List<VarDecl> v = n.varList; v != null; v = v.tail){
			LlvmValue field = v.head.accept(this);
			GetClassInUse().AddField(field.toString(), field.type); //VarDecl tem de retornar seu tipo e seu nome
			listaTipos.add(field.type);
		}
		GetClassInUse().AddClassType(new LlvmStructure(listaTipos));
	    	// Percorre n.methodList visitando cada m������todo
		
		for(util.List<MethodDecl> m = n.methodList; m != null; m = m.tail){
			m.head.accept(this);
		}
		
		classEnv = null;
		methodEnv = null;
		
		return null;
	}
	
		public LlvmValue visit(ClassDeclExtends n){return null;}
		public LlvmValue visit(VarDecl n){
			return new LlvmNamedValue(n.name.s, n.type.accept(this).type);
		}
		
		public LlvmValue visit(MethodDecl n){
			GetClassInUse().AddMethod(n.name.s, new MethodNode(n.name.s, n.returnType.accept(this)));
			SetMethodInUse(n.name.s);
			for(util.List<VarDecl> v = n.locals; v != null; v = v.tail){
				LlvmValue local = v.head.accept(this);
				GetMethodInUse().AddLocal(local.toString(), local);
			}
			for(util.List<Formal> f = n.formals; f != null; f = f.tail){
				LlvmValue formal = f.head.accept(this);
				GetMethodInUse().AddParam(formal.toString(), formal);
			}
			return null;
		}
		public LlvmValue visit(IdentifierType n){
			return new LlvmNamedValue("id", LlvmPrimitiveType.I8);
		}
		public LlvmValue visit(IntArrayType n){
			return new LlvmNamedValue("int[]", LlvmPrimitiveType.I32p);
		}
		public LlvmValue visit(BooleanType n){
			return new LlvmNamedValue("BooleanType", LlvmPrimitiveType.I1);
		}
		
		public LlvmValue visit(IntegerType n){
			return new LlvmNamedValue("IntegerType", LlvmPrimitiveType.I32);
		}
		
		public LlvmValue visit(Formal n){
			return new LlvmNamedValue(n.name.s, n.type.accept(this).type);
		}
}

class Field{
	LlvmType _type;
	int _index;
	
	public Field(LlvmType type, int index){
		_type = type;
		_index = index;
	}
}

class ClassNode extends LlvmType {
	String _name;
	LlvmStructure _classStructure;
	Map<String, Field> _classFields = new HashMap<String, Field>();
	Map<String, MethodNode> _methodList = new HashMap<String, MethodNode>();;
	int counter = 0;
	
	public ClassNode(String nameClass){
		_name = nameClass;
	}
	
	public void AddClassType(LlvmStructure classType){
		_classStructure = classType;
		
	}
	
	public void AddMethod(String methodName, MethodNode m){
		_methodList.put(methodName, m);
	}
	
	//Adiciona um campo na classe
	public void AddField(String name, LlvmType type){
		_classFields.put(name, new Field(type, counter));
		counter++;
	}
	
	public Field GetField(String fieldName){
		return _classFields.get(fieldName);
	}
	
	public MethodNode GetMethod(String methodName){
		return _methodList.get(methodName);
	}
	
	public LlvmStructure GetClassType(){
		return _classStructure;
	}
	
	public String toString(){
		return "%class." + _name;
	}
}

class MethodNode {
	String _name;
	LlvmValue _returnType;
	Map<String, LlvmValue> _params = new HashMap<String, LlvmValue>();
	Map<String, LlvmValue> _locals = new HashMap<String, LlvmValue>();
	public MethodNode(String name, LlvmValue returnType){
		_name = name;
		_returnType = returnType;
	}
	public void AddParam(String paramName, LlvmValue param){
		_params.put(paramName, param);
	}
	public void AddLocal(String localName, LlvmValue local){
		_locals.put(localName, local);
	}
	public LlvmValue GetParam(String paramName){
		return _params.get(paramName);
	}
	public LlvmValue GetLocal(String localName){
		return _locals.get(localName);
	}
	
	public LlvmValue GetReturnType(){
		return _returnType;
	}
}




