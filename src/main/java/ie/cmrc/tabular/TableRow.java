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

import ie.cmrc.util.Term;
import ie.cmrc.util.Multimap;
import java.util.List;

/**
 * A generic interface for table rows. A {@link TableRow} is a collection of cells
 * ({@link TableCell}) each corresponding to a field. A field is identified by
 * its name and language, and therefore represented as a {@link ie.cmrc.util.Term}.
 * A field may have 0 or many cells associated with it.
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public interface TableRow extends Tabular {
    
    /**
     * Extracts a {@link TableCell} object matching the provided field
     * @param field {@link ie.cmrc.util.Term} representing the 
     * @return TableCell object corresponding to the provided {@link ie.cmrc.util.Term}.
     * Please note that a {@code TableRow} object may have several cells
     * matching the provided {@link ie.cmrc.util.Term}. Which one to return is implementation
     * specific; it may be the "first" match.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty cell is returned. This allows users to
     * chain calls such as
     * {@code tabelRow.getCell(myField).getStringValue();}
     * without having to check whether {@code getCell(Term field)} is null.
     */
    TableCell getCell(Term field);
    
    /**
     * Extracts the {@link TableCell} objects matching the provided field name
     * and stores them in a {@code ie.cmrc.util.Multimap} object where keys are
     * language codes and values are cells ({@link TableCell}) associated with them.
     * @param fieldName Name of a field. Please note that this a simple name
     * and <b>not the qualified name</b> of a field.
     * @return {@code Multimap<String, TableCell>} object mapping the available
     * language codes to the corresponding {@link TableCell} objects.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty {@link ie.cmrc.util.Multimap} is returned
     * rather than a null value.
     */
    Multimap<String, TableCell> getCells(String fieldName);
    
    /**
     * Extracts the {@code List} of cells ({@link TableCell}) matching the provided
     * {@link ie.cmrc.util.Term}.
     * @param field {@link ie.cmrc.util.Term} object
     * @return {@code List<ie.cmrc.util.Term>} object containing all the cells matching {@code field}.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty {@code List} is returned
     * rather than a null value.
     */
    List<TableCell> getCells(Term field);
    
    
    // Convenience methods
    
    /**
     * Convenience method to get the first non null Integer value of a provided field, if any
     * @param field Field the value of which to be returned
     * @return First non null Integer value of the provided {@code field}, if any; otherwise null.
     */
    Integer getFieldIntValue(Term field);

    /**
     * Convenience method to get the first non null Double value of a provided field, if any
     * @param field Field the value of which to be returned
     * @return First non null Double value of the provided {@code field}, if any; otherwise null.
     */
    Double getFieldDoubleValue(Term field);
    
    /**
     * Convenience method to get the first non null and non-empty String value of a field, if any
     * @param field Field the value of which to be returned
     * @return First non null and non-empty String value of the provided {@code field}, if any; otherwise null.
     */
    String getFieldStringValue(Term field);
    
    /**
     * Convenience method to get the first non null Boolean value of a provided field, if any.
     * Boolean values are parsed as follows following the (case-insensitive) rules below<br/>
     * <code>"true", "yes", "y", "t", "1" --> true</code><br/>
     * <code>"false", "no", "n", "f", "0" --> false</code><br/>
     * <code>Any other value --> null</code><br/>
     * @param field Name of the field the value of which to be returned
     * @return First non null Boolean value of the provided {@code field}, if any; otherwise null.
     */
    Boolean getFieldBooleanValue(Term field);
}
