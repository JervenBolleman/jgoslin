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

import de.isas.lipidomics.palinom.exceptions.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nils.hoffmann
 */
public enum LipidClass {

    UNDEFINED(LipidCategory.UNDEFINED, "UNDEFINED", "Undefined lipid class"),
    /**
     * Fatty acyls [FA] Fatty acids and conjugates [FA01]
     */
    FA(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "FA", "10-HDoHE",
            "11-HDoHE", "11-HETE", "11,12-DHET", "11(12)-EET", "12-HEPE",
            "12-HETE", "12-HHTrE", "12-OxoETE", "12(13)-EpOME", "13-HODE",
            "13-HOTrE", "14,15-DHET", "14(15)-EET", "14(15)-EpETE", "15-HEPE",
            "15-HETE", "15d-PGJ2", "16-HDoHE", "16-HETE", "18-HEPE", "5-HEPE",
            "5-HETE", "5-HpETE", "5-OxoETE", "5,12-DiHETE", "5,6-DiHETE",
            "5,6,15-LXA4", "5(6)-EET", "8-HDoHE", "8-HETE", "8,9-DHET",
            "8(9)-EET", "9-HEPE", "9-HETE", "9-HODE", "9-HOTrE", "9(10)-EpOME",
            "AA", "alpha-LA", "DHA", "EPA", "Linoleic acid", "LTB4", "LTC4",
            "LTD4", "Maresin 1", "Palmitic acid", "PGB2", "PGD2", "PGE2",
            "PGF2alpha", "PGI2", "Resolvin D1", "Resolvin D2", "Resolvin D3",
            "Resolvin D5", "tetranor-12-HETE", "TXB1", "TXB2", "TXB3"
    ),
    /**
     * Glycerolipids [GL] Monoradylglycerols [GL01]
     */
    MG(LipidCategory.GL, "Monoradylglycerols [GL01]", "MG", "MAG"),
    /**
     * Diradylglycerols [GL02]
     */
    DG(LipidCategory.GL, "Diradylglycerols [GL02]", "DG", "DAG"),
    /**
     * Triradylglycerols [GL03]
     */
    TG(LipidCategory.GL, "Triradylglycerols [GL03]", "TG", "TAG"),
    /**
     * Glycerophospholipids [GP]
     *
     */
    BMP(LipidCategory.GP, "Monoacylglycerophosphomonoradylglycerols [GP0410]", "BMP"),
    CDPDAG(LipidCategory.GP, "CDP-diacylglycerols [GP1301]", "CDP DG", "CDPDAG"),
    CL(LipidCategory.GP, "Glycerophosphoglycerophosphoglycerols [GP12]", "CL"),
    MGDG(LipidCategory.GP, "Glycosyldiacylglycerols [GL0501]", "MGDG"),
    DGDG(LipidCategory.GP, "Glycosyldiacylglycerols [GL0501]", "DGDG"),
    SQMG(LipidCategory.GP, "Glycosylmonoacylglycerols [GL0401]", "SQMG"),
    SQDG(LipidCategory.GP, "Glycosyldiacylglycerols [GL0501]", "SQDG"),
    MLCL(LipidCategory.GP, "Glycerophosphoglycerophosphoglycerols [GP12]", "CL"),
    PA(LipidCategory.GP, "Glycerophosphates [GP10]", "PA"),
    LPA(LipidCategory.GP, "Glycerophosphates [GP10]", "LPA"),
    PC(LipidCategory.GP, "Glycerophosphocholines [GP01]", "PC"),
    PC_O(LipidCategory.GP, "Glycerophosphocholines [GP01]", "PC O"),
    LPC(LipidCategory.GP, "Glycerophosphocholines [GP01]", "LPC"),
    LPC_O(LipidCategory.GP, "Glycerophosphocholines [GP01]", "LPC O"),
    PE(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PE"),
    PE_O(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PE O"),
    PET(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PEt"),
    LPE(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "LPE"),
    LPE_O(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "LPE O"),
    PG(LipidCategory.GP, "Glycerophosphoglycerols [GP04]", "PG"),
    LPG(LipidCategory.GP, "Glycerophosphoglycerols [GP04]", "LPG"),
    PGP(LipidCategory.GP, "Glycerophosphoglycerophosphates [GP05]", "PGP"),
    PI(LipidCategory.GP, "Glycerophosphoinositols [GP06]", "PI"),
    LPI(LipidCategory.GP, "Glycerophosphoinositols [GP06]", "LPI"),
    LPS(LipidCategory.GP, " Glycerophosphoserines [GP03]", "LPS"),
    PIP(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP"),
    PIP_3p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[3']"),
    PIP_4p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[4']"),
    PIP_5p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[5']"),
    PIP2(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2"),
    PIP2_3p_4p(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2[3',4']"),
    PIP2_3p_5p(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2[3',5']"),
    PIP3(LipidCategory.GP, "Glycerophosphoinositol trisphosphates [GP09]", "PIP3"),
    PS(LipidCategory.GP, "Glycerophosphoserines [GP03]", "PS"),
    /**
     * Sphingolipids
     */
    CER(LipidCategory.SP, "Ceramides [SP02]", "Cer"),
    C1P(LipidCategory.SP, "Ceramide-1-phosphates [SP0205]", "C1P"),
    SPH(LipidCategory.SP, "Sphingoid bases [SP01]", "SPH"),
    S1P(LipidCategory.SP, "Sphingoid bases [SP01]", "S1P"),
    SM(LipidCategory.SP, "Phosphosphingolipids [SP03]", "SM"),
    HEXCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "HexCer"),
    GLCCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "GlcCer"),
    GALCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "GalCer"),
    HEX2CER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "Hex2Cer"),
    HEX3CER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "Hex3Cer"),
    SHEXCER(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "SHexCer"),
    LACCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "LacCer"),
    /**
     * Sterol lipids
     */
    ST(LipidCategory.ST, "Sterols [ST01]", "ST"),
    SE(LipidCategory.ST, "Steryl esters [ST0102]", "SE"),
    //    FC(LipidCategory.ST, "Cholesterol [LMST01010001]", "FC"),
    CH(LipidCategory.ST, "Cholesterol [LMST01010001]", "FC", "Ch", "Cholesterol"),
    CHE(LipidCategory.ST, "Cholesteryl esters [ST0102]", "ChE", "CE");

    private final LipidCategory category;
    private final String lipidMapsClassName;
    private final List<String> synonyms;

    private LipidClass(LipidCategory category, String lipidMapsClassName, String... synonyms) {
        this.category = category;
        this.lipidMapsClassName = lipidMapsClassName;
        if (synonyms.length == 0) {
            throw new IllegalArgumentException("Must supply at least one synonym!");
        }
        this.synonyms = Arrays.asList(synonyms);
    }

    public LipidCategory getCategory() {
        return this.category;
    }

    public String getAbbreviation() {
        return this.synonyms.get(0);
    }

    public String getLipidMapsClassName() {
        return this.lipidMapsClassName;
    }

    public List<String> getSynonyms() {
        return this.synonyms;
    }

    public boolean matchesAbbreviation(String headGroup) {
        return this.synonyms.stream().anyMatch((synonym) -> {
            return synonym.equals(headGroup);
        });
    }

    public String getLysoAbbreviation(LipidClass lipidClass) {
        if (lipidClass.getCategory() == LipidCategory.GP) {
            return "L" + lipidClass.getAbbreviation();
        }
        throw new ConstraintViolationException("Lipid category must be " + LipidCategory.GP + " for lyso-classes!");
    }

    public static Optional<LipidClass> forHeadGroup(String headGroup) {
        return Arrays.asList(values()).stream().filter((lipidClass) -> {
            return lipidClass.matchesAbbreviation(headGroup.trim());
        }).findFirst();
    }

}