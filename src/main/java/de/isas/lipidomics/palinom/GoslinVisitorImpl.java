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
package de.isas.lipidomics.palinom;

import de.isas.lipidomics.domain.Adduct;
import de.isas.lipidomics.domain.MolecularFattyAcid;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.domain.LipidCategory;
import de.isas.lipidomics.domain.LipidLevel;
import de.isas.lipidomics.domain.LipidMolecularSubspecies;
import de.isas.lipidomics.domain.LipidSpeciesInfo;
import de.isas.lipidomics.domain.LipidStructuralSubspecies;
import de.isas.lipidomics.domain.MolecularFattyAcid.MolecularFattyAcidBuilder;
import de.isas.lipidomics.domain.StructuralFattyAcid;
import de.isas.lipidomics.domain.StructuralFattyAcid.StructuralFattyAcidBuilder;
import de.isas.lipidomics.palinom.GoslinParser.Adduct_infoContext;
import de.isas.lipidomics.palinom.GoslinParser.Lipid_pureContext;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.RuleNode;

/**
 *
 * @author nils.hoffmann
 */
@Slf4j
class GoslinVisitorImpl extends GoslinBaseVisitor<LipidAdduct> {

    @Override
    public LipidAdduct visitLipid(GoslinParser.LipidContext ctx) {
        GoslinParser.Lipid_eofContext lipid = ctx.lipid_eof();
        Optional<Lipid_pureContext> categoryContext = Optional.ofNullable(lipid.lipid_pure());
        Optional<Adduct_infoContext> adductTermContext = Optional.ofNullable(lipid.adduct_info());

        LipidAdduct la = new LipidAdduct(categoryContext.map((cc) -> {
            return new LipidVisitor().visitLipid_pure(cc);
        }).orElse(LipidSpecies.NONE), adductTermContext.map((t) -> {
            return new AdductVisitor().visitAdduct_info(t);
        }).orElse(Adduct.NONE));
        return la;
    }

    private static class LipidVisitor extends GoslinBaseVisitor<LipidSpecies> {

        @Override
        public LipidSpecies visitLipid_pure(GoslinParser.Lipid_pureContext ctx) {
            LipidSpecies lipid = null;
            BitSet bs = new BitSet(5);
            bs.set(LipidCategory.ST.ordinal(), ctx.cholesterol() != null);
            bs.set(LipidCategory.GL.ordinal(), ctx.gl() != null);
            bs.set(LipidCategory.FA.ordinal(), ctx.mediatorc() != null);
            bs.set(LipidCategory.GP.ordinal(), ctx.pl() != null);
            bs.set(LipidCategory.SP.ordinal(), ctx.sl() != null);
            LipidCategory contextCategory = LipidCategory.UNDEFINED;
            switch (bs.cardinality()) {
                case 0:
                    throw new IllegalStateException("Parsing context did not contain content for any lipid category. Must contain exactly one of " + Arrays.toString(LipidCategory.values()));
                case 1:
                    contextCategory = LipidCategory.values()[bs.nextSetBit(0)];
                    break;
                default:
                    throw new IllegalStateException("Parsing context contained content for more than one lipid category. Must contain exactly one of " + Arrays.toString(LipidCategory.values()));
            }
            switch (contextCategory) {
                case ST:
                    if (ctx.cholesterol().chc() != null) {
                        lipid = new LipidSpecies(ctx.cholesterol().chc().ch().getText());
                        break;
                    } else if (ctx.cholesterol().che() != null) {
//                            lipid.setHeadGroup(ctx.cholesterol().che().().getText());
                        throw new RuntimeException("CHe not implemented yet!");
                    } else {
                        throw new RuntimeException("Unhandled sterol lipid: " + ctx.cholesterol().getText());
                    }
                case GL:
//                    ctx.gl();
                    throw new RuntimeException("GL not implemented yet!");
                case FA:
//                    ctx.mediator();
                    lipid = new LipidSpecies(ctx.mediatorc().getText());
                    break;
                case GP:
                    lipid = handleGlyceroPhospholipid(ctx).orElse(LipidSpecies.NONE);
                    break;
                case SP:
                    throw new RuntimeException("SP not implemented yet!");
//                    if (ctx.sl().dsl() != null) {
//                        lipid = new LipidSpecies(ctx.sl().dsl().hg_dslc().getText());
//                        visitFas(Arrays.asList(ctx.sl().dsl().fa()), lipid);
//                    } else if (ctx.sl().lsl() != null) {
//                        throw new RuntimeException("LSL handling not implemented yet in SL!");
////                        lipid.setLipidClass("LSL");
////                        lipid.setHeadGroup(ctx.sl().lsl().hg_lsl().getText());
////                        visitFas(Arrays.asList(ctx.sl().lsl().lcb().fa()), lipid);
//                    } else {
//                        throw new RuntimeException("Unhandled sphingolipid: " + ctx.sl().getText());
//                    }
//                    break;
                default:
                    log.warn("Unhandled contextCategory: {}", contextCategory);
            }
//            lipid.setLipidCategory(contextCategory.name());
//            lipid.setHeadGroup(headGroup);
//            lipid.setFa(fa);
            return lipid;
        }

        private Optional<LipidSpecies> handleGlyceroPhospholipid(Lipid_pureContext ctx) throws RuntimeException {
            //glycerophospholipids
            //cardiolipin
            if (ctx.pl().cl() != null) {
                return handleCl(ctx.pl().cl());
            } else if (ctx.pl().dpl() != null) {
                return handleDpl(ctx.pl().dpl());
            } else if (ctx.pl().lpl() != null) {
                return handleLpl(ctx.pl().lpl());
            } else if (ctx.pl().mlcl() != null) {
                return handleMlcl(ctx.pl().mlcl());
            } else if (ctx.pl().pl_o() != null) {
                throw new RuntimeException("PL_o handling not implemented yet in PL!");
            } else {
                throw new RuntimeException("Unhandled context state in PL!");
            }
        }

        private Optional<LipidSpecies> handlePlo(GoslinParser.Pl_oContext ploc) {
//            String headGroup = ploc.hg_clc().getText();
//            if (cl.pl_species() != null) { //species level
//                //process species level
//                return visitSpeciesFas(headGroup, cl.pl_species().fa());
//            } else if (cl.cl_subspecies() != null) {
//                //process subspecies
//                if (cl.cl_subspecies().sorted_fa_separator() != null) {
//                    //sorted => StructuralSubspecies
//                    return visitStructuralSubspeciesFas(headGroup, cl.cl_subspecies().fa());
//                } else if (cl.cl_subspecies().unsorted_fa_separator() != null) {
//                    //unsorted => MolecularSubspecies
//                    return visitMolecularSubspeciesFas(headGroup, cl.cl_subspecies().fa());
//                }
//            } else {
//                throw new RuntimeException("Unhandled context state in CL!");
//            }
            return Optional.empty();
        }

        private Optional<LipidSpecies> handleCl(GoslinParser.ClContext cl) {
            String headGroup = cl.hg_clc().getText();
            if (cl.pl_species() != null) { //species level
                //process species level
                return visitSpeciesFas(headGroup, cl.pl_species().fa());
            } else if (cl.cl_subspecies() != null) {
                //process subspecies
                if (cl.cl_subspecies().sorted_fa_separator() != null) {
                    //sorted => StructuralSubspecies
                    return visitStructuralSubspeciesFas(headGroup, cl.cl_subspecies().fa());
                } else if (cl.cl_subspecies().unsorted_fa_separator() != null) {
                    //unsorted => MolecularSubspecies
                    return visitMolecularSubspeciesFas(headGroup, cl.cl_subspecies().fa());
                }
            } else {
                throw new RuntimeException("Unhandled context state in CL!");
            }
            return Optional.empty();
        }

        private Optional<LipidSpecies> handleMlcl(GoslinParser.MlclContext mlcl) {
            String headGroup = mlcl.hg_mlclc().getText();
            if (mlcl.pl_species() != null) { //species level
                //process species level
                return visitSpeciesFas(headGroup, mlcl.pl_species().fa());
            } else if (mlcl.mlcl_subspecies() != null) {
                //process subspecies
                if (mlcl.mlcl_subspecies().sorted_fa_separator() != null) {
                    //sorted => StructuralSubspecies
                    return visitStructuralSubspeciesFas(headGroup, mlcl.mlcl_subspecies().fa());
                } else if (mlcl.mlcl_subspecies().unsorted_fa_separator() != null) {
                    //unsorted => MolecularSubspecies
                    return visitMolecularSubspeciesFas(headGroup, mlcl.mlcl_subspecies().fa());
                }
            } else {
                throw new RuntimeException("Unhandled context state in CL!");
            }
            return Optional.empty();
        }

        private Optional<LipidSpecies> handleDpl(GoslinParser.DplContext dpl) {
            String headGroup = dpl.hg_plc().getText();
            if (dpl.pl_species() != null) { //species level
                //process species level
                return visitSpeciesFas(headGroup, dpl.pl_species().fa());
            } else if (dpl.pl_subspecies() != null) {
                //process subspecies
                if (dpl.pl_subspecies().sorted_fa_separator() != null) {
                    //sorted => StructuralSubspecies
                    return visitStructuralSubspeciesFas(headGroup, dpl.pl_subspecies().fa());
                } else if (dpl.pl_subspecies().unsorted_fa_separator() != null) {
                    //unsorted => MolecularSubspecies
                    return visitMolecularSubspeciesFas(headGroup, dpl.pl_subspecies().fa());
                }
            } else {
                throw new RuntimeException("Unhandled context state in PL!");
            }
            return Optional.empty();
        }

        private Optional<LipidSpecies> handleLpl(GoslinParser.LplContext lpl) {
            String headGroup = lpl.hg_lplc().getText();
            //lyso PL has one FA, Species=MolecularSubSpecies=StructuralSubSpecies
            if (lpl.fa() != null) {
                return visitStructuralSubspeciesFas(headGroup, Arrays.asList(lpl.fa()));
            } else {
                throw new RuntimeException("Unhandled context state in PL!");
            }
        }

        private Optional<LipidSpecies> visitSpeciesFas(String headGroup, GoslinParser.FaContext faContext) {
            return Optional.of(new LipidSpecies(headGroup, getSpeciesInfo(faContext)));
        }

        private Optional<LipidSpecies> visitMolecularSubspeciesFas(String headGroup, List<GoslinParser.FaContext> faContexts) {
            List<MolecularFattyAcid> fas = new LinkedList<>();
            for (int i = 0; i < faContexts.size(); i++) {
                MolecularFattyAcid fa = buildMolecularFa(faContexts.get(i), "FA" + (i + 1));
                fas.add(fa);
            }
            MolecularFattyAcid[] arrs = new MolecularFattyAcid[fas.size()];
            fas.toArray(arrs);
            return Optional.of(new LipidMolecularSubspecies(headGroup, arrs));
        }

        private Optional<LipidSpecies> visitStructuralSubspeciesFas(String headGroup, List<GoslinParser.FaContext> faContexts) {
            List<StructuralFattyAcid> fas = new LinkedList<>();
            for (int i = 0; i < faContexts.size(); i++) {
                StructuralFattyAcid fa = buildStructuralFa(faContexts.get(i), "FA" + (i + 1), i + 1);
                fas.add(fa);
            }
            StructuralFattyAcid[] arrs = new StructuralFattyAcid[fas.size()];
            fas.toArray(arrs);
            return Optional.of(new LipidStructuralSubspecies(headGroup, arrs));
        }

        private Optional<LipidSpeciesInfo> getSpeciesInfo(GoslinParser.FaContext faContext) {
            if (faContext.fa_pure().heavy() != null) {
                throw new RuntimeException("Heavy label in FA_pure context not implemented yet!");
            }
            return Optional.of(new LipidSpeciesInfo(LipidLevel.SPECIES, Integer.parseInt(faContext.fa_pure().carbon().getText()), Integer.parseInt(faContext.fa_pure().hydroxyl().getText()), Integer.parseInt(faContext.fa_pure().hydroxyl().getText()), faContext.ether() != null));
        }
    }

//    private static class PlVisitor extends PaLiNomBaseVisitor<Object> {
//        
//    }
    private static class AdductVisitor extends GoslinBaseVisitor<Adduct> {

        @Override
        public Adduct visitAdduct_info(GoslinParser.Adduct_infoContext ctx) {
            Adduct adduct = new Adduct(ctx.adduct().getText(), ctx.adduct().getText(), Integer.parseInt(ctx.charge().getText()), Integer.parseInt(ctx.charge_sign().getText()));
            return adduct;
        }
    }

//    private static class MolecularFaVisitor extends GoslinBaseVisitor<MolecularFattyAcid> {
//
//        @Override
//        public MolecularFattyAcid visitFa(GoslinParser.FaContext ctx) {
//            if (ctx.ether() != null) {
//                throw new RuntimeException("Not implemented yet!");
//            } else if (ctx.fa_pure() != null) {
//                //hydroxyl case
//                MolecularFattyAcid fa = buildMolecularFa(ctx.fa_pure()., faName)
//                fa.setNCarbon(Integer.parseInt(ctx.fa_pure().carbon().getText()));
//
//                if (ctx.fa_pure().db() != null) {
//                    //double bonds without positions
//                    if (ctx.fa_pure().db().db_count() != null) {
//                        fa.addDoubleBonds(Integer.parseInt(ctx.fa_pure().db().db_count().getText()));
//                    } else if (ctx.fa_pure().db().db_position() != null) {
//                        throw new RuntimeException("Support for double bond positions not implemented yet!");
//                    } else {
//                        throw new RuntimeException("Unsupported double bond specification!");
//                    }
//                }
//                //base case
//                if (ctx.fa_pure().hydroxyl() != null) {
//                    fa.setNHydroxy(Integer.parseInt(ctx.fa_pure().hydroxyl().getText()));
//                }
//                return fa;
//
//            } else {
//                throw new RuntimeException("Uninitialized FaContext!");
//            }
//        }
//
//    }
    public static MolecularFattyAcid buildMolecularFa(GoslinParser.FaContext ctx, String faName) {
        MolecularFattyAcidBuilder fa = MolecularFattyAcid.molecularFaBuilder();
        if (ctx.ether() != null) {
            fa.ether(true);
        }
        if (ctx.fa_pure() != null) {
            fa.nCarbon(asInt(ctx.fa_pure().carbon(), 0));
            fa.nHydroxy(asInt(ctx.fa_pure().hydroxyl(), 0));
            if (ctx.fa_pure().db() != null) {
                fa.nDoubleBonds(asInt(ctx.fa_pure().db().db_count(), 0));
                if (ctx.fa_pure().db().db_position() != null) {
                    throw new RuntimeException("Support for double bond positions not implemented yet!");
                }
            }
            return fa.name(faName).build();

        } else {
            throw new RuntimeException("Uninitialized FaContext!");
        }
    }

    public static <T extends RuleNode> Integer asInt(T context, Integer defaultValue) {
        return maybeMapOr(context, (t) -> {
            return Integer.parseInt(t.getText());
        }, defaultValue);
    }

    public static <T> Optional<T> maybe(T t) {
        return Optional.ofNullable(t);
    }

    public static <T, R> R maybeMapOr(T t, Function<? super T, R> mapper, R r) {
        return maybe(t).map(mapper).orElse(r);
    }

    public static StructuralFattyAcid buildStructuralFa(GoslinParser.FaContext ctx, String faName, int position) {
        StructuralFattyAcidBuilder fa = StructuralFattyAcid.structuralFaBuilder();
        if (ctx.ether() != null) {
            fa.ether(true);
        }
        if (ctx.fa_pure() != null) {
            fa.nCarbon(asInt(ctx.fa_pure().carbon(), 0));
            fa.nHydroxy(asInt(ctx.fa_pure().hydroxyl(), 0));
            if (ctx.fa_pure().db() != null) {
                fa.nDoubleBonds(asInt(ctx.fa_pure().db().db_count(), 0));
                if (ctx.fa_pure().db().db_position() != null) {
                    throw new RuntimeException("Support for double bond positions not implemented yet!");
                }
            }
            return fa.name(faName).position(position).build();

        } else {
            throw new RuntimeException("Uninitialized FaContext!");
        }
    }

//    private static class MediatorVisitor extends PaLiNomBaseVisitor<Object> 
}
