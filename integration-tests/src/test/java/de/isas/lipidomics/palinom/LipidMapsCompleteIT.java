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
package de.isas.lipidomics.palinom;

import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.palinom.exceptions.ParseTreeVisitorException;
import de.isas.lipidomics.palinom.exceptions.ParsingException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author nils.hoffmann
 */
@Slf4j
public class LipidMapsCompleteIT {

    @ParameterizedTest
    @CsvFileSource(resources = "/de/isas/lipidomics/palinom/lipidmaps-names-Feb-10-2020.tsv", numLinesToSkip = 1, delimiter = '\t', encoding = "UTF-8", lineSeparator = "\n")
    public void isValidLipidMapsNameForCurrentLipidMapsForVisitorParser(
            String lipidMapsId,
            String lipidMapsName,
            String systematicName,
            String abbreviation,
            String lipidMapsCategory,
            String lipidMapsMainClass,
            String lipidMapsSubClass
    ) throws ParsingException, IOException {
        if (abbreviation == null) {
            fail("Entry "+lipidMapsId+" had no abbreviation!");
        }
        log.info("Parsing current lipid maps identifier: {}", abbreviation);
        LipidMapsVisitorParser parser = new LipidMapsVisitorParser();
        LipidAdduct lipidAdduct;
        try {
            lipidAdduct = parser.parse(abbreviation);
            LipidSpecies ls = lipidAdduct.getLipid();
            assertNotNull(ls);
        } catch (ParsingException ex) {
            fail("Parsing current LipidMAPS identifier: " + abbreviation + " failed - name unsupported in grammar!");
        } catch (ParseTreeVisitorException pve) {
            fail("Parsing current LipidMAPS identifier: " + abbreviation + " failed - missing implementation!");
        }
    }
}