package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Posible duplicidad, se puede con simplemente referenciar desde {@link EntityMaterialStat}
 * a una instancia de {@link EntityMaterial}, posiblemente tambien sea por el session.get(PartTypeMaterial)
 */
@Entity
@Table
public class PartTypes_Materials implements IEntity {
    private static final long serialVersionUID = -7119548862162508497L;
    private int id;
    private EntityMaterial material;
    private EntityPartType partType;

    public PartTypes_Materials() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartTypes_Materials other = (PartTypes_Materials) obj;
        return Objects.equals(material, other.material) && Objects.equals(partType, other.partType);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(/*id,*/material, partType);
    }

    @Override
    public String toString() {
        return  id +
                "/" + material +
                "/" + partType;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public EntityMaterial getMaterial() {
        return material;
    }

    public void setMaterial(EntityMaterial material) {
        this.material = material;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public EntityPartType getPartType() {
        return partType;
    }

    public void setPartType(EntityPartType partType) {
        this.partType = partType;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("id",String.valueOf(id));
        map.put("Material",material.getId());
        map.put("PartType",partType.getId());
        return map;
    }

    /**
     * Voy a programar este metodo como el Joan del pasado lo planteo para no hacer cambios en la BBDD, que los
     * necesitaria.
     *
     * @param dbconn  the connection between the entity and the database.
     * @param cascade if it will ask to user when changing relationships.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == 0 || this.material == null || this.partType == null) {
            boolean exit;
            List<EntityMaterial> materialList = (List<EntityMaterial>) dbconn.createQuery("FROM " + EntityMaterial.class.getSimpleName()).list();
            List<EntityPartType> partTypeList = (List<EntityPartType>) dbconn.createQuery("FROM " + EntityPartType.class.getSimpleName()).list();
            do {
                EntityMaterial material = (EntityMaterial) Leer.selectObject("Selecciona material para componer este identificador: ", materialList, true);
                EntityPartType partType = (EntityPartType) Leer.selectObject("Selecciona tipo de parte para componer este identificador:", partTypeList, true);
                if (material == null) {
                    material = new EntityMaterial();
                    material.changeAttributes(dbconn, cascade);
                }
                if (partType == null) {
                    partType = new EntityPartType();
                    partType.changeAttributes(dbconn, cascade);
                }
                exit = Utils.identifierExistsInSession(dbconn, this, this.id, Objects.hash(material, partType));
                if (exit) {
                    this.id = Objects.hash(material, partType);
                    this.partType = partType;
                    this.material = material;
                }
            } while (!exit);
        }
    }
}
