import package virtual-kata-lib-3f0664dc35c9e2396b1a75b2d6f78ad8620d73df/src/main/java/vk/core;
import java.util.List;
import java.util.LinkedList;
import java.nio.file;

public class Check{

	boolean KompilierbarUndEinTestFailed;

	public boolean checkRED(String file, String NameDerDatei, boolean isTest){


		if(...){

		}
		else{ 
			JavaStringCompiler.compileAndRunTests()
			if(TestResult.getNumberOfFailedTests() == 1){
				return true;
			}
			KompilierbarUndEinTestFailed = false;
		}
		return KompilierbarUndEinTestFailed;
			
	}

}
