package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;

@Entity
@Table(name = "PARTTYPE_FLETCHINGS")
public class EntityFletching extends EntityMaterialStat {
	private static final long serialVersionUID = 8776923769277300113L;
	private float accuracy;
	private float modifier;

	public EntityFletching() {
		this.accuracy = 0;
		this.modifier = 0;
	}
/*
	public EntityFletching(Material mat) {
		super(mat, TConstructPartTypes.FLETCHING);
		this.accuracy = getMaterialStats().accuracy;
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
		EntityFletching other = (EntityFletching) obj;
		return Objects.equals(this.accuracy, other.accuracy) && Objects.equals(this.modifier, other.modifier)
				&& super.equals(other);
	}

	@Column
	public float getAccuracy() {
		return accuracy;
	}

	@Column
	public float getModifier() {
		return modifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accuracy, modifier);
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "EntityFletching [toString()=" + super.toString() + ", accuracy=" + accuracy + ", modifier=" + modifier
				+ "]";
	}
/*	@Override
	public void merge(IEntity o) {
		if(o instanceof EntityFletching) {
			EntityFletching instance = (EntityFletching) o;
			this.accuracy = instance.accuracy;
			this.modifier = instance.modifier;
		}
	}*/
}
