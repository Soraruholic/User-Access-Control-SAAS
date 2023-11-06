package SAAS.Test.ModelTest;

import SAAS.UAC.LogIn.PhoneMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneMessageTest {
    @BeforeAll
    public static void setup() {
        PhoneMessage.initializeSecretKey();
    }
    @Disabled
    @Test
    void test_sendRegisterCode() {
        String code = PhoneMessage.sendRegisterCode("17721011625", true);
        System.out.println("Register code = " + code);
    }
    @Disabled
    @Test
    void test_sendLoginCode() {
        String code = PhoneMessage.sendLoginCode("17721011625", true);
        System.out.println("Login code = " + code);
    }
//    @Disabled
    @Test
    void test_sendResetCode() {
        String code = PhoneMessage.sendResetCode("17721011625", true);
        System.out.println("Reset code = " + code);
    }
}