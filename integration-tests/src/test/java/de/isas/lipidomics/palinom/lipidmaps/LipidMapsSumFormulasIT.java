/*
 * Copyright 2020 nilshoffmann.
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
package de.isas.lipidomics.palinom.lipidmaps;

import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.palinom.exceptions.ParseTreeVisitorException;
import de.isas.lipidomics.palinom.exceptions.ParsingException;
import de.isas.lipidomics.palinom.swisslipids.SwissLipidsVisitorParser;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author nilshoffmann
 */
@Slf4j
public class LipidMapsSumFormulasIT {

    @ParameterizedTest(name = "{index} ==> ''{0}'' can be parsed with the lipid maps grammar and sum formulas match")
    @CsvFileSource(resources = "/de/isas/lipidomics/palinom/formulas-lipid-maps.csv", numLinesToSkip = 0, delimiter = ',', encoding = "UTF-8", lineSeparator = "\n")
    public void testSumFormulas(String lipidName, String sumFormula) {
        LipidAdduct lipidAdduct;
        LipidMapsVisitorParser parser = new LipidMapsVisitorParser();
        try {
            lipidAdduct = parser.parse(lipidName.replaceAll("\"", ""));
            LipidSpecies ls = lipidAdduct.getLipid();
            assertNotNull(ls);
            assertEquals(sumFormula.replaceAll("\"", ""), lipidAdduct.getSumFormula(), "for lipid name " + lipidName);
        } catch (ParseTreeVisitorException pve) {
            fail("Parsing current LipidMAPS identifier: " + lipidName + " failed - incomplete implementation: " + pve.getMessage());
        } catch (ParsingException | RuntimeException ex) {
            fail("Parsing current LipidMAPS identifier: " + lipidName + " failed - name unsupported in grammar!");
        }
    }

}
