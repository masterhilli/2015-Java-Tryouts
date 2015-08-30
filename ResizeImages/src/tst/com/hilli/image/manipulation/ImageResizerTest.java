package tst.com.hilli.image.manipulation;

import static org.junit.Assert.*;
import com.hilli.image.manipulation.ImageResizer;
import org.junit.Test;

public class ImageResizerTest {
	private static String TEST_FOLDER_PATH = "D:\\dev\\2015_JavaTryouts\\2015-Java-Tryouts\\ResizeImages\\tst\\";
	private static String TEST_FOLDER_OUTPUT_PATH = TEST_FOLDER_PATH + "output\\";

//	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathLandScapeImage_ReturnsTrue() {
		assertTrue(ImageResizer.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "test1.jpg", TEST_FOLDER_OUTPUT_PATH));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathPortraitImage_ReturnsTrue() {
		assertTrue(ImageResizer.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "test1_head.jpg", TEST_FOLDER_OUTPUT_PATH));
	}
	
	//@Test
	public void testCreateImagesOutOfOriginalPath_InputSmallerPortraitImage_ReturnsTrue() {
		assertTrue(ImageResizer.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "test2.jpg", TEST_FOLDER_OUTPUT_PATH));
	}
	
//	@Test
	public void testCreateImagesOutOfOriginalPath_InputWrongPath_ReturnsFalse() {
		assertFalse(ImageResizer.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "incorrect\\test1.jpg", TEST_FOLDER_OUTPUT_PATH));
	}


}
