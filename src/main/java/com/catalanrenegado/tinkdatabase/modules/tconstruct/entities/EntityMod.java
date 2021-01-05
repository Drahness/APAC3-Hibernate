package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "FORGE_MODS")
public class EntityMod implements IEntity {
    private static final long serialVersionUID = 7363894315486890318L;

    @Id
    private String ID;

    @Column
    private String version;
    // if i change this to method, will throw hydrate exception
    @Column
    private boolean dependsOnTinkers;

    @SuppressWarnings("unused")
    public EntityMod() {
    }

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

    public String getName() {
        return ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ID, this.version, this.dependsOnTinkers);
    }

    @Override
    public String toString() {
        return ID ;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("id",ID);
        map.put("Version",version);
        map.put("Depends On Tinkers",String.valueOf(dependsOnTinkers));
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.ID == null) {
            boolean existenceCheck;
            String identifier;
            do { // TODO The identity cannot be changed in runtime.
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.ID, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un mod, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.ID = identifier;
        }
        String version = Leer.leerTexto(String.format("<TinkersExtractor#EntityMod> Inserta la version del mod (%s): ", this.version));
        if (version.equals("") && this.version != null) {
            version = this.version;
        }
        boolean depends = Leer.leerBoolean(String.format("<TinkersExtractor#EntityMod> Depende de tinkers (%s)?: ", this.dependsOnTinkers));
        this.version = version;
        this.dependsOnTinkers = depends;
    }
}
