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

import ie.cmrc.tabular.TabularDataset;
import ie.cmrc.tabular.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * This class wraps an Excel spreadsheet (Apache POI <code>org.apache.poi.ss.usermodel.Workbook</code>) as a {@link TabularDataset}.
 * Each sheet in this spreadsheet will be considered as a {@link Table}
 * @author Yassine Lassoued
 */
public class ExcelDataset implements TabularDataset {
    
    /**
     * Apache POI Workbook object
     */
    private final Workbook wb;
    
    
    /**
     * Builds a Dataset using an Apache POI <code>org.apache.poi.ss.usermodel.Workbook</code> object
     * @param workbook Excel spreadsheet to wrap as a Dataset
     */
    public ExcelDataset(Workbook workbook) {
        this.wb = workbook;
    }
    
    /**
     * Returns a table ({@link Table}) given its name
     * @param tableName Name of a table
     * @return Table having as a name the provided String value. If no such table exists, then null is returned.
     */
    @Override
    public Table getTable(String tableName) {
        if (this.wb != null) {
            Sheet sheet = this.wb.getSheet(tableName);
            
            if (sheet != null) {
                return new ExcelTable(sheet);
            }
            else return null;
        }
        else return null;
    }
    
    /**
     * Returns the list of table names within this dataset
     * @return A <code>java.util.List</code> object containing the names of tables within this dataset. If the inner Excel spreadsheet (Apache POI org.apache.poi.ss.usermodel.Workbook) is null then null is returned.
     */
    @Override
    public List<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<String>();
        int n = wb.getNumberOfSheets();
        for (int i=0; i<n; i++) {
            tableNames.add(wb.getSheetName(i));
        }
        return tableNames;
    }

    /**
     * Implementation of the close method of the <code>java.io.Closeable</code> interface. In an ExcelDataset, this method does nothing.
     * @throws IOException This never happens in the case of an ExcelDataset
     */
    @Override
    public void close() throws IOException {
        // Do nothing
    }
    
}
