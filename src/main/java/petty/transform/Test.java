package petty.transform;

public class Test {
	public static void main(String[] args) {
		//短整型和byte数组转换
		System.out.println(IntegerTransform.bytesToShort(IntegerTransform.shortToBytes(-33)));
		System.out.println(IntegerTransform.bytesToShort(IntegerTransform.shortToBytes(33)));
		System.out.println(IntegerTransform.bytesToShort(IntegerTransform.shortToBytes(32767))); //最大
		System.out.println(IntegerTransform.bytesToShort(IntegerTransform.shortToBytes(-32768))); //最小
		System.out.println("——————————————————————————————————————————————————————————————");
		//整型和byte数组转换
		System.out.println(IntegerTransform.bytesToInt(IntegerTransform.intToBytes(-88)));
		System.out.println(IntegerTransform.bytesToInt(IntegerTransform.intToBytes(88)));
		System.out.println(IntegerTransform.bytesToInt(IntegerTransform.intToBytes(2147483647))); //最大
		System.out.println(IntegerTransform.bytesToInt(IntegerTransform.intToBytes(-2147483648))); //最大
		System.out.println("——————————————————————————————————————————————————————————————");
		//长整型和byte数组转换
		System.out.println(IntegerTransform.bytesToLong(IntegerTransform.longToBytes(-55L)));
		System.out.println(IntegerTransform.bytesToLong(IntegerTransform.longToBytes(55L)));
		System.out.println(IntegerTransform.bytesToLong(IntegerTransform.longToBytes(0x7FFFFFFFFFFFFFFFL))); //最大
		System.out.println(IntegerTransform.bytesToLong(IntegerTransform.longToBytes(0x8000000000000000L))); //最小
		System.out.println("——————————————————————————————————————————————————————————————");

		try {
			//byte和16进制字符串转换
			System.out.println(IntegerTransform.hexStringToByte("7F"));
			System.out.println(IntegerTransform.hexStringToByte("80"));
			System.out.println(IntegerTransform.byteToHexString((byte) 127));
			System.out.println(IntegerTransform.byteToHexString((byte) -128));
			System.out.println("——————————————————————————————————————————————————————————————");
			//短整形和16进制字符串转换
			System.out.println(IntegerTransform.hexStringToShort("7FFF"));
			System.out.println(IntegerTransform.hexStringToShort("8000"));
			System.out.println(IntegerTransform.shortToHexString((short) 32767));
			System.out.println(IntegerTransform.shortToHexString((short) -32768));
			System.out.println("——————————————————————————————————————————————————————————————");
			//整形和16进制字符串转换
			System.out.println(IntegerTransform.hexStringToInt("7FFFFFFF"));
			System.out.println(IntegerTransform.hexStringToInt("80000000"));
			System.out.println(IntegerTransform.intToHexString(2147483647));
			System.out.println(IntegerTransform.intToHexString(-2147483648));
			System.out.println("——————————————————————————————————————————————————————————————");
			//长整形和16进制字符串转换
			System.out.println(IntegerTransform.hexStringToLong("7FFFFFFFFFFFFFFF"));
			System.out.println(IntegerTransform.hexStringToLong("8000000000000000"));
			System.out.println(IntegerTransform.longToHexString(9223372036854775807L));
			System.out.println(IntegerTransform.longToHexString(-9223372036854775808L));
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		}
	}
}
