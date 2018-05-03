package lyn.fastdfsfile.fastdfsfile;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import com.sun.org.apache.xpath.internal.SourceTree;
import lyn.fastdfsfile.fastdfsfile.service.fastdfsFileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FastdfsfileApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FastdfsfileApplication.class, args);
	}
}
