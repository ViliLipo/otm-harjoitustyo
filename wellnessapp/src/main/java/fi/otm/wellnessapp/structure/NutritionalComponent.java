/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.otm.wellnessapp.structure;

/**
 *
 * @author vili
 */
public class NutritionalComponent {
    private final String name;
    private final String unit;
    private final String cmpClass;
    private final String cmpClassp;
    
    public NutritionalComponent(String name, String unit, String cmpClass,
            String cmpClassp) {
        this.name = name;
        this.unit = unit;
        this.cmpClass = cmpClass;
        this.cmpClassp = cmpClassp;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUnit() {
        return this.unit;
    }
    public String getCmpClass () {
        return this.cmpClass;
    }
    
    public String getCmpClassp() {
        return this.cmpClassp;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object o){
        if(o.getClass().equals(this.getClass())) {
            NutritionalComponent nc = (NutritionalComponent)o;
            return this.name.contentEquals(nc.getName());
        }
        return false;
    }
    

}
