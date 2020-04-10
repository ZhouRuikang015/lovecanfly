package me.zhouruikang.lovecanfly;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class LovecanflyApplicationTests {

    @Test
    void contextLoads() {
    }


    public static void main(String[] args) {
//        System.out.println(4 << 1);
//        HashMap<String, Object> map = new HashMap<>();
//        map.remove()
        ArrayList<String> list = new ArrayList<>();
        PriorityQueue<Object> queue = new PriorityQueue<>();
        HashSet<Object> set = new HashSet<>();

        HashMap<Object, Object> map = new HashMap<>();

        int[] nums = {3,4,-1,1};
        new LovecanflyApplicationTests().firstMissingPositive(nums);
    }
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        int count = 0;
        for(int num: nums){
            if(num > n || num < 1){
                count++;
            }
        }

        int l = 0, r = n - 1;
        while(l < r){
            if(nums[r] > n || nums[r] < 1){
                r--;
            }else if(nums[l] > n || nums[l] < 1){
                swap(nums, l, r--);
            }else{
                l++;
            }
        }

        for(int i = 0; i < count; i++){
            while(nums[i] != i + 1 && nums[i] <= count){
                swap(nums, i, nums[i]-1);
            }
        }

        for(int i = 0; i < count; i++){
            if(nums[i] != i+1) return i+1;
        }
        return count+1;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
