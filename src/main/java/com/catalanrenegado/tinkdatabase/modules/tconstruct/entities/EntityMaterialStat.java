package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.ConsoleColors;
import com.catalanrenegado.tinkdatabase.DatabaseUtils;
import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.embebbeds.SpriteImage;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.*;

@MappedSuperclass
abstract public class EntityMaterialStat implements IEntity {
    private static final long serialVersionUID = 619350608107994296L;
    private final String type;
    private PartTypes_Materials partMaterials;

    public EntityMaterialStat(String type) {
        this.type = type;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(partMaterials.getId()));
        if(cascade) {
            if(partMaterials != null) {
                List<Map<String,String>> n = new ArrayList<>();
                n.add(partMaterials.getMap(false));
                map.put(DatabaseUtils.LINES, "\t " + Objects.requireNonNull(DatabaseUtils.getFormattedTable(n)).replace("\n", "\n\t"));
                map.put(DatabaseUtils.HEADER,"\t "+ ConsoleColors.BLUE+ PartTypes_Materials.class.getSimpleName());
            }
        }
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        EntityMaterialStat other = (EntityMaterialStat) o;
        return Objects.equals(other.partMaterials, this.partMaterials);
    }

    @Transient
    @Override
    public EntityMaterialStat getId() {
        return this;
    }
    // TODO This is odd, i will left this right here, when you reference an id
    // TODO declared in a {@link MappedSuperclass} the id is the instance itself

    public void setId(PartTypes_Materials o) {
        partMaterials = o;
    }

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    public PartTypes_Materials getPartMaterials() {
        return partMaterials;
    }

    public void setPartMaterials(PartTypes_Materials o) {
        this.partMaterials = o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partMaterials);
    }

    @Override
    @SuppressWarnings({"unchecked","ConstantConditions"})
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.partMaterials == null) { // TODO ask for existence i think this id cannot be changed.
            List<PartTypes_Materials> partTypes_materialsList = new ArrayList<>();
            for (PartTypes_Materials part : (List<PartTypes_Materials>) dbconn.createQuery("FROM " + PartTypes_Materials.class.getSimpleName()).list()) {
                if (part.getPartType().getId().equals(this.type)) {
                    partTypes_materialsList.add(part);
                }
            }
            PartTypes_Materials selectedMaterial;
            do {
                selectedMaterial = (PartTypes_Materials) Leer.selectObject(String.format("<TinkersExtractor#%s>Selecciona la relacion entre material y parte, o creala.", this.getClass().getSimpleName()), partTypes_materialsList, true);
            } while (selectedMaterial == null || !selectedMaterial.getPartType().getId().equalsIgnoreCase(type));
            if (selectedMaterial == null) { // intellij da warning aqui, pero es falso, dice que siempre sera nulo.
                selectedMaterial = new PartTypes_Materials();
                do {
                    selectedMaterial.changeAttributes(dbconn, cascade);
                    if (!selectedMaterial.getPartType().getId().equals(type)) {
                        System.out.printf("<TinkersExtractor#%s>%sError de introduccion, la parte debe coincidir con el tipo que establece esta clase.%s%n", this.getClass().getSimpleName(), ConsoleColors.RED,ConsoleColors.RESET);
                    }
                } while (!selectedMaterial.getPartType().getId().equals(type));
                dbconn.persist(this.partMaterials);
            }
            this.partMaterials = selectedMaterial;
        }
    }

    @Override
    public String toString() {
        return partMaterials.getMaterial().getName() + "/" + partMaterials.getPartType().getId();
    }
}
