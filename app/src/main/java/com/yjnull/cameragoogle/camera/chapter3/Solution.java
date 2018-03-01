package com.yjnull.cameragoogle.camera.chapter3;

import java.util.HashMap;
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

    public static void main(String[] args) {
        System.out.print(lengthOfLongestSubstring("abcabcbb"));
    }
}
