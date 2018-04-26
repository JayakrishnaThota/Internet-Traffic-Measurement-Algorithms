import java.util.*;
import java.io.*;
public class VirtualFM {
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
	                int k_val = Math.abs(destination.hashCode());
	                int k_zero_count = getZeroesAfterMSB(k_val);
	                bitmap[Math.abs(index.hashCode()) % large_size] = 1 << k_zero_count;
	            }
	        }

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
		            int lsb = 0;
		            for(int i=0; i<small_size; i++)
		            {
		                Integer ri = random.get(i);
		                Integer sh= hash ^ ri;
		                Integer srh = Math.abs(sh.hashCode());
		                vbitmap[i] = bitmap[srh % large_size];
		                lsb += getFirstZeroFromLeft(vbitmap[i]);
		            }


		            int estimate = lsb/small_size;
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
	            int lsb = 0;
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
	            int estimate = lsb/small_size;
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

	    public static int getZeroesAfterMSB(int k){
	        int count = 0;
	        for(int i=31;i>=0;i--){
	            if((k&(1<<i))==0)
	                count++;
	            else break;
	        }
	        return count;
	    }

	    public static int getFirstZeroFromLeft(int k){
//	        System.out.println(k);
	        for(int i=1;i<32;i++){
	            if(((1<<i)&k)==0)
	            {
	                return i;
	            }
	        }
	        return 32;
	    }

}
