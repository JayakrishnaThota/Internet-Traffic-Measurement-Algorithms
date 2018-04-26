import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

public class CountMin {
	public static void main(String[] args)throws Exception {
        int size = 1000000;
        int[][] hash = new int[3][size];
        Map<String, Integer> map = new HashMap<>();
        File finput = new File("C:\\Users\\Ajantha\\Desktop\\CN\\ITMProject\\Input\\traffic.txt");
		File foutput = new File("C:\\Users\\Ajantha\\Desktop\\CN\\ITMProject\\Input\\virtual.txt");
		Scanner sc = null;
		try
		{
			sc = new Scanner(finput);
			sc.nextLine();
			while(sc.hasNextLine())
			{
				String s = sc.nextLine();
				String[] tokens = s.split("\\s+");
				String key = tokens[0]+'$'+tokens[1];
				int val = Integer.parseInt(tokens[2]);
				map.put(key, map.getOrDefault(key,0)+val);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(foutput), "utf-8"));
		writer.write("Source Address		Destination Address			Estimate");
		writer.newLine();

        List<Integer> random = new ArrayList<>();
        Random rand = new Random();
        for(int i=0; i<99; i++)
        {
            random.add(rand.nextInt(Integer.MAX_VALUE)+1);
        }

        for (String key:map.keySet()) {
            for(int i=0; i<3; i++)
            {
                int hashcode = Math.abs(key.hashCode());
                int randomhashcode = random.get(i)^(hashcode);
                int index = (randomhashcode)%size;
                hash[i][index] += map.get(key);
            }
        }
        HashMap<Integer, Integer> result_graph_hash = new HashMap<>();
        for(String key:map.keySet())
        {
            int min = Integer.MAX_VALUE;
            String[] tokens = key.split("\\$");
            for(int i=0; i<3; i++)
            {
                int hashcode = Math.abs(key.hashCode());
                int randomhash = random.get(i)^(hashcode);
                int index = Math.abs(randomhash)%size;
                min = Math.min(min, hash[i][index]);
            }
            result_graph_hash.put(map.get(key),min);
            writer.write(tokens[0]+"\t\t"+tokens[1]+"\t\t"+min);
            writer.newLine();
        }
        XYChart chart = new XYChart("Flow Cardinality", "Original vs Estimated", result_graph_hash);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        writer.close();
    }
}
