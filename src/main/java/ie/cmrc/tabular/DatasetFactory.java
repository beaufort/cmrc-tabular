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

import ie.cmrc.tabular.exceptions.InvalidFormatException;
import ie.cmrc.tabular.ascii.ASCIIFileDirectoryDataset;
import ie.cmrc.tabular.excel.ExcelDataset;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * A factory class that provides static methods for building {@link TabularDataset} instances
 * @author Yassine Lassoued
 */
public class DatasetFactory {
    
    /**
     * Creates a {@link TabularDataset} instance from an Excel file. The sheets in the Excel file must follow a tabular style. Each sheet must include a header as the first row. The header defines the name of each column. Each subsequent row is considered as a data record ({@link TableRow}).
     * @param file A <code>link java.io.File</code> pointing to the Excel file.
     * @return A {@link TabularDataset} object for accessing the content of the Excel file as a set of tables.
     * @throws IOException If an I/O error is occurs while reading the file 
     * @throws InvalidFormatException If the file format is not a valid Excel format
     * @throws FileNotFoundException If the file does not exist
     * @throws NullPointerException If the provided file is null
     */
    public static TabularDataset createFromExcel(File file) throws IOException, InvalidFormatException, FileNotFoundException, NullPointerException {
        if (file != null) {
            if (file.exists()) {
                if (file.isFile()) {
                    Workbook wb;
                    try {
                        wb = WorkbookFactory.create(file);
                    return new ExcelDataset(wb);
                    } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException ex) {
                        throw new InvalidFormatException(ex.getMessage());
                    }
                }
                else throw new InvalidFormatException("'"+file.getAbsolutePath()+"' is not a file.");
            }
            else {
                throw new FileNotFoundException("Directory '"+file.getAbsolutePath()+"' does not exist.");
            }
        }
        else throw new NullPointerException("Specified file is null");
    }
    
    /**
     * Creates a {@link TabularDataset} instance from a directory of ASCII files. Data files in the data directory must follow a CSV style, but may use any non-null and non-empty String as separator. Each data file must include as a first line the header of the table. The header is the list of data fields (columns). Each subsequent line is considered as a record ({@link TableRow}).
     * Example of data file ("|" being the separator):</br>
     * 
     * <code>id|name|dat of birth</br>
     * 1|Marck|01021970</br>
     * 2|Matthew|01021972</br>
     * ...</code>
     * 
     * @param file A java.io.File pointing to the data directory.
     * @param fileExtension Extension of the files within the directory to be wrap as tables ({@link Table}). The extension may or may not contain <code>"."</code>.If the extension is empty (<code>""</code>), then all file are considered as potential data tables. The name of each {@link Table} within the {@link TabularDataset} is that of the corresponding file without the extension. 
     * @param separator <code>String</code> separator used in the data file. If the provided value is null or empty then <code>ASCIIFileDirectoryDataset.DEFAULT_SEPARATOR</code> is used.
     * @return A {@link TabularDataset} object for accessing the content of the data files within the directory as a set of tables.
     * @throws InvalidFormatException If the provided file is not a directory
     * @throws FileNotFoundException If the directory does not exist
     * @throws NullPointerException If the provided file is null
     */
    public static TabularDataset createFromASCIIFileDir(File file, String fileExtension, String separator) throws InvalidFormatException, FileNotFoundException, NullPointerException {
        if (file!=null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    return new ASCIIFileDirectoryDataset(file, fileExtension, separator);
                }
                else throw new InvalidFormatException("'"+file.getAbsolutePath()+"' is not a directory.");
            }
            else {
                throw new FileNotFoundException("Directory '"+file.getAbsolutePath()+"' does not exist.");
            }
        }
        else throw new NullPointerException("Specified file is null");
    }
    
}
