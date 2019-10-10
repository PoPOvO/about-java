package jdk8test;

import java.lang.reflect.Modifier;

/**
 *
 * ----Java 8 lambda测试----
 *
 * <br>
 * Lambda表达式是用于简化Java的匿名内部类的书写，是配合函数式接口工作的。
 * 所谓函数式接口的目的就是其他语言中的函数指针类型，在Java中表现出来就是只有一个
 * 方法的接口，如Runnable接口。为了实现一个接口一般都是使用匿名内部类的形式，而
 * Lambda表达式常用于和接口配合，从而简化匿名内部类的书写。这种写法类似于ES6的匿
 * 名函数的书写方式。
 * <br>
 * <strong>注：Lambda的实现也是匿名内部类，但是在编译目录下未产生$*.class
 * 形式的文件。</strong>
 *
 * <br>
 * 语法如下：
 * <br>
 * (para) -> expression
 *	或者
 *	(para) -> {statements;}
 * <ul>
 * <li>可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。</li>
 * <li>可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。</li>
 * <li>可选的大括号：如果主体包含了一个语句，就不需要使用大括号。</li>
 * <li>可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号
 * 需要指定明表达式返回了一个数值。</li>
 * </ul>
 *
 * @author xl
 *
 */
class LambdaExpTest {
    public static void main(String[] args) {
        //带类型声明
        MathOper add = (int a, int b) -> a + b;

        //不用类型声明
        MathOper sub = (a, b) -> a-b;

        //大括号中带return
        MathOper mul = (a, b) -> {return a*b;};

        //不带大括号以及省略return
        MathOper div = (a, b) -> a/b;

        System.out.println(add.compute(5, 8));
        System.out.println(sub.compute(5, 8));
        System.out.println(mul.compute(5, 8));
        System.out.println(div.compute(5, 8));

        System.out.println("测试：" + Modifier.toString(LambdaExpTest.class.getModifiers()));
    }
}

@FunctionalInterface
interface MathOper {
    int compute(int a, int b);
}
