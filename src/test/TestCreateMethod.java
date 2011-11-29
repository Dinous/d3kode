package test;

import java.util.HashSet;
import java.util.Set;

import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.mTrace.WebApplication;
import org.liris.mTrace.kTBS.SingleKtbs;

public class TestCreateMethod {

	public static void main(String[] args) {
		SingleKtbs.getInstance().initialize(new WebApplication("http://localhost:8001/"));
		
		String sparqlRequest = "PREFIX : <http://localhost:8001/BASE_DE_TRACES_EDF/MODELE_MTRACE_PREMIERE/>" +
				"PREFIX ktbs: <http://liris.cnrs.fr/silex/2009/ktbs#>" +
				"PREFIX rdf: <http://www.w3.org/2000/01/rdf-schema#>" +
				"CONSTRUCT {" +
				"[" +
				" a :ActionInstructeur ;" +
				" ktbs:hasSourceObsel ?Alarme_1,?Alarme_2, ?Telephonie_1; " +
				" ktbs:hasTrace <http://localhost:8001/BASE_DE_TRACES_EDF/SparqlTrace18/>; " +
				" ktbs:hasBegin ?hasBegin1;" +
				" ktbs:hasEnd ?hasEnd2" +
				" ]}" +
				" WHERE {" +
				" ?Alarme_1 a :Alarme;" +
				" :Evenement ?Evenement1;" +
				" :NumOrdre ?NumOrdre1;" +
				" :Materiel ?Materiel1;" +
				" ktbs:hasBegin ?hasBegin1;" +
				" ktbs:hasEnd ?hasEnd1;" +
				" rdf:label ?label1. " +
				" FILTER (regex(?Evenement1 , 'En apparition'))" +
				"}";
		
//		IMethod iMethod = SingleKtbs.getInstance().createMethodKtbs("http://localhost:8001/BASE_DE_TRACES_EDF/",null, "Requête sparql", sparqlRequest);
//		
//		Set<String> sourcesTrace = new HashSet<String>();
//		sourcesTrace.add("http://localhost:8001/BASE_DE_TRACES_EDF/MTRACE_PREMIERE/");
//		SingleKtbs.getInstance().createComputedTrace(
//				"http://localhost:8001/BASE_DE_TRACES_EDF/", "SparqlTrace19", iMethod.getUri(),sourcesTrace, null);
		
		
	}
}
