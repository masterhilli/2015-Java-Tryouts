package com.hilli.image.manipulation;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {
	private static final Dimension minimumDimension = new Dimension(1000, 1000);
	private static final int IMG_WIDTH = 400;
	private static final int IMG_HEIGHT = 400;

	private static final String JPG = "jpg";
	private static final String PNG = "png";

	public static boolean createImagesOutOfOriginalPath(String path, String outputPath) {
		try {

			File imageToRead = new File(path);
			String fileName = imageToRead.getName();
			BufferedImage originalImage = ImageIO.read(imageToRead);
			
			Dimension newDimension = getScaledDimension(new Dimension(originalImage.getWidth(), originalImage.getHeight()), minimumDimension);

			createImageByType(outputPath + fileName, originalImage, JPG, true);
			createImageByType(outputPath + fileName, originalImage, JPG, false);

		} catch (IOException e) {
			System.out.println(e.getMessage());
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

	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
	}
	

	private static void createImageByType(String outputFilePath, BufferedImage originalImage, String type, boolean withHint)
			throws IOException {
		BufferedImage resizeImage = null;
		int origtype = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		if (withHint) {
			resizeImage = resizeImageWithHint(originalImage, origtype);
		} else {
			resizeImage = resizeImage(originalImage, origtype);
		}
		ImageIO.write(resizeImage, type, new File(outputFilePath + (withHint ? "_hint." : ".") + type));
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}
