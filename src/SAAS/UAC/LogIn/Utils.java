package SAAS.UAC.LogIn;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String get_sha256Hex(String str) {
        Digest sha256Digest = new SHA256Digest();
        sha256Digest.update(str.getBytes(), 0, str.length());
        byte[] sha256Hash = new byte[sha256Digest.getDigestSize()];
        sha256Digest.doFinal(sha256Hash, 0);
        return new String(Hex.encode(sha256Hash));
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[1-9]\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    private static String convertUTCToString(Date utcDate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(utcDate);
    }
    private static Date convertStringToUTC(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(dateString);
    }
    public static double getUTCDifference(String utcTimeString1, String utcTimeString2, String format, double step) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date1 = dateFormat.parse(utcTimeString1);
        Date date2 = dateFormat.parse(utcTimeString2);

        long timeDifferenceInMilliseconds = date2.getTime() - date1.getTime();

        return timeDifferenceInMilliseconds / step;
    }
    public static String get_currentTime() {
        Date date = new Date();
        return convertUTCToString(date, "yyyy-MM-dd HH:mm:ss");
    }
    public static double get_diff(String utcTimeString1, String utcTimeString2) throws ParseException {
        return getUTCDifference(utcTimeString1, utcTimeString2, "yyyy-MM-dd HH:mm:ss", 1.0 * 24 * 60 * 60 * 1000);
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPSW(String PSW) {
        String pattern = "^(?![0-9A-Za-z]+$)(?![0-9A-Z\\W]+$)(?![0-9a-z\\W]+$)(?![A-Za-z\\W]+$)[0-9A-Za-z~!@#$%^&*()_+`\\-={}|\\[\\]\\\\:\";'<>?,./]{8,}$";
        return Pattern.matches(pattern, PSW);

    }


    public static String get_authenticationCode(){
        //1.创建集合
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));

        }
        StringBuilder sb = new StringBuilder();
        //2.随机抽取4个
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        //3.随机数字
        int number =  r.nextInt(10);
        sb.append(number);
        //4.数字出现在任意位置
        //先把字符串变成字符数组,然后修改
        char []arr = sb.toString().toCharArray();
        int randomindex = r.nextInt(arr.length);
        char temp = arr[randomindex];
        arr[randomindex] = arr[arr.length-1];
        arr[arr.length-1]=temp;
        return new String(arr);
    }
    public static void send_EmailCode(String userID, String code, String subject) {
        try {
            Session session = Mail.createSession();
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 设置邮件主题
            message.setSubject(subject);
            // 设置邮件内容
            message.setText(code);
            // 设置发件人
            message.setFrom(new InternetAddress("rdammt766@163.com"));
            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userID));

            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
