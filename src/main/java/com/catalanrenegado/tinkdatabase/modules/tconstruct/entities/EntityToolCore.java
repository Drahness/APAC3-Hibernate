package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOOLCORES")
public class EntityToolCore {
	private static final long serialVersionUID = -8930128904344947102L;
	private Float damageCutoff;
	private Float damagePotential;
	private Double attackSpeed;
	private Float knockback;
	private Float durabilityModifier;
	private Float miningSpeedModifier;
	@SuppressWarnings("unused")
	public EntityToolCore() {
		super();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityToolCore other = (EntityToolCore) obj;
		return Objects.equals(attackSpeed, other.attackSpeed) 
				&& Objects.equals(knockback, other.knockback)
				&& Objects.equals(damageCutoff, other.damageCutoff)
				&& Objects.equals(miningSpeedModifier, other.miningSpeedModifier) 
				&& Objects.equals(damagePotential, other.damagePotential) 
				//&& super.equals(other)
				;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attackSpeed, knockback, damageCutoff, miningSpeedModifier);
	}
	@Column
	public double getAttackSpeed() {
		return attackSpeed;
	}
	@Column(nullable = true)
	public float getDamagePotential() {
		return damagePotential;
	}
	@Column
	public float getDamageCutoff() {
		return damageCutoff;
	}
	@Column
	public float getKnockback() {
		return knockback;
	}
	@Column
	public float getMiningSpeedModifier() {
		return miningSpeedModifier;
	}
	@Column
	public float getDurabilityModifier() {
		return durabilityModifier;
	}

	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setDamageCutoff(float damageCutoff) {
		this.damageCutoff = damageCutoff;
	}

	public void setDurabilityModifier(float durabilityModifier) {
		this.durabilityModifier = durabilityModifier;
	}

	public void setKnockback(float knockback) {
		this.knockback = knockback;
	}

	public void setMiningSpeedModifier(float miningSpeedModifier) {
		this.miningSpeedModifier = miningSpeedModifier;
	}

	@Transient
	public EntityToolCore getId() {
		return this;
	}


	@Override
	public String toString() {
		return "EntityToolCore [toString()=" + /*super.toString() +*/ ", damageCutoff=" + damageCutoff + ", attackSpeed="
				+ attackSpeed + ", knockback=" + knockback + ", durabilityModifier=" + durabilityModifier
				+ ", miningSpeedModifier=" + miningSpeedModifier + "]";
	}

	public void setDamagePotential(Float damagePotential) {
		this.damagePotential = damagePotential;
	}


	public boolean needToBePersisted() {
		return true;
	}
}
