package be.ugent.verkeer4.verkeerweb.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RouteEdit {

    private Integer id;

    @NotNull(message = "Naam is verplicht")
    @Size(min = 1, max = 255, message = "Naam is verplicht en mag niet langer dan 255 karakters zijn")
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
