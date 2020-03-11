/*
 * 
 */
package de.isas.lipidomics.palinom;

import de.isas.lipidomics.palinom.exceptions.ParsingException;
import de.isas.lipidomics.domain.Adduct;
import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.domain.LipidCategory;
import de.isas.lipidomics.domain.LipidClass;
import de.isas.lipidomics.domain.LipidFaBondType;
import de.isas.lipidomics.domain.LipidIsomericSubspecies;
import de.isas.lipidomics.domain.LipidLevel;
import de.isas.lipidomics.domain.LipidMolecularSubspecies;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.domain.LipidSpeciesInfo;
import de.isas.lipidomics.domain.LipidStructuralSubspecies;
import de.isas.lipidomics.palinom.swisslipids.SwissLipidsVisitorParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author nils.hoffmann
 */
@Slf4j
public class SwissLipidsVisitorParserTest {

    @Test
    public void testCh() throws ParsingException {
        String ref = "CE(12:1)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("CE", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.ST, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.STRUCTURAL_SUBSPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(12, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(1, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }
    
    @Test
    public void testFas() throws ParsingException {
        String ref = "FA(18:4)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        System.out.println(lipidAdduct);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("FA", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.FA, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.SPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(18, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(4, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }

    @Test
    public void testMediators() throws ParsingException {
        String ref1 = "11,12-DiHETrE";
        String ref2 = "5,6-EpETrE";
        System.out.println("Testing first mediator name " + ref1);
        LipidAdduct lipidAdduct = parseLipidName(ref1);
        assertNotNull(lipidAdduct);
        System.out.println(lipidAdduct);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals(ref1, lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.FA, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidClass.forHeadGroup(ref1).get(), lipidAdduct.getLipid().getLipidClass().get());
        assertEquals(LipidSpeciesInfo.NONE, lipidAdduct.getLipid().getInfo().get());

        System.out.println("Testing second mediator name " + ref2);
        lipidAdduct = parseLipidName(ref2);
        assertNotNull(lipidAdduct);
        System.out.println(lipidAdduct);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals(ref2, lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.FA, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidClass.forHeadGroup(ref2).get(), lipidAdduct.getLipid().getLipidClass().get());
        assertEquals(LipidSpeciesInfo.NONE, lipidAdduct.getLipid().getInfo().get());
    }

    @Test
    public void testPL_hyphen() throws ParsingException {

        String ref = "PE(18:3_16:2)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        LipidMolecularSubspecies lipid = LipidMolecularSubspecies.class.cast(lipidAdduct.getLipid());
        assertNotNull(lipid);
        System.out.println(lipid);
        assertEquals("PE", lipid.getHeadGroup());
        assertEquals("FA1", lipid.getFa().
                get("FA1").
                getName());
        assertEquals(18, lipid.getFa().
                get("FA1").
                getNCarbon());
        assertEquals(3, lipid.getFa().
                get("FA1").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA1").
                getNHydroxy());
        assertEquals("FA2", lipid.getFa().
                get("FA2").
                getName());
        assertEquals(16, lipid.getFa().
                get("FA2").
                getNCarbon());
        assertEquals(2, lipid.getFa().
                get("FA2").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA2").
                getNHydroxy());
    }

    @Test
    public void testLysoPL() throws ParsingException {
        String ref1 = "LPE(18:0)";
        System.out.println("Testing lysolipid name " + ref1);
        LipidAdduct lipidAdduct1 = parseLipidName(ref1);
        assertNotNull(lipidAdduct1);
        LipidSpecies lipid1 = lipidAdduct1.getLipid();
        assertNotNull(lipid1);
        System.out.println(lipid1);
        assertEquals("LPE", lipid1.getHeadGroup());
        LipidSpeciesInfo li = lipid1.getInfo().get();
//        assertEquals("FA1", li.getFa().
//                get("FA1").
//                getName());
        assertEquals(18, li.
                getNCarbon());
        assertEquals(0, li.
                getNDoubleBonds());
        assertEquals(0, li.
                getNHydroxy());
    }
    
    @Test
    public void testFailForImplicitLyso() throws ParsingException {
//        assertThrows(ConstraintViolationException.class, () -> {
            String ref2 = "PE(18:0_0:0)";
            System.out.println("Testing implicit lysolipid name " + ref2);
            LipidAdduct lipidAdduct2 = parseLipidName(ref2);
            assertNotNull(lipidAdduct2);
            LipidMolecularSubspecies lipid2 = (LipidMolecularSubspecies) lipidAdduct2.getLipid();
            assertNotNull(lipid2);
            System.out.println(lipid2);
            assertEquals("PE", lipid2.getHeadGroup());
            assertEquals("FA1", lipid2.getFa().
                    get("FA1").
                    getName());
            assertEquals(18, lipid2.getFa().
                    get("FA1").
                    getNCarbon());
            assertEquals(0, lipid2.getFa().
                    get("FA1").
                    getNDoubleBonds());
            assertEquals(0, lipid2.getFa().
                    get("FA1").
                    getNHydroxy());
            assertEquals(2, lipid2.getFa().size());
            assertEquals("FA2", lipid2.getFa().
                    get("FA2").
                    getName());
            assertEquals(0, lipid2.getFa().
                    get("FA2").
                    getNCarbon());
            assertEquals(0, lipid2.getFa().
                    get("FA2").
                    getNDoubleBonds());
            assertEquals(0, lipid2.getFa().
                    get("FA2").
                    getNHydroxy());
//        });
    }

    @Test
    public void testPG_isomeric() throws ParsingException {
        String ref = "PG(0:0/16:2(9Z,12Z))";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        LipidIsomericSubspecies lipid = (LipidIsomericSubspecies) lipidAdduct.getLipid();
        assertNotNull(lipid);
        System.out.println(lipid);
    }
    
    @Test
    public void testPE_plasmanyl() throws ParsingException {
        String ref = "PE(O-18:3/16:2)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        LipidStructuralSubspecies lipid = (LipidStructuralSubspecies) lipidAdduct.getLipid();
        assertNotNull(lipid);
        System.out.println(lipid);
        assertEquals("PE", lipid.getHeadGroup());
        assertEquals(LipidFaBondType.ETHER_PLASMANYL, lipid.getFa().get("FA1").getLipidFaBondType());
        assertEquals("FA1", lipid.getFa().
                get("FA1").
                getName());
        assertEquals(18, lipid.getFa().
                get("FA1").
                getNCarbon());
        // these are actually 3 + 1 (double bond after ether)
        assertEquals(3, lipid.getFa().
                get("FA1").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA1").
                getNHydroxy());
        assertEquals("FA2", lipid.getFa().
                get("FA2").
                getName());
        assertEquals(16, lipid.getFa().
                get("FA2").
                getNCarbon());
        assertEquals(2, lipid.getFa().
                get("FA2").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA2").
                getNHydroxy());
    }

    @Test
    public void testPE_plasmenyl() throws ParsingException {
        String ref = "PE(P-18:0/16:2)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        LipidStructuralSubspecies lipid = (LipidStructuralSubspecies) lipidAdduct.getLipid();
        assertNotNull(lipid);
        System.out.println(lipid);
        assertEquals("PE", lipid.getHeadGroup());
        assertEquals(LipidFaBondType.ETHER_PLASMENYL, lipid.getFa().get("FA1").getLipidFaBondType());
        assertEquals("FA1", lipid.getFa().
                get("FA1").
                getName());
        assertEquals(18, lipid.getFa().
                get("FA1").
                getNCarbon());
        // these are actually 0 + 1 (double bond after ether)
        assertEquals(0, lipid.getFa().
                get("FA1").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA1").
                getNHydroxy());
        assertEquals("FA2", lipid.getFa().
                get("FA2").
                getName());
        assertEquals(16, lipid.getFa().
                get("FA2").
                getNCarbon());
        assertEquals(2, lipid.getFa().
                get("FA2").
                getNDoubleBonds());
        assertEquals(0, lipid.getFa().
                get("FA2").
                getNHydroxy());
    }

    @Test
    public void testTag() throws ParsingException {
        String ref = "TG(14:0_16:0_18:1)";
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertNotNull(lipidAdduct);
        LipidMolecularSubspecies lipid = (LipidMolecularSubspecies) lipidAdduct.getLipid();
        System.out.println(lipid);
        assertEquals(3, lipid.getFa().size());
        assertEquals(14, lipid.getFa().get("FA1").getNCarbon());
        assertEquals(0, lipid.getFa().get("FA1").getNDoubleBonds());
        assertEquals(0, lipid.getFa().get("FA1").getNHydroxy());

        assertEquals(16, lipid.getFa().get("FA2").getNCarbon());
        assertEquals(0, lipid.getFa().get("FA2").getNDoubleBonds());
        assertEquals(0, lipid.getFa().get("FA2").getNHydroxy());

        assertEquals(18, lipid.getFa().get("FA3").getNCarbon());
        assertEquals(1, lipid.getFa().get("FA3").getNDoubleBonds());
        assertEquals(0, lipid.getFa().get("FA3").getNHydroxy());

        assertEquals("TG 14:0_16:0_18:1", lipid.getLipidString(LipidLevel.MOLECULAR_SUBSPECIES));
    }

    @Test
    public void testSmSpeciesHydroxy() throws ParsingException {
        String ref = "SM(d32:0)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("SM", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.SP, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.SPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(32, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(2, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }

    @Test
    public void testSmSpeciesPlain() throws ParsingException {
        String ref = "SM(d32:0)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("SM", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.SP, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.SPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(32, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(2, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }
    
    @Test
    public void testMhdg() throws ParsingException {
        String ref = "MHDG (18:3/16:1)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("MHDG", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.GL, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.STRUCTURAL_SUBSPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(34, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(4, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }
    
    @Test
    public void testDhdg() throws ParsingException  {
        String ref = "DHDG (16:0/16:1)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("DHDG", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.GL, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.STRUCTURAL_SUBSPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(32, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(1, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(0, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }

    @Test
    public void testHex2Cer() throws ParsingException {
        String ref = "Hex2Cer(d18:1/16:0)";
        System.out.println("Testing lipid name " + ref);
        LipidAdduct lipidAdduct = parseLipidName(ref);
        assertEquals(Adduct.NONE, lipidAdduct.getAdduct());
        assertEquals("Hex2Cer", lipidAdduct.getLipid().getHeadGroup());
        assertEquals(LipidCategory.SP, lipidAdduct.getLipid().getLipidCategory());
        assertEquals(LipidLevel.STRUCTURAL_SUBSPECIES, lipidAdduct.getLipid().getInfo().get().getLevel());
        assertEquals(34, lipidAdduct.getLipid().getInfo().get().getNCarbon());
        assertEquals(1, lipidAdduct.getLipid().getInfo().get().getNDoubleBonds());
        assertEquals(2, lipidAdduct.getLipid().getInfo().get().getNHydroxy());
    }

    protected LipidAdduct parseLipidName(String ref) throws ParsingException {
        SwissLipidsVisitorParser parser = new SwissLipidsVisitorParser();
        LipidAdduct lipid = parser.parse(ref);
        return lipid;
    }

    static Stream<String> provideWenkSwissLipidsNamesForVisitorParser() throws IOException {
        URL u = SwissLipidsVisitorParserTest.class.getClassLoader().getResource("de/isas/lipidomics/palinom/wenk-lm-lipids.txt");
        try (InputStreamReader ir = new InputStreamReader(u.openStream())) {
            try (BufferedReader br = new BufferedReader(ir)) {
                return br.lines();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideWenkSwissLipidsNamesForVisitorParser")
    public void isValidSwissLipidsNameForVisitorParser(String lipidMapsName1) throws ParsingException {
        log.info("Parsing lipid maps identifier: {}", lipidMapsName1);
        SwissLipidsVisitorParser parser = new SwissLipidsVisitorParser();
        LipidAdduct lipidAdduct = parser.parse(lipidMapsName1);
        LipidSpecies ls = lipidAdduct.getLipid();
        log.info("Swiss lipids name {}:{}", lipidMapsName1, ls.getLipidString(ls.getInfo().orElse(LipidSpeciesInfo.NONE).getLevel()));
    }
}
