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

import ie.cmrc.util.Term;
import ie.cmrc.tabular.FieldMapHeader;
import ie.cmrc.tabular.Header;
import ie.cmrc.tabular.Table;
import ie.cmrc.tabular.TableRow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A Table wrapper for an ASCII file, where data values are separated using a given common separator. The first line of the ASCII file must be the table header, i.e., data field names separated using the common separator.
 * The default separator is DEFAULT_SEPARATOR.
 * @author Yassine Lassoued
 */
public class ASCIIFileTable implements Table {
    
    /**
     * File to read data from
     */
    private final File file;
    
    /**
     * File reader
     */
    private FileReader fileReader = null;
    
    /**
     * Buffered reader to read the file line by line
     */
    private BufferedReader bufferedReader = null;

    /**
     * String separator used in the ASCII file. Defaults to DEFAULT_SEPARATOR if not specified at construction time.
     */
    private final String separator;
    
    /**
     * Default file separator
     */
    public final static String DEFAULT_SEPARATOR = ",";
    
    /**
     * Name of the table
     */
    private final String name;
    
    /**
     * Header of the table in the form of a {@link ie.cmrc.tabular.FieldMapHeader} that stores the index of each column (field) name
     */
    private final FieldMapHeader<Integer> header;
    
    /**
     * Constructs a Table object that wraps the provided <code>java.io.File</code> object. The default separator for the ASCIIFile Table is <code>DEFAULT_SEPARATOR</code>. The name of the table will be null.
     * @param file An ASCII data file to load and wrap as a {@link Table}
     */
    public ASCIIFileTable(File file) {
        this.file = file;
        this.name = null;
        this.separator = DEFAULT_SEPARATOR;
        
        // Open the file readers
        this.openReaders();
        
        // Read the first line and build the header
        this.header = this.parseHeader();
    }
    
    
    /**
     * Constructs a Table object that wraps the provided File object. The default separator for the <code>ASCIIFileTable</code> is <code>ASCIIFileTable.DEFAULT_SEPARATOR</code>.
     * @param file An ASCII data file to load and wrap as a Table
     * @param name Name of the table
     */
    public ASCIIFileTable(File file, String name) {
        this.file = file;
        this.name = name;
        
        this.separator = DEFAULT_SEPARATOR;
        
        // Open the file readers
        this.openReaders();
        
        // Read the first line and build the header
        this.header = this.parseHeader();
    }
    
    /**
     * Constructs a Table object that wraps the provided File object
     * @param file A text file to load and wrap as a Table
     * @param name Name of the table
     * @param separator String separator used in the ASCII file
     */
    public ASCIIFileTable(File file, String name, String separator) {
        this.file = file;
        this.name = name;
        
        // Make sure the separator is valid
        if (separator==null || separator.isEmpty()) this.separator = DEFAULT_SEPARATOR;
        else this.separator = separator;
        
        // Open the file readers
        this.openReaders();
        
        // Read the first line and build the header
        this.header = this.parseHeader();
    }
    
    /**
     * Extracts the header of the table in the form of a HashMap<String, Integer> that stores the index of each column name.
     * @return A hash map mapping each colum name with its index. If the sheet is null or empty or the first row is empty, then an empty {@link ie.cmrc.tabular.FieldMapHeader} is returned.
     */
    private FieldMapHeader<Integer> parseHeader() {
        FieldMapHeader<Integer> tableHeader = new FieldMapHeader<>();
        
        if (this.bufferedReader != null) {
            String line = null;
            try {
                line = this.bufferedReader.readLine();
            } catch (IOException ex) {}

            if (line != null && !line.isEmpty()) {
                String[] fields = line.split(this.separator);
                if (fields != null) {
                    for (int i=0; i<fields.length; i++) {
                        String fieldName = fields[i];
                        if (fieldName!=null) fieldName = fieldName.trim();
                        Term field = new Term(fieldName);
                        tableHeader.put(field, i);
                    }
                }
            }

        }
        
        return tableHeader;
    }
    
    
    /**
     * Opens the file readers. Nothing happens when a FileNotFoundException is caught.
     */
    private void openReaders() {
        if (this.file!=null && this.file.exists() && this.file.isFile()) {
            try {
                this.fileReader = new FileReader(this.file);
                this.bufferedReader = new BufferedReader(this.fileReader);
                
            }
            catch (FileNotFoundException ex) {}
        }
    }
    
    /**
     * Closes the file readers. Nothing happens when an IOException is caught.
     */
    private void closeReaders() {
        if (this.bufferedReader != null) {
            try {
                this.bufferedReader.close();
            } catch (IOException ex) {}
        }

        if (this.fileReader != null) {
            try {
                this.fileReader.close();
            } catch (IOException ex) {}
        }
    }
    
    /**
     * Closes and opens again the readers in order to reinitialise the Table and point the next row to the first row. This is needed to rest the table (method reset).
     */
    private void resetReaders() {
        this.closeReaders();
        this.openReaders();
        if (this.bufferedReader != null) {
            try {
                // Skip header by reading the first line
                String line = this.bufferedReader.readLine();
            } catch (IOException ex) {}
        }
    }
    
    /**
     * Returns the table name
     * @return Name of the table
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Implementation of the close method of the <code>java.io.Closeable interface</code>. In an <code>ASCIIFileTable</code>, this method closes the inner file readers. It is strongly recommended to call this method when you are finished using the <code>ASCIIFileTable</code> object.
     * @throws IOException This is never thrown in the case of an ASCIIFileTable
     */
    @Override
    public void close() throws IOException {
        this.closeReaders();
    }

    /**
     * Returns the next row ({@link TableRow}) and increments the reading pointer.
     * @return Next row ({@link TableRow}) in the table. If the table contains
     * no more rows, then null is returned.
     */
    @Override
    public TableRow getNextRow() {
        if (this.bufferedReader!=null) {
            try {
                String line = this.bufferedReader.readLine();
                if (line!=null) return new ASCIITableRow(this.header, line, this.separator);
                else return null;
            } catch (IOException ex) {
                return null;
            }
        }
        else return null;
    }

    /**
     * Rewinds the table row reader by pointing it to the first row
     */
    @Override
    public void reset() {
        this.resetReaders();
    }

    /**
     * {@inheritDoc}
     * @return {@link ie.cmrc.tabular.Header} of the {@code ASCIIFileTable}.
     * Any modification of the returned {@code Header} will be reflected in the
     * {@code ASCIIFileTable} object, but not in the physical file itself.
     * Therefore, you are strongly not recommended to modify the content of the
     * returned object.
     */
    @Override
    public Header getHeader() {
        return this.header;
    }

}
