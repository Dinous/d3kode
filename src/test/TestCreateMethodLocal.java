package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.liris.ktbs.client.KtbsConstants;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class TestCreateMethodLocal {

	public static void main(String[] args) {
		try {
			String request = FileUtils.readFileToString(new File("src/test/requete.rq"));
			
			Model trace = ModelFactory.createDefaultModel();
			trace.read(new FileInputStream("src/test/@obsels.rdf"), "", KtbsConstants.JENA_RDF_XML);
			
			Query query = QueryFactory.create(request, Syntax.syntaxARQ);

			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, trace);
			Model resultModel = qe.execConstruct();
			resultModel.write(System.out, KtbsConstants.JENA_TURTLE, null);
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main2(String[] args) {
		try {
			String request = FileUtils.readFileToString(new File("src/test/requete.rq"));
			
			Model trace = ModelFactory.createDefaultModel();
			trace.read(new FileInputStream("src/test/obsels.rdf"), "", KtbsConstants.JENA_RDF_XML);
			
			Query query = QueryFactory.create(request, Syntax.syntaxARQ);

			// Execute the query and obtain results
			QueryExecution qExec = QueryExecutionFactory.create(query, trace) ;
			 ResultSetFormatter.out(System.out, qExec.execSelect(), query) ;
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
