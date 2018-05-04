package lyn.fastdfsfile.fastdfsfile;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import com.sun.org.apache.xpath.internal.SourceTree;
import lyn.fastdfsfile.fastdfsfile.service.fastdfsFileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class FastdfsfileApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FastdfsfileApplication.class, args);

	}
}
