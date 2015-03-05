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
import java.util.List;

/**
 * A generic interface for representing {@link Table} and {@link TableRow} headers.
 * While this interface (currently) only supports read operations, this does not
 * prevent the actual implementations from supporting write operations.
 * 
 * @author Yassine Lassoued
 */
public interface Header {
    /**
     * Returns the list of fields contained in this {@code Header}.
     * @return A {@code java.util.List<Term>} object containing the fields of this {@code Header}.
     * Please note that if the header is empty (contains no fields), then an
     * empty {@code List} is returned rather than a {@code null} value.
     */
    List<Term> getFields();
    
    /**
     * Returns the list of fields matching the provided field name
     * @param fieldName Name of a field
     * @return A {@code java.util.List<Term>} object containing the fields that match
     * the provided field name {@code fieldName}.
     * Please note that if the header does not contain any field with the provided name, then an
     * empty {@code List} is returned rather than a {@code null} value.
     */
    List<Term> getFields(String fieldName);
    
    /**
     * Returns a field matching the provided field name
     * @param fieldName Name of a field
     * @return A {@code Term} representing a field matching the
     * the provided field name {@code fieldName}, if any; {@code null} otherwise
     */
    Term getField(String fieldName);
    
    /**
     * Returns the list of languages available for a provided field name
     * @param fieldName Name of a field
     * @return {@code java.util.List<String>} object containing all the languages
     * available for the provided field name. Please note that if no such field
     * exists in the {@code Header}, then an empty {@code List} is returned.
     */
    List<String> getFieldLanguages(String fieldName);
    
    /**
     * Returns the list of all the languages available in the header.
     * @return {@code java.util.List<String>} object listing all the languages
     * available in the header.
     */
    List<String> getLanguages();
    
    /**
     * Returns the {@code List} of distinct field names regardless of their languages
     * @return {@code List} of distinct field names available in the {@code Header}.
     * Please note that if the {@code Header} has no fields, then an empty 
     * {@code List} is returned rather than a {@code null} value.
     */
    List<String> getFieldNames();
    
    /**
     * Indicates whether this {@code Header} is empty
     * @return {@code true} is this {@code Header} is empty. {@code false} otherwise.
     */
    boolean isEmpty();
    
    /**
     * Indicates whether this {@code Header} contains the specified field
     * @param field The field to check
     * @return {@code true} is the field exists in the {@code Header}, {@code false} otherwise.
     */
    boolean containsField(Term field);
    
    /**
     * Indicates whether this {@code Header} contains the field specified by its name {@code fieldName}
     * @param fieldName Name of a field to check
     * @return {@code true} is the named field exists in the {@code Header}, {@code false} otherwise.
     */
    boolean containsField(String fieldName);
    
    /**
     * Returns the number of distinct fields in the {@code Header}
     * @return Number of distinct fields in the {@code Header}. Each field
     * is counted only once. This is equivalent to {@code getFields.getSize()}.
     */
    int getNumFields();
    
    /**
     * Returns the size of the {@code Header}. This is obtained by counting all the
     * occurrences of all the fields contained in the {@code Header}.
     * @return Number of non-distinct fields in the Header. If a field appears more
     * than once then all its occurrences are counted. The result of this method
     * may be different than {@linkplain  #getNumFields()} (if one or more fields appear
     * more than once).
     */
    int getSize();
    
}
