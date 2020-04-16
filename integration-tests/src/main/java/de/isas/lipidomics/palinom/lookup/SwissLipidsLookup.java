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
package de.isas.lipidomics.palinom.lookup;

import de.isas.lipidomics.domain.ExternalDatabaseReference;
import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.domain.LipidLevel;
import de.isas.lipidomics.domain.LipidSpeciesInfo;
import de.isas.lipidomics.palinom.swisslipids.SwissLipidsVisitorParser;
import de.isas.lipidomics.palinom.exceptions.ParsingException;
import de.isas.lipidomics.palinom.exceptions.ParseTreeVisitorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nils.hoffmann
 */
@Slf4j
public class SwissLipidsLookup {

    public static void main(String[] args) throws IOException {
        log.info("Parsing ", args[0]);
        URL swissLipidsTable = new File(args[0]).toURI().toURL();
        try (InputStream is = swissLipidsTable.openStream()) {
            try (InputStreamReader isr = new InputStreamReader(is, "UTF-8")) {
                try (BufferedReader br = new BufferedReader(isr)) {
                    File outputFile = new File("swiss-lipids-normalized.tsv");
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                        // SLID	LEVEL	NAME	ABBREVIATION	SYNONYMS1	SYNONYMS2	SYNONYMS3	SYNONYMS4	SYNONYMS5
                        bw.write("databaseUrl\tdatabaseElementId\tlipidLevel\tnativeAbbreviation\tnativeName\tnormalizedName");
                        bw.newLine();
                        br.lines().skip(1).map((swissLipidsEntry) -> {
                            String[] elements = swissLipidsEntry.split("\t", -1);
                            System.out.println(Arrays.deepToString(elements));
                            return new ExternalDatabaseReference(
                                    "https://www.swisslipids.org/#/entity/",
                                    elements[0],
                                    elements[1],
                                    elements[3],
                                    elements[2],
                                    parseAbbreviation(elements[2])
                            );
                        }).forEach((edr) -> {
                            StringBuilder sb = new StringBuilder();
                            sb.
                                    append(edr.getDatabaseUrl()).append("\t").
                                    append(edr.getDatabaseElementId()).append("\t").
                                    append(edr.getLipidLevel()).append("\t").
                                    append(edr.getNativeName()).append("\t").
                                    append(edr.getNativeAbbreviation()).append("\t").
                                    append(edr.getNormalizedName()).append("\t");
                            try {
                                bw.write(sb.toString());
                                bw.newLine();
                            } catch (IOException ex) {
                                log.error("Exception while writing external database reference " + edr + " to file " + outputFile, ex);
                            }
                        });
                    }
                }
            }
        }
    }

    public static String parseAbbreviation(String abbreviation) {
        SwissLipidsVisitorParser parser = new SwissLipidsVisitorParser();
        String result;
        try {
            LipidAdduct la = parser.parse(abbreviation);
            result = la.getLipid().getLipidString(la.getLipid().getInfo().orElse(LipidSpeciesInfo.NONE).getLevel(), true);
        } catch (ParsingException ex) {
            log.error("Exception while parsing " + abbreviation, ex);
            result = "N.D.";
        } catch (ParseTreeVisitorException pve) {
            log.error("Exception in GoslinVisitorParser " + abbreviation, pve);
            result = "N.I.";
        } catch (NumberFormatException nfe) {
            log.error("Exception in GoslinVisitorParser " + abbreviation, nfe);
            result = "N.I.";
        }
        return result;
    }
}
