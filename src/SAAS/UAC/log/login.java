//package SAAS.UAC.log;
//
//
//import SAAS.UAC.UPR.User;
//
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;
//import java.security.*;
//import java.nio.charset.StandardCharsets;
//
//
//
//// TODO: connect to mysql database
//// TODO: add email verification
//// TODO: add phone number verification
//// TODO: use sha instead of MD5
//// TODO: test cases
//
//public class login {
//    public static void main(String[] args) {
//        ArrayList<User>list = new ArrayList<>();
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("欢迎来到SAAS");
//            System.out.println("请选择操作: 1.登录 2.注册 3.忘记密码");
//
//            String choose = sc.next();
//            switch (choose){
//                case "1"-> login(list);
//                case "2"-> register(list);
//                case "3"-> forgotpassword(list);
//                case "4"-> {
//                    System.out.println("退出");
//                    System.exit(0);
//
//                }
//                default -> System.out.println("没有这个选项");
//
//            }
//        }
//    }
//
//    private static void forgotpassword(ArrayList<User>list) {
//        // System.out.println("忘记密码");
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入用户名"
//        );
//        String username = sc.next();
//        boolean flag = contains(list,username);
//        if(!flag){
//            System.out.println("error");
//            return ;
//        }
////        System.out.println("请输入身份证号");
////        String personID = sc.next() ;
//        System.out.println("请输入手机号");
//        String phonenumber = sc.next();
//
//        int index = findindex(list,username);
//        User user = list.get(index);
//
//        if(!(user.getPhonenumber().equalsIgnoreCase(phonenumber))){
//            System.out.println("输入有误,不能修改");
//            return;
//        }
//        String password;
//        String password2;
//        while (true) {
//            System.out.println("请输入新密码");
//            password = sc.next();
//            System.out.println("请再次输入密码");
//            password2 = sc.next();
//            if(password.equals(password2)){
//                System.out.println("两次密码输入一致");
//                break;
//            }
//            else{
//                System.out.println("两次密码输入不一致,重新输入");
//            }
//        }
//        user.setPassword(md5(password));
//        System.out.println("修改成功");
//
//    }
//
//    private static int findindex(ArrayList<User> list, String username) {
//        for (int i = 0; i < list.size(); i++) {
//            User user = list.get(i);
//            if(user.getUsername().equals(username)){
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private static void register(ArrayList<User>list) {
//        // System.out.println("注册");
//        //用户信息添加到集合
//        Scanner sc = new Scanner(System.in);
//        String username;
//        while (true) {
//
//
//            System.out.println("请输入用户名");
//            username = sc.next();
//            //验证格式正确
//            //长度3-15位
//            //字母加数字
//            // 用户名唯一
//            boolean flag =  checkusername(username);
//            if(!flag){
//                System.out.println("不格式满足");
//                continue;
//            }
//            boolean flag1 = contains(list,username);
//            if(flag1){
//                //用户名已存在,重新输入
//                System.out.println("用户"+username+"已存在"
//                );
//            }else{
//                System.out.println("用户"+username+"不存在");
//                break;
//            }
//        }
//        //2.键盘录入密码
//        String password;
//        while (true) {
//            System.out.println("请输入密码");
//            password = sc.next();
//            System.out.println("请再次输入密码"
//            );
//            String password2 = sc.next();
//            if(!password.equals(password2)){
//                System.out.println("两次密码不一致,请重新登录");
//            }else{
//                System.out.println("两次密码一致");
//                break;
//            }
//        }
////        //3.键盘录入身份证
////        String personID;
////        while (true) {
////            System.out.println("请输入身份证号");
////            personID = sc.next();
////            boolean flag = checkPersonID(personID);
////            if(flag){
////                System.out.println("身份证正确");
////                break;
////            }else{
////                System.out.println("身份证输入错误,请重新输入");
////            }
////        }
//        //4.键盘录入手机号
//        String phoneNumber;
//        while(true) {
//            System.out.println("请输入手机号");
//            phoneNumber =  sc.next();
//            boolean flag = checkPhonenumber(phoneNumber);
//            if(flag){
//                System.out.println("手机号码正确");
//                break;
//            }else{
//                System.out.println("手机号错误");
//
//            }
//        }
//        String passwordMd5 = md5(password);
//        //数据放入对象
//        User u = new User(username, null,null,null,null,null, passwordMd5, phoneNumber) {
//        };
//        //放入集合
//        list.add(u);
//        System.out.println("注册成功");
//
//    }
//
//
//    public static String md5(String data) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] md5 = md.digest(data.getBytes(StandardCharsets.UTF_8));
//
//            // 将处理后的字节转成 16 进制，得到最终 32 个字符
//            StringBuilder sb = new StringBuilder();
//            for (byte b : md5) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private static boolean checkPhonenumber(String phoneNumber) {
//        if(phoneNumber.length()!= 11){
//            return false;
//        }
//        // boolean flag = phonenumber.startsWith("0");
//        if(phoneNumber.startsWith("0")){
//            return false;
//        }
//        for (int i = 0; i < phoneNumber.length(); i++) {
//            char c=  phoneNumber.charAt(i);
//            if(!(c>= '0' && c<='9')){
//                return false;
//            }
//        }return true;
//    }
//
////    private static boolean checkPersonID(String personID) {
////        if(personID.length()!=18){
////            return false;
////        }
////        //不能0开头
////        //boolean flag = personID.startsWith("0");
////        if(personID.startsWith("0")){
////            return false;
////        }
////        //前面17位是数字
////        for (int i = 0; i < personID.length()-1; i++) {
////            char c = personID.charAt(i);
////            if(!(c>= '0' && c<='9')){
////                return false;
////            }
////        }
////        //最后一位可以是数字或者x
////        char endchar = personID.charAt(personID.length()-1);
////        return (endchar >= '0' && endchar <= '9') || (endchar == 'x') || (endchar == 'X');
////    }
//
//    private static boolean contains(ArrayList<User> list, String username) {
//        for (SAAS.UAC.UPR.User user : list) {
//            String rightname = user.getUsername();
//            if (rightname.equals(username)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static boolean checkusername(String username) {
//        int len = username.length();
//        if(len<3 ||len>15){
//            return false;
//        }
//        //循环得到字符,判断是否又不是字母
//        for (int i = 0; i < username.length(); i++) {
//            char c = username.charAt(i);
//            if(!((c>='a' && c <= 'z')||(c>= 'A' && c<='Z')||(c >= '0'&& c<='9'))){
//                //长度满足,内容满足
//                //不能纯数字
//                //统计多少个字母
//                return false;
//            }
//        }int count = 0;
//        for (int i = 0; i < username.length(); i++) {
//            char c = username.charAt(i);
//            if((c>='a' && c <= 'z')||(c>= 'A' && c<='Z')){
//                count ++;
//                break;
//            }
//        }
//        return count >0;
//    }
//
//    private static void login(ArrayList<User>list) {
//        // System.out.println("登录");
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入用户名");
//        String username = sc.next();
//        //判断是否存在
//        boolean flag = contains(list,username);
//        if(!flag){
//            System.out.println("用户"+username +"没有注册");
//            return;
//        }
//
//        System.out.println("请输入密码");
//        String password = sc.next();
//
//        while (true) {
//            String rightcode = getCode();
//            System.out.println("当前验证码为"+rightcode);
//            System.out.println("请输入验证码");
//            String code = sc.next();
//
//            if(code.equalsIgnoreCase(rightcode)){
//                System.out.println("输入正确");
//                break;
//            }
//            else{
//                System.out.println("验证码错误");
//
//            }
//        }
//        //定义一个方法,判断用户名和密码
//        //封装,零散的数据封装为对象
//        User userinfo = new User(username, null,null,null,null,null, md5(password), null) {
//        };
//        boolean result = checkuserinfo(list,userinfo);
//        if(result){
//            System.out.println("登录成功");
//        }else {
//            System.out.println("登录失败,密码或用户名错误");
//
//        }
//    }
//
//    private static boolean checkuserinfo(ArrayList<User> list, User userinfo) {
//        //遍历
//        for (SAAS.UAC.UPR.User user : list) {
//            if (user.getUsername().equals(userinfo.getUsername()) && (user.getPassword().equals(userinfo.getPassword()))) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    private static String getCode(){
//        //1.创建集合
//        ArrayList<Character> list = new ArrayList<>();
//        for (int i = 0; i < 26; i++) {
//            list.add((char)('a'+i));
//            list.add((char)('A'+i));
//
//        }
//        StringBuilder sb = new StringBuilder();
//        //2.随机抽取4个
//        Random r = new Random();
//        for (int i = 0; i < 4; i++) {
//            int index = r.nextInt(list.size());
//            char c = list.get(index);
//            sb.append(c);
//        }
//        //3.随机数字
//        int number =  r.nextInt(10);
//        sb.append(number);
//        //4.数字出现在任意位置
//        //先把字符串变成字符数组,然后修改
//        char []arr = sb.toString().toCharArray();
//        int randomindex = r.nextInt(arr.length);
//        char temp = arr[randomindex];
//        arr[randomindex] = arr[arr.length-1];
//        arr[arr.length-1]=temp;
//        return new String(arr);
//    }
//}