package de.hhu.propra16;

import vk.core.api.CompilationUnit;
import vk.core.api.JavaStringCompiler;

import static vk.core.api.CompilerFactory.getCompiler;

public class Compile {

    private CompilationUnit code;	// original code
    private CompilationUnit test;	// original test
    private CompilationUnit test2;	// acceptance test
    private JavaStringCompiler compiler;

    public void setCode(String className, String codee) {
        code = new CompilationUnit(className, codee, false);
    }

    public void setTest(String testName, String testCode) {
        test = new CompilationUnit(testName, testCode, true);
    }

    public void setAcceptanceTest(String acceptanceTestName, String acceptanceTestCode) {
        test2 = new CompilationUnit(acceptanceTestName,acceptanceTestCode, true);
    }




    public void compileCodeAndTestTests() {
        if (code == null && test == null && test2 == null)
            getCompiler(code, test, test2).compileAndRunTests();

        else if (code == null && test == null)
            getCompiler(code, test).compileAndRunTests();

        else if (code == null && test2 == null)
            getCompiler(code, test2).compileAndRunTests();

        else if (test == null && test2 == null)
            getCompiler(test, test2).compileAndRunTests();

        else if (code == null)
            getCompiler(code).compileAndRunTests();

        else if (test == null)
            getCompiler(test).compileAndRunTests();

        else if (test2 == null)
            getCompiler(test2).compileAndRunTests();


    }

    /*public void compileCodeAndTestTests() {
        if(code == null && test == null && test2 == null)
            compiler = CompilerFactory.getCompiler(code, test, test2));
        else if(code == null && test == null)
            compiler = CompilerFactory.getCompiler(code, test);
        else if(code == null && test2 == null)
            compiler = CompilerFactory.getCompiler(code, test2);
        else if(test == null && test2 == null)
            compiler = CompilerFactory.getCompiler(test, test2);
        else if(code == null)
            compiler = CompilerFactory.getCompiler(code);
        else if(test == null)
            compiler = CompilerFactory.getCompiler(test);
        else if(test2 == null)
            compiler = CompilerFactory.getCompiler(test2);


        compiler.compileAndRunTests();

    }

    public boolean compilationErrors() {
        return compiler.getCompilerResult().hasCompileErrors();
    }

    private int countFailedAcceptanceTest(CompilationUnit unit) {
        if(unit == null || compiler.getTestResult().getNumberOfFailedTests() == 0)
            return 0;
        int result = 0;
        Collection<TestFailure> failures = compiler.getTestResult().getTestFailures();

        for(TestFailure failure : failures)
            if(failure.getTestClassName().equalsIgnoreCase(unit.getClassName()))
                result++;
        return result;
    }

    public int numberOfTestsFailed() {
        return countFailedAcceptanceTest(test);
    }

    public int numberOfFailedAcceptanceTests() {
        return countFailedAcceptanceTest(test2);
    }

    public int numberOfTestsPassed() {
        return compiler.getTestResult().getNumberOfSuccessfulTests();
    }

*/
}
