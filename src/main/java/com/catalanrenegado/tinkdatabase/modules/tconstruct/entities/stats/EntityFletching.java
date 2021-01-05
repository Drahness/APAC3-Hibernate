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
@Table(name = "PARTTYPE_FLETCHINGS")
public class EntityFletching extends EntityMaterialStat {
    private static final long serialVersionUID = 8776923769277300113L;
    private float accuracy;
    private float modifier;

    public EntityFletching() {
        super("fletching");
        this.accuracy = 0;
        this.modifier = 0;
    }
    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Accuracy",String.valueOf(accuracy));
        map.put("Modifier",String.valueOf(accuracy));
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
        EntityFletching other = (EntityFletching) obj;
        return Objects.equals(this.accuracy, other.accuracy) && Objects.equals(this.modifier, other.modifier)
                && super.equals(other);
    }

    @Column
    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
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
        return Objects.hash(accuracy, modifier);
    }


    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float accuracy = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta precision de la flecha resultante, actual (%f): ",this.getClass().getSimpleName(), this.accuracy));
        if (accuracy == 0.0f && this.accuracy != 0.0f) {
            accuracy = this.accuracy;
        }
        float modifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%f): ",this.getClass().getSimpleName(), this.modifier));
        if (modifier == 0.0f && this.modifier != 0.0f) {
            modifier = this.modifier;
        }
        this.accuracy = accuracy;
        this.modifier = modifier;
    }
}
