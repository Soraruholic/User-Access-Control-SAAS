package SAAS.Test.ModelTest;

import SAAS.Utils.GlobalVariables;
import org.junit.jupiter.api.Test;

import SAAS.UAC.LogIn.GoogleAuthenticator;

import static org.junit.jupiter.api.Assertions.*;

class GoogleAuthenticatorTest {
    @Test
    void test_GoogleAuthenticator_F() throws Exception {
        // generate the QRCode for the user who is registering and show the QR code to the user
        GoogleAuthenticator.initAuthentication("saas111");

        // Check the verification code entered by the user
        int result = GoogleAuthenticator.verifyTOTPCode("saas111", "WRONG_CODE");
        assertEquals(GlobalVariables.GOOGLE_AUTHENTICATION_FAILURE, result);
    }

    @Test
    void test_GoogleAuthenticator_S() throws Exception {
        // generate the QRCode for the user who is registering and show the QR code to the user
        // GoogleAuthenticator.initAuthentication("saas111");

        // Check the verification code entered by the user
        int result = GoogleAuthenticator.verifyTOTPCode("saas111", "RIGHT_CODE");
        assertEquals(GlobalVariables.GOOGLE_AUTHENTICATION_SUCCESS, result);
    }
}