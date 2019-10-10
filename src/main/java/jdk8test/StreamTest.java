package jdk8test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Java 8的Stream测试
 *
 * @author xli
 *
 */
public class StreamTest {
    public static void main(String[] args) {
        //元素集合
        List<String> ls = Arrays.asList("A", "B", "D", "C", "F", "H", "", "");

        //生成串行流
        Stream<String> serialStream = ls.stream();
        //生成并行流
        @SuppressWarnings("unused")
        Stream<String> parallelStream = ls.parallelStream();

        Consumer<String> s = (e) -> {System.out.print("[" + e + "]");};

        //forEach：遍历
        serialStream.forEach(s);

        //filter：过滤出流中不为空的元素，并放到array
        Object[] os = ls.stream().filter((e) -> {
            return !"".equals(e);
        }).toArray();

        System.out.println();
        System.out.println(Arrays.toString(os));
    }
}
