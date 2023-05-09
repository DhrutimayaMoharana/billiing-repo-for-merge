package erp.workorder.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static String uploadFile(String rootPath, String folderName, MultipartFile[] files) {

		String url = null;
		rootPath = rootPath + folderName;
		File loc = new File(rootPath);
		if (!loc.exists()) {
			if (loc.mkdir()) {
				System.out.println("Directory is created!");
			}
		}

		System.out.println("Saved file path : " + rootPath);
		StringJoiner sj = new StringJoiner(",");
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				return null;
			}
			try {
				byte[] bytes = file.getBytes();
				url = new Date().getTime() + "-" + file.getOriginalFilename();
				Path path = Paths.get(rootPath + url);
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sj.add(folderName + url);
		}

		String uploadedFileName = sj.toString();
		if (org.springframework.util.StringUtils.isEmpty(uploadedFileName)) {
			System.out.println("Please select a file to upload");
		} else {
			System.out.println("You successfully uploaded '" + uploadedFileName + "'");
			// ImageIcon img=new ImageIcon(folderName+url);
			// Image newImage = img.getImage().getScaledInstance(4, 4, Image.SCALE_DEFAULT);

		}
		return uploadedFileName;
	}

	public static void copyFile(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}

}