package com.catalanrenegado.tinkdatabase.interfaces;

import java.io.Serializable;

public interface IEntity extends Serializable, Mantainable {
    /**
     * Get the id of this object
     *
     * @return The id NEEDS to implement the {@link Serializable} and you can change
     * the return to label this as a property getter
     */
    Serializable getId();
}
