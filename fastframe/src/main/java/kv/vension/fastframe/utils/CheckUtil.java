package kv.vension.fastframe.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    /**
     * 判断两个string是否相等
     */
    public static boolean checkEquels(Object strObj0, Object strObj1) {
        String str0 = strObj0 + "";
        String str1 = strObj1 + "";
        if (str0.equals(str1)) {
            return true;
        }
        return false;
    }
    /**
     * 判断两个string是否相等
     */
    public static boolean checkEqual(String str1, String str2) {
        return !(str1==null||str2==null)&&str1.equals(str2);
    }


    /**
     * 同时判断多个参数是否为空
     *
     * @param strArray
     * @return
     */
    public static boolean isNull(String... strArray) {
        for (String obj : strArray) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判是否是字母
     *
     * @return
     */
    public static boolean isLetter(String txt) {
        if (isNull(txt)) {
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串对象是否为空
     */
    public static boolean isNull(String strObj) {
        return (strObj == null || "".equals(strObj));
    }

    /**
     * 判断是否邮箱
     *
     * @param strObj
     * @return
     */
    public static boolean checkEmail(Object strObj) {
        String str = strObj + "";
//        if (!str.endsWith(".com")) {
//            return false;
//        }
        String match = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为电话号码
     * 判断范围
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通信：1349
     */
    public static boolean checkPhone(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phoneNumber.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNumber);
            return m.matches();
        }
    }

    /**
     * 检查内容的长度是否大于等于要求
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLength(Object strObj, int length) {
        String str = strObj + "";
        if (str.length() >= length) {
            return true;
        }
        return false;
    }

    /**
     * 检查字符串的长度
     *
     * @param strObj
     * @param length
     * @return
     */
    public static boolean checkLengthEq(Object strObj, int length) {
        String str = strObj + "";
        if (str.length() == length) {
            return true;
        }
        return false;
    }

    /**
     * @param @param  strObj
     * @param @param  min
     * @param @param  max
     * @return boolean    返回类型
     * @throws
     * @Title: checkNum
     * @Description: 检查是否为数字，以及这个数在min与max之间，包含min与max
     */
    public static boolean checkNum(Object strObj, int min, int max) {
        String str = strObj + "";
        try {
            int number = Integer.parseInt(str);
            if (number <= max && number >= min) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param @param  strObj
     * @param @param  min
     * @param @param  max
     * @return boolean    返回类型
     * @throws
     * @Title: checkNumWithDecimal
     * @Description: 检查是否为数字，以及这个数在min与max之间，包含min与max,可以为小数
     */
    public static boolean checkNumWithDecimal(Object strObj, float min, float max) {
        String str = strObj + "";
        try {
            float number = Float.parseFloat(str);
            if (number <= max && number >= min) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param @param  strObj
     * @param @param  start
     * @param @param  end
     * @return boolean    返回类型
     * @throws
     * @Title: checkLength
     * @Description: 检查字符串的长度范围是否符合要求
     */
    public static boolean checkLength(Object strObj, int start, int end) {
        String str = strObj + "";
        if (str.length() >= start && str.length() <= end) {
            return true;
        }
        return false;
    }

    /**
     * @param @param  strObj
     * @param @param  num  倍数
     * @return boolean    返回类型
     * @throws
     * @Title: checkMoney
     * @Description: 检查金额是否为数字以及是否为一个数的倍数
     */
    public static boolean checkMoney(Object strObj, int num) {
        String str = strObj + "";
        try {
            int money = Integer.parseInt(str);
            if (money % num == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 检查是否是网络链接
     *
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        if (isNull(url)) {
            return false;
        }
        String regular = "(http|https)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    /**
     * 检测文件链接
     * @param url
     * @return
     */
    public static boolean checkFileURL(String url) {
        if (isNull(url)) {
            return false;
        }
        String regular = "(file)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
