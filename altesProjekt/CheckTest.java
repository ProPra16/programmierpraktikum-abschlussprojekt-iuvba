import static org.junit.Assert.*;
import org.junit.Test;

public class CheckTest {
//checkRED Test
    @Test
    public void testCheckRED(){
		String name = "Hi.java";
		String inhalt =  " import static org.junit.Assert.*; import org.junit.Test; public class Hi{ @Test public void test(){ boolean x = true; assertEquals(true, x); }}";
		boolean x = Check.check(name,inhalt, true);
       assertEquals(true,x);
    }

	@Test
	public void testCheckRED2(){
	String name = "hi.java";
	String inhalt = "public class hi{public main(()}";
	boolean x = Check.check(name,inhalt,true);
	assertEquals(false,x);
	}

	@Test
	public void testCheckRED3(){
	String name = "Hi.java";
		String inhalt =  " import static org.junit.Assert.*; import org.junit.Test; public class Hi{ @Test public void test(){ boolean x = true; assertEquals(false, x); }}";
		boolean x = Check.check(name,inhalt, true);
       assertEquals(false,x);
    }

}
