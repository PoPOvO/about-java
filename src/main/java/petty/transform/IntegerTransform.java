package petty.transform;

import java.util.Arrays;

/**
 * 对Java的整型值进行转换，将整型值于byte数组和十六进制字符串进行转换
 *
 * @author xli
 */
public class IntegerTransform {
	//应用于给256求次方，因为使用Math.pow()存在舍入误差，数字很大时会导致结果有偏差
	private static long _256pow(int step) {
		long[] bool = {1L, 256L, 65536L, 16777216L, 4294967296L, 1099511627776L, 281474976710656L, 72057594037927936L};

		return bool[step];
	}

	/**
	 * 短整形转换为byte数组
	 *
	 * @param data
	 * @return
	 */
	public static byte[] shortToBytes(int data) {
		return integerToBytes(data, 2);
	}

	/**
	 * 整型转为byte数组
	 *
	 * @param data 整型数值
	 * @return 由低位开始的byte数组
	 */
	public static byte[] intToBytes(int data) {
		return integerToBytes(data, 4);
	}

	/**
	 * 长整型转化为字节数组
	 *
	 * @param data 长整型数值
	 * @return 由低位开始的byte数组
	 */
	public static byte[] longToBytes(long data) {
		return integerToBytes(data, 8);
	}

	/**
	 * byte数组转换为短整形
	 *
	 * @param bytes 长度为2的byte数组
	 * @return
	 */
	public static short bytesToShort(byte[] bytes) {
		return (short) bytesToInteger(bytes);
	}

	/**
	 * byte数组转换为整形
	 *
	 * @param bytes 长度为4的byte数组
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		return (int) bytesToInteger(bytes);
	}

	/**
	 * byte数组转换为长整型
	 *
	 * @param bytes
	 * @return
	 */
	public static long bytesToLong(byte[] bytes) {
		return bytesToInteger(bytes);
	}

	private static long bytesToInteger(byte[] bytes) {
		long res = 0L;

		/*
		long的取值范围是-2^63 ~ 2^63-1。因为是将long存储在byte数组，因此long的低7字节中每字节被存储为小于128，即0x80。若是大于了，就会被当做补码，当做负数
		因此在将byte数组向long转换时会发生数值不一致的问题。这是不应该的，因此区别对待byte数组的最高字节和低7字节。

		int/short类似。
		*/
		for (int i = 0; i < bytes.length; i++) {
			//对于long的最高字节，直接采用byte数组的元素处理
			if (i == bytes.length-1) {
				res += bytes[i] *  _256pow(i);
			} else {
				//除了最高字节外，需要将负数转化为正数
				res += ((bytes[i] + 256) % 256) * _256pow(i);
			}
		}
		return res;
	}

	private static byte[] integerToBytes(long data, int size) {
		byte[] bytes = new byte[size];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (data & 0xff);
			data = data >> 8;
		}

		return bytes;
	}

	/**
	 * byte类型转换为16进制字符串
	 *
	 * @param data 数值类型，可以是十六进制、十进制、八进制、二进制数值
	 * @return
	 */
	public static String byteToHexString(byte data) {
		return integerToHexString(data, 1);
	}

	/**
	 * 短整型转换为16进制字符串
	 *
	 * @param data 数值类型，可以是十六进制、十进制、八进制、二进制数值
	 * @return
	 */
	public static String shortToHexString(short data) {
		return integerToHexString(data, 2);
	}

	/**
	 * 整型转换为16进制字符串
	 *
	 * @param data 数值类型，可以是十六进制、十进制、八进制、二进制数值
	 * @return
	 */
	public static String intToHexString(int data) {
		return integerToHexString(data, 4);
	}

	/**
	 * 将长整型转化为十六进制数的字符串
	 *
	 * @param data 数值类型，可以是十六进制、十进制、八进制、二进制数值
	 * @return 十六进制字符串
	 */
	public static String longToHexString(long data) {
		return integerToHexString(data, 8);
	}

	private static String integerToHexString(long data, int byteLen) {
		StringBuilder sb = new StringBuilder();
		byte[] start = byteArrayClear(integerToBytes(data, byteLen));

		for (int i = start.length-1; i >= 0 ; i--) {
			sb.append("0123456789ABCDEF".charAt((start[i] >> 4) & 0x0f));
			sb.append("0123456789ABCDEF".charAt(start[i] & 0x0f));
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转换为byte类型
	 *
	 * @param hexString 16进制字符串，长度小于2
	 * @return
	 * @throws ParameterInvalidException
	 */
	public static byte hexStringToByte(String hexString) throws ParameterInvalidException {
		if (hexString == null || hexString.length() > 2 || hexString.length() == 0
				|| hexString.codePointCount(0, hexString.length()) != hexString.length()) {
			throw new ParameterInvalidException("参数非法!请确保字符串转换的范围");
		}

		return (byte) hexStringToInteger(hexString, 1);
	}

	/**
	 * 16进制字符串转换为短整型
	 *
	 * @param hexString 16进制字符串，长度小于4
	 * @return
	 * @throws ParameterInvalidException
	 */
	public static short hexStringToShort(String hexString) throws ParameterInvalidException {
		if (hexString == null || hexString.length() > 4 || hexString.length() == 0
				|| hexString.codePointCount(0, hexString.length()) != hexString.length()) {
			throw new ParameterInvalidException("参数非法!请确保字符串转换的范围");
		}

		return (short) hexStringToInteger(hexString, 2);
	}

	/**
	 * 16进制字符串转换为整型
	 *
	 * @param hexString 16进制字符串，长度小于8
	 * @return
	 * @throws ParameterInvalidException
	 */
	public static int hexStringToInt(String hexString) throws ParameterInvalidException {
		if (hexString == null || hexString.length() > 8 || hexString.length() == 0
				|| hexString.codePointCount(0, hexString.length()) != hexString.length()) {
			throw new ParameterInvalidException("参数非法!请确保字符串转换的范围");
		}

		return (int)hexStringToInteger(hexString, 4);
	}

	/**
	 * 16进制字符串转换为长整型
	 *
	 * @param hexString 十六进制数字符串，长度最大为16
	 * @return 长整型的数值
	 * @throws ParameterInvalidException
	 */
	public static long hexStringToLong(String hexString) throws ParameterInvalidException  {
		if (hexString == null || hexString.length() > 16 || hexString.length() == 0
				|| hexString.codePointCount(0, hexString.length()) != hexString.length()) {
			throw new ParameterInvalidException("参数非法!请确保字符串转换的范围");
		}

		return hexStringToInteger(hexString, 8);
	}

	private static long hexStringToInteger(String hexString, int byteLen) throws ParameterInvalidException {
		int length = hexString.length();
		long res = 0L;

		for (int i = length-1; i >= 0; i--) {
			//求出字符的码点间隔
			int step = hexString.charAt(i) - "0".charAt(0);

			//字符范围0~9
			if (step >= 0 && step <= 9) {
				res += step * (long) (Math.pow((long)16, (long)(length-i-1)));
				//字符范围A~F
			} else if (step >= 17 && step <= 22) {
				res += (step-7) * (long) (Math.pow((long)16, (long)(length-i-1)));
			} else {
				throw new ParameterInvalidException("字符串包含非十六进制元素!" + "\tindex:[" + i + "]");
			}
		}

		//处理long的负数，若十六进制串大于70FF FFFF FFFF FFFF，将作为补码处理。short/int类似
		if (hexString.length() == byteLen && hexString.codePointAt(0) > 55) {
			res = (~(res-1));
		}

		return res;
	}

	/**
	 * 清除byte数组后面值为0的元素，例如[1, 1, 1, 1, 0, 0, 0, 0]，处理后为[1, 1, 1, 1]
	 *
	 * @param bytes 原始byte数组
	 * @return 被截断的byte数组
	 */
	public static byte[] byteArrayClear(byte[] bytes) {
		int count = bytes.length;

		for (int i = bytes.length-1; i >= 0 ; i--) {
			if (bytes[i] == 0) {
				count--;
			} else {
				break;
			}
		}
		return Arrays.copyOf(bytes, count);
	}
}
