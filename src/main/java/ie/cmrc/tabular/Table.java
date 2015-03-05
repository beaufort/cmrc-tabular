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

/**
 * A generic interface for data tables. A {@code Table} has a {@link Header} and
 * a collection of rows ({@link TableRow}). The header specifies the fields of the table.
 * Fields in a {@link Table} are multilingual and may have multi values. For instance,
 * a table may have a field called "label" which has several English and French values.
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public interface Table extends Tabular, Closeable {
    
    /**
     * Returns the table name
     * @return Name of this table
     */
    String getName();
    
    /**
     * Returns the next row ({@link TableRow}) and increments the reading pointer.
     * @return Next row ({@link TableRow}) in the table. If the table contains no more rows, then null is returned.
     */
    TableRow getNextRow();

    /**
     * Rewinds the table row reader by pointing it to the first row
     */
    void reset();
}
