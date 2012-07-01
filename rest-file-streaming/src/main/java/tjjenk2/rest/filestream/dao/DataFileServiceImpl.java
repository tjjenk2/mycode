package tjjenk2.rest.filestream.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import tjjenk2.rest.filestream.model.DataFileInfo;

@Service
public class DataFileServiceImpl implements DataFileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataFileServiceImpl.class);
	
	
	private String gndbDataDirectory;
	
	@Required
	@Value("#{ gndbxproperties['gndbx.data.directory'] }")
    public void setGndbDataDirectory(String gndbDataDirectory) { 
		this.gndbDataDirectory = gndbDataDirectory;
    }
	
	public String getGndbDataPath() {
		return PathMatchingResourcePatternResolver.CLASSPATH_URL_PREFIX + this.gndbDataDirectory;
	}

	//public static final String GNDB_DATA_DIRECTORY = "gndb/data";
//	private static final String PATH = PathMatchingResourcePatternResolver.CLASSPATH_URL_PREFIX + GNDB_DATA_DIRECTORY;

	public DataFileInfo[] getFileInformation() {
		final String PATH = getGndbDataPath();
		ArrayList<DataFileInfo> fileInfoList = new ArrayList<DataFileInfo>();
		
		LOGGER.info("getting resources from [" + PATH + "]...");
		
		
//		if (ResourceUtils.isUrl(PATH)) {
//			File file = ResourceUtils.getFile(ResourceUtils.getURL(PATH));
//		}
		
		if (ResourceUtils.isUrl(PATH)) {
			PathMatchingResourcePatternResolver r = new PathMatchingResourcePatternResolver();
			try {
				Resource[] resources = r.getResources(PATH);
				LOGGER.info("resources[] length: " + resources.length);
				for (Resource resource : resources) {
					File fileDirectory = resource.getFile();
					
					if (fileDirectory.isDirectory()) {
						LOGGER.info("Directory: ["+fileDirectory.getName()+"]");
						File[] dataFiles = fileDirectory.listFiles();
						for (File currentFile : dataFiles) {
							DataFileInfo currentFileInfo = new DataFileInfo();
							currentFileInfo.setFileName(currentFile.getName());
							currentFileInfo.setLastModified(currentFile.lastModified());
							currentFileInfo.setFileSize(currentFile.length());	
							LOGGER.info("currentFileInfo: ["+currentFileInfo.toString()+"]");
							fileInfoList.add(currentFileInfo);
						}
					}
				}
			} catch (IOException e) {
				LOGGER.error("Unable to read resources", e);
			}
		} else {
			LOGGER.warn(PATH + "is not a resource path.");
		}

		
		return fileInfoList.toArray(new DataFileInfo[fileInfoList.size()]);
	}
}