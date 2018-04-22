import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TwoLevelHashMap {
	public static void main(String[] args){
		File f = new File("C:/Users/JayaKrishna/Desktop/Nad/project/traffic.txt");
		Map<String,Set<String>> map = new HashMap();
		Scanner sc = null;
		try{
			sc = new Scanner(f);
			sc.nextLine();
			while(sc.hasNextLine()){
				String s = sc.nextLine();
				String[] tokens = s.split("\\s+");
				String source = tokens[0];
				String destination = tokens[1];
				if(!map.containsKey(source)){
					map.put(source, new HashSet());
				}
				map.get(source).add(destination);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		for(String key:map.keySet()){
			System.out.println(key+"        "+map.get(key).size());
		}
	}
}
