package SAAS.Test.ModelTest;

import SAAS.Database.Database;
import SAAS.UAC.LogIn.LogIn;
import SAAS.UAC.LogIn.Register;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogInTest {

    @BeforeAll
    public static void create() throws Exception {
        Database.create();
        //register user no.1
        Register.register(null,"saas111","Saas@123","166640379@qq.com","123123123");
    }
    @Test
    void test_Register() throws Exception {

        //test 0 , user already exists
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","166640379@qq.com","123123123");
        });;
        assertEquals("User already exists", exception.getMessage());

        //test 1 , username is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"null","Saas@123","166640379@qq.com","123123123");
        });;
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , username is invalid of length
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"sas","Saas@123","166640379@qq.com","123123123");
        });;
        assertEquals("The length of userName should be between 3 and 15", exception.getMessage());

        //test 3 , username is invalid of format
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas","Saas@123","166640379@qq.com","123123123");
        });;
        assertEquals("The userName should include both digit and letter", exception.getMessage());

        //test 4 , password is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas123","166640379@qq.com","123123123");
        });;
        assertEquals("Invalid password", exception.getMessage());

        //test 5 , email is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","166640379@com","123123123");
        });;
        assertEquals("Invalid email", exception.getMessage());

        //test 6 , phonenumber is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Register.register(null,"saas111","Saas@123","166640379@qq.com","123");
        });;
        assertEquals("Invalid phone number", exception.getMessage());

        //test 7 , userInfo in database is null, failed in insert

    }

    @Test
    void test_login(){

        //test 1 , username is null
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code(null,"Saas@123");
        });;
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , password is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("166640379@qq.com",null);
        });;
        assertEquals("The PSW is null", exception.getMessage());

        //test 3 , user doesnt exist
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("66640379@qq.com","Saas@123");
        });;
        assertEquals("User does not exist", exception.getMessage());

        //test 4 , user doesnt exist
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("166640379@com","Saas@123");
        });;
        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    void test_ResetPSW(){

        //test 1 , username is null
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code(null,"Saas@123");
        });;
        assertEquals("The userName is null", exception.getMessage());

        //test 2 , user does not exist
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("66640379@com","Saas@123");
        });;
        assertEquals("User does not exist", exception.getMessage());

        //test 3 , NEW password is invalid
        exception = assertThrows(IllegalArgumentException.class, () -> {
            LogIn.send_code("166640379@com","Saas123");
        });;
        assertEquals("Invalid password", exception.getMessage());

        //test 4 , NEW password reset failed due to database unknown error

    }
}
