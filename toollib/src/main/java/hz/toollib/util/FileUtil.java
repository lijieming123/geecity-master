package hz.toollib.util;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * @author LiuPeng
 *
 */
public class FileUtil {
	
	/**
	 * 获取SD卡路径
	 * @return SD卡路径，SD卡如不存在，返回null
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		} else {
			return null;
		}
		return sdDir.toString();

	}
	
	/**
	 * 将文件转换为base64字符串
	 * 
	 * @param filePath 文件路径
	 * @return
	 */
	public static String file2Base64(String filePath) {
		File file = new File(filePath);
		FileInputStream inputFile;
		try {
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 文件转为base64并输出到文件
	 *
	 * @param filePath
	 * @return
	 */
	public static void file2Base64File(String filePath,String toFile) {
		/*File file = new File(filePath);
		FileInputStream inputFile;
		File outFile = new File(toFile);
		try {
			FileOutputStream outputStream = new FileOutputStream(outFile);
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			byte[] out = Base64.encode(buffer,Base64.DEFAULT);
			Log.d("base64",out.toString());
			outputStream.write(out);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String base64 = file2Base64(filePath);
		Log.d("base64",base64);
		try {
			FileOutputStream outputStream = new FileOutputStream(toFile);
			outputStream.write(base64.getBytes("utf-8"));
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
