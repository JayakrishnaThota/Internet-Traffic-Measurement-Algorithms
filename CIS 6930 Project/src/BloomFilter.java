import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BloomFilter {
	public static List<String> getIPS(int size){
		List<String> list = new ArrayList<String>();
		Random rand = new Random();
		for(int i=0;i<size;i++){
			list.add(rand.nextInt(255)+"."+rand.nextInt(255)+"."+rand.nextInt(255)+"."+rand.nextInt(255));
		}
		return list;
	}

	public static List<Integer> getRandomList(int size){
		List<Integer> list = new ArrayList<Integer>();
		Random rand2 = new Random();
		for (int i = 0; i < size; i++) {
			list.add(rand2.nextInt(Integer.MAX_VALUE) + 1);
		}
		Collections.shuffle(list);
		return list;
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		File finput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/traffic.txt");
        Set<String> set = new HashSet<String>();
        List<Integer> randomList = getRandomList(10);
        int width = 6000000;
        int k = 3;
        int[] map = new int[width];
		Scanner sc = new Scanner(finput);
			sc.nextLine();
			while (sc.hasNext()) {
				String source = sc.next();
				set.add(source);
				sc.next();
				int combinedHash = Math.abs(source.hashCode());
				for (int i = 0; i < k; i++) {
					int index = randomList.get(i) ^ combinedHash;
					map[index % width] = 1;
				}
				sc.next();
			}
			sc.close();
		List<String> generatedIPS = getIPS(20000);
		int count = 0, ans = 1, total = 0;
		for(String s : generatedIPS) {
			ans = 1;
			if(!set.contains(s)) {
				count++;
				int key = Math.abs(s.hashCode());
				for(int i = 0; i < k; i++) {
					int r = key ^ randomList.get(i);
					ans &= map[r % width];
				}
			}
			if(ans == 1)
				total++;
		}
		System.out.println(count + "		" + total);
		System.out.println((double) total/count);
	}
}
