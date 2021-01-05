package com.catalanrenegado.tinkdatabase.embebbeds;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.Mantainable;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityPartType;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityTrait;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Embeddable
public class EmbeddedTrait implements Serializable, Mantainable {
    private static final long serialVersionUID = -1481482455540864380L;
    private EntityTrait trait;
    private EntityPartType partType_Id;

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("trait",trait.getId());
        map.put("parttype",partType_Id.getId());
        return map;
    }

    @SuppressWarnings("unused")
    public EmbeddedTrait() {
    }

    @SuppressWarnings("unused")
    public EmbeddedTrait(EntityTrait iTrait, EntityPartType partType_Id) {
        this.trait = iTrait;
        this.partType_Id = partType_Id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmbeddedTrait other = (EmbeddedTrait) obj;
        return Objects.equals(partType_Id, other.partType_Id) && Objects.equals(trait, other.trait);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trait, partType_Id);
    }

    @ManyToOne
    public EntityTrait getiTrait() {
        return trait;
    }

    @SuppressWarnings("unused")
    public void setiTrait(EntityTrait entityTrait) {
        this.trait = entityTrait;
    }

    @ManyToOne
    public EntityPartType getPartType_Id() {
        return partType_Id;
    }

    @SuppressWarnings("unused")
    public void setPartType_Id(EntityPartType partType_Id) {
        this.partType_Id = partType_Id;
    }

    @SuppressWarnings("unused")
    public void setTrait(EntityTrait trait) {
        this.trait = trait;
    }

    public String toString() {
        return this.partType_Id.getId() + ":" + this.trait.getId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.trait == null || cascade || Leer.leerBoolean(String.format("<TinkerExtractor#%s> Quieres cambiar el trait (%s)? S/N", this.getClass().getSimpleName(), this.trait))) {
            List<EntityTrait> traits = (List<EntityTrait>) dbconn.createQuery("FROM " + EntityTrait.class.getSimpleName());
            AtomicInteger i = new AtomicInteger();
            EntityTrait traitSelected = (EntityTrait) Leer.selectObject("Selecciona un trait.", traits, true);
            if (traitSelected == null) {
                traitSelected = new EntityTrait();
                traitSelected.changeAttributes(dbconn, cascade);
                dbconn.persist(traitSelected);
            }
            this.trait = traitSelected;
        }
        if (this.partType_Id == null || cascade || Leer.leerBoolean(String.format("<TinkerExtractor#%s> Quieres cambiar la parte (%s)? S/N", this.getClass().getSimpleName(), this.partType_Id))) {
            AtomicInteger i = new AtomicInteger();
            List<EntityPartType> partTypes = (List<EntityPartType>) dbconn.createQuery("FROM " + EntityPartType.class.getSimpleName());
            EntityPartType partTypeSelected = (EntityPartType) Leer.selectObject("Selecciona una parte.", partTypes, true);
            if (partTypeSelected == null) {
                partTypeSelected = new EntityPartType();
                partTypeSelected.changeAttributes(dbconn, cascade);
                dbconn.persist(partTypeSelected);
            }
            this.partType_Id = partTypeSelected;
        }
    }
}
