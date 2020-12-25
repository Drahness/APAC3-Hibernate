package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "COMPONENTS")
public class EntityComponent implements IEntity {
	private static final long serialVersionUID = -4346166039156093888L;

	public static long getSerialversionuid() { // NO_UCD (unused code)
		return serialVersionUID;
	}
	private int id;
	private ItemID core_Id;
	private SpriteTConstructBase texture;
	private List<EntityPartType> partTypes_Id;
	private EntityPart toolPart_Id;
	//private static int AUTO_INCREMENT = 0;
	@SuppressWarnings("unused")
	public EntityComponent() {}
	/*public EntityComponent(AbstractTinkersItem<?> core, EntityPart toolPart, List<EntityPartType> partType, String textureKey) {
		core_Id = Instancer.getItemID(core.getRepresentativeInstance());
		toolPart_Id = toolPart;
		partTypes_Id = partType;
		//id = ++AUTO_INCREMENT;
		texture = Instancer.getSpriteBase(textureKey);
	}*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityComponent other = (EntityComponent) obj;
		return Objects.equals(partTypes_Id, other.partTypes_Id) && Objects.equals(toolPart_Id, other.toolPart_Id)
				&& Objects.equals(core_Id, other.core_Id);
	}
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		if(id == 0) {
			id = this.hashCode();
		}
		return id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = EntityPart_ID_COLUMN_NAME)
	public EntityPart getToolPart_Id() {
		return toolPart_Id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public ItemID getCore_Id() {
		return core_Id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(/*partTypes_Id,*/ toolPart_Id, core_Id,texture);
	}

	public void setCore_Id(ItemID core_Id) {
		this.core_Id = core_Id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setToolPart_Id(EntityPart toolPart_Id) {
		this.toolPart_Id = toolPart_Id;
	}

	@Override
	public String toString() {
		return "EntityComponent [id=" + id + ", core_Id=" + core_Id + ", partTypes_Id=" + partTypes_Id
				+ ", toolPart_Id=" + toolPart_Id + "]";
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

	@Override@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { core_Id, toolPart_Id };
	}
	@ManyToMany(cascade = CascadeType.ALL)
	public List<EntityPartType> getPartTypes_Id() {
		return partTypes_Id;
	}

	public void setPartTypes_Id(List<EntityPartType> lpartTypes_Id) {
		this.partTypes_Id = lpartTypes_Id;
	}
	@ManyToOne
	public SpriteTConstructBase getTexture() {
		return texture;
	}

	public void setTexture(SpriteTConstructBase texture) {
		this.texture = texture;
	}
	/**@Override
	public void merge(IEntity o) {

	}
	@Override
	public boolean canBeMerged() {
		return false;
	}*/
}
