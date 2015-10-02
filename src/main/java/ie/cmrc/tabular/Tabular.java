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

/**
 * A generic interface for tabular structures. A tabular structure may be a {@link Table}
 * or a {@link TableRow}, which has a {@link Header}.
 * @author Yassine Lassoued
 */
public interface Tabular {
    
    /**
     * Returns the {@link Header} of this {@code Tabular} structure
     * @return {@link Header} of the {@code Tabular} structure.
     * Please note that if this {@code Tabular} structure has no {@link Header},
     * then an empty {@link Header} is returned rather than a null value.
     */
    Header getHeader();
}
