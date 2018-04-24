import java.io.*;
import java.util.*;

public class Project1 {
	public static void main(String[] args){
		File finput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/traffic.txt");
		File foutput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/two_level_hash_output.txt");
		Map<String,Set<String>> map = new HashMap();
		Scanner sc = null;
		try
		{
			sc = new Scanner(finput);
			sc.nextLine();
			while(sc.hasNextLine())
			{
				String s = sc.nextLine();
				String[] tokens = s.split("\\s+");
				String source = tokens[0];
				String destination = tokens[1];
				if(!map.containsKey(source))
				{
					map.put(source, new HashSet<String>());
				}
				map.get(source).add(destination);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(foutput), "utf-8")))
		{
			writer.write("Source Address	No of unique destinations");
			for(String key:map.keySet())
			{
				writer.write(key+"	"+map.get(key).size());
				writer.newLine();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
