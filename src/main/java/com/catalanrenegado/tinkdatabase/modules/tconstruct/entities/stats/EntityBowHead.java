package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;


@Entity
@Table(name = "PARTTYPE_BOWHEADS")
public class EntityBowHead extends EntityMaterialStat {
	private static final long serialVersionUID = 3309507296451055892L;
	private float bonusDamage;
	private float drawspeed;
	private float range;

	public EntityBowHead() {
		bonusDamage = 0;
		drawspeed = 0;
		range = 0;
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

	@Column
	public float getDrawspeed() {
		return drawspeed;
	}

	@Column(name = "attack_range")
	public float getRange() {
		return range;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bonusDamage, drawspeed, range);
	}

	public void setBonusDamage(float bonusDamage) {
		this.bonusDamage = bonusDamage;
	}

	public void setDrawspeed(float drawspeed) {
		this.drawspeed = drawspeed;
	}

	public void setRange(float range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "EntityBowHead [toString()=" + super.toString() + ", bonusDamage=" + bonusDamage + ", drawspeed="
				+ drawspeed + ", range=" + range + "]";
	}

	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityBowHead) {
			EntityBowHead instance = (EntityBowHead) o;
			this.bonusDamage = instance.bonusDamage;
			this.range = instance.range;
			this.drawspeed = instance.drawspeed;
		}
	}*/

}
