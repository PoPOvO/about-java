package jdk8test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * -----用于测试新的日期、时间API-----
 * <br>
 *
 * @author xl
 *
 */
public class NewDateTimeAPITest {
	public static void testDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("本地日期-时间：" + localDateTime);

		System.out.println("----------测试日期----------");
		//LocalDate date = localDateTime.toLocalDate();
		LocalDate localDate = LocalDate.now();
		//格式化日期
		String formatDate = localDate.format(DateTimeFormatter.ofPattern("yyyy MM dd"));
		System.out.println("格式化日期：" + formatDate);
		System.out.println("本地日期：" + localDate);
		System.out.println("该月天数:" + localDate.lengthOfMonth());
		System.out.println("该年天数：" + localDate.lengthOfYear());
		System.out.println("是否是闰年：" + localDate.isLeapYear());
		//减去月数并返回LocalDate的副本
		LocalDate february = localDate.minusMonths(6);
		System.out.println("二月天数：" + february.lengthOfMonth());
		//从指定的年、月、日得到LocalDate对象
		LocalDate newYears = LocalDate.of(2018, 1, 1);
		System.out.println("指定日期：" + newYears);
		//更改年份并返回该LocalDate的副本
		LocalDate elseDate = localDate.withYear(2019);
		System.out.println("更改年：" + elseDate);


		System.out.println("----------测试时间----------");
		//LocalTime time = localDateTime.toLocalTime();
		LocalTime localTime = LocalTime.now();
		System.out.println("本地时间：" + localTime);
		//格式化时间
		String formatTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		System.out.println("格式化时间：" + formatTime);
		LocalTime minusSeconds = localTime.minusSeconds(20);
		System.out.println("减去20s：" + minusSeconds);
		//从指定的时分秒得到LocalTime对象
		LocalTime elseTime = LocalTime.of(12, 0, 59);
		System.out.println("指定时间：" + elseTime);
		//更改小时并返回LocalTime的副本
		LocalTime otherTime = localTime.withHour(10);
		System.out.println("更改小时：" + otherTime);
	}

	public static void testZonedDateTime() {
		System.out.println("----------测试时区----------");
		//从指定格式串解析ZomedDateTime对象
		ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
		ZonedDateTime date2 = ZonedDateTime.now();
		System.out.println("date1: " + date1);
		System.out.println("data2：" + date2);

		ZoneId id = ZoneId.of("Europe/Paris");
		//根据对应的时区ID产生对象，ZonedDateTime对象和普通时期API同
		ZonedDateTime date3 = ZonedDateTime.now(id);
		System.out.println("date3:" + date3);

		ZoneId currentZone = ZoneId.systemDefault();
		System.out.println("当前时区: " + currentZone);
	}

	public static void main(String[] args) {
		NewDateTimeAPITest.testDateTime();
		NewDateTimeAPITest.testZonedDateTime();
	}
}
