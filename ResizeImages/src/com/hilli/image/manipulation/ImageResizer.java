package com.hilli.image.manipulation;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import org.apache.commons.io.FilenameUtils;

public class ImageResizer {
	private static final Dimension maximumDimension = new Dimension(1500, 1500);

	private static final String JPG = "jpg";

	public static boolean createImagesOutOfOriginalPath(String path, String outputPath) {
		try {

			File imageToRead = new File(path);
			String fileName = FilenameUtils.removeExtension(imageToRead.getName());

			// first rotate the image depending on EXIF Information
			BufferedImage rotatedImage = rotateImage(imageToRead);

			// scale dimension of file
			Dimension newDimension = getScaledDimension(
					new Dimension(rotatedImage.getWidth(), rotatedImage.getHeight()), maximumDimension);

			createImageByType(outputPath + fileName, rotatedImage, JPG, newDimension, true);

		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return false;
		} catch (ImageProcessingException ipex) {
			System.out.println(ipex.getMessage());
			return false;
		} catch (MetadataException mex){
			System.out.println(mex.getMessage());
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
		ImageIO.write(resizeImage, type, new File(outputFilePath + "." + type));
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

	private static BufferedImage rotateImage(File originalFile) throws IOException, ImageProcessingException, MetadataException{
		BufferedImage originalImageBuffer = ImageIO.read(originalFile);

		Metadata metadata = ImageMetadataReader.readMetadata(originalFile);
		int orientation = getOrientation(metadata);

		Dimension dimensionFromMetadata = getFileDimensionFromMetadata(metadata);

		AffineTransform affineTransform = getAffineTransformFromMetadata(orientation, dimensionFromMetadata);

		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage destinationImage = new BufferedImage(originalImageBuffer.getHeight(),
				originalImageBuffer.getWidth(), originalImageBuffer.getType());
		destinationImage = affineTransformOp.filter(originalImageBuffer, destinationImage);
		return destinationImage;
	}

	private static AffineTransform getAffineTransformFromMetadata(int orientation, Dimension dimensionFromMetadata) {
		AffineTransform affineTransform = new AffineTransform();

		switch (orientation) {
		case 1:
			break;
		case 2: // Flip X
			affineTransform.scale(-1.0, 1.0);
			affineTransform.translate(-dimensionFromMetadata.width, 0);
			break;
		case 3: // PI rotation
			affineTransform.translate(dimensionFromMetadata.width, dimensionFromMetadata.height);
			affineTransform.rotate(Math.PI);
			break;
		case 4: // Flip Y
			affineTransform.scale(1.0, -1.0);
			affineTransform.translate(0, -dimensionFromMetadata.height);
			break;
		case 5: // - PI/2 and Flip X
			affineTransform.rotate(-Math.PI / 2);
			affineTransform.scale(-1.0, 1.0);
			break;
		case 6: // -PI/2 and -width
			affineTransform.translate(dimensionFromMetadata.height, 0);
			affineTransform.rotate(Math.PI / 2);
			break;
		case 7: // PI/2 and Flip
			affineTransform.scale(-1.0, 1.0);
			affineTransform.translate(-dimensionFromMetadata.height, 0);
			affineTransform.translate(0, dimensionFromMetadata.width);
			affineTransform.rotate(3 * Math.PI / 2);
			break;
		case 8: // PI / 2
			affineTransform.translate(0, dimensionFromMetadata.width);
			affineTransform.rotate(3 * Math.PI / 2);
			break;
		default:
			break;
		}
		return affineTransform;
	}

	private static Dimension getFileDimensionFromMetadata(Metadata metadata) throws MetadataException {
		JpegDirectory jpegDirectory = (JpegDirectory) metadata.getDirectory(JpegDirectory.class);
		return new Dimension (jpegDirectory.getImageWidth(), jpegDirectory.getImageHeight());
	}

	private static int getOrientation(Metadata metadata) {
		ExifIFD0Directory exifIFD0Directory = metadata.getDirectory(ExifIFD0Directory.class);
		
		int orientation = 1;
		try {
			orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return orientation;
	}
}