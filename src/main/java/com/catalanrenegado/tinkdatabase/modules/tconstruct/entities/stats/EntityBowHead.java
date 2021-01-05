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
@Table(name = "PARTTYPE_BOWHEADS")
public class EntityBowHead extends EntityMaterialStat {
    private static final long serialVersionUID = 3309507296451055892L;
    private float bonusDamage;
    private float drawspeed;
    private float range;

    public EntityBowHead() {
        super("bow");
    }

	/*public EntityBowHead(Material mat) {
		super(mat, TConstructPartTypes.BOWHEAD);
		bonusDamage = getMaterialStats().bonusDamage;
		drawspeed = getMaterialStats().drawspeed;
		range = getMaterialStats().range;
	}*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityBowHead other = (EntityBowHead) obj;
        return Objects.equals(other.bonusDamage, this.bonusDamage) && Objects.equals(other.drawspeed, this.drawspeed)
                && Objects.equals(other.range, this.range) && super.equals(other);
    }

    @Column
    public float getBonusDamage() {
        return bonusDamage;
    }

    public void setBonusDamage(float bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    @Column
    public float getDrawspeed() {
        return drawspeed;
    }

    public void setDrawspeed(float drawspeed) {
        this.drawspeed = drawspeed;
    }

    @Column(name = "attack_range")
    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonusDamage, drawspeed, range);
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = super.getMap(cascade);
        map.put("Bonus Damage",String.valueOf(bonusDamage));
        map.put("Drawspeed",String.valueOf(drawspeed));
        map.put("Range",String.valueOf(range));
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float bonusDamage = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el bono de daño del arco, actual (%f): ",this.getClass().getSimpleName(), this.bonusDamage));
        if (bonusDamage == 0.0f && this.bonusDamage != 0.0f) {
            bonusDamage = this.bonusDamage;
        }
        float drawspeed = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el tiempo de tensado del arco, actual (%f): ",this.getClass().getSimpleName(), this.drawspeed));
        if (drawspeed == 0.0f && this.drawspeed != 0.0f) {
            drawspeed = this.drawspeed;
        }
        float range = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el rango del arco, actual (%f): ",this.getClass().getSimpleName(), this.range));
        if (range == 0.0f && this.range != 0.0f) {
            range = this.range;
        }
        this.bonusDamage = bonusDamage;
        this.range = range;
        this.drawspeed = drawspeed;
    }
}
