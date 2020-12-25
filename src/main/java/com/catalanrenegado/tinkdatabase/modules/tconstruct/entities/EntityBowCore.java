package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BOWCORES")
public class EntityBowCore extends EntityToolCore {
	private static final long serialVersionUID = -3543286505804173762L;

	private float drawTime;
	private float baseProjectileDamage;
	private float projectileDamageModifier;
	@SuppressWarnings("unused")
	public EntityBowCore() {}
/*
	public EntityBowCore(BowCore bow) {
		super(bow);
		this.drawTime = bow.getDrawTime();
		this.baseProjectileDamage = bow.baseProjectileDamage();
		this.projectileDamageModifier = bow.projectileDamageModifier();
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
		EntityBowCore other = (EntityBowCore) obj;
		return Objects.equals(drawTime, other.drawTime)
				&& Objects.equals(baseProjectileDamage, other.baseProjectileDamage)
				&& Objects.equals(projectileDamageModifier, other.projectileDamageModifier) && super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(drawTime, baseProjectileDamage, projectileDamageModifier);
	}

	@Column
	public float getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(float drawTime) {
		this.drawTime = drawTime;
	}

	@Column
	public float getBaseProjectileDamage() {
		return baseProjectileDamage;
	}

	public void setBaseProjectileDamage(float baseProjectileDamage) {
		this.baseProjectileDamage = baseProjectileDamage;
	}

	@Column
	public float getProjectileDamageModifier() {
		return projectileDamageModifier;
	}

	public void setProjectileDamageModifier(float projectileDamageModifier) {
		this.projectileDamageModifier = projectileDamageModifier;
	}

	@Override
	public String toString() {
		return "EntityBowCore [toString()=" + super.toString() + " ,drawTime=" + drawTime + ", baseProjectileDamage="
				+ baseProjectileDamage + ", projectileDamageModifier=" + projectileDamageModifier + "]";
	}
	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityBowCore) {
			super.merge(o);
			EntityBowCore instance = (EntityBowCore) o;
			this.baseProjectileDamage = instance.baseProjectileDamage;
			this.drawTime = instance.drawTime;
			this.projectileDamageModifier = instance.projectileDamageModifier;
		}
	}*/
}
