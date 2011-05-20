/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.category;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bkt
 */
@Entity
@Table(name="category")
public class Category implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected long _categoryId;
    
    @Column(name = "name")
    protected String _name;

    public long getCategoryId() {
        return _categoryId;
    }

    public void setCategoryId(long _categoryId) {
        this._categoryId = _categoryId;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }
    
    
}
