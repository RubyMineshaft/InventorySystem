package model;

/** The In-house part class.
 * This class creates instances of InHouse parts which inherit from the Part class and include machine IDs.
 */
public class InHouse extends Part {

    private int machineId;

    /** Constructor for the InHouse class.
     * @param id the part ID
     * @param name the part name
     * @param price the part's price
     * @param stock number of units in stock
     * @param min minimum number of units
     * @param max maximum number of units
     * @param machineId the machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Getter for the machine ID.
     * @return the machine ID
     */
    public int getMachineId() {
        return machineId;
    }


    /** Setter for the machine ID.
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
