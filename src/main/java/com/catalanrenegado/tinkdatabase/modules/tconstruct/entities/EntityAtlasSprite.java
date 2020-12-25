package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.embebbeds.SpriteImage;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "A")
//@Access(AccessType.FIELD)
public class EntityAtlasSprite implements IEntity {
	/**
	 * https://www.codota.com/code/java/methods/net.minecraft.client.renderer.texture.TextureAtlasSprite/getMaxU
	 */
	private static final long serialVersionUID = 2045799047315580439L;
	private int id;
	public SpriteTConstructBase spriteName;
	///@ElementCollection
	//@CollectionTable(name = "sprites")
	public List<SpriteImage> spritesInFrames = new ArrayList<>();
	public EntityMaterial material;
	private int originX;
	private boolean belongsToToolpart;
	private int originY;
	private int width;
	private int height;
	private float minU;
	private float maxU;
	private float minV;
	private float maxV;
	private int frameCount;
	@SuppressWarnings("unused")
	public EntityAtlasSprite() {}

	/*public EntityAtlasSprite(TextureAtlasSprite tas) {
		super(tas);
		setSpriteName(getSpriteName() != null ? getSpriteName() : new SpriteTConstructBase(tas.getIconName()));
		originX = tas.getOriginX();
		originY = tas.getOriginY();
		width = tas.getIconWidth();
		height = tas.getIconHeight();
		minU = tas.getMinU();
		maxU = tas.getMaxU();
		minV = tas.getMinV();
		maxV = tas.getMinU();
		frameCount = tas.getFrameCount();
		if (frameCount != 0) {
			for (int i = 0; i < frameCount; i++) {
				int[][] frameData = tas.getFrameTextureData(i);
				for (int j = 0; j < frameData.length; j++) {
					getSpritesInFrames().add(new SpriteImage(i, j, frameData[j], this));
				}
			}
		}
		this.setBelongsToToolpart(TextureUtils
				.belongsToToolPart(getResourceLocation(getSpriteName().getModString(), getSpriteName().getPath())));
	}

	public EntityAtlasSprite(String key, String key1, TextureAtlasSprite tas) {
		this(tas);
		spriteName = Instancer.getSpriteBase(key);
		//setSpriteName(new SpriteTConstructBase(key));
		material = (EntityMaterial) Instancer.getEntityNullable(EntityMaterial.class, key1);
	}*/
	@Column
	public int getFrameCount() {
		return frameCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(belongsToToolpart, frameCount, height, material, maxU, maxV, minU, minV, originX, originY,
				spriteName, /*spritesInFrames,*/ width);
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public EntityMaterial getMaterial() {
		return material;
	}

	public void setMaterial(EntityMaterial material) {
		this.material = material;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityAtlasSprite other = (EntityAtlasSprite) obj;
		return Objects.equals(belongsToToolpart, other.belongsToToolpart)
				&& Objects.equals(frameCount, other.frameCount) && Objects.equals(height, other.height)
				&& Objects.equals(material, other.material) && Objects.equals(maxU, other.maxU)
				&& Objects.equals(maxV, other.maxV) && Objects.equals(minU, other.minU)
				&& Objects.equals(minV, other.minV) && Objects.equals(originX, other.originX)
				&& Objects.equals(originY, other.originY) && Objects.equals(spriteName, other.spriteName)
				&& Objects.equals(spritesInFrames, other.spritesInFrames) && Objects.equals(width, other.width);
	}

	@Override
	public boolean needToBePersisted() {
		/*if (getSpriteName().getIDItem().contains("broken_") /*|| material == null) {
			/*return false;
		}*/
		return true;
	}

	public int getHeight() {
		return height;
	}
	@Override
	@Id
	public Integer getId() {
		if(id == 0) {
			id = this.hashCode();
		}
		return id;
	}
	@Column
	public float getMaxU() {
		return maxU;
	}
	@Column
	public float getMaxV() {
		return maxV;
	}
	@Column
	public float getMinU() {
		return minU;
	}
	@Column
	public float getMinV() {
		return minV;
	}

	/*@Override@Transient
	public IModule getModModule() {
		return ModuleTConstruct.getInstance();
	}*/
	@Column
	public int getOriginX() {
		return originX;
	}
	@Column
	public int getOriginY() {
		return originY;
	}

	/*private ResourceLocation getResourceLocation(String domain, String path) {
		return new ResourceLocation(domain, path);
	}*/
	@ManyToOne(cascade = CascadeType.ALL)
	public SpriteTConstructBase getSpriteName() {
		return spriteName;
	}
	@Column
	public int getWidth() {
		return width;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	public void setFramesTextureData(List<SpriteImage> framesTextureData) {
		this.setSpritesInFrames(framesTextureData);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMaxU(float maxU) {
		this.maxU = maxU;
	}

	public void setMaxV(float maxV) {
		this.maxV = maxV;
	}

	public void setMinU(float minU) {
		this.minU = minU;
	}

	public void setMinV(float minV) {
		this.minV = minV;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public void setSpriteName(SpriteTConstructBase spriteName) {
		this.spriteName = spriteName;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isBelongsToToolpart() {
		return belongsToToolpart;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setBelongsToToolpart(boolean belongsToToolpart) {
		this.belongsToToolpart = belongsToToolpart;
	}

	@Override
	public String toString() {
		return "EntityAtlasSprite [id=" + id + ", spriteName=" + getSpriteName() + ", spritesInFrames="
				+ spritesInFrames + ", originX=" + originX + ", belongsToToolpart=" + belongsToToolpart + ", originY="
				+ originY + ", width=" + width + ", height=" + height + ", minU=" + minU + ", maxU=" + maxU + ", minV="
				+ minV + ", maxV=" + maxV + ", frameCount=" + frameCount + "]";
	}
	@ElementCollection
	@CollectionTable(name = "sprites")
	public List<SpriteImage> getSpritesInFrames() {
		return spritesInFrames;
	}

	public void setSpritesInFrames(List<SpriteImage> spritesInFrames) {
		this.spritesInFrames = spritesInFrames;
	}

	/*@Override
	protected TextureAtlasSprite resolveRepresentativeInstance() {
		for (String key1 : Utils.getSprites().keySet()) {
			for (String key2 : Utils.getSprites().get(key1).keySet()) {
				if(new EntityAtlasSprite(key1,key2,Utils.getSprites().get(key1).get(key2)).equals(this));
			}
		}
		throw new RuntimeException("Failed to resolve RepresentativeInstance ID: "+this.getId());
	}*/

	/*@Override
	public void merge(IEntity o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canBeMerged() {
		return false;
	}*/
}
