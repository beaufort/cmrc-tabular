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

/**
 * A generic interface for table cells. A {@code TableCell} is the atomic element of a
 * {@link Table} or a {@link TableRow}.
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public interface TableCell {

    /**
     * Returns the Double value of the cell
     * @return Double value of the cell. If a Double cannot be parsed, then null is returned.
     */
    Double getDoubleValue();

    /**
     * Returns the Integer value of the cell
     * @return Integer value of the cell. If an Integer cannot be parsed, then null is returned.
     */
    Integer getIntValue();

    /**
     * Returns the String value of the cell
     * @return String value of the cell.
     */
    String getStringValue();

    /**
     * Returns the Boolean value of the cell
     * @return Boolean value of the cell, following the (case-insensitive) rules below<br/>
     * <code>"true", "yes", "y", "t", "1" --> true</code><br/>
     * <code>"false", "no", "n", "f", "0" --> false</code><br/>
     * <code>Any other value --> null</code><br/>
     */
    Boolean getBooleanValue();
    
    /**
     * Indicates whether this table cell is empty.
     * @return {@code true} if the cell is empty, {@code false} otherwise.
     */
    Boolean isEmpty();
    
}
