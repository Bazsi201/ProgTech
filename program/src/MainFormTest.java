import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainFormTest {

    @Test
    void testIsNotNull(){
        MainForm mf = new MainForm();
        assertEquals("",mf.Test(""));
    }

}