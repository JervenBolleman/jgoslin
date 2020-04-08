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
package de.isas.lipidomics.palinom.goslinfragments;

import de.isas.lipidomics.domain.IsomericFattyAcid;
import de.isas.lipidomics.domain.LipidFaBondType;
import de.isas.lipidomics.domain.LipidIsomericSubspecies;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.domain.LipidStructuralSubspecies;
import de.isas.lipidomics.domain.StructuralFattyAcid;
import de.isas.lipidomics.palinom.GoslinFragmentsParser;
import static de.isas.lipidomics.palinom.HandlerUtils.asInt;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author nilshoffmann
 */
public class StructuralSubspeciesLcbHandler {

    private final StructuralSubspeciesFasHandler ssfh;
    private final IsomericSubspeciesLcbHandler islh;

    public StructuralSubspeciesLcbHandler(StructuralSubspeciesFasHandler ssfh, IsomericSubspeciesLcbHandler islh) {
        this.ssfh = ssfh;
        this.islh = islh;
    }

    public Optional<LipidSpecies> visitStructuralSubspeciesLcb(String headGroup, GoslinFragmentsParser.LcbContext lcbContext, List<GoslinFragmentsParser.FaContext> faContexts) {
        List<StructuralFattyAcid> fas = new LinkedList<>();
        StructuralFattyAcid lcbA = buildStructuralLcb(lcbContext, "LCB", 1);
        fas.add(lcbA);
        int nIsomericFas = 0;
        if (lcbA instanceof IsomericFattyAcid) {
            nIsomericFas++;
        }
        for (int i = 0; i < faContexts.size(); i++) {
            StructuralFattyAcid fa = ssfh.buildStructuralFa(headGroup, faContexts.get(i), "FA" + (i + 1), i + 2);
            fas.add(fa);
            if (fa instanceof IsomericFattyAcid) {
                nIsomericFas++;
            }
        }
        if (nIsomericFas == fas.size()) {
            IsomericFattyAcid[] arrs = new IsomericFattyAcid[fas.size()];
            fas.stream().map((t) -> {
                return (IsomericFattyAcid) t;
            }).collect(Collectors.toList()).toArray(arrs);
            return Optional.of(new LipidIsomericSubspecies(headGroup, arrs));
        } else {
            StructuralFattyAcid[] arrs = new StructuralFattyAcid[fas.size()];
            fas.toArray(arrs);
            return Optional.of(new LipidStructuralSubspecies(headGroup, arrs));
        }
    }

    public Optional<LipidSpecies> visitStructuralSubspeciesLcb(String headGroup, GoslinFragmentsParser.LcbContext lcbContext) {
        return visitStructuralSubspeciesLcb(headGroup, lcbContext, Collections.emptyList());
    }

    public StructuralFattyAcid buildStructuralLcb(GoslinFragmentsParser.LcbContext ctx, String faName, int position) {
        if (ctx.lcb_pure() != null && ctx.heavy_lcb() != null) {
            throw new RuntimeException("Heavy label in lcb_pure context not implemented yet!");
        }
        GoslinFragmentsParser.Lcb_pureContext pureCtx = ctx.lcb_pure();
        StructuralFattyAcid.StructuralFattyAcidBuilder fa = StructuralFattyAcid.structuralFattyAcidBuilder();
        fa.nCarbon(asInt(pureCtx.carbon(), 0));
        fa.nHydroxy(asInt(pureCtx.hydroxyl(), 0));
        if (pureCtx.db() != null) {
            int nDoubleBonds = asInt(pureCtx.db().db_count(), 0);
            if (pureCtx.db().db_positions() != null || nDoubleBonds == 0) {
                return islh.buildIsomericLcb(ctx, faName, position);
            } else if (pureCtx.db().db_count() != null) {
                fa.nDoubleBonds(nDoubleBonds);
            }
        }
        fa.lipidFaBondType(LipidFaBondType.ESTER);
        return fa.name(faName).position(position).lcb(true).build();
    }

}
