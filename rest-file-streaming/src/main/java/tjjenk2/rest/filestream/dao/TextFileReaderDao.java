package tjjenk2.rest.filestream.dao;

import java.io.IOException;

public interface TextFileReaderDao {
	String getFileContents(String filePath) throws IOException;
}