package model;


/** The outsourced part class.  This class creates instances of Outsource parts that inherit from the Part class and
 * have company names associated.
 */
public class Outsourced extends Part{

    private String companyName;

    /** Constructor for the Outsourced part class.
     * @param id the part id
     * @param name the part name
     * @param price the part's price
     * @param stock number of units currently in stock
     * @param min minimum number of stock allowed
     * @param max maximum number of stock allowed
     * @param companyName the name of the company providing the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
