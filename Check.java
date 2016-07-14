import main.CompilationUnit;
import main.CompilerFactory;
import main.JavaStringCompiler;
import main.CompilerResult;
import main.TestResult;

//import java.util.LinkedList;

public class Check {

	static boolean KompilierbarUndEinTestFailed;
	static boolean KompilierbarUndKeinTestFailed;
	static JavaStringCompiler compiler;
	
//	public static void main(String[] args){
//		checkRED(args[0],args[1],false);
//}

	// compiles and a test fails

	public static boolean checkRED(String NameDerDatei, String file, boolean isTest){
		if (NameDerDatei == null && file == null && isTest == true) {
			int laenge = NameDerDatei.length();
			NameDerDatei = NameDerDatei.substring(0,laenge-5);
			//System.out.print(NameDerDatei);
			//return false;
			
			//TODO: check if the variables are null or not and print it out
			CompilationUnit compilationUnits = new CompilationUnit(NameDerDatei,file,isTest);
			compiler.compileAndRunTests();
			compiler = CompilerFactory.getCompiler(compilationUnits);
			
		if(!((CompilerResult) compiler).hasCompileErrors()) {
//		if(!compiler.hasCompileErrors()) {
			KompilierbarUndEinTestFailed = false;
		}
		else{ 
			compiler.compileAndRunTests();
			if(((TestResult) compiler).getNumberOfFailedTests() == 1){
				return true;
			}
			KompilierbarUndEinTestFailed = true;
		}
		return KompilierbarUndEinTestFailed;
		}
		return false;
	}
	
//	compiles and test passes -- refactor??
	public static boolean check(String NameDerDatei, String file, boolean isTest){
			int laenge = NameDerDatei.length();
			NameDerDatei = NameDerDatei.substring(0,laenge-5);
			//System.out.print(NameDerDatei);
			//return false;
			CompilationUnit compilationUnits = new CompilationUnit(NameDerDatei,file,isTest);
			compiler = CompilerFactory.getCompiler(compilationUnits);
			compiler.compileAndRunTests();

		if(!((CompilerResult) compiler).hasCompileErrors()){ // hasCompilError(): return true if no compile errors
			KompilierbarUndKeinTestFailed = false;
		}
		else{ 
			compiler.compileAndRunTests();
			if(((TestResult) compiler).getNumberOfFailedTests() == 0){  // get#OfFailedTests(): return number of tests failed
				return true;
			}
			KompilierbarUndKeinTestFailed = false;
		}
		return KompilierbarUndKeinTestFailed;
	}
}
