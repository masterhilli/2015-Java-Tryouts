package tst.com.hilli.image.manipulation;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hilli.image.manipulation.ImageResizer;

import org.junit.BeforeClass;
import org.junit.Test;

public class ImageResizerTest {
	private static final String TEST2_JPG = "test2.jpg";
	private static final String TEST1_HEAD_JPG = "test1_head.jpg";
	private static final String TEST1_JPG = "test1.jpg";
	
	private static String TEST_FOLDER_PATH = "D:\\dev\\2015_JavaTryouts\\2015-Java-Tryouts\\ResizeImages\\tst\\";
	private static String TEST_FOLDER_OUTPUT_PATH = TEST_FOLDER_PATH + "output\\";
	
	private static ImageResizer imageResizerMax1500;
	
	@BeforeClass
	public static void createResizer() {
		imageResizerMax1500 = new ImageResizer(new Dimension(1500, 1500));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathLandScapeImage_CheckPathIfFileExists() {
		imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + TEST1_JPG, TEST_FOLDER_OUTPUT_PATH);
		assertTrue((new File(TEST_FOLDER_OUTPUT_PATH+TEST1_JPG)).exists() );
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathLandScapeImage_ReturnsTrue() {
		assertTrue(imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "test1.jpg", TEST_FOLDER_OUTPUT_PATH));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathPortraitImage_ReturnsTrue() {
		assertTrue(imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + TEST1_HEAD_JPG, TEST_FOLDER_OUTPUT_PATH));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputSmallerPortraitImage_ReturnsTrue() {
		assertTrue(imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + TEST2_JPG, TEST_FOLDER_OUTPUT_PATH));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputWrongPath_ReturnsFalse() {
		assertFalse(imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + "incorrect\\test1.jpg", TEST_FOLDER_OUTPUT_PATH));
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputCorrectPathPortraitImage_CheckForOutputDimension() throws IOException {
		imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + TEST1_HEAD_JPG, TEST_FOLDER_OUTPUT_PATH);
		BufferedImage outputFile = ImageIO.read(new File(TEST_FOLDER_OUTPUT_PATH + TEST1_HEAD_JPG));
		
		
		assertTrue(outputFile.getWidth() <= imageResizerMax1500.getMaximumDimension().getWidth());
		assertTrue(outputFile.getHeight() <= imageResizerMax1500.getMaximumDimension().getHeight());
	}
	
	@Test
	public void testCreateImagesOutOfOriginalPath_InputSmallerPortraitImage_CheckForOutputDimension() throws IOException {
		imageResizerMax1500.createImagesOutOfOriginalPath(TEST_FOLDER_PATH + TEST2_JPG, TEST_FOLDER_OUTPUT_PATH);
		
		BufferedImage outputFile = ImageIO.read(new File(TEST_FOLDER_OUTPUT_PATH + TEST2_JPG));
		BufferedImage inputFile = ImageIO.read(new File(TEST_FOLDER_PATH + TEST2_JPG));
		
		
		assertEquals(inputFile.getWidth(), outputFile.getWidth());
		assertEquals(inputFile.getHeight(), outputFile.getHeight());
		
	}


}
