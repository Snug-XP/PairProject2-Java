



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class wordCount {
	
	/*
	 * ��ͳ�ƽ��������ļ�"result.txt"
	 * ���룺ͳ�Ƶ��ļ��ַ�������ͳ�Ƶ��ʺʹ�Ƶ��Map��ͳ�Ƶ��ļ���Ч����
	 * �������
	 */
	public static void writeToFile(int countChar, Map<String, String>wordsMap , int countLinnes) {
		try {
			int countWords = Integer.parseInt( wordsMap.get("count_words") );
			
			File output_file=new File("result.txt");
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(new FileOutputStream(output_file));
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("characters: "+countChar+"\r\n");
			bufferedWriter.write("words: "+countWords+"\r\n");
			bufferedWriter.write("lines: "+countLinnes+"\r\n");
            bufferedWriter.flush();
            
            System.out.println("characters: "+countChar);
            System.out.println("words: "+countWords);
            System.out.println("lines: "+countLinnes);
            
            if( countWords <= 0 )
            {
            	writer.close();
            	return;
            }
            
            int n = 10;
            while( countWords > 0 && n-- > 0)
            {
            	String temp = "";
            	int maxNum=-1;
            	Iterator<Map.Entry<String, String>> iterator = wordsMap.entrySet().iterator();
            	while (iterator.hasNext()) {
            		Map.Entry<String, String> entry = iterator.next();
            		if(  Integer.parseInt( entry.getValue()) >= maxNum && !entry.getKey().equals("count_words") )
            		{
            			if( Integer.parseInt( entry.getValue()) == maxNum && entry.getKey().compareTo(temp) > 0 )
            			{//��ͬƵ�ʵĵ���ѡ�ֵ���ǰ�ĵ���
            				continue;
            			}
            			temp = entry.getKey();
            			maxNum = Integer.parseInt( entry.getValue());
            		}
            	}
            	bufferedWriter.write("<"+temp+">: "+maxNum+"\r\n");
            	bufferedWriter.flush();
                System.out.println("<"+temp+">: "+maxNum);

            	wordsMap.remove(temp);//����и��������ǻ��ƻ������ɵĵ���Map���������õ�����Map���ᷢ��ȱʧ����
            	countWords = countWords-maxNum;
            }
            
            writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ͳ���ļ�����Ч���������ǿհ��ַ����У�
	 * ���룺�ļ�·��
	 * ����ļ�����Ч�����������ǿհ��ַ����У�
	 */
	public static int count_Lines(String filePath) {
		try {
			File input_file=new File(filePath);
			if(input_file.isFile() && input_file.exists())//�ж��ļ��Ƿ����
			{ 
				InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;
				int countLinnes = 0;
				while( (str = bufferedReader.readLine()) != null)
				{
					for(int i = 0 ; i < str.length() ; i++)
					{
						if( 32< str.charAt(i) && str.charAt(i) < 127 )
						{
							countLinnes++;
							break;
						}
					}
				}
				reader.close();
				return countLinnes;
			} else{
				System.out.println("�Ҳ���ָ�����ļ�");
				return -1;
			}
		} catch (IOException e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
			return -1;
		}
	}

	
	/*
	 * ͳ���ļ��еĵ�������
	 * ���룺�ļ�·��
	 * ������ļ��еĵ�������
	 */
	public static int get_Words_Num(String filePath)
	{
		Map< String, String> wordsMap = wordCount.count_Words(filePath);
		int countWords = Integer.parseInt( wordsMap.get("count_words") );
		return countWords;
	}
	
	
	/*
	 * ���ļ���ȡ���ʲ���ͳ�Ƶ��ʳ��ִ����͵�������
	 * ���룺�ļ�·��
	 * ������������������͸������ʳ��ִ�����Map
	 */
	public static Map<String, String> count_Words(String filePath) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("count_words", "0");//ע�⣡��wordsMap�м�����һ����count_words���ļ�����ͳ�Ƶ�����������������Ŷ��
		
		try {
			File input_file=new File(filePath);
			if(input_file.isFile() && input_file.exists()){ //�ж��ļ��Ƿ����
				InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;
				
				while( (str = bufferedReader.readLine()) != null)
				{
					str = str.toLowerCase();
					for(int i = 0 ; i < (str.length()-4) ; i++)
					{
						if( i > 0 )
						{
							if( ('a'<= str.charAt(i-1) && str.charAt(i-1) <= 'z') || (48<=str.charAt(i-1)&&str.charAt(i-1)<=57) )
							{//���ǰһ���ַ����ַ�������
								continue;
							}
						}
						if( 'a'<= str.charAt(i) && str.charAt(i) <= 'z' )
						{
							if( 'a'<= str.charAt(i+1) && str.charAt(i+1) <= 'z' )
							{
								if( 'a'<= str.charAt(i+2) && str.charAt(i+2) <= 'z' )
								{
									if( 'a'<= str.charAt(i+3) && str.charAt(i+3) <= 'z' )
									{//�ҵ�����
										int j;
										for(j = i+4 ; j < str.length() ; j++)
										{//�������Ƿ����
											if( 'a'> str.charAt(j) || str.charAt(j) > 'z' )
											{
												if(48>str.charAt(j)||str.charAt(j)>57)//��������
													break;
											}
										}
										String temp = str.substring(i,j);//��ȡ�ַ���������i��j���򣨰���i��������j��
										//�ӵ�ͼ��ȥ
										if( map.containsKey(temp) )
										{
											int n = Integer.parseInt( map.get(temp) );
											n++;
											map.put(temp, n+"");
										}
										else 
											map.put(temp,"1");
										
										int n = Integer.parseInt( map.get("count_words") );
										n++;//�ܵ��ʸ�����һ
										map.put("count_words", n+"");
										
										i=j;
									}
									else i = i+3;
								}
								else i = i+2;
							}
							else i = i+1;
						}
						
					}
				}
				reader.close();
				return map;
					
			} else{
           	 System.out.println("�Ҳ���ָ�����ļ�");
           	 return null;
            }
		} catch (IOException e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * ͳ���ļ��ܵ��ַ���
	 * ���룺�ļ�·��
	 * ������ļ����ַ����������հ��ַ���
	 */
	public static int count_Characters(String filePath){
        try {
                File input_file=new File(filePath);
                File output_file=new File("result.txt");
                if(input_file.isFile() && input_file.exists()){ //�ж��ļ��Ƿ����
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(input_file));
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output_file));
                    int countChar = 0;
                    int temp;
                    int last_char=-1;
                    while((temp = reader.read() )!= -1){
                    	countChar++;
                    	if(last_char=='\r'&&temp=='\n')
                    	{
                    		countChar--;
                    	}
                    	last_char=temp;
                    }
                    
                    reader.close();
                    writer.close();
                    return countChar;
                 }
                 else{
                	 System.out.println("�Ҳ���ָ�����ļ�");
                	 return -1;
                 }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
            return -1;
        }
     
    }



}
