package com.hust.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenPicCodeUtil {

    private static final int width = 90;// 定义图片的width
    private static final int height = 20;// 定义图片的height
    private static final int codeCount = 4;// 定义图片上显示验证码的个数
    private static final int xx = 15;
    private static final int fontHeight = 18; //字体高度
    private static final  int codeY = 16;
    private static final String srcPre = "data:image/jpg;base64,"; //返回base64码的前缀
    private static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static final Random random = new Random();

    public static Map<String,Object> generateCodeAndPic() {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);

//        // 画边框。
//        gd.setColor(Color.BLACK);
//        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0;// 设置三原色
        int green = 0;// 设置三原色
        int blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);// 设置三原色
            green = random.nextInt(255);// 设置三原色
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red ,green, blue));// 设置三原色
//            gd.setColor(new Color(red)); //只用红色
            gd.drawString(code, (i + 1) * xx, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        Map<String,Object> map  =new HashMap<String,Object>();
        try {
            //存放验证码
            map.put("code", randomCode.toString());
            //存放生成的验证码BufferedImage对象
            map.put("codeBase64", transform(buffImg));
        } catch (IOException e) {
            //输出异常
        }
        return map;
    }

    /**
     * 转换为base64.
     * @param bImg BufferedImage
     * @return String
     * @throws IOException Exception
     */
    public static String transform (BufferedImage bImg) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(bImg, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String jpg_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        jpg_base64 = jpg_base64.replace("\n", "").replace("\r", "");//删除\r\n
        return srcPre + jpg_base64;
    }

}