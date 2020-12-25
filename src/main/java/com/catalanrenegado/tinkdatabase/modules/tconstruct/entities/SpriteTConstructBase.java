package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.TinkersExtractor;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;


/*
armor
parts
food
crossbow
pickaxe
kama
bolt
shovel
longbow
boots
shortbow
excavator
katana
scythe
leggings
hammer
chestplate
laser_gun
rapier
broadsword
lumberaxe
shuriken
helmet
arrow
modifiers
mattock
battlesign
longsword
cleaver
hatchet
frypan

SELECT * FROM tinkerdata.parts where _name_ not in (SELECT DISTINCT itemBase FROM tinkerdata.atlassprites where category like "parts"); -- No son COMPARTIDAS 
SELECT * FROM tinkerdata.parts where _name_ in (SELECT itemBase FROM tinkerdata.atlassprites where category like "parts") ; -- Saber que partes son compartidas
SELECT DISTINCT * FROM tinkerdata.atlassprites, tinkerdata.sprites where sprites.EntityAtlasSprite_id = atlassprites.id and category like 'armor'; -- parts y armor son casi lo mismo
SELECT DISTINCT * FROM tinkerdata.atlassprites, tinkerdata.sprites where sprites.EntityAtlasSprite_id = atlassprites.id and category like 'kama'; -- comprobacion que tiene un arma
SELECT DISTINCT * FROM tinkerdata.atlassprites, tinkerdata.sprites where sprites.EntityAtlasSprite_id = atlassprites.id and category like 'parts' and itemBase like 'binding'; 
-- los binding en la categoria concreta del arma, es la representacion en su propio modelo, mientras que en parts, es la representacion del item, quiero ambos.
 */
@Entity
@Table(name = "SPRITEREFERENCES")
public class SpriteTConstructBase implements IEntity {
	private static final long serialVersionUID = 2526766613977136031L;
	private Integer id;
	private EntityModifier modifier = null;
	private EntityMod modId;
	private String module;
	private String category;
	private String itemBase;
/*
	@Transient
	private transient String[] sections;
*/
	@SuppressWarnings("unused")
	public SpriteTConstructBase() {}
	/*public SpriteTConstructBase(String iconName) {
		sections = splitSections(iconName);
		this.modId = Instancer.getMod(sections[0]);
		this.module = sections[1];
		this.category = sections[2];
		this.itemBase = sections[3];
		//this.entity_Component = null; // TODO
	}
	public SpriteTConstructBase(ResourceLocation iconName) {
		this(iconName.toString());
	}
	private String[] splitSections(String iconName) {
		TinkersExtractor.log.info(iconName);
		String[] mod = iconName.split(":");
		String[] other = mod[1].split("/");
		return new String[] {mod[0],other[0],other[1],other[2]};
	}*/
	@Transient
	public String getModString() {
		return modId.toString();
	}
	@Transient
	public String getIDItem() {
		return itemBase;
	}
	/*@ManyToMany
	public List<EntityComponent> getReference() {
		return entity_Component;
	}
	public void setReference(List<EntityComponent> reference) {
		this.entity_Component = reference;
	}*/
	@Transient
/*	public String getPath() {
		return sections[1];
	}*/
	public void setId(int id) {
		this.id = id;
	}
	@Override@Id
	public Integer getId() {
		if(id == null) {
			id = Objects.hash(modId,module,category,itemBase);
		}
		return id;
	}
	/*@Override@Transient
	public IModule getModModule() {
		return ModuleTConstruct.getInstance();
	}*/

	@ManyToOne
	public EntityMod getMod() {
		return modId;
	}

	@Column
	public String getModule() {
		return module;
	}

	@Column
	public String getCategory() {
		return category;
	}

	@Column
	public String getItem() {
		return itemBase;
	}

	public void setMod(EntityMod mod) {
		this.modId = mod;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setItem(String item) {
		this.itemBase = item;
	}

	@Transient
	@Override
	public int hashCode() {
		return Objects.hash(modId, module, category, itemBase,/* entity_Component,*/modifier);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpriteTConstructBase other = (SpriteTConstructBase) obj;
		return 		Objects.equals(modId, other.modId) 
				&& 	Objects.equals(module, other.module)
				&& 	Objects.equals(modifier, other.modifier)
				&& 	Objects.equals(category, other.category)
				//&&  Objects.equals(entity_Component, other.entity_Component)
				&& 	Objects.equals(itemBase, other.itemBase);

	}

	@Override
	public String toString() {
		return modId + ":" + module + "/" + category + "/" + itemBase;
	}
	@ManyToOne
	public EntityModifier getModifier() {
		return modifier;
	}
	public void setModifier(EntityModifier modifier) {
		this.modifier = modifier;
	}
	@Override

	public boolean needToBePersisted() {
		return true;
	}
}
