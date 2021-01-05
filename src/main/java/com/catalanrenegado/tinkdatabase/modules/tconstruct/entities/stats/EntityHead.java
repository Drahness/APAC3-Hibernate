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
@Table(name = "PARTTYPE_HEADS")
public class EntityHead extends EntityMaterialStat {
    // la id esta en la clase padre MATERIALSTAT
    private static final long serialVersionUID = 862144679511585785L;
    private int durability;
    private float attack;
    private float miningSpeed;
    private int harvestLevel;

    public EntityHead() {
        super("head");
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Attack",String.valueOf(attack));
        map.put("Durability",String.valueOf(durability));
        map.put("Mining Speed",String.valueOf(miningSpeed));
        map.put("Harvest Level",String.valueOf(harvestLevel));

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
        EntityHead other = (EntityHead) obj;
        return Objects.equals(other.attack, this.attack) && Objects.equals(other.durability, this.durability)
                && Objects.equals(other.harvestLevel, this.harvestLevel)
                && Objects.equals(other.miningSpeed, this.miningSpeed) && super.equals(other);
    }

    @Column
    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    @Column
    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Column
    public int getHarvestLevel() {
        return harvestLevel;
    }

    public void setHarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    @Column
    public float getMiningSpeed() {
        return miningSpeed;
    }

    public void setMiningSpeed(float miningSpeed) {
        this.miningSpeed = miningSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attack, durability, harvestLevel, miningSpeed);
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        int durability = Leer.leerEntero(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%d): ",this.getClass().getSimpleName(), this.durability));
        if (durability == 0.0f && this.durability != 0.0f) {
            durability = this.durability;
        }
        float attack = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%f): ",this.getClass().getSimpleName(), this.attack));
        if (attack == 0&& this.attack != 0) {
            attack = this.attack;
        }

        float miningSpeed = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%f): ",this.getClass().getSimpleName(), this.miningSpeed));
        if (miningSpeed == 0.0f && this.miningSpeed != 0.0f) {
            miningSpeed = this.miningSpeed;
        }

        int harvestLevel = Leer.leerEntero(String.format("<TinkersExtractor#%s>Inserta el modificador de durabilidad, actual (%d): ",this.getClass().getSimpleName(), this.harvestLevel));
        if (harvestLevel == 0.0f && this.harvestLevel != 0.0f) {
            harvestLevel = this.harvestLevel;
        }
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.harvestLevel = harvestLevel;
        this.attack = attack;
    }
}
