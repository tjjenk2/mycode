package tjjenk2.rest.filestream;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tjjenk2.rest.filestream.dao.DataFileServiceImpl;
import tjjenk2.rest.filestream.dao.WebFileStreamer;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/fetch")
public class FileStreamController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileStreamController.class);
	
	@Autowired
	private WebFileStreamer fileStreamer;
	
	@Autowired
	private DataFileServiceImpl fileNameReader;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void fetchAllFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		fileStreamer.streamFile(request, response, "some_other_file.pdf");
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public void fetchVersionFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		fileStreamer.streamFile(request, response, "gndb.version");
		
	}
	
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public void fetchFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="fileName", required=false) String fileName) throws IOException {
		if (fileName == null || fileName.trim().length() == 0) {
			throw new FileNotFoundException("Missing fileName parameter");
		}
		fileStreamer.streamFile(request, response, fileName);
		
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ModelAndView handleFileNotFoundException(FileNotFoundException ex) {
		ModelAndView mv = new ModelAndView(ClassUtils.getShortName(ex.getClass()), "exceptionMessage", ex.getMessage()); 
		return mv;
	}
}
