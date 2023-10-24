package log;

public class user {
    private  String username;
    private  String passwordMd5;
    private  String personID;
    private  String phonenumber;


    public user() {
    }

    public user(String username, String passwordMd5, String personID, String phonenumber) {
        this.username = username;
        this.passwordMd5 = passwordMd5;
        this.personID = personID;
        this.phonenumber = phonenumber;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return passwordMd5;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.passwordMd5 = password;
    }

    /**
     * 获取
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * 设置
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * 获取
     * @return phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * 设置
     * @param phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


}