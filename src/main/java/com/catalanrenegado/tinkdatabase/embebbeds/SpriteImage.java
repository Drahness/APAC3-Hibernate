package com.catalanrenegado.tinkdatabase.embebbeds;

import com.catalanrenegado.tinkdatabase.interfaces.Mantainable;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityAtlasSprite;
import com.catalanrenegado.tinkdatabase.utils.TextureUtils;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Embeddable
public class SpriteImage implements Serializable, Mantainable {
    private static final long serialVersionUID = 6585724161930327488L;
    private int frameIndex;
    private int spriteIndex;
    /**
     * This is the image of the sprite
     */
    private byte[] sprite;

    @SuppressWarnings("unused")
    public SpriteImage() {
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
        return Objects.equals(frameIndex, other.frameIndex) && Arrays.equals(sprite, other.sprite)
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

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    @Column
    public int getSpriteIndex() {
        return spriteIndex;
    }

    public void setSpriteIndex(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    @Column(columnDefinition = "BLOB")
    public byte[] getSprite() {
        return sprite;
    }

    public void setSprite(byte[] sprite) {
        this.sprite = sprite;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("Frame index",String.valueOf(this.frameIndex));
        map.put("Sprite index",String.valueOf(this.spriteIndex));
        //map.put("Bytes", Arrays.toString(this.sprite));
        return map;
    }
}
