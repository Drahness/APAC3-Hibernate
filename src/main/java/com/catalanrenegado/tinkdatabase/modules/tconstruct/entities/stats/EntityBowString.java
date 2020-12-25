package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;

@Entity
@Table(name = "PARTTYPE_BOWSTRINGS")
public class EntityBowString extends EntityMaterialStat{
	private static final long serialVersionUID = 3309507296451055892L;
	private float modifier;

	public EntityBowString() {
		modifier = 0;
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

	@Override
	public int hashCode() {
		return Objects.hash(modifier);
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "EntityBowString [toString()=" + super.toString() + ", modifier=" + modifier + "]";
	}

	@Override
	public boolean needToBePersisted() {
		return false;
	}
}
