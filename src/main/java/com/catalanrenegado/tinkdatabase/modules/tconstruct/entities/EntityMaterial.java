package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.embebbeds.EmbebbedTrait;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "MATERIALS")
public class EntityMaterial implements IEntity {
	private static final long serialVersionUID = 3023059943109261927L;
	private String id;

	private String name;
	/*@Column(name = "TEXTCOLOR")
	private final String textColor;*/
	private EntityMod domain; // Mod de el que viene esto tengo que verlo en mas profundidad ya que es una

	private List<EmbebbedTrait> traits;
	@SuppressWarnings("unused")
	public EntityMaterial() {}
	/*public EntityMaterial(Material material) {
		super(material);
		this.id = Finder.getIdentifier(material);
		this.name = Finder.getName(material);
		this.domain = Instancer.getMod(DomainFinder.getDomain(material));
		this.traits = Lists.newArrayList();
		for (TypeEnum partType : TypeEnum.values()) {
			if (material.hasStats(partType.getTypeID())) {
				for (ITrait entityTrait : material.getAllTraitsForStats(partType.getTypeID())) {
					traits.add(Instancer.getTrait(entityTrait, partType));
				}
			}
		}
	}*/

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
		EntityMaterial obj = (EntityMaterial) o;
		return Objects.equals(obj.domain, this.domain) && Objects.equals(obj.id, this.id)
				&& Objects.equals(obj.name, this.name)/* && Objects.equals(obj.textColor, this.textColor)*/
				&& Objects.equals(obj.traits, this.traits);
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public EntityMod getDomain() {
		return domain;
	}

	@Override	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	/*@Override@Transient
	public IModule getModModule() {
		return ModuleTConstruct.getInstance();
	}*/
	@Column(name = "MaterialName")
	public String getName() {
		return name;
	}

	/*public String getTextColor() {
		return textColor;
	}*/

	@Override
	public int hashCode() {
		return Objects.hash(this.domain, this.id, this.name);
	}

	@Override
	public String toString() {
		return "EntityMaterial [identifier=" + id + ", name=" + name + ", textColor=" + //textColor
				/*+*/ ", vertex_Color=" +  ", domain=" + domain + ", traits=" + traits + "]";
	}

	@Override@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { domain };
	}
	@Override
	public boolean needToBePersisted() {
		if(id.contains("render")) {
			return false;
		}
		return true;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "MATERIAL_TRAITS_PARTTYPES")
	public List<EmbebbedTrait> getTraits() {
		return traits;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setDomain(EntityMod domain) {
		this.domain = domain;
	}

	public void setTraits(List<EmbebbedTrait> traits) {
		this.traits = traits;
	}
	/*protected Material resolveRepresentativeInstance() {
		for(Material mat: Utils.getAllMaterials()) {
			if(Finder.getIdentifier(mat).equals(id)) {
				return mat;
			}
		}
		throw new RuntimeException("Failed to resolve RepresentativeInstance ID: "+id);
	}*/

	/*@Override
	public void merge(IEntity o) {
		if(o instanceof EntityMaterial) {
			EntityMaterial instance = (EntityMaterial) o;
			this.domain = instance.domain;
			this.name = instance.name;
			this.traits = instance.traits;
		}
	}

	@Override
	public boolean canBeMerged() {
		return true;
	}*/
}
