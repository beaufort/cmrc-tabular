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

package ie.cmrc.tabular.excel;

import ie.cmrc.tabular.AbstractTableRow;
import ie.cmrc.util.Term;
import ie.cmrc.tabular.FieldMapHeader;
import ie.cmrc.tabular.Header;
import ie.cmrc.tabular.TableCell;
import ie.cmrc.util.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * A {@link ie.cmrc.tabular.TableRow} wrapper for an Excel row (Apache POI {@code org.apache.poi.ss.usermodel.Row}).
 * @author Yassine Lassoued
 */
public class ExcelTableRow extends AbstractTableRow {
    
    /**
     * Header of the row, in the form of a {@code FieldMAp<Integer>} that stores the index of each {@code Field}
     */
    private final FieldMapHeader<Integer> header;
    
    /**
     * Excel row (Apache POI {@code org.apache.poi.ss.usermodel.Row}) wrapped by this {@link TableRow} object
     */
    private final Row row;

    /**
     * Constructs an ExcelTableRow given a table header, and an Excel row. The
     * header is a {@code FieldMap<Integer>} that specifies the cell index for
     * each field name.
     * @param header Header of the table in the form of a {@code FieldMap<Integer>}
     * that stores the index of each field
     * @param row Excel row (Apache POI {@code org.apache.poi.ss.usermodel.Row})
     * to be wrapped by this {@link ie.cmrc.tabular.TableRow} object
     */
    public ExcelTableRow(FieldMapHeader<Integer> header, Row row) {
        this.header = header;
        this.row = row;
    }

    
    /**
     * {@inheritDoc}
     * @param field {@link ie.cmrc.util.Term} object
     * @return TableCell object corresponding to the provided {@link ie.cmrc.util.Term}.
     * Please note that a {@code TableRow} object may have several cells
     * matching the provided {@link ie.cmrc.util.Term}. Which one to return is implementation
     * specific; it may be the "first" match.
     * <p>Please note that if the row or header is empty or null, or the field
     * name does not exist, then an empty cell is returned. This allows users to
     * chain calls such as
     * {@code tabelRow.getCell(myField).getStringValue();}
     * without having to check whether {@code getCell(Field field)} is null.
     */
    @Override
    public TableCell getCell(Term field) {
        
        if (this.header!=null && this.row!=null) {
            Integer index = this.header.getValue(field);
            if (index!=null && this.row!=null) return (new ExcelTableCell(this.row.getCell(index)));
        }
        
        return new ExcelTableCell(null);
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
    public Multimap<String,TableCell> getCells(String fieldName) {
        Multimap<String,TableCell> map = new Multimap<String,TableCell>();
        
        Multimap<String,Integer> langIndexes = this.header.getValues(fieldName);
        Set<String> langs = langIndexes.keySet();
        
        for (String lang: langs) {
            List<Integer> indexes = langIndexes.getAll(lang);
            if (indexes != null) {
                for (Integer index: indexes) {
                    if (index!=null && this.row!=null) {
                        map.put(lang, new ExcelTableCell(this.row.getCell(index)));
                    }
                }
            }
        }
        return map;
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
        List<TableCell> cells = new ArrayList<TableCell>();
        
        List<Integer> indexes = this.header.getValues(field);
        if (indexes != null) {
            for (Integer index: indexes) {
                if (index != null) {
                    if (this.row != null) {
                        Cell cell = this.row.getCell(index);
                        if (cell!=null) {
                            cells.add(new ExcelTableCell(cell));
                        }
                    }
                }
            }
        }
        return cells;
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
        return this.header;
    }
}
