package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.ConsoleColors;
import com.catalanrenegado.tinkdatabase.DatabaseUtils;
import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.embebbeds.EmbeddedTrait;
import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COMPONENTS")
public class EntityComponent implements IEntity {
    private static final long serialVersionUID = -4346166039156093888L;
    private int id;
    private ItemID core_Id;
    private SpriteTConstructBase texture;
    private final List<EntityPartType> partTypes_Id = new ArrayList<>();
    private EntityPart toolPart_Id;
    @SuppressWarnings("unused")
    public EntityComponent() {}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityComponent other = (EntityComponent) obj;
        return Objects.equals(partTypes_Id, other.partTypes_Id) && Objects.equals(toolPart_Id, other.toolPart_Id)
                && Objects.equals(core_Id, other.core_Id);
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("id",String.valueOf(id));
        map.put("ToolId",core_Id.getItem());
        map.put("TextureId",String.valueOf(texture.getId()));
        map.put("Part",toolPart_Id.getId());
        if(cascade) {
            List<Map<String,String>> maps = new ArrayList<>();
            if(partTypes_Id.size() > 0) {
                for (EntityPartType partType: partTypes_Id) {
                    maps.add(partType.getMap(false));
                }
                map.put(DatabaseUtils.HEADER,ConsoleColors.BLUE+ EntityPartType.class.getSimpleName()+ConsoleColors.RESET);
                map.put(DatabaseUtils.LINES, "\t"+ Objects.requireNonNull(DatabaseUtils.getFormattedTable(maps)).replace("\n","\n\t"));
            }
        }
        return map;
    }

    /**
     * Esta clase usa la misma tecnica que {@link PartTypes_Materials}, hashcodea las relaciones y crea una id a partir de ellas.
     *
     * Haciendolas immutables, recheckear en el projecto real si esto es necesario.
     *
     * @return the hashcoded id
     */
    @Id
    @Override
    public Integer getId() {
        if (id == 0) {
            id = this.hashCode();
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = EntityPart_ID_COLUMN_NAME)
    public EntityPart getToolPart_Id() {
        return toolPart_Id;
    }

    public void setToolPart_Id(EntityPart toolPart_Id) {
        this.toolPart_Id = toolPart_Id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public ItemID getCore_Id() {
        return core_Id;
    }

    public void setCore_Id(ItemID core_Id) {
        this.core_Id = core_Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*partTypes_Id,*/ toolPart_Id, core_Id, texture);
    }

    @Override
    public String toString() {
        return id +" / "+ core_Id.getItem() +" | "+ partTypes_Id +" | "+ toolPart_Id.getId();
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<EntityPartType> getPartTypes_Id() {
        return partTypes_Id;
    }

    public void setPartTypes_Id(List<EntityPartType> lpartTypes_Id) {
        this.partTypes_Id.addAll(lpartTypes_Id);
    }

    @ManyToOne
    public SpriteTConstructBase getTexture() {
        return texture;
    }

    public void setTexture(SpriteTConstructBase texture) {
        this.texture = texture;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if(this.id == 0 || this.core_Id == null || this.toolPart_Id == null) {
            List<ItemID> itemcores = (List<ItemID>) dbconn.createQuery("FROM "+ ItemID.class.getSimpleName()).list();
            ItemID selectedItemID = (ItemID) Leer.selectObject("Selecciona la identidad de la herramienta.",itemcores,true);
            if(selectedItemID == null) {
                selectedItemID = new ItemID();
                selectedItemID.changeAttributes(dbconn,cascade);
                dbconn.persist(selectedItemID);
            }
            List<EntityPartType> entityPartsTypes = (List<EntityPartType>) dbconn.createQuery("FROM " + EntityPartType.class.getSimpleName());
            boolean exit;
            do {
                EntityPartType selectedPart = (EntityPartType) Leer.selectObject("Selecciona una parte o creala.",entityPartsTypes,true);
                this.partTypes_Id.add(selectedPart);
                exit = Leer.leerBoolean("Has acabado los ajustes? S/N ");
            } while(!exit);
            List<SpriteTConstructBase> textures = (List<SpriteTConstructBase>) dbconn.createQuery("FROM "+SpriteTConstructBase.class.getSimpleName()).list();
            SpriteTConstructBase selectedTexture = (SpriteTConstructBase) Leer.selectObject("Selecciona la textura.", textures, false);
            if(selectedTexture == null) {
                selectedTexture = new SpriteTConstructBase();
                selectedTexture.changeAttributes(dbconn,cascade);
                dbconn.persist(selectedTexture);
            }
            this.core_Id = selectedItemID;
            this.texture = selectedTexture;
        }
    }
}
