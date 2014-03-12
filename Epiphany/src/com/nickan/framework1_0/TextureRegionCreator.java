package com.nickan.framework1_0;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Just created to automate the creation of texture region to be used in animation for me not to rewrite it over and over again
 * @author Nickan
 *
 */
public class TextureRegionCreator {
	private TextureRegionCreator() {}

	/**
	 * Creates a texture region from the given texture and coordinates for animation
	 * @param texture
	 * @param srcX
	 * @param srcY
	 * @param width
	 * @param height
	 * @param columns
	 * @param totalFrames
	 * @return
	 */
	public static TextureRegion[] getTextureRegions(Texture texture, int srcX, int srcY, int width, int height,
			int columns, int totalFrames) {
		TextureRegion region[] = new TextureRegion[totalFrames];
		for (int i = 0; i < region.length; ++i) {
			int frameX = (i % columns) * width;
			int frameY = (i / columns) * height;
			region[i] = new TextureRegion(texture, srcX + frameX, srcY + frameY, width, height);
		}
		return region;
	}


}
