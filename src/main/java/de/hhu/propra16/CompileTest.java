package de.hhu.propra16;

import org.junit.Test;

public class CompileTest {

    @Test
    public void testCompilerAndTestRunner() {
        String code =	"public class Code {\n" + "   public boolean test() {\n" +
                "      return true;\n" + "   }\n" + "}";

        String test =	"import static org.junit.Asser.*;\n" + "import org.junit.Test;\n" +
                "\n" + "public class CodeTest {\n" + "	@Test \n" +
                "	public void testToTest() {\n" + "		assertEquals(true, Code.test());\n" +
                "}\n" + "}";

        Compile compiler = new Compile();

        compiler.setCode("Code", code);
        compiler.setTest("CodeTest", test);
        compiler.setAcceptanceTest("AcceptanceTest", "acceptanceTest is missing here");
        compiler.compileCodeAndTestTests();

 //       assertEquals(false,compiler.compilationErrors());
 //       assertEquals(true,compiler.numberOfTestsFailed());

    }

}
