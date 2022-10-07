package com.panghu.blog;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogUserApplicationTests {

    public boolean isVowel(char ch){
        return ch==65||ch==69||ch==73||ch==79
                ||ch==85||ch==97||ch==101||ch==105
                ||ch==111||ch==117;
    }

    @Test
    void contextLoads() {
        System.out.println(2&1);
    }
    @Test
    void test01() {
        System.out.println('A'+1);
        System.out.println('E'+1);
        System.out.println('I'+1);
        System.out.println('O'+1);
        System.out.println('U'+1);
        System.out.println('a'+1);
        System.out.println('e'+1);
        System.out.println('i'+1);
        System.out.println('o'+1);
        System.out.println('u'+1);
    }
    @Test
    void test02() {
        System.out.println("hello".toCharArray());
    }



}
