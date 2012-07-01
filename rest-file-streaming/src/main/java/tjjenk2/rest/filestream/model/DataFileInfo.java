package tjjenk2.rest.filestream.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DataFileInfo {
	private String fileName;
	private Date lastModified;
	private long fileSize;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = new Date(lastModified);
	}

	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getFormattedLastModified() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(lastModified);
	}
}