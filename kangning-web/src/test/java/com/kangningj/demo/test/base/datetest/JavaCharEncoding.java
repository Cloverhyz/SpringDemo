package com.kangningj.demo.test.base.datetest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 这里研究了 java 的内部的字符编码的方式.
 *
 * @author jianhong.li Date: 2018-12-04 Time: 1:27 PM
 * @version $Id$
 */
public class JavaCharEncoding {

    public static void main(String[] args) throws UnsupportedEncodingException {
        char x = '中';
        /** 可以打印出一个 char 的值:中 */
        System.out.println(String.format("%c", x));
        int y = (int) x;
        /** 可以看到一个 char 转为 int 时打印其值为: 0x4e2d */
        System.out.println(String.format("0x%x", y));
        short z = (short) x;
        System.out.println(String.format("0x%x", z));

        char c = 'a';
        System.out.println(String.format("%c", c));
        y = (int) c;
        /** 可以看到一个 char 转为 int 时打印其值为: 0x61 */
        System.out.println(String.format("0x%x", y));
        z = (short) c;
        System.out.println(String.format("0x%x", z));

        /**
         * UTF16 编码格式:<a>https://www.cnblogs.com/dragon2012/p/5020259.html</a>
         * 这里测试一个字符在 java 中占用了几个字节.几个字符(char)
         */
        String symbol = "😋";
        //utf-16: 0xd8 0x3d 0xde 0xb --> 转换为 code point 为:
        System.out.println("bytes(utf-16):" + printInnerCodeHex(symbol));
        //utf-8:  0xf0 0x9f 0x98 0x8b
        System.out.println("bytes(utf-8 ):" + printHex(symbol.getBytes(StandardCharsets.UTF_8)));
        //utf-32: 0x00 0x01 0xf6 0x0b
        System.out.println("bytes(utf-32):" + printHex(symbol.getBytes("UTF-32")));

        /**
        如上:
        表情符号(笑脸)的 unicode code point 应该是: 0x00 0x01 0xf6 0x0b
        下面为 utf-8的编码规则.

        即普通 ascii 码为1字节直接表示.
        1.5字节的 unicode 为 两字节 utf-8 表示
        1.5字节以上 到 2字节的 unicode 用 三字节的 utf-8 表示.

        那表情的 unicode 为:0x00 0x01 0xf6 0x0b , 占用了2.5字节,因此需要4个 utf-8 byte 表示.
        先写为二进制: 0000,0000 | 0000,0001 | 1111,0110 | 0000,1011 再重新划分二进制位.

        0000,0000|0000,00 01|1111, 0110|00 00,1011
        ............. 000  011111   011000  001011
        .............____ _______  _______ _______
        ........11110____10______10______10_______
        ........11110000,10011111,10011000,10001011

        最终得到:0xF0,0x9f,0x98,0x8b (与程序输出一致)

        u-00000000 - U-0000007F: 0xxxxxxx
        U-00000080 - U-000007FF: 110xxxxx 10xxxxxx
        U-00000800 - U-0000FFFF: 1110xxxx 10xxxxxx 10xxxxxx
        U-00010000 - U-001FFFFF: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx <----------------
        U-00200000 - U-03FFFFFF: 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
        U-04000000 - U-7FFFFFFF: 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx

        单字节可编码的Unicode码点值范围十六进制为0x0000 ~ 0x007F，十进制为0 ~ 127；
        双字节可编码的Unicode码点值范围十六进制为0x0080 ~ 0x07FF，十进制为128 ~ 2047；
        三字节可编码的Unicode码点值范围十六进制为0x0800 ~ 0xFFFF，十进制为2048 ~ 65535；
        四字节可编码的Unicode码点值范围十六进制为0x10000 ~ 0x1FFFFF，十进制为65536 ~

        最后看一下 utf-16的编码格式如何得来:
        内码,unicode code point 应该是: 0x00 0x01 0xf6 0x0b
        那这个已经是非 BMP 平面的基础字符.因此需要使用代理映射到基础平面.
        根据以下方法:减去0x10000得到 0000,1111,01 10,0000,1011
                                  ____________|____________
                                  0000111101  |10,0000,1011

                                  00,0011,1101|10,0000,1011

                                  +0xD800     + 0xDC00
                                  0xD83D , 0xDE0B (最终结果,与上面打印值一致)
        --------------------------------------------------------------------------------------------------------------

        辅助平面(Supplementary Planes)中的码位，大于等于0x10000，在UTF-16中被编码为一对16比特长的码元（即32bit，4Bytes），
        称作 code units called a 代理对（surrogate pair），具体方法是：

        Ø 码位减去0x10000, 得到的值的范围为20比特长的0..0xFFFFF（因为Unicode的最大码位是0x10ffff，减去0x10000后，
        得到的最大值是0xfffff，所以肯定可以用20个二进制位表示），写成二进制形式：yyyy yyyy yyxx xxxx xxxx。

        Ø 高位的10比特的值（值的范围为0..0x3FF）被加上0xD800得到第一个码元或称作高位代理（high surrogate）,
        值的范围是0xD800..0xDBFF。由于高位代理比低位代理的值要小，所以为了避免混淆使用，Unicode标准现在称高位代理为前导代理(lead surrogates)。

        Ø 低位的10比特的值（值的范围也是0..0x3FF）被加上0xDC00得到第二个码元或称作低位代理（low surrogate）,
        现在值的范围是0xDC00..0xDFFF。 由于低位代理比高位代理的值要大，所以为了避免混淆使用，Unicode标准现在称低位代理为后尾代理(trail surrogates)。

        Ø 最终的UTF-16（4字节）的编码（二进制）就是：110110yyyyyyyyyy 110111xxxxxxxxxx。
        * */

        /**
         * 通过这里理解,java 里面只有一种字符编码.没有其它编码.String 内部永远存储的是 UTF-16
         */
        String s1 = "你好";
        String s2 = new String(s1.getBytes("GB2312"), StandardCharsets.ISO_8859_1);
        String s3 = new String(s2.getBytes(StandardCharsets.ISO_8859_1), "GB2312");

        System.out.println("你好1:" + s2);
        System.out.println("你好2:" + s3);


       /**
        https://ctext.org/dictionary.pl?if=en&char=%F0%A4%B6%BE&remap=gb
        怎样把 unicode 值转为 utf-16 (或者说是转换为 char 的编码)
        这里有一个汉字:'𤶾',属于康熙字典中的非常用汉字,其 unicode 超出了两字节的 unicode 可以表示的范围.
        这个字符你直接在string 里面粘出来不是直接显示的汉字.而是显示的两个码元 (code unit) ( 码元为 UTF-16编码格式 )
        其编码十六进制为: 0x24dbe
        用上面的方法进行编码转换:
            0010 0100,1101 1011,1110
          - 0001 0000 0000 0000 0000
          ___________________________
          = 0001 0100 1101 1011 1110
            0001 0100 11|01 1011 1110
          +  0xd800       + dc00

            00,0101,0011|01,1011,1110
          0000,0000,0101,0011|0000,0001,1011,1110
          d     8     0    0    d    c    0    0
          ________________________________________
          0xd80x53,0xdd0xbe
        * */
        String chinesePi = "\uD853\uDDBE";
        System.out.println(chinesePi);

        // 直接从 unicode inner value convert
        char[] piChars = Character.toChars(0x24dbe);
        System.out.println(new String(piChars));

        /**
         * 如果有如下代码:
         * char ch = '𤶾' // 这个代码有错误,无法编译.
         *
         */
        //char chineseChar = '𤶾';// '\uD853\uDDBE'

        testPrintCodeNotInBMP();

    }

    private static String printHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(String.format("0x%02x ", aByte));
        }
        return sb.toString();
    }

    /**
     * 直接打印一个 String 的存储的内码. 以 HEX 形式直接打印出来
     *
     * @param input 待打印String
     */
    public static String printInnerCodeHex(String input) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean isStart = true;
        if (chars.length > 0) {
            for (char aChar : chars) {
                if (!isStart) {
                    sb.append(" ");
                }
                int codePoint = (int) aChar;
                int cHigh = (codePoint & 0x0000ff00) >> 8;
                int cLow = (codePoint & 0xff);
                sb.append(String.format("0x%02X ", cHigh)).append(String.format("0x%02X", cLow));
                isStart = false;
            }
        }
        return sb.toString();
    }

    /**
     * 测试打印一些非 BMP 区域的文字字符
     */
    private static void testPrintCodeNotInBMP() {
        int code = 0x20000;
        for (int i = 0; i < 20000; i++) {
            code += 1;
            String encodeString = new String(Character.toChars(code));
            System.out.println("code:" + String.format("0x%04x \t", code) + encodeString);
        }

        /* 𤶾 */
        String hanzhi = "\uD853\uDD84";
        System.out.println(hanzhi);
    }
}
