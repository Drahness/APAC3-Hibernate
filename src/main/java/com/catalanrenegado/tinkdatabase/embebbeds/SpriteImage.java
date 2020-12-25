package com.catalanrenegado.tinkdatabase.embebbeds;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityAtlasSprite;
import com.catalanrenegado.tinkdatabase.utils.TextureUtils;

@Embeddable
public class SpriteImage implements Serializable {
	private static final long serialVersionUID = 6585724161930327488L;
	private int frameIndex;
	private int spriteIndex;
	/**
	 * This is the image of the sprite
	 */
	private byte[] sprite;
	@SuppressWarnings("unused")
	public SpriteImage() {}

	public SpriteImage(int frameIndex, int spriteIndex, int[] spriteData, EntityAtlasSprite eas) {
		this.frameIndex = frameIndex;
		this.spriteIndex = spriteIndex;
		this.sprite = toByteArray(TextureUtils.getBufferedImage(eas.getHeight(), eas.getWidth(), spriteData));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpriteImage other = (SpriteImage) obj;
		return Objects.equals(frameIndex, other.frameIndex) && Arrays.equals(sprite, other.sprite) // Siempre que sea un
																									// array, es
																									// Arrays.equals
																									// loco, no objects
																									// que no es un
																									// objeto perse!
				&& Objects.equals(spriteIndex, other.spriteIndex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(frameIndex, Arrays.hashCode(sprite), spriteIndex);
	}

	private byte[] toByteArray(BufferedImage bf) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bf, "PNG", out);
		} catch (IOException e) {
			// Creo que es imposible que salte esta excepcion
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	@Column
	public int getFrameIndex() {
		return frameIndex;
	}
	@Column
	public int getSpriteIndex() {
		return spriteIndex;
	}
	@Column(columnDefinition = "BLOB")
	public byte[] getSprite() {
		return sprite;
	}

	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public void setSprite(byte[] sprite) {
		this.sprite = sprite;
	}
	
}
