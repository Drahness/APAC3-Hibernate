package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "PARTTYPES")
public class EntityPartType implements IEntity {
    private static final long serialVersionUID = 6674731406224696980L;
    private String id;
    private EntityMod domain;

    public EntityPartType() {}

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("Id",id);
        map.put("Mod",domain.getName());
        return map;
    }

    @OneToOne
    public EntityMod getDomain() {
        return domain;
    }

    public void setDomain(EntityMod domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityPartType other = (EntityPartType) obj;
        return Objects.equals(other.domain, this.domain) && Objects.equals(other.id, this.id);
    }

    @Override
    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, domain);
    }

    @Override
    public String toString() {
        return id+"/"+this.domain.getName();
    }

    @Override
    @SuppressWarnings("unchecked")

    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == null) {
            boolean existenceCheck;
            String identifier;
            do {
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier.equals("") && this.id != null) {
                    identifier = this.id;
                }
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un material, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar el Mod de esta parte? Actual(%s) S/N", EntityMod.class.getSimpleName(), this.domain))) {
            List<EntityMod> mods = (List<EntityMod>) dbconn.createQuery("FROM " + EntityMod.class.getSimpleName()).list();
            this.domain = (EntityMod) Leer.selectObject("Selecciona un mod, o 0 si quieres agregar uno nuevo.", mods, false);
        }
    }
}
