package Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteInFile {
    public static void WriteFile(String data,String QUEUE_NAME){
        FileOutputStream fop = null;
        File file;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = formatter.format(new Date());
        //写入到文件
        try {
            file = new File("D:/TFJC/"+QUEUE_NAME+"/Data_"+date+".txt");
            File file_dir = new File("D:/TFJC/"+QUEUE_NAME+"/");
            if(!file_dir.exists()){
                file_dir.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            fop = new FileOutputStream(file,true);
            byte[] contentInBytes = data.getBytes();
            fop.write(contentInBytes);
            fop.write(System.getProperty("line.separator").getBytes());
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
