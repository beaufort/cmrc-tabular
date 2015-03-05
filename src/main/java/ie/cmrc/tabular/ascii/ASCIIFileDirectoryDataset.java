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

import ie.cmrc.tabular.TabularDataset;
import ie.cmrc.tabular.Table;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Dataset wrapper for a directory File. The directory should contain tabular data encoded as ASCII files. The ASCII files share the same separator, and must include, as a first line, the header of the table, i.e., names of the columns separated by the separator.
 * @author Yassine Lassoued
 */
public class ASCIIFileDirectoryDataset implements TabularDataset {
    
    /**
     * File object pointing to the physical directory of this dataset
     */
    private final File dir;
    
    /**
     * Extension of the ASCII files in this directory
     */
    private final String fileExtension;
    
    /**
     * String separator used in the ASCII files
     */
    private final String separator;
    
    /**
     * Default file extension
     */
    public final static String DEFAULT_FILE_EXTENSION = "";
    
    /**
     * Default file separator
     */
    public final static String DEFAULT_SEPARATOR = ",";
    
    /**
     * Builds a Dataset using a directory File (<code>java.io.File</code>). By default, the ASCII file extension is <code>DEFAULT_FILE_EXTENSION</code>. Use ASCIIFileDirectoryDataset(File dir, String asciiFileExtension) if you want to use a different file extension. By default the file separator is DEFAULT_FILE_EXTENSION. Use ASCIIFileDirectoryDataset(File dir, String asciiFileExtension, String separator) if you want to specify a different separator.
     * The constructor does not check if the <code>java.io.File</code> object is a directory.
     * @param dir <code>java.io.File</code> object pointing to the physical directory of this dataset
     */
    public ASCIIFileDirectoryDataset(File dir) {
        this.dir = dir;
        this.fileExtension = DEFAULT_FILE_EXTENSION;
        this.separator = DEFAULT_SEPARATOR;
    }
    
    /**
     * Builds a {@link TabularDataset} using a directory and a file extension.
     * The constructor does not check if the <code>java.io.File</code> object is a directory.
     * @param dir File object pointing to the physical directory of this dataset
     * @param fileExtension Extension of the ASCII files to read in this directory, this may or may not start with ".". If the provided extension is null then DEFAULT_FILE_EXTENSION is used. If the provided file extension is empty (""), then all files in the directory are considered as potential tables.
     */
    public ASCIIFileDirectoryDataset(File dir, String fileExtension) {
        this.dir = dir;
        String ext = fileExtension;
        if (ext!=null) {
            ext = ext.trim();
            if (!ext.isEmpty()) {
                if (!ext.startsWith(".")) ext = "."+ext;
            }
        }
        else ext = DEFAULT_FILE_EXTENSION;
        this.fileExtension = ext;
        this.separator = DEFAULT_SEPARATOR;
    }
    
    /**
     * Builds a Dataset using a directory, a file extension, and a separator.
     * The constructor does not check if the File object is a directory.
     * @param dir <code>java.io.File</code> object pointing to the physical directory of this dataset
     * @param fileExtension Extension of the ASCII files to read in this directory, this may or may not start with ".". If the provided extension is null then DEFAULT_FILE_EXTENSION is used. If the provided file extension is empty (""), then all files in the directory are considered as potential tables.
     * @param separator String separator used in the ASCII files. If this is null or empty then <code>DEFAULT_SEPARATOR</code> is used instead.
     */
    public ASCIIFileDirectoryDataset(File dir, String fileExtension, String separator) {
        this.dir = dir;
        String ext = fileExtension;
        if (ext!=null) {
            if (!ext.isEmpty()) {
                if (!ext.startsWith(".")) ext = "."+ext;
            }
        }
        else ext = DEFAULT_FILE_EXTENSION;
        this.fileExtension = ext;
        
        // Make sure that the separator is valid
        String sep = separator;
        if (sep==null || sep.isEmpty()) sep = DEFAULT_SEPARATOR;
        this.separator = sep;
    }
    
    /**
     * Returns a table given its name
     * @param tableName Name of a table. This must not include the specified file extension for this ASCIIFileDirectoryDataset.
     * @return {@link Table} having as a name the provided String value. If no such table exists, then null is returned.
     */
    @Override
    public Table getTable(String tableName) {
        if (this.dir != null && this.dir.isDirectory() && tableName!=null) {            String fileName = tableName + this.fileExtension;
            File file = new File(this.dir, fileName);
                        
            if (file.exists() && file.isFile()) {                return new ASCIIFileTable(file, tableName, this.separator);
            }
            else {                return null;
            }
        }
        else {            return null;
        }
    }
    
    /**
     * Returns the list of table names within this dataset. In an <code>ASCIIFileDirectoryDataset</code>, all files with the specified extension are considered as potential tables.
     * @return A List object containing the names of tables within this dataset
     */
    @Override
    public List<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<String>();
        if (this.dir!=null && this.dir.isDirectory()) {
            File[] files = this.dir.listFiles(
                    new FileFilter(){
                        private String acceptedExtension;
                        
                        @Override
                        public boolean accept(File f){
                            return (f.isFile() && f.getName().endsWith(this.acceptedExtension));
                        }
                        
                        private FileFilter setAcceptedExtension(String acceptedExtension) {
                            this.acceptedExtension = acceptedExtension;
                            if (this.acceptedExtension == null) this.acceptedExtension="";
                            return this;
                        }
                    }.setAcceptedExtension(this.fileExtension)
            );
            
            String regex = this.fileExtension + "$";
            
            for (int i=0; i<files.length; i++) {
                String tableName = files[i].getName();
                if (this.fileExtension.isEmpty()) {
                    tableName = tableName.replaceAll(regex, "");
                }
                tableNames.add(tableName);
            }
            
        }
        
        return tableNames;
    }

    /**
     * Implementation of the close method of the <code>java.io.Closeable</code> interface. In an <code>ASCIIFileDirectoryDataset</code>, this method does nothing.
     * @throws IOException This never happens in the case of an ASCIIFileDirectoryDataset
     */
    @Override
    public void close() throws IOException {
        // Do nothing
    }
    
}
