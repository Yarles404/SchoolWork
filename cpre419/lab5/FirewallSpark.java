import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


import scala.Tuple2;

public class FirewallSpark {
	public static void main(String[] args) throws Exception {
		// Job Setup
		if (args.length != 4) {
			System.err.println("Usage: Firewall <ip_trace> <raw_block> <output a> <output b>");
			System.exit(1);
		}

		SparkConf sparkConf = new SparkConf().setAppName("Regenerate Firewall in Spark");
		JavaSparkContext context = new JavaSparkContext(sparkConf);


        // Start spark job
		JavaRDD<String> ip_trace = context.textFile(args[0]);
        JavaRDD<String> raw_block = context.textFile(args[1]);

		JavaPairRDD<String, IpTrace> ipTrace = ip_trace.mapToPair(t -> {
			String[] data = t.split(" ");
			return new Tuple2<String, IpTrace>(
				data[1],
				new IpTrace(
					data[0],
					data[1],
					data[2],
					data[4].substring(0, data[4].length() - 1)
				)
			);
		});

		JavaPairRDD<String, RawBlock> rawBlocks = raw_block.mapToPair(b -> {
			String[] data = b.split(" ");
			return new Tuple2<String, RawBlock>(
				data[0],
				new RawBlock(data[0], data[1])
			);
		});
		rawBlocks = rawBlocks.filter(b -> b._2.Action.equals("Blocked"));

		JavaPairRDD<String, Tuple2<IpTrace, RawBlock>> blockedInfo = ipTrace.join(rawBlocks);

		JavaRDD<String> firewallLog = blockedInfo.map(i -> {
			IpTrace trace = i._2._1;
			return String.format("%s %s %s %s Blocked", trace.Time, trace.ConnectionId, trace.SourceIp, trace.DestinationIp);
		});

		// JavaPairRDD<String, Iterable<Tuple2<String, Tuple2<IpTrace, RawBlock>>>> groupedBlocks = blockedInfo.groupBy(i -> {
		// 	return i._2._1.SourceIp;
		// });

		Map<String, Long> blockCounts = blockedInfo.map(i -> i._2._1.SourceIp).countByValue();
		ArrayList<Tuple2<Long, String>> temp = new ArrayList<Tuple2<Long, String>>(blockCounts.size());
		for (Map.Entry<String, Long> entry : blockCounts.entrySet()) {
			temp.add(new Tuple2<Long, String>(entry.getValue(), entry.getKey()));
		}


		JavaPairRDD<Long, String> countsByIp = context.parallelizePairs(temp).sortByKey(false);

		
		firewallLog.saveAsTextFile(args[2]);
		countsByIp.saveAsTextFile(args[3]);
		
		context.stop();
		context.close();
	}

	@SuppressWarnings("serial")
	static class IpTrace implements Serializable {
		public String Time;
		public String ConnectionId;
		public String SourceIp;
		public String DestinationIp;

		public IpTrace(String time, String connectionId, String sourceIp, String destinationIp) {
			Time = time;
			ConnectionId = connectionId;
			SourceIp = sourceIp;
			DestinationIp = destinationIp;
		}
	}

	@SuppressWarnings("serial")
	static class RawBlock implements Serializable {
		public String ConnectionId;
		public String Action;

		public RawBlock(String connectionId, String action) {
			ConnectionId = connectionId;
			Action = action;
		}
	}
}