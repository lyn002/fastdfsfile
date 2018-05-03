package lyn.fastdfsfile.fastdfsfile.service;

import lombok.Data;
import lyn.fastdfsfile.fastdfsfile.FastdfsfileApplication;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static lyn.fastdfsfile.fastdfsfile.Dao.FileManagerCofig.PROTOCOL;
import static lyn.fastdfsfile.fastdfsfile.Dao.FileManagerCofig.SEPARATOR;
import static lyn.fastdfsfile.fastdfsfile.Dao.FileManagerCofig.TRACKER_NGNIX_ADDR;

@Service
public class fastdfsFileService {

    private static String CONFIG_FILENAME = "templates/fdfs_client.conf";

    private static String path;

    static {
        try {
            path = new File(fastdfsFileService.class.getResource("/").getFile()).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String fdfsClientConfigFilePath = path + File.separator + CONFIG_FILENAME;
    private static StorageClient1 storageClient1 = null;

    // 初始化FastDFS Client
    static {
        try {
            ClientGlobal.init(fdfsClientConfigFilePath);
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }

            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }

            storageClient1 = new StorageClient1(trackerServer,storageServer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传文件
    //file 文件对象
    //fileName 文件名
    //metaList 文件元数据
    public static String uploadFile(File file, String fileName, Map<String,String> metaList) {
        try {
            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
            NameValuePair[] nameValuePairs = null;
            if (metaList != null) {
                nameValuePairs = new NameValuePair[metaList.size()];
                int index = 0;
                for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name, value);
                }
            }
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String adress = PROTOCOL +TRACKER_NGNIX_ADDR + SEPARATOR + storageClient1.upload_file1(buff, suffix , nameValuePairs);
            return adress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


     //上传文件
     //file 文件对象
     //fileName 文件名
    public static String uploadFile(File file, String fileName) {
        return uploadFile(file,fileName,null);
    }

    //获取文件元数据
    public static Map<String,String> getFileMetadata(String fileId) {
        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //删除文件
    public static int deleteFile(String fileId) {
        String result = "";
        int j = 0, startIndex = 0, endIndex = 0;
        for (int i = 0; i < fileId.length(); i++) {
            if (fileId.charAt(i) == '/') {
                j++;
                if (j == 2)
                    startIndex = i;
                else if (j == 3)
                    endIndex = i;
            }
        }
        result = fileId.substring(endIndex+1 ,fileId.length() );
        try {
            return storageClient1.delete_file1(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //下载文件
    public static int downloadFile(String fileId, String downloadAdress,String fileName) {
        String s = downloadAdress + SEPARATOR + fileName;
        File outFile = new File(s);
        String result = "";
        int j = 0, startIndex = 0, endIndex = 0;
        for (int i = 0; i < fileId.length(); i++) {
            if (fileId.charAt(i) == '/') {
                j++;
                if (j == 2)
                    startIndex = i;
                else if (j == 3)
                    endIndex = i;
            }
        }
        result = fileId.substring(endIndex+1 ,fileId.length() );
        FileOutputStream fos = null;
        try {
            byte[] content = storageClient1.download_file1(result);
            if(content == null)
                return -1;
            fos = new FileOutputStream(outFile);
            IOUtils.write(content,fos);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
