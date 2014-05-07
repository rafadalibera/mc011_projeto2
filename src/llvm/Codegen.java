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
		indexLabel = indexLabel + 1;
		return indexLabel;
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
				"type " + new LlvmStructure(symTab.GetClassInUse()._classStructure.typeList).toString()));

		for (util.List<MethodDecl> m = n.methodList; m != null; m = m.tail){
			m.head.accept(this);
		}
		
		return null;

	}
	//Function CLASSDECLEXTENDS:
	public LlvmValue visit(ClassDeclExtends n){
		
		symTab.SetClassInUse(n.name.s);
		
		assembler.add(new LlvmConstantDeclaration("%class."+n.name.s, 
				"type " + new LlvmStructure(symTab.GetClassInUse()._classStructure.typeList).toString()));

		for (util.List<MethodDecl> m = n.methodList; m != null; m = m.tail){
			m.head.accept(this);
		}
		
		return null;
	}
	//Function VARDECL:
	public LlvmValue visit(VarDecl n){
		LlvmRegister reg = new LlvmRegister(n.type.accept(this).type);
		reg.type = new LlvmPointer(reg.type);
		
		symTab.GetMethodInUse().SetRegisterVariable(n.name.s, reg);
		
		assembler.add(new LlvmAlloca(reg, n.type.accept(this).type, new LinkedList<LlvmValue>()));
		
		return null;
	}
	//Function METHODDECL:
	public LlvmValue visit(MethodDecl n){
		symTab.SetMethodInUse(n.name.s);
		List<LlvmValue> listaArgs = new ArrayList<LlvmValue>();
		List<String> listaArgsString = new ArrayList<String>();
		listaArgs.add(new LlvmNamedValue("%this", new LlvmPointer(symTab.GetClassInUse())));
		
		for (util.List<Formal> c = n.formals; c != null; c = c.tail)
		{
		
				LlvmNamedValue temp = (LlvmNamedValue)c.head.accept(this);
				listaArgs.add(new LlvmNamedValue("%" + temp.name, temp.type));
				listaArgsString.add(c.head.name.s);
			
		}
		LlvmInstruction definition = new LlvmDefine("@__" + n.name.toString() + "_" + symTab.GetClassInUse()._name , n.returnType.accept(this).type, listaArgs);
		assembler.add(definition);
		
		
		for(int i = 1; i < listaArgs.size(); i++){
			LlvmRegister addr = new LlvmRegister(listaArgs.get(i).type);
			addr.type = new LlvmPointer(addr.type);
			symTab.GetMethodInUse().SetRegisterVariable(listaArgsString.get(i-1), addr); 
			assembler.add(new LlvmAlloca(addr, listaArgs.get(i).type, new LinkedList<LlvmValue>()));
			assembler.add(new LlvmStore(listaArgs.get(i), addr));
		}
		
		
		for(util.List<VarDecl> v = n.locals; v != null; v = v.tail){
			v.head.accept(this);
		}
		
		for(util.List<Statement> s = n.body; s != null; s = s.tail){
			s.head.accept(this);
		}
		
		assembler.add(new LlvmRet(n.returnExp.accept(this)));
		
		assembler.add(new LlvmCloseDefinition());
		
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
		return new LlvmNamedValue("", new LlvmPointer(LlvmPrimitiveType.I32));
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
		return new LlvmNamedValue(n.name, new LlvmPointer(symTab.classes.get(n.name)));
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
			assembler.add(new LlvmLabel(labelElse));
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
		
		assembler.add(new LlvmBranch(labelLoop));
		
		assembler.add(new LlvmLabel(labelLoop));
		
		assembler.add(new LlvmBranch(n.condition.accept(this), labelBegin, labelEnd));
		assembler.add(new LlvmLabel(labelBegin));
		n.body.accept(this);
		assembler.add(new LlvmBranch(labelLoop));
		
		assembler.add(new LlvmLabel(labelEnd));

		return null;
	}
	//Function ASSIGN:
	public LlvmValue visit(Assign n){
		LlvmValue val = n.var.accept(this);
		assembler.add(new LlvmStore(n.exp.accept(this), val));
		return null;
	}
	//Function ARRAYASSIGN:
	public LlvmValue visit(ArrayAssign n){
		
		LlvmValue ptrptrBase = n.var.accept(this);
		LlvmRegister ptrBase = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		assembler.add(new LlvmLoad(ptrBase, ptrptrBase));
		LlvmRegister pointerToCell = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		List<LlvmValue> offsets = new ArrayList<LlvmValue>();
		LlvmRegister offsetregtemp = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmTimes(offsetregtemp, LlvmPrimitiveType.I32, n.index.accept(this), new LlvmIntegerLiteral(1)));
		LlvmRegister offsetreg = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmPlus(offsetreg, LlvmPrimitiveType.I32, offsetregtemp, new LlvmIntegerLiteral(1)));
		offsets.add(offsetreg);
		assembler.add(new LlvmGetElementPointer(pointerToCell, ptrBase, offsets));
		assembler.add(new LlvmStore(n.value.accept(this), pointerToCell));
		
		return null;		
		
		
		
		//LlvmValue ptrBase = n.var.accept(this);
		
//		LlvmValue arrayBase = new LlvmRegister (new LlvmPointer (LlvmPrimitiveType.I32));		
//		assembler.add (new LlvmLoad (arrayBase, ptrBase));
//		
		// Registrador que aponta o inicio do vetor
//		LlvmRegister elementPtr = new LlvmRegister( new LlvmPointer(LlvmPrimitiveType.I8));
	    // Carrega lista de offsets
//		List<LlvmValue> offsets = new LinkedList<LlvmValue> ();
//	    offsets.add(new LlvmIntegerLiteral(((LlvmIntegerLiteral)n.index.accept(this)).value+1));
	    //offsets.add(new LlvmIntegerLiteral(0));
		
		// BitCast para usar getElementPointer
//	    LlvmValue xx = new LlvmRegister (new LlvmPointer(LlvmPrimitiveType.I8));
//	    assembler.add(new LlvmBitcast(xx, arrayBase, new LlvmPointer(LlvmPrimitiveType.I8)));
//	    assembler.add(new LlvmGetElementPointer (elementPtr, xx, offsets));	
	    // BitCast para salvar 
//	    LlvmValue yy = new LlvmRegister (new LlvmPointer(LlvmPrimitiveType.I32));
//	    assembler.add(new LlvmBitcast(yy, elementPtr, new LlvmPointer(LlvmPrimitiveType.I32)));
//	    assembler.add(new LlvmStore (n.value.accept (this), yy));
	//	return null;
		/*
		LlvmValue regBaseO = n.var.accept(this);
		LlvmValue regBase = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		LlvmRegister reg = new LlvmRegister(LlvmPrimitiveType.I32);
		LlvmRegister regAccess = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		
		
		assembler.add(new LlvmLoad(regBase, regBaseO));
		
		LlvmRegister tempO = new LlvmRegister(LlvmPrimitiveType.I32);
		
		assembler.add(new LlvmPlus(tempO, LlvmPrimitiveType.I32, n.index.accept(this), new LlvmIntegerLiteral(1)));
		
		assembler.add(new LlvmTimes (reg, LlvmPrimitiveType.I32, tempO, new LlvmIntegerLiteral(4)));
		
		LlvmRegister ptr = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		
		assembler.add(new LlvmInttoptr(ptr, reg, toType))
		
		assembler.add(new LlvmPlus(regAccess, new LlvmPointer(LlvmPrimitiveType.I32), regBase, reg));
		
				
		assembler.add(new LlvmStore(n.value.accept(this), regAccess));
		*/
		//return null;
	}
	// Function AND:
	public LlvmValue visit(And n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister v1temp = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmZext(v1temp, v1, LlvmPrimitiveType.I32));
		
		LlvmRegister v2temp = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmZext(v2temp, v2, LlvmPrimitiveType.I32));
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmTimes(lhs, LlvmPrimitiveType.I32, v1temp, v2temp));
		LlvmRegister ret = new LlvmRegister(LlvmPrimitiveType.I1);
		assembler.add(new LlvmTrunc(ret, lhs, LlvmPrimitiveType.I1));
		return ret;
	}
	//Function LESSTHAN:
	public LlvmValue visit(LessThan n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I1);
		assembler.add(new LlvmIcmp(lhs, 2, LlvmPrimitiveType.I32,  v1, v2));

		return lhs;
	}
	//Function EQUAL:
	public LlvmValue visit(Equal n){
		LlvmValue v1 = n.lhs.accept(this);
		LlvmValue v2 = n.rhs.accept(this);
		
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I1);
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
		LlvmValue ptrBase = n.array.accept(this);
		LlvmRegister pointerToCell = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		List<LlvmValue> offsets = new ArrayList<LlvmValue>();
		LlvmRegister offsetregtemp = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmTimes(offsetregtemp, LlvmPrimitiveType.I32, n.index.accept(this), new LlvmIntegerLiteral(1)));
		LlvmRegister offsetreg = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmPlus(offsetreg, LlvmPrimitiveType.I32, offsetregtemp, new LlvmIntegerLiteral(1)));
		offsets.add(offsetreg);
		assembler.add(new LlvmGetElementPointer(pointerToCell, ptrBase, offsets));
		LlvmRegister valor = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmLoad(valor, pointerToCell));
		return valor;		
		
		
		//LlvmValue arrayBase = new LlvmRegister (new LlvmPointer (LlvmPrimitiveType.I32));		
		//assembler.add (new LlvmLoad (arrayBase, ptrBase));
		
		// Registrador que aponta o inicio do vetor
		//LlvmRegister elementPtr = new LlvmRegister( new LlvmPointer(LlvmPrimitiveType.I8));
	    // Carrega lista de offsets
		//List<LlvmValue> offsets = new LinkedList<LlvmValue> ();
	    //offsets.add(new LlvmIntegerLiteral(((LlvmIntegerLiteral)n.index.accept(this)).value+1));
	    //offsets.add(new LlvmIntegerLiteral(0));
		
		// BitCast para usar getElementPointer
	    //LlvmValue xx = new LlvmRegister (new LlvmPointer(LlvmPrimitiveType.I8));
	    //assembler.add(new LlvmBitcast(xx, arrayBase, new LlvmPointer(LlvmPrimitiveType.I8)));
	    //assembler.add(new LlvmGetElementPointer (elementPtr, xx, offsets));	
	    // BitCast para salvar 
	    //LlvmValue yy = new LlvmRegister (new LlvmPointer(LlvmPrimitiveType.I32));
	    //assembler.add(new LlvmBitcast(yy, elementPtr, new LlvmPointer(LlvmPrimitiveType.I32)));
	    
	    //assembler.add(new LlvmStore (n.value.accept (this), yy));
	    //LlvmRegister ret = new LlvmRegister(LlvmPrimitiveType.I32);
	    //assembler.add(new LlvmLoad(ret, yy));

/*		
		LlvmValue regBase = n.array.accept(this);
		LlvmRegister reg = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		LlvmRegister regAccess = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		
		LlvmRegister temp = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		
		assembler.add(new LlvmPlus(temp, new LlvmPointer(LlvmPrimitiveType.I32), n.index.accept(this), new LlvmIntegerLiteral(1)));
		
		assembler.add(new LlvmTimes (reg, new LlvmPointer(LlvmPrimitiveType.I32), temp, new LlvmIntegerLiteral(4)));
		
		assembler.add(new LlvmPlus(regAccess, new LlvmPointer(LlvmPrimitiveType.I32), regBase, reg));
		
		LlvmRegister ret = new LlvmRegister(LlvmPrimitiveType.I32);
		
		assembler.add(new LlvmLoad(ret, regAccess));
*/		
		//return ret;
	}
	//Function ARRAYLENGTH:
	public LlvmValue visit(ArrayLength n){
		
		LlvmValue regBase = n.array.accept(this);
		
		LlvmRegister ret = new LlvmRegister(LlvmPrimitiveType.I32);
		
		assembler.add(new LlvmLoad(ret, regBase));
		
		return ret;
	}
	//Function CALL:
	public LlvmValue visit(Call n){
		
		LlvmValue regThis = n.object.accept(this);
		List<LlvmValue> args = new ArrayList<LlvmValue>();
		ClassNode classeMetodo = symTab.GetClassByMethodAndType(n.method.s, regThis.type);
		
		
		
		args.add(regThis);
		
		for(util.List<Exp> s = n.actuals; s != null; s = s.tail){
			args.add(s.head.accept(this));
		}
		
		LlvmRegister regResultado = new LlvmRegister(classeMetodo.GetMethod(n.method.s)._returnType.type);
		assembler.add(new LlvmCall(regResultado, regResultado.type, "@__" + n.method.s + "_" + classeMetodo._name, args));
		
		return regResultado;
	}
	//Function TRUE:
	public LlvmValue visit(True n){
		return new LlvmBool(1);
	}
	//Function FALSE:
	public LlvmValue visit(False n){
		return new LlvmBool(0);
	}
	//Function IDENTIFIEREXP:
	public LlvmValue visit(IdentifierExp n){
		
		LlvmValue LlvmVarReg = n.name.accept(this);
		
		LlvmRegister ret;
		
		Field f = symTab.GetClassInUse().GetField(n.name.s);
		
		if(f != null){
			ret = new LlvmRegister(f._type);
		}
		else{
			ret = new LlvmRegister(symTab.GetMethodInUse().GetVariable(n.name.s)._variable.type);
		}
		
		assembler.add(new LlvmLoad(ret, LlvmVarReg));
		
		return ret;
	}
	//Function THIS:
	public LlvmValue visit(This n){
		return new LlvmNamedValue("%this", n.type.accept(this).type);
	}
	//Function NEWARRAY:
	public LlvmValue visit(NewArray n){
		LlvmValue pretam = n.size.accept(this); 
		
		LlvmRegister tam = new LlvmRegister(LlvmPrimitiveType.I32);
		
		assembler.add(new LlvmPlus(tam, tam.type, pretam, new LlvmIntegerLiteral(1)));
		
		LlvmRegister mallocares = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I32));
		
		assembler.add(new LlvmMalloc(mallocares, LlvmPrimitiveType.I32, tam));
		
		assembler.add(new LlvmStore(pretam, mallocares));
		
		return mallocares;
	}
	//Function NEWOBJECT:
	public LlvmValue visit(NewObject n){
		LlvmValue sizeValue = new LlvmIntegerLiteral(symTab.classes.get(n.className.s)._classStructure.sizeByte);
		LlvmRegister lhsMalloc = new LlvmRegister(new LlvmPointer(LlvmPrimitiveType.I8));
		List<LlvmValue> args = new LinkedList<LlvmValue>();
		args.add(sizeValue);
		assembler.add(new LlvmCall(lhsMalloc, lhsMalloc.type, "@malloc", args));
		LlvmRegister lhsBitCast = new LlvmRegister(new LlvmPointer(symTab.classes.get(n.className.s)));
		assembler.add(new LlvmBitcast(lhsBitCast, lhsMalloc, lhsBitCast.type));
		
		return lhsBitCast;
	}
	//Function NOT:
	public LlvmValue visit(Not n) {
		LlvmValue v = n.exp.accept(this);
		LlvmRegister vtemp = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmZext(vtemp, v, LlvmPrimitiveType.I32));
		LlvmRegister lhs = new LlvmRegister(LlvmPrimitiveType.I32);
		assembler.add(new LlvmPlus(lhs, LlvmPrimitiveType.I32, vtemp, new LlvmIntegerLiteral(1)));
		LlvmRegister ret = new LlvmRegister(LlvmPrimitiveType.I1);
		assembler.add(new LlvmTrunc(ret, lhs, LlvmPrimitiveType.I1));
		return ret;
	}
	//Function IDENTIFIER:
	public LlvmValue visit(Identifier n){
		Field f = symTab.GetClassInUse().GetField(n.s);
		if(f == null){		
			return symTab.GetMethodInUse().GetVariable(n.s)._register;
		}
		else
		{
			LlvmRegister regadd = new LlvmRegister(new LlvmPointer(f._type));
			List<LlvmValue> offsets = new ArrayList<LlvmValue>();
			offsets.add(new LlvmIntegerLiteral(0));
			offsets.add(new LlvmIntegerLiteral(f._index));
			assembler.add(new LlvmGetElementPointer(regadd, new LlvmRegister("%this", new LlvmPointer(symTab.GetClassInUse())), offsets));
			return regadd;
		}
	
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
    
    public ClassNode GetClassByMethodAndType(String methodName, LlvmType type){
    	for(Map.Entry<String, ClassNode> entrada : classes.entrySet()){
    		if(entrada.getValue().GetMethod(methodName) != null && type.toString().contains(entrada.getValue().toString())){
    			return entrada.getValue();
    		}
    	}
    	
    	return null;
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
	
		public LlvmValue visit(ClassDeclExtends n){
			classes.put(n.name.s, new ClassNode(n.name.s));
			SetClassInUse(n.name.s);
			ClassNode classePai = classes.get(n.superClass.s);
			
			List<LlvmType> listaTipos = new LinkedList<LlvmType>();
			
			for(util.List<VarDecl> v = n.varList; v != null; v = v.tail){
				LlvmValue field = v.head.accept(this);
				GetClassInUse().AddField(field.toString(), field.type); //VarDecl tem de retornar seu tipo e seu nome
				listaTipos.add(field.type);
			}
			
			for(Map.Entry<String, Field> classField : classePai._classFields.entrySet()){
				if(GetClassInUse()._classFields.get(classField.getKey()) == null){
					GetClassInUse().AddField(classField.getKey(), classField.getValue()._type); //VarDecl tem de retornar seu tipo e seu nome
					listaTipos.add(classField.getValue()._type);
				}
			}
			
			GetClassInUse().AddClassType(new LlvmStructure(listaTipos));
			
			for(util.List<MethodDecl> m = n.methodList; m != null; m = m.tail){
				m.head.accept(this);
			}
			
			
			for(Map.Entry<String, MethodNode> mPai : classePai._methodList.entrySet()){
				if(GetClassInUse()._methodList.get(mPai.getKey()) == null){
					GetClassInUse().AddMethod(mPai.getKey(), new MethodNode(mPai.getKey(), mPai.getValue()._returnType));
					SetMethodInUse(mPai.getKey());
					
					for(Map.Entry<String, MethodVariable> varPai : mPai.getValue()._variables.entrySet()){
						GetMethodInUse().AddVariable(varPai.getKey(), varPai.getValue()._variable);
					}
				}
			}
			
			return null;
		}
		public LlvmValue visit(VarDecl n){
			return new LlvmNamedValue(n.name.s, n.type.accept(this).type);
		}
		
		public LlvmValue visit(MethodDecl n){
			GetClassInUse().AddMethod(n.name.s, new MethodNode(n.name.s, n.returnType.accept(this)));
			SetMethodInUse(n.name.s);
			for(util.List<VarDecl> v = n.locals; v != null; v = v.tail){
				LlvmValue local = v.head.accept(this);
				GetMethodInUse().AddVariable(local.toString(), local);
			}
			for(util.List<Formal> f = n.formals; f != null; f = f.tail){
				LlvmValue formal = f.head.accept(this);
				GetMethodInUse().AddVariable(formal.toString(), formal);
			}
			return null;
		}
		public LlvmValue visit(IdentifierType n){
			return new LlvmNamedValue(n.name, new LlvmPointer(classes.get(n.name)));
		}
		public LlvmValue visit(IntArrayType n){
			return new LlvmNamedValue("int[]", new LlvmPointer(LlvmPrimitiveType.I32));
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

class MethodVariable{
	public LlvmValue _variable;
	public LlvmRegister _register;
	
	public MethodVariable(LlvmValue variable){
		_variable = variable;
	}
	
	public LlvmRegister GetRegister(){
		return new LlvmRegister(_register.name, new LlvmPointer(_variable.type).content);
	}
}

class MethodNode {
	String _name;
	LlvmValue _returnType;
	Map<String, MethodVariable> _variables = new HashMap<String, MethodVariable>();
	public MethodNode(String name, LlvmValue returnType){
		_name = name;
		_returnType = returnType;
	}
	public void AddVariable(String paramName, LlvmValue param){
		_variables.put(paramName, new MethodVariable(param));
	}
	public MethodVariable GetVariable(String paramName){
		return _variables.get(paramName);
	}
	
	public void SetRegisterVariable(String paramName, LlvmRegister reg){
		GetVariable(paramName)._register = reg;
	}
	
	
	public LlvmValue GetReturnType(){
		return _returnType;
	}
}




