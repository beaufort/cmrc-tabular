/* 
 * Copyright 2015 Coastal and Marine Research Centre (CMRC), Beaufort,
 * Environmental Research Institute (ERI), University College Cork (UCC).
 * Yassine Lassoued <y.lassoued@gmail.com, y.lassoued@ucc.ie>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ie.cmrc.tabular;

import java.io.Closeable;
import java.util.List;

/**
 * A {@code TabularDataset} can be viewed as a collection of tables ({@link Table})
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public interface TabularDataset extends Closeable {
    
    /**
     * Returns a table ({@link Table} instance) given its name
     * @param tableName Name of a table
     * @return {@link Table} having as a name the provided String value. If no such table exists, then null is returned.
     */
    Table getTable(String tableName);

    /**
     * Returns the list of table names within this dataset
     * @return A List object containing the names of tables within this dataset
     */
    List<String> getTableNames();
    
}
