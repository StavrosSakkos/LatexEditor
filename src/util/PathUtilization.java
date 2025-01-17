package util;

public class PathUtilization {

	public static final String BACKSLASH = System.getProperty("file.separator");
	public static final String TEMPLATE_ID = "templates"+ BACKSLASH;
	public static final String OUTPUT_FOLDER = "output" + BACKSLASH;
	public static final String PROJECT_PATH = System.getProperty("user.dir").split("src")[0] + BACKSLASH;
	public static final String OUTPUT_FOLDER_PATH = PROJECT_PATH + OUTPUT_FOLDER;
	public static final String TEMPLATE_ID_FOLDER_PATH = PROJECT_PATH + TEMPLATE_ID;
}