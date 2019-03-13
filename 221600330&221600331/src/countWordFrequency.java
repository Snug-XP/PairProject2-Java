import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class countWordFrequency {

	/*
	 * ��Ȩ��ͳ���ļ��ĵ���(�����)��Ƶ(0��ʾ���� Title��Abstract �ĵ���(�����)Ȩ����ͬ��Ϊ 1; 1 ��ʾ���� Title
	 * �ĵ���(�����)Ȩ��Ϊ10������Abstract ����(�����)Ȩ��Ϊ1) ���룺����ͳ�Ƶĵ���(�����)�ʹ�Ƶ��Map������ѡ��
	 * �������Ȩ��ͳ�Ƶĵ���(�����)�ʹ�Ƶ��Map
	 */
	public static Map<String, String> count_Word_Frequency(String input_file_path, Map<String, String> wordsMap,
			int choose) {

		try {
			File input_file = new File(input_file_path);
			InputStreamReader reader;
			BufferedReader bufferedReader;
			String str = null;

			if (input_file.isFile() && input_file.exists())// �ж��ļ��Ƿ����
			{
				

				if (wordsMap.containsKey("title"))// ����ǵ��ʵ�Map
				{
					// ��ȥ��wordsMap����ͳ���С�title: ���͡�abstract: ����ռ�õĴ�Ƶ
					reader = new InputStreamReader(new FileInputStream(input_file));
					bufferedReader = new BufferedReader(reader);
					while ((str = bufferedReader.readLine()) != null) {
						str = str.toLowerCase();
						if (str.contains("title: ")) {
							// title�Ĵ�Ƶ��һ
							int value = Integer.parseInt(wordsMap.get("title"));
							value--;
							wordsMap.put("title", value + "");

							// �ܵ��ʸ�����һ
							int n = Integer.parseInt(wordsMap.get("count_words"));
							n--;
							wordsMap.put("count_words", n + "");
						}
						if (str.contains("abstract: ")) {
							// abstract�Ĵ�Ƶ��һ
							int value = Integer.parseInt(wordsMap.get("abstract"));
							value--;
							wordsMap.put("abstract", value + "");

							// �ܵ��ʸ�����һ
							int n = Integer.parseInt(wordsMap.get("count_words"));
							n--;
							wordsMap.put("count_words", n + "");
						}
					}
				}
				// if (wordsMap.get("title").equals("0"))
				// wordsMap.remove("title");
				// if (wordsMap.get("abstract").equals("0"))
				// wordsMap.remove("abstract");

				if (choose == 0) {
					// Ȩ��1:1
					return wordsMap;
				} else if (choose == 1) {
					// Ȩ��10:1
					reader = new InputStreamReader(new FileInputStream(input_file));
					bufferedReader = new BufferedReader(reader);
					while ((str = bufferedReader.readLine()) != null) {
						str = str.toLowerCase();
						if (str.contains("title: ")) {
							for (int i = 5; i < (str.length() - 4); i++) {
								if (('a' <= str.charAt(i - 1) && str.charAt(i - 1) <= 'z')
										|| (48 <= str.charAt(i - 1) && str.charAt(i - 1) <= 57)) {// ���ǰһ���ַ����ַ�������
									continue;
								}
								if ('a' <= str.charAt(i) && str.charAt(i) <= 'z') {
									if ('a' <= str.charAt(i + 1) && str.charAt(i + 1) <= 'z') {
										if ('a' <= str.charAt(i + 2) && str.charAt(i + 2) <= 'z') {
											if ('a' <= str.charAt(i + 3) && str.charAt(i + 3) <= 'z') {
												// �ҵ�����
												int j;
												for (j = i + 4; j < str.length(); j++) {
													// �������Ƿ����
													if ('a' > str.charAt(j) || str.charAt(j) > 'z') {
														if (48 > str.charAt(j) || str.charAt(j) > 57)
															// �����ַ������֣����ʽ���
															break;
													}
												}
												String temp = str.substring(i, j);// ��ȡ�ַ���������i��j���򣨰���i��������j��
												// ��Ƶ��9���һ��¼���Ƶ10
												if (wordsMap.containsKey(temp)) {
													int n = Integer.parseInt(wordsMap.get(temp));
													n += 9;
													wordsMap.put(temp, n + "");
												} else
													wordsMap.put(temp, "10");
												i = j;
											} else
												i = i + 3;
										} else
											i = i + 2;
									} else
										i = i + 1;
								}
							}
						}
					}
					reader.close();
					return wordsMap;
				} else {
					System.out.println("������󣡣�");
					return null;
				}
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
				return null;
			}
		} catch (IOException e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
			return null;
		}

	}
}
