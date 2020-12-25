package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;

@Entity
@Table(name = "PARTTYPE_EXTRAS")
public class EntityExtra extends EntityMaterialStat {
	private static final long serialVersionUID = 5278649154610055194L;
	private int durability;

	public EntityExtra() {
		this.durability = 0;
	}
/*
	public EntityExtra(Material mat) {
		super(mat, TConstructPartTypes.EXTRA);
		this.durability = getMaterialStats().extraDurability;
	}
*/
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

	@Override
	public int hashCode() {
		return Objects.hash(durability);
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	@Override
	public String toString() {
		return "EntityExtra [toString()=" + super.toString() + ", durability=" + durability + "]";
	}
/*	@Override
	public void merge(IEntity o) {
		if(o instanceof EntityExtra) {
			EntityExtra instance = (EntityExtra) o;
			this.durability = instance.durability;
		}
	}
*/
}
