# Java I/O包使用测试

1. Java的I/O操作中一个核心的概念就是流，流就是一个数据序列。分为字节流和字符流。**Java I/O的核心就是基于字节流和在字符流。**
<br>
2. 根据操作的不同，分为InputStream（向内存输入）和OutputStream（从内存输出）。
<br>
3. 根据传递数据的形式，Java的xxInputStrea、xxOutputStream形式的类都是传输字节流，而xxReader、xxWriter形式的都是传递字符流。
<br>
4. 流可以组合使用实现更高级的输入输出，例如：使用BufferedInputStream读取，增加读取文件的速度。

	InputStream input = new BufferedInputStream(new FileInputStream("file.txt"));

