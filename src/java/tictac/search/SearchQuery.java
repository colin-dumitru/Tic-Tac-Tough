/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.search;

import java.util.StringTokenizer;

/**
 *
 * @author bkt
 */
public class SearchQuery {
    private String _query;

    public String getQuery() {
        return _query;
    }

    public void setQuery(String _query) {
        this._query = _query;
    }
  
    public String[] getTokens(){
        String[] ret=new String[10];
        StringTokenizer st=new StringTokenizer(_query, " ");
        int i=0;
        while(st.hasMoreTokens()){
            ret[i++]=st.nextToken();
        }
        return ret;
    }
}
