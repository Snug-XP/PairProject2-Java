import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
 * ���ļ���ȡm�����ʹ��ɵĴ��鲢��ͳ�ƴ�����ִ����ʹ�������
 * ���룺�ļ�·����һ������ĵ�����
 * ������������������͸���������ִ�����Map
 */
public class countPhrase {
	public static Map<String, String> count_Phrase_frequency(String filePath, int m) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("count_words", "0");// ע�⣡��wordsMap�м�����һ����count_words���ļ�����ͳ�ƴ�����������������Ŷ��

		try {
			File input_file = new File(filePath);
			if (input_file.isFile() && input_file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;

				while ((str = bufferedReader.readLine()) != null) {
					str = str.toLowerCase();

					int count = 0;// �����������ֵĵ�����
					String[] words = new String[101];// �洢�������ֵĵ���

					for (int i = 0; i < (str.length() - 4); i++) {
						if (i > 0) {
							if (('a' <= str.charAt(i - 1) && str.charAt(i - 1) <= 'z')
									|| (48 <= str.charAt(i - 1) && str.charAt(i - 1) <= 57)) {// ���ǰһ���ַ����ַ�������
								continue;
							}
						}
						if ('a' <= str.charAt(i) && str.charAt(i) <= 'z') {
							if ('a' <= str.charAt(i + 1) && str.charAt(i + 1) <= 'z') {
								if ('a' <= str.charAt(i + 2) && str.charAt(i + 2) <= 'z') {
									if ('a' <= str.charAt(i + 3) && str.charAt(i + 3) <= 'z') {// �ҵ�һ������

										int j;
										for (j = i + 4; j < str.length(); j++) {// �������Ƿ����
											if ('a' > str.charAt(j) || str.charAt(j) > 'z') {
												if (48 > str.charAt(j) || str.charAt(j) > 57)// �������֣������ָ���
													break;
											}
										}
										String temp = str.substring(i, j);// ��ȡ�ַ���������i��j���򣨰���i��������j+1��---��ȡ����
										if( j==str.length() )//һ�����Ե��ʽ�β
											temp = temp+" ";
										else
										    temp = temp +str.charAt(j);//�ѵ��ʺ����һ���ָ����ӵ�������ȥ
										count++;
										words[count] = temp;
										if (count >= m) {
											temp = words[count-m+1];
											for( int k=m ; k>1 ; k-- )
											{
											    temp = temp + words[count-k+2];
											}
											temp = temp.substring(0, temp.length()-1);// �Ͳ���ȥ��ĩβ�ķָ��
											// �ӵ�ͼ��ȥ
											if (map.containsKey(temp)) {
												int n = Integer.parseInt(map.get(temp));
												n++;
												map.put(temp, n + "");
											} else
												map.put(temp, "1");
											int n = Integer.parseInt(map.get("count_words"));
											n++;// �ܵ��ʸ�����һ
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
