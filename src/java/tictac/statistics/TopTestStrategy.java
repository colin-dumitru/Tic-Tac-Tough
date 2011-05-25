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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictac.core.AplicationParams;
import tictac.test.Test;
import tictac.test.TestDao;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public class TopTestStrategy implements TestStaticticStrategy {

    public static final int D_LIST_SIZE = 10;
    public static final String D_XML_PATH = "rss/top_tests.xml";
    
    protected AplicationParams _params;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void update(TestDao testDao) {
        List<Test> tests = null;

        try {
            tests = testDao.listTestsByAccessed(true);
        } catch (TransactionError ex) {
            Logger.getLogger(TopTestStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (tests == null) {
            return;
        }

        /*pastram primii 10 utilizatori*/
        while (tests.size() > D_LIST_SIZE) {
            tests.remove(0);
        }

        /*inversam lista*/
        java.util.Collections.reverse(tests);

        this.createXml(TopTestStrategy.D_XML_PATH, tests);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    private void createXml(String path, List<Test> tests) {
        Document doc = new DocumentImpl();
        
        /*elementul radacina*/
        Element rss = doc.createElement("rss");
        rss.setAttribute("version", "2.0");
        doc.appendChild(rss) ;
        
        /*channelul*/
        Element chanel = doc.createElement("chanel");  rss.appendChild(chanel);
        
        /*informatiile canalului*/
        Element title = doc.createElement("title"); chanel.appendChild(title);
        title.appendChild(doc.createTextNode("Top Tests"));
        
        Element link = doc.createElement("link"); chanel.appendChild(link);
        link.appendChild(doc.createTextNode("www.google.com"));
        
        Element description = doc.createElement("description"); description.appendChild(title);
        description.appendChild(doc.createTextNode("The feed contains the tests with the best scores"));
        
        /*utilizatorii*/
        for(Test test : tests) {
            Element item = doc.createElement("item"); chanel.appendChild(item);
            
            Element ititle = doc.createElement("title"); item.appendChild(ititle);
            ititle.appendChild(doc.createTextNode(test.getName().trim() + "(" + test.getAccessed() + ")"));
            
            Element ilink = doc.createElement("link"); item.appendChild(ilink);
            Element idescription = doc.createElement("description"); item.appendChild(idescription);            
        }   
        
        /*scriem rezultatul in xml*/
        FileOutputStream out = null;
         
        try {
             out = new FileOutputStream(this._params.getContext().getRealPath(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TopUserStrategy.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
        of.setIndent(1);
        of.setIndenting(true);
        of.setDoctype(null,"tests.dtd");
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
