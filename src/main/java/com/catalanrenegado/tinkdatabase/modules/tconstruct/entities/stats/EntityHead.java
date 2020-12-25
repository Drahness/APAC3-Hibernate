package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.abstractions.EntityMaterialStat;

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
		super();
		this.durability = 0;
		this.attack = 0;
		this.miningSpeed = 0;
		this.harvestLevel = 0;
	}
/*
	public EntityHead(Material mat) {
		super(mat, TConstructPartTypes.HEAD);
		this.durability = getMaterialStats().durability;
		this.attack = getMaterialStats().attack;
		this.miningSpeed = getMaterialStats().miningspeed;
		this.harvestLevel = getMaterialStats().harvestLevel;
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
		EntityHead other = (EntityHead) obj;
		return Objects.equals(other.attack, this.attack) && Objects.equals(other.durability, this.durability)
				&& Objects.equals(other.harvestLevel, this.harvestLevel)
				&& Objects.equals(other.miningSpeed, this.miningSpeed) && super.equals(other);
	}

	@Column
	public float getAttack() {
		return attack;
	}

	@Column
	public int getDurability() {
		return durability;
	}

	@Column
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Column
	public float getMiningSpeed() {
		return miningSpeed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attack, durability, harvestLevel, miningSpeed);
	}

	public void setAttack(float attack) {
		this.attack = attack;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public void setHarvestLevel(int harvestLevel) {
		this.harvestLevel = harvestLevel;
	}

	public void setMiningSpeed(float miningSpeed) {
		this.miningSpeed = miningSpeed;
	}

	@Override
	public String toString() {
		return "EntityHead [toString()=" + super.toString() + ", durability=" + durability + ", attack=" + attack
				+ ", miningSpeed=" + miningSpeed + ", harvestLevel=" + harvestLevel + "]";
	}
	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityHead) {
			EntityHead instance = (EntityHead) o;
			this.durability = instance.durability;
			this.attack = instance.attack;
			this.miningSpeed = instance.miningSpeed;
			this.harvestLevel = instance.harvestLevel;
		}
	}*/
}
