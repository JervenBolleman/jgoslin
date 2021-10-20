package de.isas.lipidomics.palinom.sparql;

import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.common.iteration.EmptyIteration;
import org.eclipse.rdf4j.common.iteration.SingletonIteration;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XSD;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.algebra.evaluation.TripleSource;

import de.isas.lipidomics.domain.LipidAdduct;
import de.isas.lipidomics.palinom.VisitorParser;
import de.isas.lipidomics.palinom.lipidmaps.LipidMapsVisitorParser;
import de.isas.lipidomics.palinom.swisslipids.SwissLipidsVisitorParser;

public class GoslinTripleSource implements TripleSource {

	private final ValueFactory vf;

	public GoslinTripleSource(ValueFactory vf) {
		this.vf = vf;
	}

	@Override
	public CloseableIteration<? extends Statement, QueryEvaluationException> getStatements(Resource subject,
			IRI predicate, Value object, Resource... contexts) throws QueryEvaluationException {
		// TODO Here the main work needs to be done.
		// We should define a set of predicates that allow us to select a parser.
		// Assuming an input query
		// PREFIX goslin: <TO BE DEFINED>
		// SELECT ?string
		// WHERE { [] goslin:swisslipids 'Cer(d18:1/20:2)' ; 
		//            goslin:lipidClassName ?string . }
		VisitorParser parser;
		if (predicate.equals(TOBEFINED.SWISSLIPIDS)) {
			//get a swisslipid parser
			parser = new SwissLipidsVisitorParser();
		} else if (predicate.equals(TOBEFINED.LIPIDMAPS)) {
			parser = new LipidMapsVisitorParser();
		} else if (predicate.equals(TOBEFINED.A_METHOD_ON_A_LIPID_ADDUCT_EG_CLASS_NAME)) {
			//We should already have a lipidadjuct as subject.
			LipidAdduct lipid = ((LipidWrappingBnode) subject).getLipidAdduct();
			final Literal lipidClassName = vf.createLiteral(lipid.getClassName());
			if (object == null || lipidClassName.equals(object)) {
				Statement stat = vf.createStatement(subject, predicate, lipidClassName);
				return new SingletonIteration<Statement, QueryEvaluationException>(stat);
			} else {
				// if the object is set and it is not the same as what the parse gave we have 
				// no results. 
				// This could be the case if someone uses this service to test if a value is what it 
				// is supposed to be.
				return new EmptyIteration<>();
			}
		} else {
			// All the other cases we can't deal with
			return new EmptyIteration<>();
		}
		if (parser != null && object == null) {
			//We have a parser but no string to parse
			return new EmptyIteration<>();
		} else if (object.isLiteral() && ((Literal) object).getDatatype().equals(XSD.STRING)) {
			LipidAdduct lipid = (LipidAdduct) parser.parse(object.stringValue());
			// We are going to hide this in a bnode for use later
			subject = new LipidWrappingBnode(lipid);
			Statement stat = vf.createStatement(subject, predicate, object);
			return new SingletonIteration<Statement, QueryEvaluationException>(stat);
		} else {
			//The object is not a string so we can't parse it
			return new EmptyIteration<>();
		}
		
		return new EmptyIteration<>();
	}

	@Override
	public ValueFactory getValueFactory() {
		return vf;
	}

}
