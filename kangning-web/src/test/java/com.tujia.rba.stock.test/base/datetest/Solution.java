package com.tujia.rba.stock.test.base.datetest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author 加康宁 Date: 2019-02-23 Time: 16:13
 * @version $Id$
 */
class Solution {

    public static Integer primePalindrome(int N) {
        int i = N;
        //检查参数
        if (1 > i || i > 100000000){
            return 0;
        }
        //特殊处理
        if (i == 1){
            return 2;
        }

        while (true){
            //数字特殊判断
            if (i == Integer.MAX_VALUE || i > 200000000){
                return 0;
            }

            String str = "" + i;
            char c = str.charAt(0);
            char c_e = str.charAt(str.length() - 1);
            if ((c == '2' && i > 2) || (c == '5' && i > 5) || c == '4' || c == '6' || c == '8' ){
                i = (int) ((Integer.valueOf(c + "") + 1) * Math.pow(10, str.length() -1));
                continue;
            }
            if (c_e == '0'){
                i++;
                continue;
            }

            if (isHuiWenShu(i) && isSuShu(i)){
                return i;
            }
            i++;
        }
    }


    private static boolean isSuShu_1(int i) {
        String str = "" + i;
        char c = str.charAt(0);
        if (c == '0' || (c == '2' && i > 2) || (c == '5' && i > 5) || c == '4' || c == '6' || c == '8' ){
            return false;
        }
        return true;
    }

    /**
     * 判断是否是回文数
     * @param i
     * @return
     */
    private static boolean isHuiWenShu(int i) {
        String number = i + "" ;
        int midLength = (int) Math.ceil(number.length() / 2);
        for (int j = 0;j < midLength;j++){
            if (number.charAt(j) != number.charAt(number.length() - j - 1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是素数
     * @param i
     * @return
     */
    private static boolean isSuShu(int i) {
        int j = 2;
        while (j < i){
            if (i%j == 0){
                return false;
            }
            if (j > (i/2)){
                return true;
            }
            j++;
        }
        return true;
    }


    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0){
            return 0;
        }
        int length = 0;
        for (int i =0; i < nums.length;i++){
            if (i == nums.length -1){
                nums[length] = nums[i];
                length++;
            }else if (nums[i] != nums[i+1]){
                nums[length] = nums[i];
                length++;
            }
        }

        return length;
    }

    public static int lengthOfLongestSubstring(String s) {
        List<Character> characters = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++){
            Character character = s.charAt(i);
            if (!characters.contains(character)){
                characters.add(s.charAt(i));
            }else {
                max =  Math.max(max,characters.size());
                int n = characters.indexOf(character);
                for (int m = n; m >= 0;m--){
                    characters.remove(m);
                }
                characters.add(character);
            }
        }
        return Math.max(max,characters.size());
    }

    public static void main(String[] args) {
        Set<Object> set = new HashSet<>();
        set.add(new Object());
        System.out.println(( 0 +(int)'a' + (int)'d' )== (0 + (int)'b' + (int)'c'));
    }

}
