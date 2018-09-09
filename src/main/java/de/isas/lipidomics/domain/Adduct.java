/*
 * 
 */
package de.isas.lipidomics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author nilshoffmann
 */
@AllArgsConstructor
@Data
public class Adduct {

    private String type;
    private Integer charge;
    private Integer chargeSign;

    public Adduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setChargeSign(Integer sign) {
        if (sign != -1 || sign != 0 || sign != 1) {
            throw new IllegalArgumentException("Sign can only be -1, 0, or 1");
        }
    }

}
