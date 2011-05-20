/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.category;

import java.util.List;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public interface CategoryDao {
    public void saveCategories(Category cat) throws TransactionError;
    public List<Category> listCategories() throws TransactionError;
    
    public Category findCategory(String categoryName) throws TransactionError;
    
}