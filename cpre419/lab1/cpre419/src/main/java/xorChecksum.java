import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class xorChecksum {

	public static void main(String[] args) throws Exception {
		// TThe system configuration
		Configuration conf = new Configuration();
		
		// Get an instance of the Filesystem
		FileSystem fs = FileSystem.get(conf);
		
		String path_name = "/lab1/bigdata";
		Path path = new Path(path_name);
		
		// Input file stream
		FSDataInputStream in = fs.open(path);
		
		// checksum
		byte[] bytes = new byte[1000];
		in.readFully(1000000000, bytes, 0, 1000);
		
		byte checksum = bytes[0];
		for (int i = 1; i < bytes.length; i++) {
			checksum ^= bytes[i];
		}
		System.out.println(checksum);
		
		// Close the file and the file system instance
		in.close();
		fs.close();
	}

}
