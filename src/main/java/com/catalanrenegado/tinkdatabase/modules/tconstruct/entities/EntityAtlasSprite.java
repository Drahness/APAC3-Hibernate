package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.ConsoleColors;
import com.catalanrenegado.tinkdatabase.DatabaseUtils;
import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.embebbeds.SpriteImage;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ATLASSPRITEs")
@SuppressWarnings({"unchecked","unused"})
public class EntityAtlasSprite implements IEntity {
    /**
     * https://www.codota.com/code/java/methods/net.minecraft.client.renderer.texture.TextureAtlasSprite/getMaxU
     */
    private static final long serialVersionUID = 2045799047315580439L;
    public SpriteTConstructBase spriteName;
    ///@ElementCollection
    //@CollectionTable(name = "sprites")
    public List<SpriteImage> spritesInFrames = new ArrayList<>();
    public EntityMaterial material;
    private int id;
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
    public EntityAtlasSprite() {
    }

    @Column
    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    @Id
    public Integer getId() {
        if (id == 0) {
            id = this.hashCode();
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public float getMaxU() {
        return maxU;
    }

    public void setMaxU(float maxU) {
        this.maxU = maxU;
    }

    @Column
    public float getMaxV() {
        return maxV;
    }

    public void setMaxV(float maxV) {
        this.maxV = maxV;
    }

    @Column
    public float getMinU() {
        return minU;
    }

    public void setMinU(float minU) {
        this.minU = minU;
    }

    @Column
    public float getMinV() {
        return minV;
    }

    public void setMinV(float minV) {
        this.minV = minV;
    }

    @Column
    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    @Column
    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public SpriteTConstructBase getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(SpriteTConstructBase spriteName) {
        this.spriteName = spriteName;
    }

    @Column
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setFramesTextureData(List<SpriteImage> framesTextureData) {
        this.setSpritesInFrames(framesTextureData);
    }

    public boolean isBelongsToToolpart() {
        return belongsToToolpart;
    }

    public void setBelongsToToolpart(boolean belongsToToolpart) {
        this.belongsToToolpart = belongsToToolpart;
    }

    @ElementCollection
    @CollectionTable(name = "sprites")
    public List<SpriteImage> getSpritesInFrames() {
        return spritesInFrames;
    }

    public void setSpritesInFrames(List<SpriteImage> spritesInFrames) {
        this.spritesInFrames = spritesInFrames;
    }


    @Override
    public String toString() {
        return "EntityAtlasSprite{" +
                "id=" + id +
                ", spriteName=" + spriteName +
                ", spritesInFrames=" + spritesInFrames +
                ", material=" + material;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(id));
        map.put("spriteName",String.valueOf(spriteName));
        map.put("material", String.valueOf(material));
        map.put("originX",String.valueOf(originX));
        map.put("belongsToToolpart",String.valueOf(belongsToToolpart));
        map.put("originY",String.valueOf(originY));
        map.put("width",String.valueOf(width));
        map.put("height",String.valueOf(height));
        map.put("minU",String.valueOf(minU));
        map.put("maxU",String.valueOf(maxU));
        map.put("minV",String.valueOf(minV));
        map.put("maxV",String.valueOf(maxV));
        map.put("frameCount",String.valueOf(frameCount));

        if(cascade) {
            List<Map<String,String>> maps = new ArrayList<>();
            if(spritesInFrames.size() > 0) {
                for (SpriteImage spriteImage: spritesInFrames) {
                    maps.add(spriteImage.getMap(false));
                }
            }
            if(maps.size() > 0) {
                map.put(DatabaseUtils.LINES, Objects.requireNonNull(DatabaseUtils.getFormattedTable(maps)).replace("\n","\n\t"));
                map.put(DatabaseUtils.HEADER,"\t "+ ConsoleColors.BLUE+SpriteImage.class.getSimpleName());
            }
        }
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == 0) {
            boolean existenceCheck;
            int identifier;
            do {
                identifier = Leer.leerEntero(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier == 0) {
                    identifier = this.id;
                }
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a una textura, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        SpriteTConstructBase spriteName;
        if(cascade && Leer.leerBoolean(String.format("Cambiar modificador? Actual(%s) S/N: ", this.spriteName))) {
            List<EntityModifier> query = (List<EntityModifier>) dbconn.createQuery("FROM "+SpriteTConstructBase.class.getSimpleName()).list();
            spriteName = (SpriteTConstructBase) Leer.selectObject(String.format("Existe los metadatos de la textura la base de datos? Actual (%s)", this.spriteName),query,true);
            if(spriteName == null) {
                spriteName = new SpriteTConstructBase();
                spriteName.changeAttributes(dbconn,cascade);
            }
            this.spriteName = spriteName;
        }
        List<SpriteImage> spritesInFrames = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (!cascade) {
            if (this.spritesInFrames.size() == 0) {
                sb.append("Ninguno.");
            } else {
                sb.append('\n');
                sb.append("\t Indice\t Binario representando la imagen.");
                this.spritesInFrames.forEach(component -> sb.append(String.format("\t %d\t - %s\n", component.getSpriteIndex(), Arrays.toString(component.getSprite()))));
            }
        }
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar los componentes que forman el item? Actual(%s) S/N", this.getClass().getSimpleName(), sb.toString()))) {
            boolean exit;
            do {
                SpriteImage selectedTrait = (SpriteImage) Leer.selectObject("Elige un trait a cambiar o 0 para añadir uno nuevo", this.spritesInFrames, true);
                if (selectedTrait == null) {
                    List<SpriteImage> allComponents = new ArrayList<>((Collection<? extends SpriteImage>) dbconn.createQuery("FROM " + SpriteImage.class.getSimpleName()).list());
                    selectedTrait = (SpriteImage) Leer.selectObject("Elige una referencia a una textura en la BBDD o 0 para añadir uno nuevo.", allComponents, true);
                    if (selectedTrait == null) {
                        selectedTrait = new SpriteImage();
                        selectedTrait.changeAttributes(dbconn, cascade);
                        dbconn.persist(selectedTrait);
                    }
                    this.spritesInFrames.add(selectedTrait);
                } else {
                    selectedTrait.changeAttributes(dbconn, cascade);
                }
                exit = Leer.leerBoolean("Has acabado los ajustes? S/N");
            } while (!exit);
        }
        if(cascade || Leer.leerBoolean(String.format("Cambiar material? Actual(%s) S/N: ", this.material))) {
            List<EntityModifier> query = (List<EntityModifier>) dbconn.createQuery("FROM "+EntityModifier.class.getSimpleName()).list();
            EntityMaterial material = (EntityMaterial) Leer.selectObject(String.format("Existe el modificador la base de datos? Actual (%s)", this.material),query,true);
            if(material == null) {
                material = new EntityMaterial();
                material.changeAttributes(dbconn, cascade);
            }
            this.material = material;
        }
        int originX = Leer.leerEntero("Inserta originX: ");
        if(this.originX != 0 && originX == 0) {
            originX = this.originX;
        }
        boolean belongsToToolpart = Leer.leerBoolean("La textura refiere a un toolcore? S/N ");
        int originY = Leer.leerEntero("Inserta originX: ");
        if(this.originY != 0 && originY == 0) {
            originY = this.originY;
        }
        int width = Leer.leerEntero("Inserta width: ");
        if(this.width != 0 && width == 0) {
            width = this.width;
        }
        int height = Leer.leerEntero("Inserta height: ");
        if(this.height != 0 && height == 0) {
            height = this.height;
        }
        float minU = Leer.leerFloat("Inserta minU: ");
        if(this.minU != 0.0f && minU == 0.0f) {
            minU = this.minU;
        }
        float maxU = Leer.leerFloat("Inserta maxU: ");
        if(this.maxU != 0.0f && maxU == 0.0f) {
            maxU = this.maxU;
        }
        float minV = Leer.leerFloat("Inserta minV: ");
        if(this.minV != 0.0f && minV == 0.0f) {
            minV = this.minV;
        }
        float maxV = Leer.leerEntero("Inserta maxV: ");
        if(this.maxV != 0.0f && maxV == 0.0f) {
            maxV = this.maxV;
        }
        int frameCount = Leer.leerEntero("Inserta el numero de frames totales: ");
        if(this.frameCount != 0 && frameCount == 0) {
            frameCount = this.frameCount;
        }
        this.originX = originX;
        this.belongsToToolpart = belongsToToolpart;
        this.originY = originY;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.maxU = maxU;
        this.minV = minV;
        this.maxV = maxV;
        this.frameCount = frameCount;
    }
}
