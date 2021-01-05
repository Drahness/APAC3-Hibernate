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
@Table(name = "PARTTYPE_BOWSTRINGS")
public class EntityBowString extends EntityMaterialStat {
    private static final long serialVersionUID = 3309507296451055892L;
    private float modifier;

    public EntityBowString() {
        super("bowstring");
        modifier = 0;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Durability modifier",String.valueOf(modifier));
        return map;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityBowString other = (EntityBowString) obj;
        return Objects.equals(other.modifier, this.modifier) && super.equals(other);
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
        return Objects.hash(modifier);
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float modifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el modificador de precision, actual (%f): ",this.getClass().getSimpleName(), this.modifier));
        if (modifier == 0.0f && this.modifier != 0.0f) {
            modifier = this.modifier;
        }
        this.modifier = modifier;
    }
}
