package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;


/** This class keeps track of all items in inventory.
 * Parts are stored in the allParts observable list. Products are stored in the allProducts observable list.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a new part to the allParts Observable List.
     * @param newPart the part to add.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Adds a new product to the allProducts Observable List.
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Looks up a part by ID.
     * @param partId the ID to search for
     * @return part with the specified ID if found
     */
    public static Part lookupPart(int partId){
        for (Part part : allParts) {
            if (part.getId() == partId)
                return part;
        }
        return null;
    }

    /** Looks up a product by ID.
     * @param productId the ID of the product to search for
     * @return product with the specified ID if found
     */
    public static Product lookupProduct(int productId){
        for (Product product : allProducts) {
            if (product.getId() == productId)
                return product;
        }
        return null;
    }

    /** Searches all part names for provided string.
     * @param partName name of part to search for
     * @return observable list of matching parts
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part part : allParts){
            if (part.getName().toLowerCase().contains(partName.toLowerCase())){
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /** Searches all products for names containing provided string.
     * @param productName name of product to search for
     * @return observable list of matching products
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product product : allProducts){
            if (product.getName().contains(productName)){
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /** Updates a part in the allParts list
     * @param index index of part to be updated
     * @param selectedPart the updated part
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /** Updates a product in the allProducts list.
     * @param index the index of the product to be updated
     * @param newProduct the updated product
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /** Removes a part from the allParts list.
     * @param selectedPart the part to be deleted
     * @return boolean indicating whether part was found and deleted
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /** Removes a product from the allProducts list.
     * @param selectedProduct the product to be deleted
     * @return boolean indicating whether the product was found and deleted
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** Getter for allParts list.
     * @return allParts observable list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Getter for allProducts list.
     * @return allProducts observable list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
