package SAAS.UAC.LogIn;
import SAAS.Database.Database;
import SAAS.Utils.GlobalVariables;

import java.util.Objects;

public class ResetPSW {
    protected static String code;

    public static void reset (String username, String newPSW) throws Exception {
        // check whether the userID is null
        if (username == null) {
            throw new IllegalArgumentException("The userName is null");
        }
        String userID = Utils.get_sha256Hex(username);

        // check whether the user exists
        if (Database.select_userBasic(userID) == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        // TODO: Check the format of the newPSW
        if (!Utils.isValidPSW(newPSW)){
            throw new IllegalArgumentException("Invalid password");
        }

        // send the authentication code to the user
        code = Utils.get_authenticationCode();
        Utils.send_EmailCode(username, code, "SAAS重置密码验证码");
    }

    public int verify (String username, String newPSW, String v_code) throws Exception {
        String userID =Utils.get_sha256Hex(username);

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
        Utils.send_EmailCode(username,"SAAS密码已修改", "SAAS安全验证");


        return GlobalVariables.RESET_PSW_SUCCESS;
    }
}
