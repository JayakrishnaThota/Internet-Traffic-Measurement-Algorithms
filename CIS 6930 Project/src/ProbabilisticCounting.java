import java.io.*;
import java.util.*;

public class ProbabilisticCounting {
	public static void main(String[] args)throws Exception
    {
		File finput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/traffic.txt");
		File foutput = new File("C:/Users/JayaKrishna/Desktop/Nad/project/probabilistic_counting.txt");
        Map<String,Set<String>> map = new HashMap<String,Set<String>>();
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

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(foutput), "utf-8"));
		writer.write("Source Address		Estimate");
		writer.newLine();
        for (String key:map.keySet()) {
            int size = 500;
            boolean[] bitmap = new boolean[size];
            List<String> destinations = new ArrayList<String>(map.get(key));
            for(int i=0; i<destinations.size(); i++)
            {
                int index = Math.abs((destinations.get(i).hashCode())%size);
                bitmap[index] = true;
            }
            int setbits = 0;
            for(boolean b:bitmap){
            	if(!b) setbits++;
            }
            double v = (double)setbits/(double)(size);
            double unique = -1*(size)*(Math.log(v));
            writer.write(key+"		"+unique);
            writer.newLine();
        }

        writer.close();
    }
}
