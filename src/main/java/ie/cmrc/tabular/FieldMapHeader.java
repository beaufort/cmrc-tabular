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
import ie.cmrc.util.TermMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@code Header} is an object that maps values of given type ({@code <V>}) to fields.
 * Regardless of the type ({@code <V>}), FieldMap implements the {@link Header} interface.
 * As confusing as this may sound, it is nevertheless an easy implementation.
 * An alternative would be to implement {@code Header}-based headers.
 * 
 * @author Yassine Lassoued
 * @param <V> Type of the mapped values
 */
public class FieldMapHeader<V> implements Header {
    
    /**
     * A FieldMap wraps a HashMap that associates with each field name a multimap.
     * Each multimap stores the field values associated with each language.
     */
    //HashMap<String, Multimap<String,V>> entries;

    
    TermMap<V> fieldMap;
    
    /**
     * Constructs an empty {@code Header} object
     */
    public FieldMapHeader() {
        this.fieldMap = new TermMap<V>();
    }
    
    /**
     * Inserts the provided {@code value} associated with the provided {@code field}
     * @param field Field with which the specified value is to be associated
     * @param value Value to be associated with the specified field
     */
    public void put(Term field, V value) {
        fieldMap.put(field, value);
    }
    
    /**
     * Returns the list of values associated with the provided field
     * 
     * @param field Field for which values will be returned
     * 
     * @return {@code List<V>} containing the values associated with {@code field}.
     * If the {@link FieldMapHeader} does not contain any values for the field, then
     * an <i>empty</i> {@code List<V>} is returned.
     * 
     * <p>Changes to the returned list itself (e.g., adding or removing objects)
     * will not update the underlying field map. However changes to the objects
     * of the list will update those in the field map.
     */
    public List<V> getValues(Term field) {
        return fieldMap.getValues(field);
    }
    
    /**
     * Returns a value (the first one) associated with {@code field} if any
     * @param field {@code link} whose associated value is to be returned
     * @return First value associated with {@code field}. If no such value exists
     * then {@code null} is returned.
     */
    public V getValue(Term field) {
        return fieldMap.getValue(field);
    }
    
    /**
     * Returns the first non-null value associated with {@code field} if any
     * @param field Field whose associated value is to be returned
     * @return First non-null value associated with {@code field}. If no such
     * value exists then {@code null} is returned.
     */
    public V getNonNullValue(Term field) {
        return fieldMap.getNonNullValue(field);
    }

    /**
     * Returns the values associated with the provided field name ({@code fieldName})
     * in the form of a {@link ie.cmrc.util.Multimap}
     * @param fieldName Name of the field whose associated values are to be returned
     * @return A {@link ie.cmrc.util.Multimap} associating with each language
     * the values of the provided field name. If no values exist then an empty
     * multimap is returned.
     */
    public Multimap<String, V> getValues(String fieldName) {
        return fieldMap.getValues(fieldName);
    }
    
    /**
     * {@inheritDoc}
     * @param fieldName {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<String> getFieldLanguages(String fieldName) {
        return fieldMap.getKeyTermLanguages(fieldName);
    }

    
    
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<String> getLanguages() {
        return this.fieldMap.getLanguages();
    }
    
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<Term> getFields() {
        return this.fieldMap.getKeyTerms();
    }

    /**
     * {@inheritDoc}
     * @param fieldName {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Term getField(String fieldName) {
        List<String> langs = this.fieldMap.getKeyTermLanguages(fieldName);
        if (langs!=null && !langs.isEmpty()) return new Term(fieldName, langs.get(0));
        else return null;
    }
    
    /**
     * {@inheritDoc}
     * @param fieldName {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<Term> getFields(String fieldName) {
        List<Term> fields = new ArrayList<Term>();
        List<String> langs = this.fieldMap.getKeyTermLanguages(fieldName);
        for (String lang: langs) fields.add(new Term(fieldName, lang));
        return fields;
    }
    
    
    
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int getNumFields() {
        return this.fieldMap.getNumKeyTerms();
    }
    
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return this.fieldMap.isEmpty();
    }
    
    /**
     * Removes the provided {@code field-value} pair from the {@code FieldMapHeader}
     * @param field A {@link ie.cmrc.util.Term} instance representing a field name and language
     * @param value Value associated with {@code field} to remove
     * @return {@code true} if the {@code Header} has changed, {@code false} otherwise.
     */
    public boolean remove(Term field, V value) {
        return this.fieldMap.remove(field, value);
    }
    
    /**
     * Removes all the values associated with the provided {@code field}.
     * At the end of this operation, {@linkplain #containsField(ie.cmrc.util.Term) }
     * will return {@code false}.
     * 
     * @param field Field whose values are to be removed
     * @return The {@code List} of values that were actually removed from the {@code Header}.
     * If no values were removed, then {@code null} is returned.
     * 
     * The returned List is modifiable, but updating it will have no
     * effect on the field map.
     */
    public List<V> removeAll(Term field) {
        return this.fieldMap.removeAll(field);
    }
    
    /**
     * Removes all the languages and values associated with the provided field name.
     * At the end of this operation, {@linkplain #containsField(java.lang.String)}
     * will return {@code false}.
     * 
     * @param fieldName Name of the field whose values are to be removed
     * @return {@code Multimap<String,V>} containing all the removed languages
     * with their associated values for the specified field name. This may be {@code null}.
     */
    public Multimap<String,V> removeAll(String fieldName) {
        return this.fieldMap.removeAll(fieldName);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<String> getFieldNames() {
        return this.fieldMap.getKeyTermStrings();
    }

    /**
     * {@inheritDoc}
     * @param field {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsField(Term field) {
        return this.fieldMap.containsKeyTerm(field);
    }
    
    /**
     * {@inheritDoc}
     * @param fieldName {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsField(String fieldName) {
        return this.fieldMap.containsKeyTerm(fieldName);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.fieldMap.getSize();
    }
}
