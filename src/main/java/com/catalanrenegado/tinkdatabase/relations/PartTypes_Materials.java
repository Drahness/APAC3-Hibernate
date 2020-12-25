package com.catalanrenegado.tinkdatabase.relations;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityMaterial;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityPartType;

@Entity
@Table
public class PartTypes_Materials implements IEntity {
	private static final long serialVersionUID = -7119548862162508497L;
	private int id;
	private EntityMaterial material;
	private EntityPartType partType;
	// private PartTypes_Materials_PK id;

	public PartTypes_Materials() {
		// id = null;
		material = null;
		partType = null;
	}
	/*public PartTypes_Materials(Material mat, TypeEnum partType) {
		this.material = Instancer.getMaterial(mat);
		this.partType = Instancer.getPartType(partType);
	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartTypes_Materials other = (PartTypes_Materials) obj;
		return Objects.equals(material, other.material) && Objects.equals(partType, other.partType);
	}

	@Id
	@Override
	public Integer getId() {
		if(id == 0) {
			id = this.hashCode(); 
		}
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(/* id, */material, partType);
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getId() + " ";
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public EntityMaterial getMaterial() {
		return material;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public EntityPartType getPartType() {
		return partType;
	}

	public void setMaterial(EntityMaterial material) {
		this.material = material;
	}

	public void setPartType(EntityPartType partType) {
		this.partType = partType;
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	@Override@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { material, partType };
	}
}
