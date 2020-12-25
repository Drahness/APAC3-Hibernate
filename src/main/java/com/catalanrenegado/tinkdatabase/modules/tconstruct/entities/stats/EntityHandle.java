package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;


@Entity
@Table(name = "PARTTYPE_HANDLES")
public class EntityHandle extends EntityMaterialStat{
	private static final long serialVersionUID = -682572813515676492L;
	private float modifier;
	private int durability;

	public EntityHandle() {
		this.modifier = 0;
		this.durability = 0;
	}

	/*public EntityHandle(Material mat) {
		super(mat, TConstructPartTypes.HANDLE);
		this.modifier = getMaterialStats().modifier;
		this.durability = getMaterialStats().durability;

	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityHandle other = (EntityHandle) obj;
		return Objects.equals(other.modifier, this.modifier) && Objects.equals(other.durability, this.durability)
				&& super.equals(other);
	}

	@Column
	public int getDurability() {
		return durability;
	}

	@Column
	public float getModifier() {
		return modifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(modifier, durability);
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "EntityHandle [toString()=" + super.toString() + ", modifier=" + modifier + ", durability=" + durability
				+ "]";
	}
	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityHandle) {
			EntityHandle instance = (EntityHandle) o;
			this.durability = instance.durability;
			this.modifier = instance.modifier;
		}
	}*/
}
