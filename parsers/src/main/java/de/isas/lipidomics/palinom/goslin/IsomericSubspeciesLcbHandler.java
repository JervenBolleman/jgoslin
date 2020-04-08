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
package de.isas.lipidomics.palinom.goslin;

import de.isas.lipidomics.domain.IsomericFattyAcid;
import de.isas.lipidomics.domain.LipidFaBondType;
import de.isas.lipidomics.domain.LipidIsomericSubspecies;
import de.isas.lipidomics.domain.LipidSpecies;
import de.isas.lipidomics.domain.LipidStructuralSubspecies;
import de.isas.lipidomics.domain.StructuralFattyAcid;
import de.isas.lipidomics.palinom.GoslinParser;
import static de.isas.lipidomics.palinom.HandlerUtils.asInt;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author nilshoffmann
 */
public class IsomericSubspeciesLcbHandler {

    private final IsomericSubspeciesFasHandler isfh;

    public IsomericSubspeciesLcbHandler(IsomericSubspeciesFasHandler isfh) {
        this.isfh = isfh;
    }

    public Optional<LipidSpecies> visitIsomericSubspeciesLcb(String headGroup, GoslinParser.LcbContext lcbContext, List<GoslinParser.FaContext> faContexts) {
        List<StructuralFattyAcid> fas = new LinkedList<>();
        StructuralFattyAcid lcbA = buildIsomericLcb(lcbContext, "LCB", 1);
        fas.add(lcbA);
        int nIsomericFas = 0;
        if (lcbA instanceof IsomericFattyAcid) {
            nIsomericFas++;
        }
        for (int i = 0; i < faContexts.size(); i++) {
            StructuralFattyAcid fa = isfh.buildIsomericFa(headGroup, faContexts.get(i), "FA" + (i + 1), i + 2);
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

    public IsomericFattyAcid buildIsomericLcb(GoslinParser.LcbContext ctx, String faName, int position) {
        if (ctx.lcb_pure() != null && ctx.heavy_lcb() != null) {
            throw new RuntimeException("Heavy label in lcb_pure context not implemented yet!");
        }
        GoslinParser.Lcb_pureContext pureCtx = ctx.lcb_pure();
        IsomericFattyAcid.IsomericFattyAcidBuilder fa = IsomericFattyAcid.isomericFattyAcidBuilder();
        fa.nCarbon(asInt(pureCtx.carbon(), 0));
        fa.nHydroxy(asInt(pureCtx.hydroxyl(), 0));
        if (pureCtx.db() != null) {
            if (ctx.lcb_pure().db().db_positions() != null) {
                Map<Integer, String> doubleBondPositions = new LinkedHashMap<>();
                GoslinParser.Db_positionContext dbPosCtx = ctx.lcb_pure().db().db_positions().db_position();
                if (dbPosCtx.db_single_position() != null) {
                    Integer dbPosition = asInt(dbPosCtx.db_single_position().db_position_number(), -1);
                    String cisTrans = dbPosCtx.db_single_position().cistrans().getText();
                    doubleBondPositions.put(dbPosition, cisTrans);
                } else if (dbPosCtx.db_position() != null) {
                    for (GoslinParser.Db_positionContext dbpos : dbPosCtx.db_position()) {
                        if (dbpos.db_single_position() != null) {
                            Integer dbPosition = asInt(dbpos.db_single_position().db_position_number(), -1);
                            String cisTrans = dbpos.db_single_position().cistrans().getText();
                            doubleBondPositions.put(dbPosition, cisTrans);
                        }
                    }
                }
                fa.doubleBondPositions(doubleBondPositions);
            } else {
                fa.doubleBondPositions(Collections.emptyMap());
            }
        }
        fa.lipidFaBondType(LipidFaBondType.ESTER);
        return fa.name(faName).position(position).lcb(true).build();
    }

}
