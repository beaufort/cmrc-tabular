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

import ie.cmrc.tabular.TableCell;
import ie.cmrc.tabular.util.BooleanParser;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Wraps an Excel cell (Apache POI {@code org.apache.poi.ss.usermodel.Cell}) as a TableCell instance.
 * @author Yassine Lassoued
 */
public class ExcelTableCell implements TableCell {
    
    /**
     * Excel cell (Apache POI org.apache.poi.ss.usermodel.Cell) wrapped by this {@code ExcelTableCell} object
     */
    private final Cell cell;

    /**
     * Indicates whether white spaces should be trimmed while reading the cell
     */
    private final boolean trimWhiteSpaces;
    
    /**
     * Constructs a TableCell object that wraps the Excel {@code org.apache.poi.ss.usermodel.Cell}
     * object provided as parameter.
     * <p>Please note that whitespaces will not be trimmed. Use {@code ExcelTableCell(Cell cell, boolean trimWhiteSpaces)}
     * if you need to trim whitespaces.
     * @param cell A Cell object ({@code org.apache.poi.ss.usermodel.Cell}) to wrap as a {@link ie.cmrc.tabular.TableCell}
     */
    public ExcelTableCell(Cell cell) {
        this.cell = cell;
        this.trimWhiteSpaces = false;
    }

    /**
     * Constructs a TableCell object that wraps the Excel {@code org.apache.poi.ss.usermodel.Cell}
     * object provided as parameter.
     * @param cell A Cell object ({@code org.apache.poi.ss.usermodel.Cell}) to
     * wrap as a {@link ie.cmrc.tabular.TableCell}
     * @param trimWhiteSpaces A {@code boolean} indicating whether white spaces
     * should be trimmed while reading the content of the cell. Set this to
     * {@code true} if you want to trim white spaces.
     */
    public ExcelTableCell(Cell cell, boolean trimWhiteSpaces) {
        this.cell = cell;
        this.trimWhiteSpaces = trimWhiteSpaces;
    }
    
    /**
     * Indicates whether this table cell is empty.
     * @return True if the cell is empty, false otherwise.
     */
    @Override
    public Boolean isEmpty() {
        return (this.cell==null ||
                this.cell.getCellType()==Cell.CELL_TYPE_BLANK ||
                this.cell.getStringCellValue()==null ||
                this.cell.getStringCellValue().isEmpty());
    }
    
    
    /**
     * Returns the String value of the cell
     * @return String value of the cell. If the cell is a formula or has an error, then null is returned.
     */
    @Override
    public String getStringValue() {
        if (this.cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING: if (this.trimWhiteSpaces) return cell.getStringCellValue().trim(); else return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC: return String.valueOf(cell.getNumericCellValue());
                case Cell.CELL_TYPE_BLANK: return "";
                case Cell.CELL_TYPE_BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
                default: return null;
            }
        }
        else return null;
    }
    
    /**
     * Returns the Integer value of the cell
     * @return Integer value of the cell. If an Integer cannot be parsed, then null is returned.
     */
    @Override
    public Integer getIntValue() {
        if (this.cell!=null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: return (int)cell.getNumericCellValue();
                case Cell.CELL_TYPE_STRING: 
                    String string = cell.getStringCellValue();
                    try {
                        return Integer.parseInt(string);
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                case Cell.CELL_TYPE_BOOLEAN:
                    Boolean bool = cell.getBooleanCellValue();
                    return (bool? 1:0);
                default: return null;

            }
        }
        else return null;
        
    }
    
    /**
     * Returns the Double value of the cell
     * @return Double value of the cell. If a Double cannot be parsed, then null is returned.
     */
    @Override
    public Double getDoubleValue() {
        if (this.cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: return (double)cell.getNumericCellValue();
                case Cell.CELL_TYPE_STRING: 
                    String string = cell.getStringCellValue();
                    try {
                        Double number = Double.parseDouble(string);
                        return number;
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                case Cell.CELL_TYPE_BOOLEAN:
                    Boolean bool = cell.getBooleanCellValue();
                    return (bool? 1.:0);
                default: return null;

            }
        }
        else return null;
    }

    /**
     * Returns the Boolean value of the cell
     * @return Boolean value of the cell, following the (case-insensitive) rules below<br/>
     * {@code "true", "yes", "y", "t", "1" --> true}<br/>
     * {@code "false", "no", "n", "f", "0" --> false}<br/>
     * {@code Any other value --> null}<br/>
     */
    @Override
    public Boolean getBooleanValue() {
        if (this.cell!=null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (cell.getNumericCellValue()==0) return false;
                    else if (cell.getNumericCellValue()==1) return true;
                    else return null;
                case Cell.CELL_TYPE_STRING: 
                    String string = cell.getStringCellValue();
                    return BooleanParser.parseBoolean(string);
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                default: return null;

            }
        }
        else return null;
    }
    
    
    
}
