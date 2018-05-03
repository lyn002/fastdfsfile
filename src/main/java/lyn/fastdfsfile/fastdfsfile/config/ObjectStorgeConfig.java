package lyn.fastdfsfile.fastdfsfile.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ObjectStorgeConfig {
    private String filename;
    private String storeAdress;
    private String trackerServerPort;
}
