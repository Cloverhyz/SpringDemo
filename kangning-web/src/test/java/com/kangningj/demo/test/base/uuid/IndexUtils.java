package com.kangningj.demo.test.base.uuid;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: hanli_1
 * @Date: 2018/8/7 22:58
 * @Description:
 */
public class IndexUtils {

    private static final long DEFAULT_KEEPALIVE_MILLIS = 10L;

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
        LinkedHashMap<Integer, Integer> hashMap = new LinkedHashMap<>();
        return h;
    }

    /**
     * 给定一个非空整数数组，除了2个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的2个元素。
     * 说明：时间空间复杂度最优
     * @param args
     */
    public static void findReptNum(String[] args) {

        int[] nums = {3, 6, 7, 9, 10, 20, 15, 7, 6, 20, 10, 34, 9, 3};
        Map<Integer, Integer> numMap = new LinkedHashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (numMap.get(nums[i]) == null) {
                numMap.put(nums[i], 1);
                continue;
            }
            int count = numMap.get(nums[i]);
            count++;
            numMap.put(nums[i], count);
        }

        for (Entry<Integer, Integer> entry : numMap.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(new Person().hashCode());
        System.out.println(new Person().hashCode());
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

    /**
     * 重建二叉树
     * @param pre
     * @param in
     * @return
     */
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

    /**
     * 奇数偶数重拍
     * @param array
     */
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

    /**
     * 第K大的数
     * @param head
     * @param k
     * @return
     */
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

    /**
     * 反转链表
     * @param head
     * @return
     */
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

    /**
     * 合并链表
     * @param list1
     * @param list2
     * @return
     */
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

    /**
     * 滑动窗口
     * @param num
     * @param size
     * @return
     */
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

    /**
     * 反转二叉树
     * @param root
     */
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

    /**
     * 打印二维数组
     * @param matrix
     * @return
     */
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

    /**
     * 查找峰值
     * @param nums
     * @return
     */
    public int findPeak(int[] nums){
        if (nums != null && nums.length > 0) {
            if (nums.length == 1) {
                return 0;
            }
            if (nums[0] > nums[1]) {//数组单调递减
                return 0;
            }
            int index = nums.length-1;
            if (nums[index] > nums[index-1]) {//数组单调递增
                return index;
            }
            int start = 0, end = index;
            int mid = 0;
            while (start < end) {//二分查找
                mid = (start + end) / 2;
                if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                    return mid;
                } else if (nums[mid] > nums[mid + 1]) {//处于下坡段, 即递减段
                    end = mid - 1;
                } else if (nums[mid] > nums[mid - 1]) {//处于上坡段, 即递增段
                    start = mid + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找峰值
     * @param nums
     * @return
     */
    public int findPeak1(int[] nums){
        if (nums.length == 0){
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int end = nums.length;
        int start = 0;
        int mid = 0;
        while (mid < end) {
            mid = (start + end)/2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else if (nums[mid] > nums[mid + 1]) {//处于下坡段, 即递减段
                end = mid - 1;
            }else if (nums[mid] > nums[mid - 1]) {//处于上坡段, 即递增段
                start = mid + 1;
            }
        }
        return -1;
    }

    public void testThread() {
        //SynchronousQueue<Runnable>
        new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        Executors.newCachedThreadPool();

        //LinkedBlockingQueue
        new ThreadPoolExecutor(10, 10, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        Executors.newFixedThreadPool(10);

        //DelayedWorkQueue
        //new ThreadPoolExecutor(10, Integer.MAX_VALUE, DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS, new DelayedWorkQueue());
        Executors.newScheduledThreadPool(10);

        //LinkedBlockingQueue
        new ThreadPoolExecutor(1, 1,0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        Executors.newSingleThreadExecutor();

        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(new Object());

    }

    /**
     * 前序遍历
     * @param root
     */
    public void preOrderTraverse1(TreeNode root) {
        if (root != null) {
            System.out.print(root.val+"  ");
            preOrderTraverse1(root.left);
            preOrderTraverse1(root.right);
        }
    }

    /**
     * 中序遍历
     * @param root
     */
    public void inOrderTraverse1(TreeNode root) {
        if (root != null) {
            inOrderTraverse1(root.left);
            System.out.print(root.val+"  ");
            inOrderTraverse1(root.right);
        }
    }

    /**
     * 后续遍历
     * @param root
     */
    public void postOrderTraverse1(TreeNode root) {
        if (root != null) {
            postOrderTraverse1(root.left);
            postOrderTraverse1(root.right);
            System.out.print(root.val+"  ");
        }
    }

    /**
     * 层序遍历 广度优先
     * @param root
     */
    public void levelTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.pop();
        queue.pollLast();

        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val+"  ");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 大顶堆 小顶堆
     */
    public void levelTraverse() {
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>();
        PriorityQueue minQueue = new PriorityQueue<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }

    public static List<Integer> baoshu(int n) {

        //初始化数组下标
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Person person = new Person();
            person.rest = true;
            person.index = i;
            list.add(person);
        }

        //循环数组
        while (list.size() > 3) {
            for(int i = 0; i < list.size(); i++) {
                if ((i + 1) % 3 == 0){
                    list.get(i).rest = false;
                }
            }
            list.removeIf(person -> !person.rest);
        }
        return list.stream().map(person -> person.index).collect(Collectors.toList());
    }

    public static class Person{

        public Boolean rest;

        public Integer index;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Person person = (Person) o;

            if (rest != null ? !rest.equals(person.rest) : person.rest != null) {
                return false;
            }
            return index != null ? index.equals(person.index) : person.index == null;
        }

        @Override
        public int hashCode() {
            int result = rest != null ? rest.hashCode() : 0;
            result = 31 * result + (index != null ? index.hashCode() : 0);
            return result;
        }
    }




    // 学校数据
    class School{
        int shoolid;
        String schoolname; //学校名字
    };
    //上课数据
    class Lesson{
        int schoolid;
        int studentCount; //学生数量
    }
//    分别有2个list：List<school> schools;List<lesson> lessons，存放school与lesson数据，lessions含有多条相同schoolid的数据，
//    现在需要统计每个学校上课的学生总数，并且将学校数据按照上课学生总数降序排列，将上课学生总数>1000的学校名字存放到一个list中。

    public void getSchoolLesson(List<School> schools, List<Lesson> lessons){
        Map<Integer, School> schoolMap = new HashMap<>();
        Map<Integer, Integer> lessonMap = new HashMap<>();
        Set<School> count_1000 = new HashSet<>();

        for (School school : schools) {
            schoolMap.put(school.shoolid, school);
        }

        for (Lesson lesson : lessons) {
            Integer num = lessonMap.get(lesson.schoolid);
            if (num != null) {
                num += lesson.studentCount;
            }else {
                num = lesson.studentCount;
            }
            lessonMap.put(lesson.schoolid, num);
            if (num >= 1000) {
                count_1000.add(schoolMap.get(lesson.schoolid));
            }
        }

        PriorityQueue<Lesson> minQueue = new PriorityQueue<Lesson>(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.studentCount - o2.studentCount;
            }
        });

        for (Entry<Integer, Integer> entry : lessonMap.entrySet()) {
            Lesson lesson = new Lesson();
            lesson.schoolid = entry.getKey();
            lesson.studentCount = entry.getValue();
            minQueue.add(lesson);
        }

        List<School> count_1000_list = new ArrayList();
        count_1000_list.addAll(count_1000);
    }

}
