import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
 * 从文件提取m个单词构成的词组并且统计词组出现次数和词组总数
 * 输入：文件路径，一个词组的单词数
 * 输出：包涵词组总数和各个词组出现次数的Map
 */
public class countPhrase {
	public static Map<String, String> count_Phrase_frequency(String filePath, int m) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("count_words", "0");// 注意！！wordsMap中加入了一个“count_words”的键用于统计词组总数（不是种数哦）

		try {
			File input_file = new File(filePath);
			if (input_file.isFile() && input_file.exists()) { // 判断文件是否存在
				InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;

				while ((str = bufferedReader.readLine()) != null) {
					str = str.toLowerCase();

					int count = 0;// 计算连续出现的单词数
					String[] words = new String[101];// 存储连续出现的单词

					for (int i = 0; i < (str.length() - 4); i++) {
						if (i > 0) {
							if (('a' <= str.charAt(i - 1) && str.charAt(i - 1) <= 'z')
									|| (48 <= str.charAt(i - 1) && str.charAt(i - 1) <= 57)) {// 如果前一个字符是字符或数字
								continue;
							}
						}
						if ('a' <= str.charAt(i) && str.charAt(i) <= 'z') {
							if ('a' <= str.charAt(i + 1) && str.charAt(i + 1) <= 'z') {
								if ('a' <= str.charAt(i + 2) && str.charAt(i + 2) <= 'z') {
									if ('a' <= str.charAt(i + 3) && str.charAt(i + 3) <= 'z') {// 找到一个单词

										int j;
										for (j = i + 4; j < str.length(); j++) {// 看单词是否结束
											if ('a' > str.charAt(j) || str.charAt(j) > 'z') {
												if (48 > str.charAt(j) || str.charAt(j) > 57)// 不是数字，遇到分隔符
													break;
											}
										}
										String temp = str.substring(i, j);// 截取字符串索引号i到j区域（包括i，不包括j+1）---截取单词
										if( j==str.length() )//一段中以单词结尾
											temp = temp+" ";
										else
										    temp = temp +str.charAt(j);//把单词后面的一个分隔符加到单词中去
										count++;
										words[count] = temp;
										if (count >= m) {
											temp = words[count-m+1];
											for( int k=m ; k>1 ; k-- )
											{
											    temp = temp + words[count-k+2];
											}
											temp = temp.substring(0, temp.length()-1);// 和并后去掉末尾的分割符
											// 加到图里去
											if (map.containsKey(temp)) {
												int n = Integer.parseInt(map.get(temp));
												n++;
												map.put(temp, n + "");
											} else
												map.put(temp, "1");
											int n = Integer.parseInt(map.get("count_words"));
											n++;// 总单词个数加一
											map.put("count_words", n + "");
										}
										i = j;
									} else {count = 0; i = i + 3;}
								} else {count = 0; i = i + 2;}
							} else {count = 0; i = i + 1;}
						} else {count = 0; }

					}
				}
				reader.close();
				return map;

			} else {
				System.out.println("找不到指定的文件");
				return null;
			}
		} catch (IOException e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}
	}
}
