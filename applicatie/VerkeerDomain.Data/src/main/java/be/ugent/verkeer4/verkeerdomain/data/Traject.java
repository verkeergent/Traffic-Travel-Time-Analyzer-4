package be.ugent.verkeer4.verkeerdomain.data;


public class Traject {
    
    private int id;
    private String naam;
    
    private double afstand;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @param naam the naam to set
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * @return the afstand
     */
    public double getAfstand() {
        return afstand;
    }

    /**
     * @param afstand the afstand to set
     */
    public void setAfstand(double afstand) {
        this.afstand = afstand;
    }
}
