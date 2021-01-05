package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityMaterialStat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;


@Entity
@Table(name = "PARTTYPE_HANDLES")
public class EntityHandle extends EntityMaterialStat {
    private static final long serialVersionUID = -682572813515676492L;
    private float modifier;
    private int durability;

    public EntityHandle() {
        super("handle");
    }
    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Durability modifier",String.valueOf(modifier));
        map.put("Durability",String.valueOf(durability));
        return map;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityHandle other = (EntityHandle) obj;
        return Objects.equals(other.modifier, this.modifier) && Objects.equals(other.durability, this.durability)
                && super.equals(other);
    }

    @Column
    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Column
    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modifier, durability);
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float modifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta la durabilidad, actual (%f): ",this.getClass().getSimpleName(), this.modifier));
        if (modifier == 0 && this.modifier != 0) {
            modifier = this.modifier;
        }

        int durability = Leer.leerEntero(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%f): ",this.getClass().getSimpleName(), this.modifier));
        if (durability == 0.0f && this.durability != 0.0f) {
            durability = this.durability;
        }
        this.modifier = modifier;
        this.durability = durability;
    }
}
