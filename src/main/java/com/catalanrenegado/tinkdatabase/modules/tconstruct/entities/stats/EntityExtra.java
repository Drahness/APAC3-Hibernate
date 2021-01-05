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
@Table(name = "PARTTYPE_EXTRAS")
public class EntityExtra extends EntityMaterialStat {
    private static final long serialVersionUID = 5278649154610055194L;
    private int durability;

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Durability",String.valueOf(durability));
        return map;
    }

    public EntityExtra() {
        super("extra");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityExtra other = (EntityExtra) obj;
        return Objects.equals(other.durability, this.durability) // Pongo el and porque esto me parece que puede inducir
                // a errores facilmente
                && super.equals(other);
    }

    @Column
    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(durability);
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        int durability = Leer.leerEntero(String.format("<TinkersExtractor#%s>Inserta la durabilidad, actual (%d): ",this.getClass().getSimpleName(), this.durability));
        if (durability == 0 && this.durability != 0) {
            durability = this.durability;
        }
        this.durability = durability;
    }
}
