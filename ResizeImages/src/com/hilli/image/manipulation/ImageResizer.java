package com.hilli.image.manipulation;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.ImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.io.FilenameUtils;

public class ImageResizer {
	private static final Dimension maximumDimension = new Dimension(1500, 1500);

	private static final String JPG = "jpg";

	public static boolean createImagesOutOfOriginalPath(String path, String outputPath) {
		try {

			File imageToRead = new File(path);
			String fileName = FilenameUtils.removeExtension(imageToRead.getName());
			BufferedImage originalImage = ImageIO.read(imageToRead);

			System.out.println("Dimension, width: " + originalImage.getWidth() + " height: " + originalImage.getHeight()
					+ " NAME: " + fileName);

			Dimension originalFileDimension = getCorrectDimensionFromFile(
					new Dimension(originalImage.getWidth(), originalImage.getHeight()), imageToRead);

			Dimension newDimension = getScaledDimension(originalFileDimension, maximumDimension);

			createImageByType(outputPath + fileName, originalImage, JPG, newDimension, true);
			createImageByType(outputPath + fileName, originalImage, JPG, newDimension, false);

		} catch (IOException e) {
			// System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// we only scale once, depending on the side which is bigger than the
		// other.
		if (original_width > original_height) {

			// first check if we need to scale width
			if (original_width > bound_width) {
				// scale width to fit
				new_width = bound_width;
				// scale height to maintain aspect ratio
				new_height = (new_width * original_height) / original_width;
			}
		} else {
			// then check if we need to scale even with the new height
			if (new_height > bound_height) {
				// scale height to fit instead
				new_height = bound_height;
				// scale width to maintain aspect ratio
				new_width = (new_height * original_width) / original_height;
			}
		}

		return new Dimension(new_width, new_height);
	}

	private static void createImageByType(String outputFilePath, BufferedImage originalImage, String type,
			Dimension newDimension, boolean withHint) throws IOException {
		BufferedImage resizeImage = null;
		int origtype = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		if (withHint) {
			resizeImage = resizeImageWithHint(originalImage, origtype, newDimension);
		} else {
			resizeImage = resizeImage(originalImage, origtype, newDimension);
		}
		ImageIO.write(resizeImage, type, new File(outputFilePath + (withHint ? "_hint." : ".") + type));
		System.out.println("Dimension, width: " + newDimension.width + " height: " + newDimension.height + " NAME: "
				+ outputFilePath + (withHint ? "_hint." : ".") + type);
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type, Dimension newDimension) {
		BufferedImage resizedImage = new BufferedImage(newDimension.width, newDimension.height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newDimension.width, newDimension.height, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, Dimension newDimension) {

		BufferedImage resizedImage = new BufferedImage(newDimension.width, newDimension.height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newDimension.width, newDimension.height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	private static Dimension getCorrectDimensionFromFile(Dimension originalReadDimension, File imageFile) {
		Dimension correctDimension = null;
		;
		try {
			JpegImageMetadata metadata =  (JpegImageMetadata)Sanselan.getMetadata(imageFile);

//			ArrayList myArrayList = metadata.getItems();
			TiffImageMetadata exifData = null;
			if (metadata != null)
				exifData=metadata.getExif();
			
			int orientation = 0;
			if (exifData != null) {
				orientation = exifData.findField(ExifTagConstants.EXIF_TAG_ORIENTATION).getIntValue();
//				int width = exifData.findField(ExifTagConstants.EXIF_TAG_IMAGE_WIDTH).getIntValue();
//				int height = exifData.findField(ExifTagConstants.EXIF_TAG_IMAGE_HEIGHT).getIntValue();
			}
			
			
			correctDimension = Sanselan.getImageSize(imageFile);
			
			if (orientation == 6 || orientation == 8) { // 6 = 90degrees & 8 = 270 degrees
				correctDimension = new Dimension (correctDimension.height, correctDimension.width);
			}
		} catch (ImageReadException e) {
			correctDimension = originalReadDimension;
		} catch (IOException e) {
			correctDimension = originalReadDimension;
		}

		return correctDimension;
	}

}

// Gona have to look into that ... *grml*
//	import java.awt.geom.AffineTransform;
//	import java.awt.image.AffineTransformOp;
//	import java.awt.image.BufferedImage;
//	import java.io.File;
//
//	import javax.imageio.ImageIO;
//
//	import com.drew.imaging.ImageMetadataReader;
//	import com.drew.metadata.Metadata;
//	import com.drew.metadata.exif.ExifIFD0Directory;
//	import com.drew.metadata.jpeg.JpegDirectory;

//	public class Main {
//
//	    private static String inFilePath = "C:\\Users\\TapasB\\Desktop\\MHIS031522.jpg";
//	    private static String outFilePath = "C:\\Users\\TapasB\\Desktop\\MHIS031522-rotated.jpg";
//
//	    public static void main(String[] args) throws Exception {
//	        File imageFile = new File(inFilePath);
//	        BufferedImage originalImage = ImageIO.read(imageFile);
//
//	        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
//	        ExifIFD0Directory exifIFD0Directory = metadata.getDirectory(ExifIFD0Directory.class);
//	        JpegDirectory jpegDirectory = (JpegDirectory) metadata.getDirectory(JpegDirectory.class);
//
//	        int orientation = 1;
//	        try {
//	            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//
//	        int width = jpegDirectory.getImageWidth();
//	        int height = jpegDirectory.getImageHeight();
//
//	        AffineTransform affineTransform = new AffineTransform();
//
//	        switch (orientation) {
//	        case 1:
//	            break;
//	        case 2: // Flip X
//	            affineTransform.scale(-1.0, 1.0);
//	            affineTransform.translate(-width, 0);
//	            break;
//	        case 3: // PI rotation
//	            affineTransform.translate(width, height);
//	            affineTransform.rotate(Math.PI);
//	            break;
//	        case 4: // Flip Y
//	            affineTransform.scale(1.0, -1.0);
//	            affineTransform.translate(0, -height);
//	            break;
//	        case 5: // - PI/2 and Flip X
//	            affineTransform.rotate(-Math.PI / 2);
//	            affineTransform.scale(-1.0, 1.0);
//	            break;
//	        case 6: // -PI/2 and -width
//	            affineTransform.translate(height, 0);
//	            affineTransform.rotate(Math.PI / 2);
//	            break;
//	        case 7: // PI/2 and Flip
//	            affineTransform.scale(-1.0, 1.0);
//	            affineTransform.translate(-height, 0);
//	            affineTransform.translate(0, width);
//	            affineTransform.rotate(3 * Math.PI / 2);
//	            break;
//	        case 8: // PI / 2
//	            affineTransform.translate(0, width);
//	            affineTransform.rotate(3 * Math.PI / 2);
//	            break;
//	        default:
//	            break;
//	        }       
//
//	        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);  
//	        BufferedImage destinationImage = new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), originalImage.getType());
//	        destinationImage = affineTransformOp.filter(originalImage, destinationImage);
//	        ImageIO.write(destinationImage, "jpg", new File(outFilePath));
//	    }
//	}
