package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public class UnescapeUtils {
	
	public static void main(String[] args) throws IOException {
		File dir = new File("E:/EasyJF/generator/gen/com/lanyotech/p2p/admin/message");
		
		File[] files = dir.listFiles();
		for(File file: files){
			if(!file.exists() || !file.isFile())
				continue;
			
			String content = FileUtils.readFileToString(file);
			content = StringEscapeUtils.unescapeJava(content);
			FileUtils.writeStringToFile(file, content);
		}
	}
}
