package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
/**
 * @Deprecated, modifiers = traits but traits != modifier.
 * 
 * @author Catalan Renegado
 *
 */
@Entity
@Table(name = "TRAITS")
public class EntityTrait implements IEntity {
	private static final long serialVersionUID = -7188699681319285288L;
	@Id
	@Column
	private String id;
	
	@Column(length = 500)
	private String description;
	@SuppressWarnings("unused")
	public EntityTrait() {}
/*
	public EntityTrait(ITrait trait) {
		super(trait);
		this.id = Finder.getIdentifier(trait);
		this.description = trait.getLocalizedDesc();
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
		EntityTrait other = (EntityTrait) obj;
		return Objects.equals(other.id, this.id)
				&& Objects.equals(other.description, this.description);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "EntityTrait [id=" + id + "]";
	}

	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityTrait) {
			EntityTrait instance = (EntityTrait) o;
			this.description = instance.description;
		}
	}

	@Override
	public boolean canBeMerged() {
		return true;
	}*/
}
