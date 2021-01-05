package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "BOWCORES")
public class EntityBowCore extends EntityToolCore {
    private static final long serialVersionUID = -3543286505804173762L;

    private float drawTime;
    private float baseProjectileDamage;
    private float projectileDamageModifier;

    @SuppressWarnings("unused")
    public EntityBowCore() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityBowCore other = (EntityBowCore) obj;
        return Objects.equals(drawTime, other.drawTime)
                && Objects.equals(baseProjectileDamage, other.baseProjectileDamage)
                && Objects.equals(projectileDamageModifier, other.projectileDamageModifier) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawTime, baseProjectileDamage, projectileDamageModifier);
    }

    @Column
    public float getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(float drawTime) {
        this.drawTime = drawTime;
    }

    @Column
    public float getBaseProjectileDamage() {
        return baseProjectileDamage;
    }

    public void setBaseProjectileDamage(float baseProjectileDamage) {
        this.baseProjectileDamage = baseProjectileDamage;
    }

    @Column
    public float getProjectileDamageModifier() {
        return projectileDamageModifier;
    }

    public void setProjectileDamageModifier(float projectileDamageModifier) {
        this.projectileDamageModifier = projectileDamageModifier;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float drawTime;
        float baseProjectileDamage;
        float projectileDamageModifier;
        drawTime = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el multiplicador de daño final, actual (%f): ",this.getClass().getSimpleName(), this.drawTime));
        if (drawTime == 0.0f && this.drawTime != 0.0f) {
            drawTime = this.drawTime;
        }
        baseProjectileDamage = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta la velocidad de ataque de la herramienta, actual (%f): ",this.getClass().getSimpleName(), this.baseProjectileDamage));
        if (baseProjectileDamage == 0.0f && this.baseProjectileDamage != 0.0f) {
            baseProjectileDamage = this.baseProjectileDamage;
        }
        projectileDamageModifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el maximo de daño de la herramienta, actual (%f): ",this.getClass().getSimpleName(), this.projectileDamageModifier));
        if (projectileDamageModifier == 0.0f && this.projectileDamageModifier != 0.0f) {
            projectileDamageModifier = this.projectileDamageModifier;
        }
        this.baseProjectileDamage = baseProjectileDamage;
        this.drawTime = drawTime;
        this.projectileDamageModifier = projectileDamageModifier;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> maps = super.getMap(cascade);
        maps.put("Drawtime",String.valueOf(drawTime));
        maps.put("Base Projectile Damage",String.valueOf(baseProjectileDamage));
        maps.put("Projectile Damage Modifier",String.valueOf(projectileDamageModifier));
        return maps;
    }
}
