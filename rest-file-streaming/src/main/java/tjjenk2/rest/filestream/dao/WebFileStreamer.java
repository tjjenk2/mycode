package tjjenk2.rest.filestream.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebFileStreamer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebFileStreamer.class);
	
	private String contentType = "application/octet-stream";
	
	private String gndbDataDirectory;
	
	@Required
	@Value("#{ gndbxproperties['gndbx.data.directory'] }")
    public void setGndbDataDirectory(String gndbDataDirectory) { 
		this.gndbDataDirectory = gndbDataDirectory;
    }
	
	public void streamFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
		String fullFilePath = this.gndbDataDirectory + "/" + fileName;
		LOGGER.info("Start fetching all file ["+fullFilePath+"]...");
				
		InputStream input = null;
		ServletOutputStream output = null;
		try {
			//get the binary file from the classpath
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullFilePath);
			if (input == null) {
				String errorMessage = "Unable to find file [" + fullFilePath + "].";
				LOGGER.error(errorMessage);
				throw new FileNotFoundException("Unable to find file [" + fullFilePath + "]");
			}
			
			//set up the response
			output = response.getOutputStream();
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			//read from input and write to output (buffered bytes)
			byte[] buffer = new byte[2048];
			int bytesRead;    
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			LOGGER.error("Unable to read or write file", e);
			throw e;
		} finally {
			//cleanup
			try {
				if (output != null) output.close();
				if (input != null) input.close();
			} catch (IOException e) {
				LOGGER.error("Unable to close streams", e);
				throw e;
			}
		}
		
		LOGGER.info("WebFileStreamer End fetching all file...");		
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}