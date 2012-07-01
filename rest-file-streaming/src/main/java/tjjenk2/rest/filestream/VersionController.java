package tjjenk2.rest.filestream;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tjjenk2.rest.filestream.dao.TextFileReaderDao;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/get")
public class VersionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VersionController.class);
	
	private static final String FILE_NAME = "/gndb.version";
	
	@Autowired
	private TextFileReaderDao textFileReaderDao;
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public void fetchAllFile(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("reading version file...");
		try {	
			String fileContents = textFileReaderDao.getFileContents(FILE_NAME);
			LOGGER.info(fileContents);
		} catch (IOException e) {
			LOGGER.error("ut oh", e);
		}
		LOGGER.info("reading version file...");
	}
}
