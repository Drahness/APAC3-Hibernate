package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityMaterialStat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "PARTTYPE_SHAFTS")
public class EntityShaft extends EntityMaterialStat {
    private static final long serialVersionUID = 342640601051379851L;
    private int bonusAmmo;
    private float modifier;

    public EntityShaft() {
        super("shaft");
    }
    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = super.getMap(cascade);
        map.put("Bonus Ammo",String.valueOf(bonusAmmo));
        map.put("Modifier",String.valueOf(modifier));
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
        EntityShaft other = (EntityShaft) obj;
        return Objects.equals(other.bonusAmmo, this.bonusAmmo) && Objects.equals(other.modifier, this.modifier)
                && super.equals(other);
    }

    @Column
    public int getBonusAmmo() {
        return bonusAmmo;
    }

    public void setBonusAmmo(int bonusAmmo) {
        this.bonusAmmo = bonusAmmo;
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
        return Objects.hash(bonusAmmo, modifier);
    }

}
