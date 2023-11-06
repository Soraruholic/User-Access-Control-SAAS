package SAAS.UAC.LogIn;

import SAAS.Database.Database;
import SAAS.UAC.UserAccessControl;
import SAAS.Utils.GlobalVariables;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

public class LogIn {
    protected static String code = null;
    public static void send_code(String email, String PSW) throws Exception {
        // Do some check
        // 1. userID is null
        if (email == null) {
            throw new IllegalArgumentException("The userName is null");
        }
        String userID = Utils.get_sha256Hex(email);

        // 2. PSW is null
        if (PSW == null) {
            throw new IllegalArgumentException("The PSW is null");
        }

        // Simply presume that the userName is the same as the email
        // Check whether the format of userName is correct
        if (!Utils.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        // Check whether the user exists
        if (Database.select_userBasic(userID) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        // Generate the authentication code
        code = Utils.get_authenticationCode();

        // Send the authentication code to the user
        Utils.send_EmailCode(email, code, "SAAS登陆验证码");

    }
    public static int verify(String userID, String PSW, String v_code) throws Exception {
        // check whether the v_code equals to the code
        if (!v_code.equals(code)) {
            return GlobalVariables.VERIFY_CODE_ERROR;
        }

        if (!Objects.requireNonNull(Database.select_userBasic(userID)).getPassword().equals(PSW)) {
            return GlobalVariables.PSW_ERROR;
        }

        // pass the userID to the UserAccessControl
        UserAccessControl.init(userID);
        return GlobalVariables.LOGIN_SUCCESS;
    }
}