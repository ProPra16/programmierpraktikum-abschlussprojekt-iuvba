package de.hhu.propra16;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

//import java.util.LinkedList;

public class Check {

	static boolean KompilierbarUndEinTestFailed;
	static boolean KompilierbarUndKeinTestFailed;
	static JavaStringCompiler compiler;
	static JavaStringCompiler compiler2;
	
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
	public static boolean check( String file, String NameDateiCode){
			String test = "test";
			String code = "code";
			
			//int laenge = NameDerDatei.length();
			//NameDerDatei = NameDerDatei.substring(0,laenge-5);
			//System.out.print(NameDerDatei);
			//return false;
			CompilationUnit compilationUnits = new CompilationUnit(code,file,false); // code wird kompiliert
			compiler = CompilerFactory.getCompiler(compilationUnits);
			compiler.compileAndRunTests();
			
			CompilationUnit compilationUnitsTest = new CompilationUnit(test,file,true); // test wird kompiliert
			compiler2 = CompilerFactory.getCompiler(compilationUnits);
			compiler2.compileAndRunTests();

		if(!((CompilerResult) compiler).hasCompileErrors()){ // hasCompilError(): return true if no compile errors
			KompilierbarUndKeinTestFailed = false;
		}
		else{ 
			compiler2.compileAndRunTests();
			if(((TestResult) compiler2).getNumberOfFailedTests() == 0){  // get#OfFailedTests(): return number of tests failed
				return true;
			}
			KompilierbarUndKeinTestFailed = false;
		}
		return KompilierbarUndKeinTestFailed;
	}
}
