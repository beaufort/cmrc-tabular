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

import ie.cmrc.util.Term;
import ie.cmrc.tabular.FieldMapHeader;
import ie.cmrc.tabular.Header;
import ie.cmrc.tabular.Table;
import ie.cmrc.tabular.TableRow;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Wraps an Excel sheet (Apache POI {@code org.apache.poi.ss.usermodel.Sheet}) as a {@link Table}. The first row of the Excel sheet must contain the header of the table, i.e., column names.
 * @author Yassine Lassoued
 */
public class ExcelTable implements Table {
    
    /**
     * ExcelTable wraps an Apache POI Sheet ({@code org.apache.poi.ss.usermodel.Sheet})
     */
    private final Sheet sheet;

    /**
     * Header of the table in the form of a {@link ie.cmrc.tabular.FieldMapHeader} that stores the index of each column (field) name
     */
    private final FieldMapHeader<Integer> header;
    
    
    /**
     * Number of records in the sheet
     */
    private final int numberOfRecords;
    
    /**
     * A counter that points to the next row. It is incremented at each getNextRow() call
     */
    private int nextRow = 0;
    
    /**
     * Constructs a Table object that wraps the provided Excel sheet
     * @param sheet An Excel sheet ({@code org.apache.poi.ss.usermodel.Sheet}) to load and wrap as a Table
     */
    public ExcelTable(Sheet sheet) {
        this.sheet = sheet;
        this.header = this.parseHeader(sheet);
        if (sheet!=null) {
            int n = this.sheet.getPhysicalNumberOfRows();
            this.numberOfRecords = Math.max(0, n-1);
        }
        else this.numberOfRecords = 0;
    }
    
    
    
    /**
     * Extracts the header of a given sheet in the form of a {@code FieldMap<Integer>} that stores the indexes of each field.
     * The header must be in the first row of the sheet.
     * @param sheet Excel Sheet ({@code org.apache.poi.ss.usermodel.Sheet}) to extract the header from
     * @return A hash map mapping each colum name with its index. If the sheet is null or empty or the first row is empty, then and empty {@link ie.cmrc.tabular.FieldMapHeader} is returned.
     */
    private FieldMapHeader<Integer> parseHeader(Sheet sheet) {
        FieldMapHeader<Integer> sheetHeader = new FieldMapHeader<Integer>();
        if (sheet!=null) {
            
            int rows = sheet.getPhysicalNumberOfRows();

            if (rows > 0) {
                Row row = sheet.getRow(0);
                if (row!=null) {
                    int n = row.getPhysicalNumberOfCells();
                    
                    if (n>0){
                        for (int i=0; i<n; i++) {
                            Cell cell = row.getCell(i);
                            if(cell != null) {
                                ExcelTableCell sc = new ExcelTableCell(cell);
                                String colName = sc.getStringValue();
                                if (colName!=null) colName = colName.trim();
                                    
                                Term field = new Term(colName);
                                sheetHeader.put(field, i);
                                
                            }
                        }
                    }
                }
            }
        }
        return sheetHeader;
    }
    
    /**
     * Returns the number of valid columns (fields) of this table
     * @return Number of columns of the table.
     */
    public int getNumberOfColumns() {
        return this.header.getNumFields();
    }
    
    /**
     * Returns the number of records in this {@code ExcelTable}
     * @return Number of records in this table
     */
    public int getNumberOfRecords() {
        return this.numberOfRecords;
    }
    
    /**
     * Indicates whether the {@code ExcelTable} is empty
     * @return {@code true} if the table is empty (has not header or records), {@code false} otherwise.
     */
    public Boolean isEmpty() {
        return (this.header==null || this.header.isEmpty() || this.sheet.getPhysicalNumberOfRows()<=1);
    }
    
    /**
     * Returns the {@link TableRow} at a given index
     * @param index Index of the table row
     * @return {@link TableRow} at the given index. If the index is greater than the number of records or negative, then null is returned.
     */
    private TableRow getRow(int index) {
        if (index>=0 && index<this.numberOfRecords) {
            return new ExcelTableRow(this.header, this.sheet.getRow(index+1));
        }
        else return null;
    }
    
    /**
     * Returns the table name
     * @return Name of the table. This is the name of the corresponding sheet.
     */
    @Override
    public String getName() {
        if (this.sheet != null) return this.sheet.getSheetName();
        else return null;
    }

    /**
     * Implementation of the close method of the {@code java.io.Closeable} interface. In an ExcelTable, this method does nothing.
     * @throws IOException This is never thrown in an ExcelTable.
     */
    @Override
    public void close() throws IOException {
        // Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableRow getNextRow() {
        TableRow row = this.getRow(this.nextRow);
        if (this.nextRow<this.numberOfRecords) this.nextRow++;
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.nextRow = 0;
    }

    /**
     * {@inheritDoc}
     * @return {@link ie.cmrc.tabular.Header} of the {@code ExcelTable}.
     * Any modification of the returned {@code Header} will be reflected in the
     * {@code ExcelTable} object, but not in the physical file itself. Therefore,
     * you are strongly not recommended to modify the content of the returned object.
     */
    @Override
    public Header getHeader() {
        return this.header;
    }
}
