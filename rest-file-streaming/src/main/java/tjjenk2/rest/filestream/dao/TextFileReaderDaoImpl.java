package tjjenk2.rest.filestream.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TextFileReaderDaoImpl implements TextFileReaderDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(TextFileReaderDaoImpl.class);
	
	private String gndbDataDirectory;
	
	@Required
	@Value("#{ gndbxproperties['gndbx.data.directory'] }")
    public void setGndbDataDirectory(String gndbDataDirectory) { 
		this.gndbDataDirectory = gndbDataDirectory;
    }
	
	public String getFileContents(String fileName) throws IOException {
		String fullFilePath = this.gndbDataDirectory + "/" + fileName;
		LOGGER.info("reading file: " + fullFilePath);
		
		InputStream input = null;
		StringBuilder fileContents = new StringBuilder();
		try {
			//get the binary file from the classpath
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullFilePath);
			Reader reader = new BufferedReader(new InputStreamReader(input));
			
			//read from input and write to output (buffered bytes)
			int currentChar;
			while ((currentChar = reader.read()) != -1) {
				fileContents.append((char) currentChar);
			}
		} catch (IOException e) {
			String errorMessage = "Unable to read or write file";
			LOGGER.error(errorMessage, e);
			throw e;
		} finally {
			//cleanup
			try {
				if (input != null) input.close();
			} catch (IOException e) {
				String errorMessage = "Unable to close file stream";
				LOGGER.error(errorMessage, e);
				throw e;
			}
		}	
		
		LOGGER.info("Returning file contents:\n" + fileContents.toString());
		
		return fileContents.toString();
	}
}