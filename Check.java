package virtual-kata-lib-3f0664dc35c9e2396b1a75b2d6f78ad8620d73df/src/main/java/vk/core;
import java.util.List;
import java.util.LinkedList;
//import java.nio.file;

public class Check{

	boolean KompilierbarUndEinTestFailed;
	JavaStringCompiler compiler;
/*	public static void main(String[] args){
		checkRED(args[0],args[1],false);
}*/

	public static boolean checkRED(String NameDerDatei, String file, boolean isTest){
			int laenge = NameDerDatei.length();
			NameDerDatei = NameDerDatei.substring(0,laenge-5);
			//System.out.print(NameDerDatei);
			//return false;
			CompilationUnit compilationUnits = new CompilationUnit(NameDerDatei,file,isTest);
			compiler = CompilerFactory.getCompiler(compilationUnits);
			compiler.compilerAndRunTests();



		if(!compiler.hasCompileErrors()){
			KompilierbarUndEinTestFailed = false;
		}
		else{ 
			JavaStringCompiler.compileAndRunTests()
			if(compiler.getNumberOfFailedTests() == 1){
				return true;
			}
			KompilierbarUndEinTestFailed = false;
		}
		return KompilierbarUndEinTestFailed;
			
	}

//kopie der Datei erstellen

// compilerfaktory -> getCompilerfkt(CompilationUnit) -> JavaStringKompiler -> FKT zum testen und und und
}
}
