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
 * An abstract class that implements the common implementation-independent methods of the {@link TableRow} interface.
 * You should extend this Abstract class rather than implement the {@link TableRow} interface.
 * @author Yassine Lassoued
 */
public abstract class AbstractTableRow implements TableRow {

    /**
     * {@inheritDoc}
     * @param field Field the value of which to be returned
     * @return First non null and non-empty String value of the provided {@code field}, if any; otherwise null.
     */
    @Override
    public String getFieldStringValue(Term field) {
        
        List<TableCell> cells = this.getCells(field);
        String preferred = null;
        if (cells != null) {
            for (TableCell cell: cells) {
                if (cell!=null && !cell.isEmpty()) {
                    String value = cell.getStringValue();
                    if (value!=null) {
                        if (!value.isEmpty()) return value;
                    }
                    else preferred = value;
                }
            }
        }
        return preferred;
    }

    /**
     * {@inheritDoc}
     * @param field Field the value of which to be returned
     * @return First non null Integer value of the provided {@code field}, if any; otherwise null.
     */
    @Override
    public Integer getFieldIntValue(Term field) {
        List<TableCell> cells = this.getCells(field);
        if (cells != null) {
            for (TableCell cell: cells) {
                if (!cell.isEmpty()) {
                    Integer value = cell.getIntValue();
                    if (value!=null) return value;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param field Field the value of which to be returned
     * @return First non null Double value of the provided {@code field}, if any; otherwise null.
     */
    @Override
    public Double getFieldDoubleValue(Term field) {
        List<TableCell> cells = this.getCells(field);
        if (cells != null) {
            for (TableCell cell: cells) {
                if (!cell.isEmpty()) {
                    Double value = cell.getDoubleValue();
                    if (value!=null) return value;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param field Name of the field the value of which to be returned
     * @return First non null Boolean value of the provided {@code field}, if any; otherwise null.
     */
    @Override
    public Boolean getFieldBooleanValue(Term field) {
        List<TableCell> cells = this.getCells(field);
        if (cells != null) {
            for (TableCell cell: cells) {
                if (!cell.isEmpty()) {
                    Boolean value = cell.getBooleanValue();
                    if (value!=null) return value;
                }
            }
        }
        return null;
    }
    
    
    
    
}
