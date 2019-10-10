package memory;

import java.util.HashMap;

/**
 * ----------内存泄漏测试------------
 * <hr>
 * 1. 使用ThreadLocalMap存储对象时容易内存泄漏，因为key即ThreadLocal是弱引用，若无其他引用存在，gc时会回收，就访问不到value。
 * 因此对于不再使用的ThreadLocal变量要及时remove，或者将ThreadLocal设置为静态的。<br>
 * 2. 单例对象含有外部对象的引用<br>
 * 3. 静态集合里里存储的对象不再使用时要及时remove，否则会内存泄漏<br>
 * 4. 集合元素存储后，其hashcode()不能再变，否则remove不成功导致内存泄漏。<br>
 * 5. 资源未释放，推荐使用try-with-resource语句<br>
 * 6. 监听器未remove
 * <br>
 *
 * 这里演示元素被存储到集合后，hashcode方法变更。
 *
 * @author xl
 *
 */
public class MemoryLeakTest {
    public static void main(String[] args) {
        People p1 = new People("Jack", 12);
        System.out.println(p1.hashCode());

        HashMap<People, String> hashMap = new HashMap<People, String>();
        hashMap.put(p1, "Object1");
        p1.setAge(13);
        //这里其实没有删除成功，因为hashcode变了，就会造成内存泄漏
        System.out.println(hashMap.remove(p1));
        System.out.println(hashMap.size());
    }
}


class People{
    private String name;
    private int age;

    public People(String name,int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public int hashCode() {
        //这里hashcode方法重写的并不好，因为它依赖于age，而age易变
        return name.hashCode()*37 + age;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
    }
}
