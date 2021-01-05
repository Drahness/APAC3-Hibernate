package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;


import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "TOOLCORES")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EntityToolCore extends AbstractTinkersItem {
    private static final long serialVersionUID = -8930128904344947102L;
    private Float damageCutoff;
    private Float damagePotential;
    private Double attackSpeed;
    private Float knockback;
    private Float durabilityModifier;
    private Float miningSpeedModifier;

    @SuppressWarnings("unused")
    public EntityToolCore() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityToolCore other = (EntityToolCore) obj;
        return Objects.equals(attackSpeed, other.attackSpeed)
                && Objects.equals(knockback, other.knockback)
                && Objects.equals(damageCutoff, other.damageCutoff)
                && Objects.equals(miningSpeedModifier, other.miningSpeedModifier)
                && Objects.equals(damagePotential, other.damagePotential)
                //&& super.equals(other)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackSpeed, knockback, damageCutoff, miningSpeedModifier);
    }

    @Column
    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    @Column
    public float getDamagePotential() {
        return damagePotential;
    }

    public void setDamagePotential(Float damagePotential) {
        this.damagePotential = damagePotential;
    }

    @Column
    public float getDamageCutoff() {
        return damageCutoff;
    }

    public void setDamageCutoff(float damageCutoff) {
        this.damageCutoff = damageCutoff;
    }

    @Column
    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }

    @Column
    public float getMiningSpeedModifier() {
        return miningSpeedModifier;
    }

    public void setMiningSpeedModifier(float miningSpeedModifier) {
        this.miningSpeedModifier = miningSpeedModifier;
    }

    @Column
    public float getDurabilityModifier() {
        return durabilityModifier;
    }

    public void setDurabilityModifier(float durabilityModifier) {
        this.durabilityModifier = durabilityModifier;
    }

    @Transient
    public EntityToolCore getId() {
        return this;
    }


    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        super.changeAttributes(dbconn, cascade);
        float damageCutoff;
        float damagePotential;
        double attackSpeed;
        float knockback;
        float durabilityModifier;
        float miningSpeedModifier;
        damagePotential = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el multiplicador de daño final, actual (%f): ",this.getClass().getSimpleName(), this.damagePotential));
        if (damagePotential == 0.0f && this.damagePotential != null) {
            damagePotential = this.damagePotential;
        }
        attackSpeed = Leer.leerDouble(String.format("<TinkersExtractor#%s>Inserta la velocidad de ataque de la herramienta, actual (%f): ",this.getClass().getSimpleName(), this.attackSpeed));
        if (attackSpeed == 0.0 && this.attackSpeed != null) {
            attackSpeed = this.attackSpeed;
        }
        damageCutoff = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el maximo de daño de la herramienta, actual (%f): ",this.getClass().getSimpleName(), this.damageCutoff));
        if (damageCutoff == 0.0f && this.damageCutoff != null) {
            damageCutoff = this.damageCutoff;
        }
        knockback = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el empuje de la herramienta al golpear, actual (%f): ",this.getClass().getSimpleName(), this.knockback));
        if (knockback == 0.0f && this.knockback != null) {
            knockback = this.knockback;
        }
        durabilityModifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta el multiplicador de durabilidad final de la herramienta, actual (%f): ",this.getClass().getSimpleName(), this.durabilityModifier));
        if (durabilityModifier == 0.0f && this.durabilityModifier != null) {
            durabilityModifier = this.durabilityModifier;
        }
        miningSpeedModifier = Leer.leerFloat(String.format("<TinkersExtractor#%s>Inserta la rapidez de minado, actual (%f): ",this.getClass().getSimpleName(), this.miningSpeedModifier));
        if (miningSpeedModifier == 0.0f && this.miningSpeedModifier != null) {
            miningSpeedModifier = this.miningSpeedModifier;
        }
        this.damageCutoff = damageCutoff;
        this.damagePotential = damagePotential;
        this.attackSpeed = attackSpeed;
        this.knockback = knockback;
        this.durabilityModifier = durabilityModifier;
        this.miningSpeedModifier = miningSpeedModifier;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = super.getMap(cascade);
        map.put("Damage Cutoff",String.valueOf(damageCutoff)) ;
        map.put("Damage Potential",String.valueOf(damagePotential));
        map.put("AttackSpeed",String.valueOf(attackSpeed)) ;
        map.put("Knockback",String.valueOf(knockback));
        map.put("DurabilityModifier",String.valueOf(durabilityModifier)) ;
        map.put("Mining Speed Modifier",String.valueOf(miningSpeedModifier));
        return map;
    }
}
