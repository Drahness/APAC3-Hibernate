package com.catalanrenegado.tinkdatabase.interfaces;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public interface Mantainable {
    /**
     * Overload of toString method, will return the default toString String if not overridden.
     *
     * @param cascade if u want to show the relationships between entities
     * @return a String depending on cascade
     */
    /*default String toString(boolean cascade) {
        if(cascade) {
            throw new NotImplementedException();
        } else {
            return this.toString();
        }
    }*/
    Map<String,String> getMap(boolean cascade);

    /**
     * Calling this will open a mini shell to change the attributes if null. If cascade is enabled, this will ask to change
     * relationships too if not null, if null will ask for a existent instance and if this is false will create a new instance.
     *
     * @param dbconn  the connection between the entity and the database.
     * @param cascade if it will ask to user when changing relationships.
     */
    /// TODO Cascade se refiere a que se saltara los checks al usuario sobre las relaciones. si es true. no preguntara y lo cambiaras directamente, si es false, pregunta.
    default void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        throw new NotImplementedException();
    }
}
