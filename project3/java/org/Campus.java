package org.example.project3_v3;

/**
 * Camous enum class that holds the campus used in booking
 * @author joshuaH, alexG
 */
public enum Campus {

    /** Busch campus in New Brunswick. */
    BUSCH("Busch", "New Brunswick"),

    /** Livingston campus in New Brunswick. */
    LIVINGSTON("Livingston", "New Brunswick"),

    /** Cook campus in New Brunswick. */
    COOK("Cook", "New Brunswick"),

    /** Newark campus in Newark. */
    NEWARK("Newark", "Newark"),

    /** Camden campus in Camden. */
    CAMDEN("Camden", "Camden");

    /** Readable campus name used for output formatting. */
    private final String name;

    /** City where the campus is located. */
    private final String city;

    /**
     * Constructs a campus constant with its display name and city.
     * @param name the campus name
     * @param city the city the campus is on
     */
    Campus(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * Returns the display name of the campus
     * @return the campus display name
     */
    public String displayName() {
        return name;
    }

    /**
     * Returns the city the campus is on
     * @return the city name for the campus
     */
    public String city() {
        return city;
    }

    /**
     * Converts a textual campus name into its enum constant

     * @param s the text
     * @return the matching campus or null if not found
     */
    public static Campus fromText(String s) {
        if (s == null ){
            return null;
        }
        String t = s.trim().toUpperCase();
        switch (t) {
            case "BUSCH":       return BUSCH;
            case "LIVINGSTON":  return LIVINGSTON;
            case "COOK":        return COOK;
            case "NEWARK":      return NEWARK;
            case "CAMDEN":      return CAMDEN;
            default:            return null;
        }
    }

    /**
     * Returns the campus display name for string output.
     *
     * @return the campus display name
     */
    @Override
    public String toString() { return name; }
}
