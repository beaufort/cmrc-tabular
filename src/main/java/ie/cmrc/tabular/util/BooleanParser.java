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

package ie.cmrc.tabular.util;

import java.util.TreeMap;

/**
 * Utility class for parsing <code>Boolean</code> values
 * @author Yassine Lassoued
 */
public class BooleanParser {
    private static final TreeMap<String,Boolean> map;
    
    static {
        map = new TreeMap<>();
        map.put("yes", Boolean.TRUE);
        map.put("y", Boolean.TRUE);
        map.put("true", Boolean.TRUE);
        map.put("t", Boolean.TRUE);
        map.put("no", Boolean.FALSE);
        map.put("n", Boolean.FALSE);
        map.put("false", Boolean.FALSE);
        map.put("f", Boolean.FALSE);
        map.put("1", Boolean.TRUE);
        map.put("0", Boolean.FALSE);
    }
    
    /**
     * Parses a <code>String</code> to extract a <code>Boolean</code> value
     * @param stringValue Text to parse
     * @return <code>Boolean</code> value of from the parsed <code>String</code>, following the (case-insensitive) rules below<br/>
     * <code>"true", "yes", "y", "t", "1" --> true</code><br/>
     * <code>"false", "no", "n", "f", "0" --> false</code><br/>
     * <code>Any other value --> null</code><br/>
     */
    public static Boolean parseBoolean(String stringValue) {
        if (stringValue != null) return map.get(stringValue.trim().toLowerCase());
        else return null;
    }
}
