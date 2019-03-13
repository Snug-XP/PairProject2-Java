import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
 * ���ļ���ȡm�����ʹ��ɵĴ��鲢��ͳ�ƴ��鼰�����Ȩ�ش�Ƶ
 * ���룺�ļ�·����һ������ĵ��������Ƿ�10:1��Ȩ��ͳ�ƴ�Ƶ��booleanֵ
 * ������������������͸��������Ƶ��Map
 */
public class CountPhraseFrequency {
	public static Map<String, String> count_Phrase_frequency(String file_path, int m, boolean w) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("count_words", "0");// ע�⣡��wordsMap�м�����һ����count_words���ļ�����ͳ�ƴ�����������������Ŷ��

		try {
			File input_file = new File(file_path);
			if (input_file.isFile() && input_file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;
				boolean is_title = false;

				while ((str = bufferedReader.readLine()) != null) {
					str = str.toLowerCase();
					
					//ȥ��"title: "��"abstract: "
					if( str.contains("title: "))
					{
						is_title = true;
						str = str.substring(0,str.indexOf("title: ") )+str.substring(str.indexOf("title: ")+7);
					}
					else
						is_title = false;
					if( str.contains("abstract: "))
						str = str.substring(0,str.indexOf("abstract: ") )+str.substring(str.indexOf("abstract: ")+7);
					
					
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
											if( w && is_title )//����ȨֵΪ10:1,���Ҹô�����title����
											{
												if (map.containsKey(temp)) {
													int n = Integer.parseInt(map.get(temp));
													n+=9;
													map.put(temp, n + "");
												} else
													map.put(temp, "10");
											} else {
												//����Ҫ����Ȩֵ
												if (map.containsKey(temp)) {
													int n = Integer.parseInt(map.get(temp));
													n++;
													map.put(temp, n + "");
												} else
													map.put(temp, "1");
											}
											
											int n = Integer.parseInt(map.get("count_words"));
											n++;// �ܴ��������һ
											map.put("count_words", n + "");
										}
										i = j;
									} else {count = 0; i = i + 3;}//��������4����ĸ�ĵ��ʣ�����count
								} else {count = 0; i = i + 2;}//��������4����ĸ�ĵ��ʣ�����count
							} else {count = 0; i = i + 1;}//��������4����ĸ�ĵ��ʣ�����count
						} else {
							if((48 > str.charAt(i) || str.charAt(i) > 57))
							{//�����ָ����ӵ��ӵ���һ������ĩβ
								words[count]+=str.charAt(i);
							}
							else {
								//�������֣�����count
								count = 0;
							}
						}

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
