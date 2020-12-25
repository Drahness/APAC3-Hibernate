package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "MODIFIERS")

public class EntityModifier implements IEntity {
	private static final long serialVersionUID = -126203684757928687L;
	@Id
	@Column(name = "ID")
	private String id;
	@Column
	private boolean isTrait;

	@SuppressWarnings("unused")
	public EntityModifier() {}
/*
	public EntityModifier(IModifier modifier) {
		super(modifier);
		this.id = Finder.getIdentifier(modifier);
		this.isTrait = modifier instanceof AbstractTrait;
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
		EntityModifier other = (EntityModifier) obj;
		return Objects.equals(other.id, this.id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	/*
        @Override
        public IModule getModModule() {
            return ModuleTConstruct.getInstance();
        }
    */
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return "EntityModifier [id=" + id + ", isTrait=" + isTrait + "]";
	}
/*
	@Override
	protected IModifier resolveRepresentativeInstance() {
		for(IModifier modifier : Utils.getModifiers()) {
			if(Finder.getIdentifier(modifier).equals(this.id)) {
				return modifier;
			}
		}
		throw new RuntimeException("Failed to resolve RepresentativeInstance ID: "+this.getId());
	}
*/
/*	@Override
	public void merge(IEntity o) {
		if(o instanceof EntityModifier) {
			EntityModifier instance = (EntityModifier) o;
			this.isTrait = instance.isTrait;
		}
	}

	@Override
	public boolean canBeMerged() {
		return true;
	}
*/
}
