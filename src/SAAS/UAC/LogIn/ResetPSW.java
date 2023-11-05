package SAAS.UAC.LogIn;
import SAAS.Database.Database;
import SAAS.Utils.GlobalVariables;

import java.util.Objects;

public class ResetPSW {
    protected static String code;

    public void reset (String userID, String newPSW) throws Exception {
        // check whether the userID is null
        if (userID == null) {
            throw new IllegalArgumentException("The userID is null");
        }
        userID = Utils.get_sha256Hex(userID);

        // check whether the user exists
        if (Database.select_userBasic(userID) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        // TODO: Check the format of the newPSW

        // send the authentication code to the user
        code = Utils.get_authenticationCode();
        Utils.send_EmailCode(userID, code, "SAAS重置密码验证码");
    }

    public int verify (String userID, String newPSW, String v_code) throws Exception {
        // check whether the v_code equals to the code
        if (!v_code.equals(code)) {
            return GlobalVariables.VERIFY_CODE_ERROR;
        }
        // update the password
        String lastPSWChange = Utils.get_currentTime();
        Database.update_userBasic_PSW(userID, newPSW, lastPSWChange);

        // check whether the password has been changed correctly
        if (!Objects.requireNonNull(Database.select_userBasic(userID)).getPassword().equals(newPSW)) {
            throw new Exception("Failed to change the password");
        }

        // TODO: Send an email to the user to inform him/her that the password has been changed\


        return GlobalVariables.RESET_PSW_SUCCESS;
    }
}
