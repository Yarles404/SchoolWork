import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class BigramCount implements WritableComparable<BigramCount> {
		private LongWritable count;
		private Text bigram;
		
		public BigramCount() {
			set(0, "");
		}
		
		public BigramCount(long count, String bigram) {
			set(count, bigram);
		}
		
		public void set(long count, String bigram) {
			this.bigram = new Text(bigram);
			this.count = new LongWritable(count);
		}

		public void write(DataOutput out) throws IOException {
			count.write(out);
			bigram.write(out);
		}

		public void readFields(DataInput in) throws IOException {
			count.readFields(in);
			bigram.readFields(in);
		}

		public int compareTo(BigramCount arg0) {
			return this.count.get() - arg0.getCount().get() < 0 ? -1 : 1;
		}
		
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + count.hashCode();
			result = prime * result + bigram.hashCode();
			return result;
		}
		
		public boolean equals (Object obj) {
			return obj != null
					&& obj.getClass().equals(getClass())
					&& (this == obj
					|| (((BigramCount) obj).getCount().equals(this.count)
							&& ((BigramCount) obj).getBigram().equals(this.bigram)));
		}
		
		public String toString() {
			return count + " " + bigram;
		}
		
		public LongWritable getCount() {
			return count;
		}
		
		public Text getBigram() {
			return bigram;
		}
	}