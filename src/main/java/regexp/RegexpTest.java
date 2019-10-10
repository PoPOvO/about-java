package regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 正则测试
 */
public class RegexpTest {
	public static void main(String[] args) {
		Pattern patt1 = Pattern.compile("([\\w]+)://([\\w.]+):?(\\d*)/?([^#\\s]*)");
		String url = "asdasdas//http://www.github.com:80/WizardXL/";
		Matcher matcher = patt1.matcher(url);

		System.out.println("源串长度:" + url.length());
		System.out.println("------------------------查看方法------------------------");
		/*
		 * 查看分组中正则匹配的串
		 */
		if(matcher.find()) {
			System.out.println("URL:" + matcher.group(0) );
			System.out.println("协议:" + matcher.group(1) );
			System.out.println("域名:" + matcher.group(2) );
			System.out.println("端口:" + matcher.group(3) );
			System.out.println("资源相对路径:" + matcher.group(4) );
		}

		System.out.println("------------------------索引方法------------------------");

		/*
		 * 整个正则匹配的起始下标
		 */
		int start = matcher.start();
		System.out.println("起始下标:" + start);

		/*
		 * 整个正则匹配的终止下标
		 */
		int end = matcher.end();
		System.out.println("终止下标:" + end);

		/*
		 * 分组中正则匹配的起始和终止下标
		 */
		System.out.println("URL index:" + matcher.start(0) + "," + matcher.end(0));
		System.out.println("协议 index:" + matcher.start(1) + "," + matcher.end(1));
		System.out.println("域名 index:" + matcher.start(2) + "," + matcher.end(2));
		System.out.println("端口 index:" + matcher.start(3) + "," + matcher.end(3));
		System.out.println("资源相对路径 index:" + matcher.start(4) + "," + matcher.end(4));

		System.out.println("------------------------研究方法------------------------");
		System.out.println("从源串起始字符开始匹配:" + matcher.lookingAt());
		System.out.println("源串整体是否完全匹配:" + matcher.matches());
		System.out.println("查看源串中是否有下一个匹配的子序列:" + matcher.find());

		System.out.println("------------------------替换方法------------------------");
		String str = "abcdfackasaada@@@";
		Pattern patt2 = Pattern.compile("a+");
		Matcher ma = patt2.matcher(str);
		StringBuffer sb = new StringBuffer();

		/*
		 * 替换第一个匹配
		 */
		System.out.println("替换第一个:" + ma.replaceFirst("*"));

		/*
		 * 替换所有匹配
		 */
		System.out.println("替换所有:" + ma.replaceAll("#"));

		/*
		 * 重置匹配器，上一步将所有的"a"替换为"#"，接下来需要重置为初始
		 */
		ma.reset();
		while (ma.find()) {
			/*
			 * 将替换的串追加到字符缓冲区中
			 */
			ma.appendReplacement(sb, "%");
		}
		System.out.println(sb.toString());
		/*
		 * 将源串剩余的字符追加到字符缓冲区
		 */
		ma.appendTail(sb);
		System.out.println(sb.toString());
	}
}
