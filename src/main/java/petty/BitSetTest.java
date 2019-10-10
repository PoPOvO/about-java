package petty;

import java.util.BitSet;

/**
 * BitSet是以bit为单位的set
 *
 * @author xli
 *
 */
public class BitSetTest {
	public static void main(String[] args) {
		/*
		 * 初始化容量大小
		 */
		BitSet bitSet = new BitSet(1 << 16);	//64k

		/*
		 * 将某位置为0
		 */
		bitSet.clear(0);

		/*
		 * 将某位置为1
		 */
		bitSet.set(0);

		/*
		 * 获得某一位的值
		 */
		bitSet.get(0);

		/*
		 * 将所有的位置为0
		 */
		bitSet.clear();
	}
}
