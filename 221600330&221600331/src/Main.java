import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<String, String> wordsMap = countPhrase.count_Phrase_frequency("result.txt", 15);//wordCount.count_Words("result.txt");
		wordsMap = countWordFrequency.count_Word_Frequency("result.txt", wordsMap, 1);

		try {//测试.... 
			int countWords = Integer.parseInt(wordsMap.get("count_words"));

			File output_file = new File("input.txt");
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(new FileOutputStream(output_file));
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("words: " + countWords + "\r\n");
			bufferedWriter.flush();

			System.out.println("words: " + countWords);

			if (countWords <= 0) {
				writer.close();
				return;
			}

			int n = 10;
			while (countWords > 0 && n-- > 0) {
				String temp = "";
				int maxNum = -1;
				Iterator<Map.Entry<String, String>> iterator = wordsMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();
					if (Integer.parseInt(entry.getValue()) >= maxNum && !entry.getKey().equals("count_words")) {
						if (Integer.parseInt(entry.getValue()) == maxNum && entry.getKey().compareTo(temp) > 0) {// 相同频率的单词选字典序靠前的单词
							continue;
						}
						temp = entry.getKey();
						maxNum = Integer.parseInt(entry.getValue());
					}
				}
				bufferedWriter.write("<" + temp + ">: " + maxNum + "\r\n");
				bufferedWriter.flush();
				System.out.println("<" + temp + ">: " + maxNum);

				wordsMap.remove(temp);
				countWords = countWords - maxNum;
			}
			writer.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
