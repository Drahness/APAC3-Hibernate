package com.catalanrenegado.tinkdatabase.abstractions;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
import com.catalanrenegado.tinkdatabase.relations.PartTypes_Materials;

@MappedSuperclass
abstract public class EntityMaterialStat implements IEntity {
	private static final long serialVersionUID = 619350608107994296L;

	private PartTypes_Materials partMaterials;

	public EntityMaterialStat() {}

	@Override
	public boolean needToBePersisted() {
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		EntityMaterialStat other = (EntityMaterialStat) o;
		return Objects.equals(other.partMaterials, this.partMaterials);
	}
	@Override
	public EntityMaterialStat getId() {
		return this;
	}
	// TODO This is odd, i will left this right here, when you reference an id
	// declared in a {@link MappedSuperclass} the id is the instance itself

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	public PartTypes_Materials getPartMaterials() {
		return partMaterials;
	}

	@Override
	public int hashCode() {
		return Objects.hash(partMaterials);
	}

	public void setId(PartTypes_Materials o) {
		partMaterials = o;
	}

	public void setPartMaterials(PartTypes_Materials o) {
		this.partMaterials = o;
	}

	@Override
	@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { partMaterials };
	}

}
