package pl.itgolo.mod.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 26.07.2017
 * Time: 15:27
 * Project name: download
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class Download {

    /**
     *
     * @param remotePath
     * @param localPath
     */
    public static Boolean download(String remotePath, String localPath) {

        Boolean success = false;

        new File(new File(localPath).getParent()).mkdirs();

        BufferedInputStream in = null;
        FileOutputStream out = null;

        try {
            URL url = new URL(remotePath);
            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();

            if (size < 0) {
                System.out.println("DEBUG: (Download) Could not get the file size");
            } else {
                System.out.println("DEBUG: (Download) File size: " + size);
            }

            in = new BufferedInputStream(url.openStream());
            out = new FileOutputStream(localPath);
            byte data[] = new byte[1024];
            int count;
            double sumCount = 0.0;

            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);

                sumCount += count;
                if (size > 0) {
                    System.out.println("DEBUG: Download progress: " + Math.round((sumCount / size * 100)) + " %");
                }
            }
            success = true;

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            if (out != null)
                try {
                    out.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
        }

        return success;
    }
}
