/*
 * Copyright 2019 nils.hoffmann.
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
package de.isas.lipidomics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author nils.hoffmann
 */
@AllArgsConstructor
@Data
public class LipidSpeciesInfo {
    
    private static final class None extends LipidSpeciesInfo {

        private None() {
            super(LipidLevel.UNDEFINED, 0,0,0, LipidFaBondType.UNDEFINED);
        }
    }
    
    public static final LipidSpeciesInfo NONE = new LipidSpeciesInfo.None();
    
    private final LipidLevel level;
    private final int nCarbon;
    private final int nHydroxy;
    private final int nDoubleBonds;
    private final LipidFaBondType lipidFaBondType;
}
