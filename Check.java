package vk.core.api;
////import vk.core.api.CompilerFactory.java;
//import vk.core.api.*;
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
			JavaStringCompiler.compileAndRunTests();
			if(compiler.getNumberOfFailedTests() == 1){
				return true;
			}
			KompilierbarUndEinTestFailed = false;
		}
		return KompilierbarUndEinTestFailed;
			
	}


	public static boolean check(String NameDerDatei, String file, boolean isTest){
			int laenge = NameDerDatei.length();
			NameDerDatei = NameDerDatei.substring(0,laenge-5);
			//System.out.print(NameDerDatei);
			//return false;
			CompilationUnit compilationUnits = new CompilationUnit(NameDerDatei,file,isTest);
			compiler = CompilerFactory.getCompiler(compilationUnits);
			compiler.compilerAndRunTests();



		if(!compiler.hasCompileErrors()){
			KompilierbarUndKeinTestFailed = false;
		}
		else{ 
			JavaStringCompiler.compileAndRunTests();
			if(compiler.getNumberOfFailedTests() == 0){
				return true;
			}
			KompilierbarUndKeinTestFailed = false;
		}
		return KompilierbarUndKeinTestFailed;
			
	}