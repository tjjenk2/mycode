package tjjenk2.rest.filestream;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tjjenk2.rest.filestream.dao.DataFileServiceImpl;
import tjjenk2.rest.filestream.dao.TextFileReaderDao;
import tjjenk2.rest.filestream.model.DataFileInfo;

@Controller
@RequestMapping(value = "/file")
public class FileInformationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileInformationController.class);
	
	private String gndbVersionFileName;
	
	@Required
	@Value("#{ gndbxproperties['gndbx.version.file.name'] }")
    public void setGndbDataDirectory(String gndbDataDirectory) { 
		this.gndbVersionFileName = gndbDataDirectory;
    }	
	
	@Autowired
	private DataFileServiceImpl dataFileService;
	
	@Autowired
	private TextFileReaderDao textFileReaderDao;
	
	@RequestMapping(value = "/information", method = RequestMethod.GET)
	public String getFileInformation(HttpServletRequest request, HttpServletResponse response, Model uiModel) throws IOException {
		
		String fileVersion = textFileReaderDao.getFileContents(this.gndbVersionFileName);
		LOGGER.info("Retrieved file version: ["+fileVersion+"]");
		DataFileInfo[] fileInformation = dataFileService.getFileInformation();
		for (DataFileInfo dataFileInfo : fileInformation) {
			LOGGER.info("File Data: ["+dataFileInfo+"]");
		}
		
		uiModel.addAttribute("fileInfoList", fileInformation);
		uiModel.addAttribute("versionInfo", fileVersion);
		
		return "home";
	}
	
	//make a view called IOException.jsp???
	@ExceptionHandler(IOException.class)
	public String handleIOException(IOException ex, HttpServletRequest request) {
		LOGGER.error("Yo some bad file exception happened", ex);
		return ClassUtils.getShortName(ex.getClass());
	}
}