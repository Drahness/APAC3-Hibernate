package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;

@Entity
@Table(name = "PARTTYPE_SHAFTS")
public class EntityShaft extends EntityMaterialStat{
	private static final long serialVersionUID = 342640601051379851L;
	private int bonusAmmo;
	private float modifier;

	public EntityShaft() {
		this.bonusAmmo = 0;
		this.modifier = 0;
	}
/*
	public EntityShaft(Material mat) {
		super(mat, TConstructPartTypes.SHAFT);
		this.bonusAmmo = getMaterialStats().bonusAmmo;
		this.modifier = getMaterialStats().modifier;
	}
*/
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

	@Column
	public float getModifier() {
		return modifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bonusAmmo, modifier);
	}

	public void setBonusAmmo(int bonusAmmo) {
		this.bonusAmmo = bonusAmmo;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "EntityShaft [toString()=" + super.toString() + ", bonusAmmo=" + bonusAmmo + ", modifier=" + modifier
				+ "]";
	}
	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityShaft) {
			EntityShaft instance = (EntityShaft) o;
			this.bonusAmmo = instance.bonusAmmo;
			this.modifier = instance.modifier;
		}
	}*/
}
