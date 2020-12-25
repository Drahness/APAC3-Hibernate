package com.catalanrenegado.tinkdatabase.embebbeds;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityPartType;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityTrait;

@Embeddable
public class EmbebbedTrait implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1481482455540864380L;
	// @ManyToOne//(cascade = CascadeType.ALL)
	// private final EntityMaterial mat;
	//@ManyToOne // (cascade = CascadeType.ALL)
	private EntityTrait trait;
	//@ManyToOne // (cascade = CascadeType.ALL)
	private EntityPartType partType_Id;

	public EmbebbedTrait() {}

	public EmbebbedTrait(EntityTrait iTrait, EntityPartType partType_Id) {
		this.trait = iTrait;
		this.partType_Id = partType_Id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmbebbedTrait other = (EmbebbedTrait) obj;
		return Objects.equals(partType_Id, other.partType_Id) && Objects.equals(trait, other.trait)
		// && Objects.equals(mat, other.mat)
		;
	}

	@Override
	public int hashCode() {
		return Objects.hash(trait, partType_Id);
	}
	@ManyToOne
	public EntityTrait getiTrait() {
		return trait;
	}
	@ManyToOne
	public EntityPartType getPartType_Id() {
		return partType_Id;
	}
	public void setTrait(EntityTrait trait) {
		this.trait = trait;
	}
	public void setPartType_Id(EntityPartType partType_Id) {
		this.partType_Id = partType_Id;
	}
	public String toString() {
		return this.partType_Id.getId()+":"+this.trait.getId();
	}

}
