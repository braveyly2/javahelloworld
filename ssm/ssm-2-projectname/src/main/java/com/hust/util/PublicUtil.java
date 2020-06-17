package com.hust.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class PublicUtil {
    /**
     * 判断对象是否Empty(null或元素为0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     *
     * @return boolean 返回的布尔值
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if (pObj == "") {
            return true;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() == 0;
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素大于0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     *
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null) {
            return false;
        }
        if (pObj == "") {
            return false;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() != 0;
        } else if (pObj instanceof Collection) {
            return !((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() != 0;
        }
        return true;
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 把密文转换成十六进制的字符串形式
     * @param bytes 待转换byte数组
     * @return 转换后的十六进制字符串
     */
    public static String byteArray2HexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 16进制的字符串表示转成byte数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] hexString2byteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | (low & 0xff));
            k += 2;
        }
        return byteArray;
    }


    /**
     * The constant STRING_NULL.
     */
    private final static String STRING_NULL = "-";
    /**
     * 匹配手机号码, 支持+86和86开头
     */
    private static final String REGX_MOBILENUM = "^((\\+86)|(86))?\\d{9,20}$";

    /**
     * 匹配邮箱帐号
     */
    private static final String REGX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 匹配手机号码（先支持13, 15, 17, 18开头的手机号码）.
     *
     * @param inputStr the input str
     *
     * @return the boolean
     */
    public static Boolean isMobileNumber(String inputStr) {
        return !PublicUtil.isNull(inputStr) && inputStr.matches(REGX_MOBILENUM);
    }

    /**
     * 判断一个或多个对象是否为空
     *
     * @param values 可变参数, 要判断的一个或多个对象
     *
     * @return 只有要判断的一个对象都为空则返回true, 否则返回false boolean
     */
    public static boolean isNull(Object... values) {
        if (!PublicUtil.isNotNullAndNotEmpty(values)) {
            return true;
        }
        for (Object value : values) {
            boolean flag;
            if (value instanceof Object[]) {
                flag = !isNotNullAndNotEmpty((Object[]) value);
            } else if (value instanceof Collection<?>) {
                flag = !isNotNullAndNotEmpty((Collection<?>) value);
            } else if (value instanceof String) {
                flag = isOEmptyOrNull(value);
            } else {
                flag = (null == value);
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is o empty or null boolean.
     *
     * @param o the o
     *
     * @return boolean boolean
     */
    private static boolean isOEmptyOrNull(Object o) {
        return o == null || isSEmptyOrNull(o.toString());
    }

    /**
     * Is s empty or null boolean.
     *
     * @param s the s
     *
     * @return boolean boolean
     */
    private static boolean isSEmptyOrNull(String s) {
        return trimAndNullAsEmpty(s).length() <= 0;
    }

    /**
     * Trim and null as empty string.
     *
     * @param s the s
     *
     * @return java.lang.String string
     */
    private static String trimAndNullAsEmpty(String s) {
        if (s != null && !s.trim().equals(STRING_NULL)) {
            return s.trim();
        } else {
            return "";
        }
        // return s == null ? "" : s.trim();
    }

    /**
     * 判断对象数组是否为空并且数量大于0
     *
     * @param value the value
     *
     * @return boolean
     */
    private static Boolean isNotNullAndNotEmpty(Object[] value) {
        boolean bl = false;
        if (null != value && 0 < value.length) {
            bl = true;
        }
        return bl;
    }

    /**
     * 判断对象集合（List,Set）是否为空并且数量大于0
     *
     * @param value the value
     *
     * @return boolean
     */
    private static Boolean isNotNullAndNotEmpty(Collection<?> value) {
        boolean bl = false;
        if (null != value && !value.isEmpty()) {
            bl = true;
        }
        return bl;
    }

    /**
     * Is email boolean.
     *
     * @param str the str
     *
     * @return the boolean
     */
    public static boolean isEmail(String str) {
        boolean bl = true;
        if (isSEmptyOrNull(str) || !str.matches(REGX_EMAIL)) {
            bl = false;
        }
        return bl;
    }

    /**
     * Uuid string.
     *
     * @return the string
     */
    public synchronized static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Nonce生成
     * @return 随机正长整形
     */
    public static int getNonce(){
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

    public static long getUtcTimestampOfSecond(){
        return System.currentTimeMillis() / 1000;
    }

    private static int _requestId = 0;

    public static BasicInput getDefaultBasicInput(){
        return new BasicInput();
    }

    public static BasicOutput getDefaultBasicOutputByInput(BasicInput basicInput){
        return new BasicOutput();
    }

    /**
     * 获得请求id
     * 此id仅用于对应请求和应答数据，并发时不同线程产生相等的id没有影响
     * @return 请求id
     */
    public static int getRequestId() {
        if (_requestId == Integer.MAX_VALUE)
            _requestId = 0;
        _requestId++;
        return _requestId;
    }


    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，取X-Forwarded-For中第一个非unknown的有效IP字符串
        if (PublicUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            String[] iplist = ip.split(",");
            for(String item : iplist){
                item = item.trim();
                if (PublicUtil.isNotEmpty(item) && !"unknown".equalsIgnoreCase(item)) {
                    if (ip.length() > 0) {
                        ip = item;
                        break;
                    }
                }
            }
        }
        return ip;
    }


    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 时间字符串转LocalDateTime类型
     * @param timeString
     * @return
     */
    public static LocalDateTime String2LocalDateTime(String timeString) {
        return LocalDateTime.parse(timeString, df);
    }

    /**
     * LocalDateTime类型转时间字符串
     * @param time
     * @return
     */
    public static String LocalDateTime2String(LocalDateTime time) {
        return df.format(time);
    }


    public static String LocalDate2String(LocalDate date) {
        return dfDate.format(date);
    }

    /**
     * 获得UTC时间LocalDateTime
     * @return
     */
    public static LocalDateTime getUTCDate() {
//		return LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.of("UTC"));
        return LocalDateTime.now(ZoneId.of("UTC"));
    }

    /**
     * 获得UTC时间字符串
     * @return
     */
    public static String getUTCDateString(){
        return df.format(getUTCDate());
    }

    /**
     * 将long类型的timestamp转为LocalDateTime UTC时间
     * @param utcTimeMillis
     * @return
     */
    public static LocalDateTime getUTCDate(long utcTimeMillis) {
        Instant instant = Instant.ofEpochMilli(utcTimeMillis);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    /**
     * 将long类型的timestamp转为 UTC时间字符串
     * @param utcTimeMillis
     * @return
     */
    public static String getUTCDateString(long utcTimeMillis){
        return df.format(getUTCDate(utcTimeMillis));
    }

    /**
     * UTC LocalDateTime 转 Long 类型 UTC时间戳
     * @param utcDateTime
     * @return
     */
    public static long getUTCTimeMillis(LocalDateTime utcDateTime){
        return utcDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }

    /**
     * 递归删除文件或目录
     *
     * @param dirPath
     */
    public static void deleteFileOrDir(String dirPath)
    {
        File file = new File(dirPath);
        if(file.isFile())
        {
            file.delete();
        }else
        {
            File[] files = file.listFiles();
            if(files == null)
            {
                file.delete();
            }else
            {
                for (int i = 0; i < files.length; i++)
                {
                    deleteFileOrDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    public static byte[] serialize(Object object) throws IOException {
        if(object == null){
            return null;
        }
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        byte[] getByte = byteArrayOutputStream.toByteArray();
        return getByte;
    }

    public static Object unserizlize(byte[] binaryByte) throws IOException, ClassNotFoundException {
        if(isEmpty(binaryByte)){
            return null;
        }
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        return obj;
    }

    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class) ||
                className.equals(String.class)) {
            return true;
        }
        return false;
    }

    public static String getDevVersion(String devVersionInDB) {
        if (isEmpty(devVersionInDB)) {
            return null;
        }
        try {
            String[] versions = devVersionInDB.split("\\.");
            return versions[0] + "." + versions[1] + "." + versions[2];
        } catch (Exception e) {
            LogUtil.error("将db中的设备版本长串换为精简设备版本时出错", "Public Method", e);
            return null;
        }
    }

    private static String lineSplit = "\n";
    public static String readLocalFile(String filePath){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer content = new StringBuffer();
        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String tcontent = null;
            while ((tcontent = bufferedReader.readLine()) != null) {
                if (PublicUtil.isNotEmpty(tcontent)) {
                    content.append("\n").append(tcontent);
                }
            }
        } catch (Exception e) {
            LogUtil.error("读取文件异常", "", e);
        }
        finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
                LogUtil.error("关闭文件异常", "", e);
            }
        }
        //去掉最后部分
        if (content.toString().startsWith(lineSplit)) {
            return content.toString().replaceFirst(lineSplit, "");
        } else {
            return content.toString();
        }
    }

    public static void writeLocalFile(String filePath, String content){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(content);
        } catch (Exception e) {
            LogUtil.error("写文件异常", "", e);
        }
        finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                LogUtil.error("关闭文件异常", "", e);
            }
        }
    }
}
