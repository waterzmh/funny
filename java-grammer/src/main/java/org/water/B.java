package org.water;

import java.util.List;

/**
 * @author mati
 * @since 2020/1/9 16:52
 */
public class B<T extends A , C> {
    public void test1(List<T> a) {
        System.out.println("AA类的方法");
    }

    public void test2(List<T> a) {
        System.out.println("AAA类的方法");
    }
}
