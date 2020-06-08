package com.laststar.fileRepository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileProxy {

	public static final String ROOT_PATH = "C:\\Users\\sinny\\Desktop\\FileRepository";
	
	
	public void upload(String fileName, String fileVersion, File file) throws Exception{
		int size = 1024;
		byte[] buf = new byte[size];
		
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(makeZipName(fileName, fileVersion))));
		
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		BufferedInputStream bis = new BufferedInputStream(fis, size);
		
		
		zos.putNextEntry(new ZipEntry(file.getName()));
		
		int len;
		while((len = bis.read(buf, 0, size)) != -1) {
			zos.write(buf, 0, len);
		}
		zos.closeEntry();
		bis.close();
		fis.close();
		file.delete();
		
		System.out.println("CLEAR!");
	}
	
	private String makeZipName(String fileName, String fileVersion) {
		return ROOT_PATH + "\\" + fileName + "-" + fileVersion + ".zip";
	}
}
