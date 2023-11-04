//package SAAS.UAC.LogIn;
//
//
//import SAAS.UAC.UPR.User;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;
//
//
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
//                case "1"-> log(list);
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
//        user.setPassword(sha(password));
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
////    private static void register(ArrayList<User>list) {
////        // System.out.println("注册");
////        //用户信息添加到集合
////        Scanner sc = new Scanner(System.in);
////        String username;
////        while (true) {
////
////
////            System.out.println("请输入用户名");
////            username = sc.next();
////            //验证格式正确
////            //长度3-15位
////            //字母加数字
////            // 用户名唯一
////            boolean flag =  checkusername(username);
////            if(!flag){
////                System.out.println("不格式满足");
////                continue;
////            }
////            boolean flag1 = contains(list,username);
////            if(flag1){
////                //用户名已存在,重新输入
////                System.out.println("用户"+username+"已存在"
////                );
////            }else{
////                System.out.println("用户"+username+"不存在");
////                break;
////            }
////        }
////        //2.键盘录入密码
////        String password;
////        while (true) {
////            System.out.println("请输入密码");
////            password = sc.next();
////            System.out.println("请再次输入密码"
////            );
////            String password2 = sc.next();
////            if(!password.equals(password2)){
////                System.out.println("两次密码不一致,请重新登录");
////            }else{
////                System.out.println("两次密码一致");
////                break;
////            }
////        }
////        //3.键盘录入邮箱
////        String email;
//////        while (true) {
////        System.out.println("请输入邮箱号");
////        email = sc.next();
//////            boolean flag = checkEmail(email);
//////            if(flag){
//////                System.out.println("邮箱号正确");
//////                break;
//////            }else{
//////                System.out.println("邮箱号输入错误,请重新输入");
//////            }
//////        }
////        //4.键盘录入手机号
////        String phoneNumber;
////        while(true) {
////            System.out.println("请输入手机号");
////            phoneNumber =  sc.next();
////            boolean flag = checkPhonenumber(phoneNumber);
////            if(flag){
////                System.out.println("手机号码正确");
////                break;
////            }else{
////                System.out.println("手机号错误");
////
////            }
////        }
////        String password_sha = sha(password);
////        //数据放入对象
////        User u = new User(username, null,null,null,null,null, email, password_sha, phoneNumber) {
////        };
////        //放入集合
////        list.add(u);
////        System.out.println("注册成功");
////
////    }
//
//
//    public static String sha(String data) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hash = md.digest(data.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
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
////    private static boolean checkEmail(String email) {
////
////        //不能0开头
////        //boolean flag = personID.startsWith("0");
////        if(email.startsWith("0")){
////            return false;
////        }
////        //前面17位是数字
////        for (int i = 0; i < email.length()-1; i++) {
////            char c = email.charAt(i);
////            if(!((c>= '0' && c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z'))){
////
////                return false;
////            }
////        }
////        //最后一位可以是数字或者x
////        char endchar = email.charAt(email.length()-1);
////        return (endchar >= '0' && endchar <= '9') || (endchar == 'x') || (endchar == 'X');
////    }
//
//    private static boolean contains(ArrayList<User> list, String username) {
//        for (User user : list) {
//            String rightname = user.getEmail();
////            if (rightname.equals(user.email)) {
////                return true;
////            }
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
//    private static void log(ArrayList<User>list) {
//        // System.out.println("登录");
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入邮箱");
//        String email = sc.next();
//        //判断是否存在
//        boolean flag = contains(list,email);
//        if(!flag){
//            System.out.println("用户"+email +"没有注册");
//            return;
//        }
//
//        System.out.println("请输入密码");
//        String password = sc.next();
//
//        String rightcode = getCode();
//        System.out.println("当前验证码为"+rightcode);
//
//
////	创建邮件对象
//        try {
//            Session session=Mail.createSession();
//            System.out.println(session);
//
//            //2.创建邮件对象
//            MimeMessage message=new MimeMessage(session);
//            //设置邮件主题
//            message.setSubject("SAAS登录验证码");
//            //设置邮件内容
//            message.setText(rightcode);
//            //设置发件人
//            message.setFrom(new InternetAddress("rdammt766@163.com"));
//            //设置收件人
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
//
//            //3.发送邮件
//            Transport.send(message);
//        } catch (AddressException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//
//
//        System.out.println("请输入验证码");
//        String code = sc.next();
//
//        if(code.equalsIgnoreCase(rightcode)){
//            System.out.println("输入正确");
//        }
//        else{
//            System.out.println("验证码错误");
//
//        }
//
//        //定义一个方法,判断用户名和密码
//        //封装,零散的数据封装为对象
//        User userinfo = new User(null, null,null,null,null,null,email, sha(password), null) {
//        };
//        boolean result = checkuserinfo(list,userinfo);
//        if(result){
//            System.out.println("登录成功");
//            System.out.println(userinfo.getPassword());
//        }else {
//            System.out.println("登录失败,密码或账户错误");
//
//        }
//    }
//
//    private static boolean checkuserinfo(ArrayList<User> list, User userinfo) {
//        //遍历2
//        for (User user : list) {
//            if (user.getEmail().equals(userinfo.getEmail()) && (user.getPassword().equals(userinfo.getPassword()))) {
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