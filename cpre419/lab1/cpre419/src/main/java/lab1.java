import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class lab1 {

	public static void main(String[] args) throws Exception {
		// TThe system configuration
		Configuration conf = new Configuration();
		
		// Get an instance of the Filesystem
		FileSystem fs = FileSystem.get(conf);
		
		String path_name = "/lab1/newfile";
		Path path = new Path(path_name);
		
		// The Output Data Stream to write into
		FSDataOutputStream file = fs.create(path, true);
		
		// Write some data
		file.writeChars("My first Hadoop Program!");
		
		// Close the file and the file system instance
		file.close();
		fs.close();
	}

}
