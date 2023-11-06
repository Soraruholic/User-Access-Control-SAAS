package SAAS.UAC.LogIn;

import SAAS.Database.Database;
import SAAS.UAC.UserAccessControl;
import SAAS.Utils.GlobalVariables;

import java.util.Objects;

public class LogIn {
    protected static String code = null;
    public static void send_code(String userID, String PSW) throws Exception {
        // Do some check
        // 1. userID is null
        if (userID == null) {
            throw new IllegalArgumentException("The userID is null");
        }
        userID = Utils.get_sha256Hex(userID);

        // 2. PSW is null
        if (PSW == null) {
            throw new IllegalArgumentException("The PSW is null");
        }

        // Check whether the user exists
        if (Database.select_userBasic(userID) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        // Simply presume that the userName is the same as the email
        // Check whether the format of userName is correct
        if (!Utils.isValidEmail(userID)) {
            throw new IllegalArgumentException("Invalid email");
        }

        // Generate the authentication code
        code = Utils.get_authenticationCode();

        // Send the authentication code to the user
        Utils.send_EmailCode(userID, code, "SAAS登陆验证码");
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
