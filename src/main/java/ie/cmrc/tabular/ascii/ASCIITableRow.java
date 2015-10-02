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

package ie.cmrc.tabular.ascii;

import ie.cmrc.tabular.AbstractTableRow;
import ie.cmrc.util.Term;
import ie.cmrc.tabular.FieldMapHeader;
import ie.cmrc.tabular.Header;
import ie.cmrc.tabular.TableCell;
import ie.cmrc.util.Multimap;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link ie.cmrc.tabular.TableRow} implementation for a text line (String)
 * that includes data values separated by a given separator.
 * @author Yassine Lassoued
 */
public class ASCIITableRow extends AbstractTableRow {
    
    /**
     * Header of the row, in the form of a {@code ie.cmrc.tabular.FieldMap<ASCIITableCell>} that
     * stores the {@link ASCIITableCell} associated with each {@code Field}
     */
    private final FieldMapHeader<TableCell> fieldCells;
    
    /**
     * String separator used in the text line. Defaults to DEFAULT_SEPARATOR if
     * not specified at construction time.
     */
    private final String separator;
    
    /**
     * Default file separator
     */
    public final static String DEFAULT_SEPARATOR = ",";
    
    /**
     * Indicates whether white spaces should be trimmed while reading the cells
     */
    private final boolean trimWhiteSpaces;
    

    /**
     * Constructs an {@code ASCIITableRow} given a table header, and a text line.
     * <p>Please note that white spaces will not be trimmed while parsing the data
     * values (cells). Use {@code ASCIITableRow(FieldMap<Integer> header, String line, String separator, boolean trimWhiteSpaces)}
     * if you need to trim white spaces.
     * @param header Header of the table in the form of a {@code FieldMap<Integer>}
     * that stores the index of each column (field) name
     * @param line A String line to parse the data values from. This contains
     * the list of data values separated using the default separator {@code DEFAULT_SEPARATOR}.
     */
    public ASCIITableRow(FieldMapHeader<Integer> header, String line) {
        
        this.separator = DEFAULT_SEPARATOR;
        
        this.fieldCells = parseCells(header, line);
        
        this.trimWhiteSpaces = false;
    }

    /**
     * Constructs an ASCIITableRow given a table header, and a text line.
     * <p>Please note that white spaces will not be trimmed while parsing the data
     * values (cells). Use {@code ASCIITableRow(FieldMap<Integer> header, String line, String separator, boolean trimWhiteSpaces)}
     * if you need to trim white spaces.
     * @param header Header of the table row in the form of a {@code FieldMap<Integer>}
     * that stores the index of each field
     * @param line A String line to parse the data values from. This contains
     * the list of data values separated using the provided separator.
     * @param separator String separator used to separate the data values in the
     * parsed line. If the provided separator is null or empty, then
     * {@code DEFAULT_SEPARATOR} is used.
     */
    public ASCIITableRow(FieldMapHeader<Integer> header, String line, String separator) {
        if (separator==null || separator.isEmpty()) this.separator = DEFAULT_SEPARATOR;
        else this.separator = separator;
        
        this.fieldCells = parseCells(header, line);
        
        this.trimWhiteSpaces = false;
    }
    
    /**
     * Constructs an ASCIITableRow given a table header, and a text line.
     * @param header Header of the table row in the form of a {@code FieldMap<Integer>}
     * that stores the index of each field
     * @param line A String line to parse the data values from. This contains
     * the list of data values separated using the provided separator.
     * @param separator String separator used to separate the data values in the
     * parsed line. If the provided separator is null or empty, then
     * {@code DEFAULT_SEPARATOR} is used.
     * @param trimWhiteSpaces Boolean value indicating whether white spaces
     * should be trimmed while parsing cells. If set to {@code true}, white spaces will
     * be trimmed.
     */
    public ASCIITableRow(FieldMapHeader<Integer> header, String line, String separator, boolean trimWhiteSpaces) {
        if (separator==null || separator.isEmpty()) this.separator = DEFAULT_SEPARATOR;
        else this.separator = separator;
        
        this.fieldCells = parseCells(header, line);
        
        this.trimWhiteSpaces = trimWhiteSpaces;
    }
    
    /**
     * Parses the String line to extract the table cells in a HashMap where each
     * cell is associated with the corresponding field name.
     * @param header Header of the table row in the form of a {@code FieldMap<Integer>}
     * that stores the index of each field
     * @param line String line to parse the data values from
     * @return A hash map {@code FieldMap<Integer>} that stores the
     * TableCell associated with each field name listed in the header.
     */
    private FieldMapHeader<TableCell> parseCells(FieldMapHeader<Integer> header, String line) {
        FieldMapHeader<TableCell> fm = new FieldMapHeader<TableCell>();
        
        if (header!=null && !header.isEmpty()) {

            if (line != null && !line.isEmpty()) {
                String[] tokens = line.split(this.separator);
                if (tokens!=null) {
                    List<Term> fields = header.getFields();
                    
                    for (Term field: fields) {
                        Integer index = header.getValue(field);
                        if (index != null && index>=0 && index < tokens.length) {
                            String value = tokens[(int)index];
                            if (this.trimWhiteSpaces) value = value.trim();
                            fm.put(field, new ASCIITableCell(value));
                        }
                    }
                }
            }
        }
        return fm;
    }

    /**
     * {@inheritDoc}
     * @param field {@link ie.cmrc.util.Term} object
     * @return TableCell object corresponding to the provided {@link ie.cmrc.util.Term}.
     * Please note that a {@code TableRow} object may have several cells
     * matching the provided {@link ie.cmrc.util.Term}. This method returns
     * the first one that is not empty, if any.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty cell is returned. This allows users to
     * chain calls such as
     * {@code tabelRow.getCell(myField).getStringValue();}
     * without having to check whether {@code getCell(Field field)} is null.
     */
    @Override
    public TableCell getCell(Term field) {
        List<TableCell> cells = this.fieldCells.getValues(field);
        for (TableCell cell: cells) {
            if (!cell.isEmpty()) return cell;
        }
        return new ASCIITableCell(null);
    }

    /**
     * {@inheritDoc}
     * @param fieldName Name of a field
     * @return {@code Multimap<String, TableCell>} object mapping the available
     * language codes to the corresponding {@link TableCell} objects.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty {@link ie.cmrc.util.Multimap} is returned
     * rather than a null value.
     */
    @Override
    public Multimap<String, TableCell> getCells(String fieldName) {
        Multimap<String, TableCell> map = this.fieldCells.getValues(fieldName);
        if (map != null) return map;
        else return new Multimap<String, TableCell>();
    }

    /**
     * {@inheritDoc}
     * @param field {@link ie.cmrc.util.Term} object
     * @return {@code List<Field>} object containing all the cells matching {@code field}.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty {@code List} is returned
     * rather than a null value.
     */
    @Override
    public List<TableCell> getCells(Term field) {
        List<TableCell> cells = this.fieldCells.getValues(field);
        if (cells!=null) return cells;
        else return new ArrayList<TableCell>();
    }

    /**
     * {@inheritDoc}
     * @return {@link ie.cmrc.tabular.Header} of the {@link ie.cmrc.tabular.TableRow}.
     * Any modification of the returned {@link ie.cmrc.tabular.Header} will be reflected in the
     * {@link ie.cmrc.tabular.TableRow} object, but not in the physical file itself.
     * Therefore, you are strongly not recommended to modify the content of the
     * returned object.
     */
    @Override
    public Header getHeader() {
        return this.fieldCells;
    }
    
    
}
