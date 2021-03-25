package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product class.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    /** The Product constructor. Creates instances of the Product class.
     * @param id the Product ID
     * @param name the name of the Product
     * @param price the price of the Product
     * @param stock the number of units in stock
     * @param min the minimum number of units
     * @param max the maximum number of units
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.price = price;
    }

    /** Getter for Product ID.
     * @return the Product ID
     */
    public int getId() {
        return id;
    }

    /** Setter for product ID.
     * @param id the Product ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for current stock level.
     * @return the current stock level
     */
    public int getStock() {
        return stock;
    }

    /** Setter for the current stock level.
     * @param stock the number of units in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for the minimum stock.
     * @return the minimum stock allowed
     */
    public int getMin() {
        return min;
    }

    /** Setter for the minimum stock.
     * @param min the minimum number of units
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for maximum stock.
     * @return the maximum number of stock allowed
     */
    public int getMax() {
        return max;
    }

    /** Setter for maximum stock.
     * @param max the maximum number of units
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Getter for Product name.
     * @return the Product name
     */
    public String getName() {
        return name;
    }

    /** Setter for Product name.
     * @param name the Product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for Product price.
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /** Setter for Product price.
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Adds a part to the associatedParts ObservableList.
     * @param part the part to add
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /** Deletes selected associated part.
     * @param selectedAssociatedPart the part to be deleted
     * @return true if part was deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /** Getter for associated parts observable list.
     * @return list of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
