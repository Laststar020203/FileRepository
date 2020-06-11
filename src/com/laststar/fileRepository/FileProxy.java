package com.laststar.fileRepository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class FileProxy {

	public static final String ROOT_PATH = "C:\\Users\\½º¼Ò°ú\\Desktop\\FileRepository";

	public String upload(String fileName, String fileVersion, File file) throws Exception {
		int size = 1024;
		byte[] buf = new byte[size];

		final String PATH = makeZipName(fileName, fileVersion);

		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(PATH)));

		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		BufferedInputStream bis = new BufferedInputStream(fis, size);

		zos.setEncoding("UTF-8");

		zos.putArchiveEntry(new ZipArchiveEntry(file.getName()));

		int len;
		while ((len = bis.read(buf, 0, size)) != -1) {
			zos.write(buf, 0, len);
		}
		bis.close();
		fis.close();
		file.delete();
		zos.closeArchiveEntry();
		zos.close();

		return PATH;
	}

	public String change(String fileName, String fileVersion, File file) throws Exception {
		new File(makeZipName(fileName, fileVersion)).delete();
		return upload(fileName, fileVersion, file);
	}

	public String rename(String modifyName, String modifyVersion, String repository_PATH) throws Exception {

		String newPath = makeZipName(modifyName, modifyVersion);
		Path filePath = Paths.get(repository_PATH);
		Path filePathToMove = Paths.get(newPath);
		Files.move(filePath, filePathToMove);
		
		return newPath;
	}

	private String makeZipName(String fileName, String fileVersion) {
		return ROOT_PATH + "\\" + fileName + "-" + fileVersion + ".zip";
	}

}
