package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public EntityModifier() {
    }

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
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return id ;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("Id",id);
        map.put("Is trait",String.valueOf(isTrait));
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == null) {
            boolean existenceCheck;
            String identifier;
            do { // TODO The identity cannot be changed in runtime.
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier.equals("") && this.id != null) {
                    identifier = this.id;
                }
                //EntityModifier check = dbconn.get(this.getClass(),identifier);
                //existenceCheck = check != null && !this.equals(check);
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un mod, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        this.isTrait = Leer.leerBoolean(String.format("<TinkersExtractor#%s> Este modificador (%s), es un trait? S/N", this.getClass().getSimpleName(), this.id));
    }
}
