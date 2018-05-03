package lyn.fastdfsfile.fastdfsfile.domain;

import lombok.Data;
import lyn.fastdfsfile.fastdfsfile.Dao.FileManagerCofig;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.annotation.Annotation;

@Entity
@Data
public class FASTFile implements FileManagerCofig {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private Integer userId;
    private String fileName;
    private String fileURL;
    private byte[] content;
    private String name;
    private String ext;
    private String length;

    @Override
    public String encoding() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
