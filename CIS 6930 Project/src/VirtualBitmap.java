import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class VirtualBitmap {
	public static void main(String[] args)throws IOException
    {
		File finput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/traffic.txt");
		File foutput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/virtualbitmap.txt");
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
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

        int large_size = 10000000, small_size = 256;
        int[] bitmap = new int[large_size];
        List<Integer> random = getRandomList(small_size);
        for (String key:map.keySet())
        {
            HashSet<String> set = map.get(key);
            int hash = Math.abs(key.hashCode());
            for (String destination:set)
            {
                Integer ri = Math.abs(destination.hashCode())%(small_size);
                Integer index = (hash ^ random.get(ri));
                bitmap[Math.abs(index.hashCode()) % large_size] = 1;
            }
        }
        int c0 = 0;
        for(int i=0; i<bitmap.length; i++)
        {
            if(bitmap[i] == 0)
                c0++;
        }
        double p1 = (double) c0/(double) large_size;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(foutput), "utf-8"));
        try
		{
			writer.write("Source Address		Destination Address			Estimate");
			writer.newLine();
			for(String key:map.keySet())
			{
				int vc0 = 0;
				Integer hash = Math.abs(key.hashCode());
	            int[] vbitmap = new int[small_size];
	            for(int i=0; i<small_size; i++)
	            {
	                Integer ri = random.get(i);
	                Integer sh= hash ^ ri;
	                Integer srh = Math.abs(sh.hashCode());
	                if(bitmap[srh % large_size] == 1)
	                	vbitmap[i] = 1;
	            }
	            for(int i=0; i< small_size; i++)
	            {
	                if(vbitmap[i] == 0)
	                	vc0++;
	            }
	            double pc1 = (double)vc0/(double)small_size;
	            double estimate = (Math.log(p1) - Math.log(pc1))*small_size;
	            writer.write(key+"\t\t"+map.get(key).size()+"\t\t"+Math.abs(estimate));
	            writer.newLine();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
        for(String key:map.keySet())
        {
            int vc0 = 0;
            int[] vbitmap = new int[small_size];
            Integer hash = Math.abs(key.hashCode());
            for(int i=0; i<small_size; i++)
            {
                Integer rand_index = random.get(i);
                Integer s = hash ^ rand_index;
                Integer srh = Math.abs(s.hashCode());
                if(bitmap[srh % large_size] == 1)
                    vbitmap[i] = 1;
            }
            for(int i=0; i< small_size; i++)
            {
                if(vbitmap[i] == 0)
                    vc0++;
            }
            double prob_one_count = (double)vc0/(double)small_size;
            double estimate = (Math.log(p1) - Math.log(prob_one_count))*small_size;
            writer.write(key+"\t\t"+map.get(key).size()+"\t\t"+Math.abs(estimate));
			writer.newLine();
        }
        writer.close();
    }

    public static List<Integer> getRandomList(int small_size){
		List<Integer> list = new ArrayList<Integer>();
		Random rand2 = new Random();
		for (int i = 0; i < small_size; i++) {
			list.add(rand2.nextInt(Integer.MAX_VALUE) + 1);
		}
		Collections.shuffle(list);
		return list;
	}
}
