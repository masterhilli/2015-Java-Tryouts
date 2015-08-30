package com.hilli.image.manipulation;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {
	private static final int IMG_WIDTH = 400;
	private static final int IMG_HEIGHT = 400;

	private static final String JPG = "jpg";
	private static final String PNG = "png";

	public static boolean createImagesOutOfOriginalPath(String path, String outputPath) {
		try {

			File imageToRead = new File(path);
			String fileName = imageToRead.getName();
			BufferedImage originalImage = ImageIO.read(imageToRead);

			createImageByType(outputPath + fileName, originalImage, JPG, true);
			createImageByType(outputPath + fileName, originalImage, JPG, false);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;

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
