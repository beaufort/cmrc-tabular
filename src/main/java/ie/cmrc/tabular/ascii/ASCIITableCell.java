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

import ie.cmrc.tabular.TableCell;
import ie.cmrc.tabular.util.BooleanParser;

/**
 * Wraps a String value as a {@link TableCell}
 * @author Yassine Lassoued
 */
public class ASCIITableCell implements TableCell {
    
    /**
     * String value wrapped by this TableCell object
     */
    private final String stringValue;

    /**
     * Constructs an <code>ASCIITableCell</code> object that wraps the String value provided as a parameter.
     * @param stringValue A String value to wrap as a TableCell object
     */
    public ASCIITableCell(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Indicates whether this table cell is empty.
     * @return True if the cell is empty, false otherwise.
     */
    @Override
    public Boolean isEmpty() {
        return (this.stringValue==null || this.stringValue.isEmpty());
    }
    
    
    /**
     * Returns the String value of the cell
     * @return String value of the cell.
     */
    @Override
    public String getStringValue() {
        return this.stringValue;
    }
    
    /**
     * Returns the Integer value of the cell
     * @return Integer value of the cell. If an Integer cannot be parsed, then null is returned.
     */
    @Override
    public Integer getIntValue() {
        if (this.stringValue!=null) {
            try {
                return Integer.parseInt(this.stringValue.trim());
            }
            catch (NumberFormatException e){
                return null;
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
        if (this.stringValue!=null) {
            try {
                return Double.parseDouble(this.stringValue.trim());
            }
            catch (NumberFormatException e){
                return null;
            }
        }
        else return null;
    }

    /**
     * Returns the Boolean value of the cell
     * @return Boolean value of the cell, following the (case-insensitive) rules below<br/>
     * <code>"true", "yes", "y", "t", "1" --> true</code><br/>
     * <code>"false", "no", "n", "f", "0" --> false</code><br/>
     * <code>Any other value --> null</code><br/>
     */
    @Override
    public Boolean getBooleanValue() {
        return BooleanParser.parseBoolean(this.stringValue);
    }
    
    
}
