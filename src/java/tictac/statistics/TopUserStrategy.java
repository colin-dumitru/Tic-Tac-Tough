/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tictac.core.AplicationParams;
import tictac.user.TransactionError;
import tictac.user.User;
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public class TopUserStrategy implements UserStatisticStrategy{
    public static final int D_LIST_SIZE = 10;
    public static final String D_XML_PATH = "top_users.xml";
    
    protected AplicationParams _params;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void update(UserDao userDao) {
        List<User> users = null;
        
        try {
            users = userDao.listUsersByScore(true);
        } catch (TransactionError ex) {
            Logger.getLogger(TopUserStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(users == null)
            return;
        
        /*pastram primii 10 utilizatori*/
        while(users.size() > D_LIST_SIZE) {
            users.remove(0);
        }
        
        /*inversam lista*/
        java.util.Collections.reverse(users);
        
        this.createXml(D_XML_PATH, users);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void createXml(String path, List<User> users)  {
        Document doc = new DocumentImpl();
        
        /*elementul radacina*/
        Element rss = doc.createElement("rss");
        rss.setAttribute("version", "2.0");
        doc.appendChild(rss) ;
        
        /*channelul*/
        Element chanel = doc.createElement("chanel");  rss.appendChild(chanel);
        
        /*informatiile canalului*/
        Element title = doc.createElement("title"); chanel.appendChild(title);
        title.appendChild(doc.createTextNode("Top Users"));
        
        Element link = doc.createElement("link"); chanel.appendChild(link);
        link.appendChild(doc.createTextNode("www.google.com"));
        
        Element description = doc.createElement("description"); description.appendChild(title);
        description.appendChild(doc.createTextNode("The feed contains the users with the best scores"));
        
        /*utilizatorii*/
        for(User user : users) {
            Element item = doc.createElement("item"); chanel.appendChild(item);
            
            Element ititle = doc.createElement("title"); item.appendChild(ititle);
            ititle.appendChild(doc.createTextNode(user.getUserName() + "(" + user.getTestScore() + ")"));
            
            Element ilink = doc.createElement("link"); item.appendChild(ilink);
            Element idescription = doc.createElement("description"); item.appendChild(idescription);            
        }   
        
        /*scriem rezultatul in xml*/
        FileOutputStream out = null;
         
        try {
            out = new FileOutputStream(this._params.getContext().getContextPath() + path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TopUserStrategy.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch(IOException ex) {
            Logger.getLogger(TopUserStrategy.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
        of.setIndent(1);
        of.setIndenting(true);
        of.setDoctype(null,"users.dtd");
        XMLSerializer serializer = new XMLSerializer(out, of);
        
        try {
            // As a DOM Serializer
            serializer.asDOMSerializer();
            serializer.serialize( doc.getDocumentElement() );
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(TopUserStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void setParams(AplicationParams params) {
        this._params = params;
    }


}
