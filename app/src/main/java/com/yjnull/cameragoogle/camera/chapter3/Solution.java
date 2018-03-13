package com.yjnull.cameragoogle.camera.chapter3;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangy on 2018/3/1.
 */

public class Solution {

    public static int lengthOfLongestSubstring(String s) {
        int num = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int j = 0, i = 0; j < num; j++) {
            if(map.containsKey(s.charAt(j))) {
                i = Math.max( map.get(s.charAt(j)), i );
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
    public static boolean isMatch(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int i = text.length(); i >= 0; i--){
            for (int j = pattern.length() - 1; j >= 0; j--){
                boolean first_match = (i < text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.'));
                if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                    dp[i][j] = dp[i][j+2] || first_match && dp[i+1][j];
                } else {
                    dp[i][j] = first_match && dp[i+1][j+1];
                }
            }
        }
        return dp[0][0];
    }

    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) {return "";}
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++){
            while(strs[i].indexOf(prefix) != 0){
                prefix = prefix.substring(0, prefix.length() - 1);
                if(prefix.length() == 0) return "";
            }
        }

        return prefix;

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> tSum = new ArrayList<>();
        for(int i = 0; i < nums.length - 2; i++) {
            if(nums[i] > 0) break;
            if(i == 0 || nums[i] != nums[i-1]){
                int j = i + 1, k = nums.length - 1;
                while(j < k) {
                    if (-nums[i] == nums[j] + nums[k]){
                        tSum.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        while(j < k && nums[j] == nums[j + 1]) j++;
                        while(j < k && nums[k] == nums[k - 1]) k--;
                        j++;
                        k--;
                    } else if (-nums[i] < nums[j] + nums[k]){
                        k--;
                    } else {
                        j++;
                    }
                }
            }

        }
        return tSum;
    }

    public static List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if(digits.isEmpty()) return ans;
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i =0; i<digits.length();i++){
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length()==i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }

    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();
    private static final String TAG = "Solution";
    public void threadLocalTest(){
        mBooleanThreadLocal.set(true);
        System.out.println(Thread.currentThread().getName() + "[Thread#main]threadLocalTest: " + mBooleanThreadLocal.get());
        //Log.d(TAG, "[Thread#main]threadLocalTest: " + mBooleanThreadLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                mBooleanThreadLocal.set(false);
                System.out.println(Thread.currentThread().getName() + "[Thread#1]threadLocalTest: " + mBooleanThreadLocal.get());
                //Log.d(TAG, "[Thread#1]threadLocalTest: " + mBooleanThreadLocal.get());
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "[Thread#2]threadLocalTest: " + mBooleanThreadLocal.get());
                //Log.d(TAG, "[Thread#2]threadLocalTest: " + mBooleanThreadLocal.get());
            }
        }.start();
    }

    public static void main(String[] args) {
        new Solution().threadLocalTest();
        Looper.prepare();
        Looper.loop();
        Handler h = new Handler(){

        };
    }
}
