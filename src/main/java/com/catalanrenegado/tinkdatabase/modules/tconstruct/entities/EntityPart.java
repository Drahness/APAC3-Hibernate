package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "PARTS")
public class EntityPart implements IEntity {
	private static final long serialVersionUID = -649166906032676389L;
	private String id;
	private String name;
	private EntityMod domain;
	
	@Override@Id
	public String getId() {
		return id;
	}
	@Column(name = "_name_")
	public String getName() {
		return name;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public EntityMod getDomain() {
		return domain;
	}

	public void setId(String iD) {
		id = iD;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDomain(EntityMod domain) {
		this.domain = domain;
	}

	@SuppressWarnings("unused")
	public EntityPart() {}
/*
	public EntityPart(MaterialItem materialItem) {
		super(materialItem);
		id = Finder.getIdentifier(materialItem);
		name = Finder.getName(materialItem);
		domain = Instancer.getMod(DomainFinder.getDomain(materialItem));
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
		EntityPart other = (EntityPart) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
	}
/*
	@Override@Transient
	public IModule getModModule() {
		return ModuleTConstruct.getInstance();
	}
*/
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "EntityPart [ID=" + id + ", name=" + name + ", domain=" + domain + "]";
	}

	@Override@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { domain };
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	/*@Override
	protected MaterialItem resolveRepresentativeInstance() {
		return null; // TODO refactor this.
	}*/
	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityPart) {
			EntityPart instance = (EntityPart) o;
			this.domain = instance.domain;
			this.name = instance.name;
		}
	}
	@Override
	public boolean canBeMerged() {
		return true;
	}*/
}
