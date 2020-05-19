package com.kangningj.demo.test.base.uuid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: hanli_1
 * @Date: 2018/8/7 22:58
 * @Description:
 */
public class IndexUtils {

    public static int getTableIndex(String key) {
        return hash(key) & 127;
    }

    private static int hash(String key) {
        int h = key.hashCode();
        System.out.println("hash code:" + Integer.toBinaryString(h));
        int m = h >>> 20;
        System.out.println("m = " + Integer.toBinaryString(m));
        int n = h >>> 12;
        System.out.println("n = " + Integer.toBinaryString(n));
        h ^= (m) ^ (n);
        System.out.println("h = " + Integer.toBinaryString(h));
        m = h >>> 7;
        System.out.println("m = " + Integer.toBinaryString(m));
        n = h >>> 4;
        System.out.println("n = " + Integer.toBinaryString(n));
        h = h ^ (m) ^ (n);
        System.out.println("h = " + Integer.toBinaryString(h));
        return h;
    }


    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-127));
        System.out.println(Integer.toBinaryString(-126));
        Queue<Integer> windows = new ArrayDeque<>(3);
        windows.add(1);
        windows.add(2);
        windows.add(3);
        windows.add(4);
        System.out.println(windows.peek());
        System.out.println(windows.poll());
        System.out.println(windows.poll());

    }

    public String replaceSpace(StringBuffer str) {
        int i = str.lastIndexOf(" ");
        if (i < 0) {
            return str.toString();
        }
        str.replace(1, i + 1, "%20");
        return replaceSpace(str);
    }

    static int JumpFloor1(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0 || n == 1 || n == 2) {
            return n;
        }
        return JumpFloor1(n - 1) + JumpFloor1(n - 2);
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || pre.length != in.length) {
            return null;
        }
        TreeNode rootNode = new TreeNode(pre[0]);
        if (pre.length == 1) {
            return rootNode;
        }
        int root = pre[0];
        int index = 0;
        for (index = 0; index < in.length; index++) {
            if (in[index] == root) {
                break;
            }
        }
        rootNode.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, index + 1),
                                              Arrays.copyOfRange(in, 0, index));
        rootNode.right = reConstructBinaryTree(Arrays.copyOfRange(pre, index + 1, pre.length),
                                               Arrays.copyOfRange(in, index + 1, in.length));
        return rootNode;
    }

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        TreeNode(int x) {
            val = x;
        }
    }


    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while (stack1.size() > 1) {
            stack2.push(stack1.pop());
        }
        int i = stack1.pop();
        while (stack2.size() > 0) {
            stack1.push(stack2.pop());
        }
        return i;
    }

    public int minNumberInRotateArray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int m = 0;
        for (int i = 0; i < array.length; i++) {
            m = Math.min(array[i], array[i + 1]);
        }
        return m;
    }

    public int JumpFloorII(int target) {
        if (target <= 2) {
            return target;
        }
        int n = target - 1;
        int m = 0;
        while (n > 0) {
            m += JumpFloorII(target - n);
            n--;
        }
        return m + 1;
    }

    /**
     * 矩形覆盖
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * @param target
     * @return
     */
    public int RectCover(int target) {
        if (target <= 2) {
            return target;
        }
        return RectCover(target - 1) + RectCover(target - 2);
    }

    /**
     * 输出该数二进制表示中1的个数。其中负数用补码表示。
     * @param n
     * @return
     */
    public int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    /**
     * 奇偶数排序
     * @param array
     */
    public void reOrderArray1(int[] array) {
        if (array.length == 0) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 1) {
                continue;
            }
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] % 2 == 0 && array[j + 1] % 2 ==1) {
                    int m = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = m;
                }
            }
        }
    }

    public void reOrderArray(int[] array) {
        if (array.length == 0) {
            return;
        }
        ArrayList<Integer> jishu = new ArrayList<>();
        ArrayList<Integer> oushu = new ArrayList<>();
        for (int value : array) {
            if (value % 2 == 1) {
                jishu.add(value);
                continue;
            }
            oushu.add(value);
        }
        jishu.addAll(oushu);
        for (int i = 0; i < array.length; i++) {
            array[i] = jishu.get(i);
        }
    }

    public TreeNode FindKthToTail(TreeNode head, int k) {
        if (head == null) {
            return null;
        }
        TreeNode mHead = head;
        int size = 1;
        while (head.next != null) {
            head = head.next;
            size++;
        }
        if (k > size || k <= 0) {
            return null;
        }
        k = size - k + 1;

        while (k > 1 && mHead.next != null) {
            mHead = mHead.next;
            k--;
        }
        return mHead;
    }

    public static TreeNode ReverseList(TreeNode head) {
        if (head == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        head = stack.pop();
        TreeNode mhead = head;
        while (stack.size() > 0) {
            mhead.next = stack.pop();
            mhead = mhead.next;
            mhead.next = null;
        }
        return head;
    }

    public TreeNode Merge(TreeNode list1,TreeNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        int m = Math.min(list1.val, list2.val);
        TreeNode list3 = new TreeNode(m);

        if (list1.val > list2.val) {
            list3.next = Merge(list1, list2.next);
            return list3;
        }
        list3.next = Merge(list1.next, list2);
        return list3;
    }

    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (size <= 0) {
            return arrayList;
        }
        for (int i = 0; i < num.length - size; i++) {
            arrayList.add(getMax(Arrays.copyOfRange(num, i, i + size)));
        }
        return arrayList;
    }

    public int getMax(int [] num) {
        int max = Integer.MIN_VALUE;
        for (int value : num) {
            max = Math.max(max, value);
        }
        return max;
    }

    public void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
        TreeNode m = root.left;
        root.left = root.right;
        root.right = m;
    }

    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> nums = new ArrayList<>();
        int row = matrix.length;
        int col = matrix[0].length;
        getNum(matrix, nums, 0, col - 1, 0, row - 1);
        return nums;
    }

    private void getNum(int[][] matrix, ArrayList<Integer> nums, int a, int b, int c, int d) {
        if (a > b || c > d) {
            return;
        }

        for (int i = a; i <= b; i++) {
            nums.add(matrix[c][i]);
        }
        c++;
        for (int i = c; i <= d; i++) {
            nums.add(matrix[i][b]);
        }
        b--;
        for (int i = b; i >= a && d >= c; i--) {
            nums.add(matrix[d][i]);
        }
        d--;
        for (int i = d; i >= c && b >= a; i--) {
            nums.add(matrix[i][a]);
        }
        a++;
        getNum(matrix, nums, a, b, c, d);
    }

    public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }

        ArrayList<RandomListNode> originNodes = new ArrayList<>();
        ArrayList<RandomListNode> newNodes = new ArrayList<>();
        while (pHead != null) {
            originNodes.add(pHead);
            RandomListNode listNode = new RandomListNode(pHead.label);
            newNodes.add(listNode);
            pHead = pHead.next;
        }

        for (int i = 0; i < originNodes.size(); i++) {
            RandomListNode oNode = originNodes.get(i);
            int nextIndex = originNodes.indexOf(oNode.next);
            if (nextIndex >= 0) {
                newNodes.get(i).next = newNodes.get(nextIndex);
            }
            int randomIndex = originNodes.indexOf(oNode.random);
            if (randomIndex >= 0) {
                newNodes.get(i).random = newNodes.get(randomIndex);
            }
        }
        return newNodes.get(0);
    }

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    class Solution {
        public int myAtoi(String str) {
            String[] strArray = str.split(" ");
            for (int i = 0; i < strArray.length; i++) {
                if (StringUtils.isBlank(strArray[i])) {

                }
            }
            return 0;
        }
    }

    /*public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        ArrayList<ListNode> arrayList = new ArrayList<>();
        while (pHead != null) {
            arrayList.add(pHead);
            pHead = pHead.next;
        }
        ListNode root;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).val == arrayList.get(i + 1).val) {
                continue;
            }
            root =
        }
    }*/
}
