package SAAS.Test.ModelTest;

import SAAS.Database.Database;
import SAAS.UAC.LogIn.LogIn;
import SAAS.UAC.LogIn.Register;
import SAAS.UAC.LogIn.ResetPSW;
import SAAS.UAC.LogIn.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogInTest {

    @BeforeAll
    public static void create() throws Exception {

        String email = "1666403795@qq.com" ;
        String userID = Utils.get_sha256Hex(email);

        Database.create();
        //register user no.1
        if(Database.select_userBasic(userID) == null) {
            String lastPSWChange = Utils.get_currentTime();
            Register.register(null, "saas111","Saas@123","1666403795@qq.com","11111111111");
            Register.verify(null, "saas111","Saas@123","1666403795@qq.com","11111111111",null);
        }
    }
    @Test
    void test_Register() throws Exception {

        //test 0 , user already exists
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","1666403795@qq.com","18080898778");
        });
        assertEquals("User already exists", exception.getMessage());

        //test 1 , username is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,null,"Saas@123","1666403795@qq.com","18080898778");
        });
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , username is invalid of length
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"sa","Saas@123","1666403795@qq.com","18080898778");
        });
        assertEquals("The length of userName should be between 3 and 15", exception.getMessage());

        //test 3 , username is invalid of format
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas","Saas@123","1666403795@qq.com","18080898778");
        });
        assertEquals("The userName should include both digit and letter", exception.getMessage());

        //test 4 , password is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas123","16664037@qq.com","18080898778");
        });
        assertEquals("Invalid password", exception.getMessage());

        //test 5 , email is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","1666403795qq.com","18080898778");
        });
        assertEquals("Invalid email", exception.getMessage());

        //test 6 , phonenumber is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","16664037@qq.com","123");
        });
        assertEquals("Invalid phone number", exception.getMessage());

        //test 7 , userInfo in database is null, failed in insert

    }

    @Test
    void test_login() throws Exception{

        //test 1 , username is null
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code(null,"Saas@123");
        });
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , password is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("1666403795@qq.com",null);
        });
        assertEquals("The PSW is null", exception.getMessage());

        //test 3 , user doesnt exist
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("666403795@qq.com","Saas@123");
        });
        assertEquals("User does not exist", exception.getMessage());

        //test 4 , Invalid email
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("1666403795qq.com","Saas@123");
        });
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void test_ResetPSW() throws Exception{

        //test 1 , username is null
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ResetPSW.reset(null,"Saas@123");
        });
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , user does not exist
        exception = assertThrows(IllegalArgumentException.class, () -> {
            ResetPSW.reset("666403795@com","Saas@123");
        });
        assertEquals("User does not exist", exception.getMessage());

        //test 3 , NEW password is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            ResetPSW.reset("1666403795@qq.com","Saas123");
        });
        assertEquals("Invalid password", exception.getMessage());

        //test 4 , NEW password reset failed due to database unknown error

    }
}
