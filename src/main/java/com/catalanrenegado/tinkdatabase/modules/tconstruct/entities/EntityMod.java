package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "FORGE_MODS")
public class EntityMod implements IEntity {
	private static final long serialVersionUID = 7363894315486890318L;

	@Id
	private String ID;

	@Column
	private String version;

	@Column
	private boolean dependsOnTinkers;

	@SuppressWarnings("unused")
	public EntityMod() {}

	/*public EntityMod(ModContainer modContainer) {
		super(modContainer);
		this.ID = Finder.getIdentifier(modContainer);
		this.version = modContainer.getVersion();
		this.dependsOnTinkers = dependsOnTinkers();
	}*/

	public EntityMod(String domain) {
		//this(Utils.getModContainer(domain));
	}

	/*private boolean dependsOnTinkers() {
		for (ArtifactVersion art : getRepresentativeInstance().getDependencies()) {
			if (art.getLabel().equals("tconstruct")) {
				return true;
			}
		}
		return false;

	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityMod other = (EntityMod) obj;
		return Objects.equals(this.ID, other.ID) && Objects.equals(this.version, other.version)
				&& Objects.equals(this.dependsOnTinkers, other.dependsOnTinkers);
	}

	@Override
	public Serializable getId() {
		return ID;
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	/*@Override
	public IModule getModModule() {
		return ModuleTConstruct.getInstance();
	}
	*/
	public String getName() {
		return ID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.ID, this.version, this.dependsOnTinkers);
	}

	@Override
	public String toString() {
		return "EntityMod [ID=" + ID + ", version=" + version + ", dependsOnTinkers=" + dependsOnTinkers + "]";
	}

	/*@Override
	protected ModContainer resolveRepresentativeInstance() {
		for (ModContainer mod : Utils.getMods()) {
			if(Finder.getIdentifier(mod).equals(this.ID)) {
				return mod;
			}
		}
		throw new RuntimeException("Failed to resolve RepresentativeInstance ID: "+this.ID);
	}*/
/*
	@Override
	public void merge(IEntity o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canBeMerged() {
		return true;
	}
*/
}
