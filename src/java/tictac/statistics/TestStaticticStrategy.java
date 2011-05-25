/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import tictac.core.AplicationParams;
import tictac.test.TestDao;

/**
 *
 * @author bkt
 */
public interface TestStaticticStrategy {
        public void update  (TestDao userDao);
        public void setParams(AplicationParams params);
    
}
