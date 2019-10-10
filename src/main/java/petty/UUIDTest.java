package petty;

import java.util.UUID;

/**
 * ----------------UUID测试--------------
 *
 * UUID含义是通用唯一识别码 (Universally Unique Identifier)。就是说这个ID是当下时空全局唯一的。
 * 因为是全局唯一的，可以用来做激活码或者是作为唯一ID。
 *
 * @author xl
 *
 */
public class UUIDTest {
	public static void main(String[] args) {
		String id = UUID.randomUUID().toString();
		System.out.println("唯一ID：" + id);
	}
}
