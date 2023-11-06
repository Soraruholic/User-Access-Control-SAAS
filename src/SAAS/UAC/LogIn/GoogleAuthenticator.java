package SAAS.UAC.LogIn;

import SAAS.Utils.GlobalVariables;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class GoogleAuthenticator {
    //    public static void main(String[] args) throws Exception {
//        initAuthentication("TENANT1");
//        int code = verifyTOTPCode("TENANT1", "274150");
//        if (code == GlobalVariables.GOOGLE_AUTHENTICATION_SUCCESS) {
//            System.out.println("Success");
//        } else {
//            System.out.println("Failure");
//        }
//    }
    public static int verifyTOTPCode(String userID, String code) {
        String secretKey = generateSecretKey(userID);
        String totpCode = getTOTPCode(secretKey);
        if (totpCode.equals(code)) {
            return GlobalVariables.GOOGLE_AUTHENTICATION_SUCCESS;
        } else {
            return GlobalVariables.GOOGLE_AUTHENTICATION_FAILURE;
        }
    }
    public static void initAuthentication(String userID) throws Exception {
        String secretKey = generateSecretKey(userID);
        String qrcode = spawnScanQRString(userID, secretKey, "BIT-SAAS");
        String base64 = createQRCode(qrcode, "test.png", 200, 200);
        System.out.println("base64:" + base64);
        // Utils.send_EmailCode(userID, base64, "SAAS-2FA-QRCode");
    }

    private static String generateSecretKey(String userID) {
        String userID_SHA = Utils.get_sha256Hex(userID);
        byte[] secretKey = userID_SHA.substring(0, 16).getBytes();
        Base32 base32 = new Base32();
        return base32.encodeToString(secretKey);
    }
    private static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
    private static String spawnScanQRString(String account, String secretKey, String title) {
        return "otpauth://totp/"
                + URLEncoder.encode(title + ":" + account, StandardCharsets.UTF_8).replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(title, StandardCharsets.UTF_8).replace("+", "%20");
    }
    private static String createQRCode(String barCodeData, String outPath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

        ByteArrayOutputStream bof = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", bof);
        String base64 = imageToBase64(bof.toByteArray());
        if(outPath!=null&&!outPath.equals("")) {
            try (FileOutputStream out = new FileOutputStream(outPath)) {
                MatrixToImageWriter.writeToStream(matrix, "png", out);
            }
        }
        return base64;
    }
    private static String imageToBase64(byte[] dataBytes) {
        if (dataBytes != null) {
            // 对字节数组Base64编码
            String encoded = Base64.getEncoder().encodeToString(dataBytes);
            return "data:image/jpeg;base64," + encoded;// 返回Base64编码过的字节数组字符串
        }
        return null;
    }

}