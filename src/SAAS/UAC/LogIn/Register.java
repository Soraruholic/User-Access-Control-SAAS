package SAAS.UAC.LogIn;

import SAAS.Database.Database;
import SAAS.UAC.LogIn.Utils.*;
import SAAS.Utils.GlobalVariables;

public class Register {
    protected static String code;
    public static void register(String userID, String userName, String PSW, String email, String phoneNumber) throws Exception {
        // Do some check
        // if the userName is null
        if (userName == null) {
            throw new IllegalArgumentException("The userName is null");
        }

        // If the userID is null, then use the SHA256 of userName as the userID
        String user_ID = userID;
        if (userID == null || userID.isEmpty()) {
            user_ID = Utils.get_sha256Hex(userName);
        }

        // Check the length of the userName
        if (userName.length() < 3 || userName.length() > 15) {
            throw new IllegalArgumentException("The length of userName should be between 3 and 15");
        }

        // Check whether the userName include both digit and letter
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (int i = 0; i < userName.length(); i++) {
            if (Character.isDigit(userName.charAt(i))) {
                hasDigit = true;
            }
            if (Character.isLetter(userName.charAt(i))) {
                hasLetter = true;
            }
        }
        if (!hasDigit || !hasLetter) {
            throw new IllegalArgumentException("The userName should include both digit and letter");
        }

        // Check if the user is already registered
        if (Database.select_userBasic(user_ID) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        // TODO: Do some check with the password

        // (DONE) Do some check with the email
        if (!Utils.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }

        // (DONE) Do some check with the phoneNumber
        if (!Utils.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        // (DONE) Send and verify the authentication code through the email
        code = Utils.get_authenticationCode();
        Utils.send_EmailCode(email, code, "SAAS注册验证码");
    }

    public int verify(String userID, String userName, String PSW, String email, String phoneNumber, String v_code) throws Exception {
        String user_ID = userID;
        if (userID == null || userID.isEmpty()) {
            user_ID = Utils.get_sha256Hex(userName);
        }

        // Check whether the v_code equals to the code
        if (!v_code.equals(code)) {
            return GlobalVariables.VERIFY_CODE_ERROR;
        }
        // (DONE) Add the user to the database
        // Record current UTC time into a string
        String lastPSWChange = Utils.get_currentTime();
        Database.insert_userBasic(user_ID, userName, PSW, email, phoneNumber, lastPSWChange, false);

        // Check if the user is successfully added to the database
        if (Database.select_userBasic(user_ID) == null) {
            throw new IllegalArgumentException("Failed to add the user to the database");
        }
        return GlobalVariables.REGISTER_SUCCESS;
    }
}
