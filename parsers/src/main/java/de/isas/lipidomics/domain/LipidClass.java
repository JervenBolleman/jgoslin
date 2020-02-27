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
 * Enumeration of lipid classes. The shorthand names / abbreviations are used to
 * look up the lipid class association of a lipid head group. We try to map each
 * abbreviation and synonyms thereof to LipidMAPS main class. However, not all
 * described head groups are categorized in LipidMAPS, or only occur in other
 * databases, so they do not have such an association at the moment.
 *
 * Example: Category=Glyerophospholipids -> Class=Glycerophosphoinositols (PI)
 * 
 * @author nils.hoffmann
 */
public enum LipidClass {

    UNDEFINED(LipidCategory.UNDEFINED, "UNDEFINED", "Undefined lipid class"),
    /**
     * Fatty acyls [FA] Fatty acids and conjugates [FA01]
     */
    FA1(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "FA"),
    FA2(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "10-HDoHE"),
    FA3(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "11-HDoHE"),
    FA4(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "11-HETE"),
    FA5(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "11,12-DHET"),
    FA6(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "11(12)-EET"),
    FA7(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "12-HEPE"),
    FA8(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "12-HETE"),
    FA9(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "12-HHTrE"),
    FA10(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "12-OxoETE"),
    FA11(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "12(13)-EpOME"),
    FA12(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "13-HODE"),
    FA13(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "13-HOTrE"),
    FA14(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "14,15-DHET"),
    FA15(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "14(15)-EET"),
    FA16(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "14(15)-EpETE"),
    FA17(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "15-HEPE"),
    FA18(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "15-HETE"),
    FA19(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "15d-PGJ2"),
    FA20(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "16-HDoHE"),
    FA21(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "16-HETE"),
    FA22(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "18-HEPE"),
    FA23(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5-HEPE"),
    FA24(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5-HETE"),
    FA25(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5-HpETE"),
    FA26(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5-OxoETE"),
    FA27(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5,12-DiHETE"),
    FA28(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5,6-DiHETE"),
    FA29(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5,6,15-LXA4"),
    FA30(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "5(6)-EET"),
    FA31(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "8-HDoHE"),
    FA32(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "8-HETE"),
    FA33(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "8,9-DHET"),
    FA34(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "8(9)-EET"),
    FA35(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "9-HEPE"),
    FA36(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "9-HETE"),
    FA37(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "9-HODE"),
    FA38(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "9-HOTrE"),
    FA39(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "9(10)-EpOME"),
    FA40(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "alpha-LA"),
    FA41(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "DHA", "Docosapentaenoic Acid"),
    FA42(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "EPA", "Eicosapentaenoic Acid"),
    FA43(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Linoleic acid"),
    FA44(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "LTB4"),
    FA45(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "LTC4"),
    FA46(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "LTD4"),
    FA47(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Maresin 1"),
    FA48(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Palmitic acid"),
    FA49(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "PGB2"),
    FA50(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "PGD2"),
    FA51(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "PGE2"),
    FA52(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "PGF2alpha"),
    FA53(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "PGI2"),
    FA54(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Resolvin D1"),
    FA55(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Resolvin D2"),
    FA56(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Resolvin D3"),
    FA57(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "Resolvin D5"),
    FA58(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "tetranor-12-HETE"),
    FA59(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "TXB1"),
    FA60(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "TXB2"),
    FA61(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "TXB3"),
    FA62(LipidCategory.FA, "Fatty acids and conjugates [FA01]", "AA", "Arachidonic acid", "Arachidonic acid"),
    NAE(LipidCategory.FA, "X-ethanolamine", "NAE"),
    GP_NAE(LipidCategory.FA, "X-ethanolamine", "GP-NAE"),
    MG(LipidCategory.GL, "Monoradylglycerols [GL01]", "MAG", "MG"),
    DG(LipidCategory.GL, "Diradylglycerols [GL02]", "DAG", "DG"),
    TG(LipidCategory.GL, "Triradylglycerols [GL03]", "TAG", "TG"),
    MGDG(LipidCategory.GL, "Glycosyldiradylglycerols [GL05]", "MGDG"),
    DGDG(LipidCategory.GL, "Glycosyldiradylglycerols [GL05]", "DGDG"),
    SQMG(LipidCategory.GL, "Glycosylmonoradylglycerols [GL04]", "SQMG"),
    SQDG(LipidCategory.GL, "Glycosyldiradylglycerols [GL05]", "SQDG"),
    BMP(LipidCategory.GP, "Monoacylglycerophosphomonoradylglycerols [GP0410]", "BMP", "LBPA"),
    CDPDAG(LipidCategory.GP, "CDP-Glycerols [GP13]", "CDPDAG", "CDPDG", "CDP-DG", "CDP-DAG"),
    CL(LipidCategory.GP, "Glycerophosphoglycerophosphoglycerols [GP12]", "CL"),
    MLCL(LipidCategory.GP, "Glycerophosphoglycerophosphoglycerols [GP12]", "MLCL"),
    DLCL(LipidCategory.GP, "Glycerophosphoglycerophosphoglycerols [GP12]", "DLCL"),
    LPA(LipidCategory.GP, "Glycerophosphates [GP10]", "LPA"),
    PC(LipidCategory.GP, "Glycerophosphocholines [GP01]", "PC"),
    LPC(LipidCategory.GP, "Glycerophosphocholines [GP01]", "LPC", "LysoPC"),
    PE(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PE"),
    PET(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PEt"),
    LPE(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "LPE", "LysoPE"),
    PG(LipidCategory.GP, "Glycerophosphoglycerols [GP04]", "PG"),
    LPG(LipidCategory.GP, "Glycerophosphoglycerols [GP04]", "LPG"),
    PGP(LipidCategory.GP, "Glycerophosphoglycerophosphates [GP05]", "PGP"),
    PA(LipidCategory.GP, "Glycerophosphates [GP10]", "PA"),
    PI(LipidCategory.GP, "Glycerophosphoinositols [GP06]", "PI"),
    LPI(LipidCategory.GP, "Glycerophosphoinositols [GP06]", "LPI"),
    PIP(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP"),
    PIP_3p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[3']", "PIP[3]"),
    PIP_4p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[4']", "PIP[4]"),
    PIP_5p(LipidCategory.GP, "Glycerophosphoinositol monophosphates [GP07]", "PIP[5']", "PIP[5]"),
    PIP2(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2"),
    PIP2_3p_4p(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2[3',4']", "PIP[3,4]"),
    PIP2_3p_5p(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2[3',5']", "PIP[3,5]"),
    PIP2_4p_5p(LipidCategory.GP, "Glycerophosphoinositol bisphosphates [GP08]", "PIP2[4',5']", "PIP[4,5]"),
    PIP3(LipidCategory.GP, "Glycerophosphoinositol trisphosphates [GP09]", "PIP3"),
    PIP3_3p_4p_5p(LipidCategory.GP, "Glycerophosphoinositol trisphosphates [GP09]", "PIP3[3',4',5']", "PIP3[3,4,5]"),
    PS(LipidCategory.GP, "Glycerophosphoserines [GP03]", "PS"),
    LPS(LipidCategory.GP, "Glycerophosphoserines [GP03]", "LPS"),
    PIM1(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM1"),
    PIM2(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM2"),
    PIM3(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM3"),
    PIM4(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM4"),
    PIM5(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM5"),
    PIM6(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PIM6"),
    PPA(LipidCategory.GP, "Glyceropyrophosphates [GP11]", "PPA"),
    GL_6_AC_GlC_GP(LipidCategory.GP, "Glycosylglycerophospholipids [GP14]", "6-Ac-Glc-GP"),
    PNC(LipidCategory.GP, "Glycerophosphonocholines [GP16]", "PnC"),
    PNE(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PnE"),
    PT(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "PT"),
    GLCDG(LipidCategory.GP, "Glycosyldiradylglycerols [GL05]", "Glc-DG"),
    PENME2(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PE-NMe2"),
    PENME(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "PE-NMe"),
    GLCGP(LipidCategory.GP, "Glycosylglycerophospholipids [GP14]", "Glc-GP"),
    NAPE(LipidCategory.GP, "Glycerophosphoethanolamines [GP02]", "NAPE"),
    LPIM1(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM1"),
    LPIM2(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM2"),
    LPIM3(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM3"),
    LPIM4(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM4"),
    LPIM5(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM5"),
    LPIM6(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "LPIM6"),
    CPA(LipidCategory.GP, "Glycerophosphoinositolglycans [GP15]", "CPA"),
    SLBPA(LipidCategory.GP, "Glycerophosphoglycerols [GP04]", "SLBPA"),
    CER(LipidCategory.SP, "Ceramides [SP02]", "Cer"),
    CERP(LipidCategory.SP, "Ceramides [SP02]", "CerP"),
    C1P(LipidCategory.SP, "Ceramide-1-phosphates [SP0205]", "C1P"),
    SM(LipidCategory.SP, "Phosphosphingolipids [SP03]", "SM"),
    HEXCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "HexCer", "GalCer", "GlcCer", "(3'-sulfo)GalCer"),
    HEX2CER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "Hex2Cer", "LacCer"),
    T_SULFO_LACCER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "(3'-sulfo)LacCer"),
    HEX3CER(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "Hex3Cer", "GB3"),
    FMC5(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "FMC-5"),
    FMC6(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "FMC-6"),
    SHEXCER(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "SHexCer", "(3'-sulfo)Galbeta-Cer"),
    LCB(LipidCategory.SP, "Sphingoid bases [SP01]", "LCB", "Sphingosine", "So", "Sphingosine-1-phosphate", "SPH"),
    LCBP(LipidCategory.SP, "Sphingoid bases [SP01]", "LCBP", "Sphinganine", "Sa", "Sphingosine-1-phosphate", "S1P", "SPH-P"),
    LHEXCER(LipidCategory.SP, "Hexosylsphingosine", "LHexCer", "HexSph"),
    EPC(LipidCategory.SP, "Phosphosphingolipids [SP03]", "EPC", "PE-Cer"),
    GB4(LipidCategory.SP, "Neutral glycosphingolipids [SP05]", "GB4"),
    GD3(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GD3"),
    GD1A_ALPHA(LipidCategory.SP, "Acidic glycosphingolipids", "GD1a alpha"),
    FUC_GAL_GM1(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "Fuc(Gal)-GM1"),
    GM3(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GM3"),
    SULFOGALCER(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "SulfoGalCer"),
    GD1A(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GD1a"),
    GM1B(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GM1b"),
    GT1B(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GT1b"),
    GQ1B(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GQ1b"),
    GT1A(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GT1a"),
    GQ1C(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GQ1c"),
    GP1C(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GP1c"),
    GD1C(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GD1c"),
    GD1B(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GD1b"),
    GT1C(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GT1c"),
    GT1A_ALPHA(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GT1a alpha"),
    GQ1B_ALPHA(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GQ1b alpha"),
    GP1C_ALPHA(LipidCategory.SP, "Acidic glycosphingolipids [SP06]", "GP1c alpha"),
    GB3CER(LipidCategory.SP, "Globoside", "Gb3Cer"),
    GB4CER(LipidCategory.SP, "Globoside", "Gb4Cer"),
    FORSSMAN(LipidCategory.SP, "Globoside", "Forssman"),
    GALGB4CER(LipidCategory.SP, "Globoside", "GalGb4Cer"),
    MSGG(LipidCategory.SP, "Globoside", "MSGG"),
    DSGG(LipidCategory.SP, "Globoside", "DSGG"),
    NEUAC_6_MSGG(LipidCategory.SP, "Globoside", "NeuAc(alpha2-6)-MSGG"),
    NEUAC_8_MSGG(LipidCategory.SP, "Globoside", "NeuAc(alpha2-8)-MSGG"),
    NOR1(LipidCategory.SP, "Globoside", "NOR1"),
    NORINT(LipidCategory.SP, "Globoside", "NORint"),
    NOR2(LipidCategory.SP, "Globoside", "NOR2"),
    GLOBO_H(LipidCategory.SP, "Globoside", "Globo-H"),
    GLOBO_A(LipidCategory.SP, "Globoside", "Globo-A"),
    AC_O9_GT3(LipidCategory.SP, "Globoside", "Ac-O-9-GT3"),
    GT1B_ALPHA_NEUGC(LipidCategory.SP, "Globoside", "GT1b alpha(NeuGc)"),
    GT1B_ALPHA(LipidCategory.SP, "Globoside", "GT1b alpha"),
    AC_O9_GT1B(LipidCategory.SP, "Globoside", "Ac-O-9-GT1b"),
    GM2_NEUGC(LipidCategory.SP, "Globoside", "GM2(NeuGc)"),
    GALGALNAC_GM1B_NEUGC(LipidCategory.SP, "Globoside", "GalGalNAc-GM1b(NeuGc)"),
    GALNAC_GM1B_NEUGC(LipidCategory.SP, "Globoside", "GalNAc-GM1b(NeuGc)"),
    GM1B_NEUGC(LipidCategory.SP, "Globoside", "GM1b(NeuGc)"),
    GALNAC_GM1B(LipidCategory.SP, "Globoside", "GalNAc-GM1b"),
    GM1_ALPHA(LipidCategory.SP, "Globoside", "GM1 alpha"),
    LEX_GM1(LipidCategory.SP, "Globoside", "Lex-GM1"),
    NEUGC_LACNAC_GM1_NEUGC(LipidCategory.SP, "Globoside", "NeuGc-LacNAc-GM1(NeuGc)"),
    GALNAC_GM1(LipidCategory.SP, "Globoside", "GalNAc-GM1"),
    SO3_GM1_NEUGC(LipidCategory.SP, "Globoside", "SO3-GM1(NeuGc)"),
    FUC_GM1_NEUGC(LipidCategory.SP, "Globoside", "Fuc-GM1(NeuGc)"),
    GM1_NEUGC(LipidCategory.SP, "Globoside", "GM1(NeuGc)"),
    FUC_GM1(LipidCategory.SP, "Globoside", "Fuc-GM1"),
    GD1C_NEUGC_NEUGC(LipidCategory.SP, "Globoside", "GD1c(NeuGc/NeuGc)"),
    GALGAL_GD1B(LipidCategory.SP, "Globoside", "GalGal-GD1b"),
    GAL_FUC_GD1B(LipidCategory.SP, "Globoside", "Gal(Fuc)-GD1b"),
    GAL_GD1B(LipidCategory.SP, "Globoside", "Gal-GD1b"),
    FUC_GD1B(LipidCategory.SP, "Globoside", "Fuc-GD1b"),
    GALNAC_GD1A_NEUGC_NEUAC(LipidCategory.SP, "Globoside", "GalNAc-GD1a(NeuGc/NeuAc)"),
    GALNAC_GD1A(LipidCategory.SP, "Globoside", "GalNAc-GD1a"),
    GD1A_NEUGC_NEUGC(LipidCategory.SP, "Globoside", "GD1a(NeuGc/NeuGc)"),
    GD1A_NEUAC_NEUGC(LipidCategory.SP, "Globoside", "GD1a(NeuAc/NeuGc)"),
    GD1A_NEUGC_NEUAC(LipidCategory.SP, "Globoside", "GD1a(NeuGc/NeuAc)"),
    AC_O9_GD1A(LipidCategory.SP, "Globoside", "Ac-O-9-GD1a"),
    SB1A(LipidCategory.SP, "Globoside", "SB1a"),
    SM1B(LipidCategory.SP, "Globoside", "SM1b"),
    SM1A(LipidCategory.SP, "Globoside", "SM1a"),
    GALNACGAL_FUC_GA1(LipidCategory.SP, "Globoside", "GalNAcGal(Fuc)-GA1"),
    GAL_FUC_GA1(LipidCategory.SP, "Globoside", "Gal(Fuc)-GA1"),
    FUC_GA1(LipidCategory.SP, "Globoside", "Fuc-GA1"),
    FUCIGB3CER(LipidCategory.SP, "Globoside", "(Fuc)iGb3Cer"),
    SO3_GAL_IGB4CER(LipidCategory.SP, "Globoside", "SO3-Gal-iGb4Cer"),
    SO3_IGB4CER(LipidCategory.SP, "Globoside", "SO3-iGb4Cer"),
    FUC_GAL_GAL_IGB4CER(LipidCategory.SP, "Globoside", "Fuc(Gal)Gal-iGb4Cer"),
    FUC_IGB3CER(LipidCategory.SP, "Globoside", "Fuc-iGb3Cer"),
    NEUACGAL_IGB4CER(LipidCategory.SP, "Globoside", "NeuAcGal-iGb4Cer"),
    GAL_IGB4CER(LipidCategory.SP, "Globoside", "Gal-iGb4Cer"),
    I_FORSSMAN(LipidCategory.SP, "Globoside", "i-Forssman"),
    IGB4CER(LipidCategory.SP, "Globoside", "iGb4Cer"),
    IGB3CER(LipidCategory.SP, "Globoside", "iGb3Cer"),
    FUC_BRANCHED_FORSSMAN(LipidCategory.SP, "Globoside", "Fuc-Branched-Forssman"),
    BRANCHED_FORSSMAN(LipidCategory.SP, "Globoside", "Branched-Forssman"),
    NEUGCNEUGC_GALGB4CER(LipidCategory.SP, "Globoside", "NeuGcNeuGc-GalGb4Cer"),
    NEUGC_GALGB4CER(LipidCategory.SP, "Globoside", "NeuGc-GalGb4Cer"),
    SO3_GALGB4CER(LipidCategory.SP, "Globoside", "SO3-GalGb4Cer"),
    SO3_GB4CER(LipidCategory.SP, "Globoside", "SO3-Gb4Cer"),
    GLCNACGB3CER(LipidCategory.SP, "Globoside", "GlcNAcGb3Cer"),
    GLOBO_B(LipidCategory.SP, "Globoside", "Globo-B"),
    PARA_FORSSMAN(LipidCategory.SP, "Globoside", "Para-Forssman"),
    GALNACGALGB3CER(LipidCategory.SP, "Globoside", "GalNAcGalGb3Cer"),
    FUCGALGB3CER(LipidCategory.SP, "Globoside", "FucGalGb3Cer"),
    GALGALGALGB3CER(LipidCategory.SP, "Globoside", "GalGalGalGb3Cer"),
    GALGALGB3CER(LipidCategory.SP, "Globoside", "GalGalGb3Cer"),
    GALGB3CER(LipidCategory.SP, "Globoside", "GalGb3Cer"),
    GLOBO_LEX_9(LipidCategory.SP, "Globoside", "Globo-Lex-9"),
    GALGLCNAC_GALGB4CER(LipidCategory.SP, "Globoside", "GalGlcNAc-GalGb4Cer"),
    GLCNAC_GALGB4CER(LipidCategory.SP, "Globoside", "GlcNAc-GalGb4Cer"),
    IPC(LipidCategory.SP, "Phosphosphingolipids [SP03]", "IPC", "PI-Cer"),
    LSM(LipidCategory.SP, "Ceramides [SP02]", "LSM", "SPC"),
    MIP2C(LipidCategory.SP, "Phosphosphingolipids [SP03]", "M(IP)2C"),
    MIPC(LipidCategory.SP, "Phosphosphingolipids [SP03]", "MIPC"),
    STT(LipidCategory.ST, "Sterols [ST01]", "ST"),
    SE(LipidCategory.ST, "Steryl esters [ST0102]", "SE"),
    CH(LipidCategory.ST, "Cholesterol [LMST01010001]", "CH", "FC", "Cholesterol"),
    CHE(LipidCategory.ST, "Cholesteryl esters [ST0102]", "ChE", "CE", "Cholesteryl ester", "Cholesterol ester"),
    AC2SGL(LipidCategory.SL, "Acyltrehaloses [SL03]", "AC2SGL"),
    PAT16(LipidCategory.SL, "Acyltrehaloses [SL03]", "PAT16"),
    PAT18(LipidCategory.SL, "Acyltrehaloses [SL03]", "PAT18"),
    DAT(LipidCategory.SL, "Acyltrehaloses [SL03]", "DAT"),
    DMPE(LipidCategory.GP, "Dimethylphosphatidylethanolamine", "DMPE"),
    PIMIP(LipidCategory.GP, "Phosphatidylinositol mannoside inositol phosphate", "PIMIP"),
    LCDPDAG(LipidCategory.GP, "Lyso-CDP-diacylglycerol", "LCDPDAG"),
    LDMPE(LipidCategory.GP, "Lysodimethylphosphatidylethanolamine", "LDMPE"),
    LMMPE(LipidCategory.GP, "Lysomonomethylphosphatidylethanolamine", "LMMPE"),
    LPIMIP(LipidCategory.GP, "Lysophosphatidylinositol- mannosideinositolphosphate", "LPIMIP"),
    LPIN(LipidCategory.GP, "Lysophosphatidylinositol-glucosamine", "LPIN"),
    /**
     * Fatty acyls [FA] Fatty esters [FA07]
     */
    WE(LipidCategory.FA, "Fatty esters [FA07]", "WE");

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
