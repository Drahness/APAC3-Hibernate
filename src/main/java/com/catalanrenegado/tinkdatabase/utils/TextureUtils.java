package com.catalanrenegado.tinkdatabase.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
/*
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.model.MaterialModelLoader;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.Pattern;
*/
public class TextureUtils { // NO UCD (Unused code)
	/*public static boolean belongsToToolPart(ResourceLocation location) {
		for (IToolPart toolpart : TinkerRegistry.getToolParts()) {
			if (!(toolpart instanceof Item)) {
				continue; // WHY?!
			}
			try {
				Optional<ResourceLocation> storedResourceLocation = MaterialModelLoader
						.getToolPartModelLocation(toolpart);
				if (storedResourceLocation.isPresent()) {
					ResourceLocation stored = storedResourceLocation.get();
					ResourceLocation modelLocation = new ResourceLocation(stored.getResourceDomain(),
							"item/" + stored.getResourcePath());
					IModel partModel = ModelLoaderRegistry.getModel(modelLocation);

					// the actual texture of the part
					ResourceLocation baseTexture = partModel.getTextures().iterator().next();
					if (baseTexture.toString().equals(location.toString())) {
						return true;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
*/
	public static BufferedImage getBufferedImage(int height, int width, int[] bytes) {
		BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < bytes.length; i++) {
			bf.setRGB(i % width, i / height, bytes[i]);
		}

		return bf;
	}
/*
	public static BufferedImage getBufferedImage(ResourceLocation rsloc) throws IOException {// NO UCD (Unused code) //
																								// NO_UCD (unused code)
		return TextureUtil.readBufferedImage(getInputStream(rsloc));
	}*/
/*
	public static BufferedImage getBufferedImage(String domain, String path) throws IOException {// NO_UCD (unused code)
																									// // NO_UCD (unused
																									// code)
		return TextureUtil.readBufferedImage(getInputStream(domain, path));
	}
*//*
	public static List<BufferedImage> getBufferedImage(TextureAtlasSprite tas) { // NO_UCD (use private)
		List<BufferedImage> images = Lists.<BufferedImage>newArrayList();

		int[][] array = tas.getFrameTextureData(0);
		for (int[] bytesImage : array) {
			images.add(getBufferedImage(tas.getIconHeight(), tas.getIconWidth(), bytesImage));
		}
		return images;
	}

	public static BufferedImage getBufferedImageTexture(TextureAtlasSprite tas, int arr) { // NO_UCD (unused code)
		return getBufferedImage(tas).get(arr);
	}

	public static InputStream getInputStream(ResourceLocation rsloc) throws IOException { // NO_UCD (use private)
		return Minecraft.getMinecraft().getResourceManager().getResource(rsloc).getInputStream();
	}

	public static InputStream getInputStream(String domain, String path) throws IOException { // NO_UCD (use private)
		return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(domain, path))
				.getInputStream();
	}
	public static String getTexturePath(Item item)
	{
		return Pattern.getTextureIdentifier(item);
	}*/
}
