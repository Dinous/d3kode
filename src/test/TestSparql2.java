package test;

import java.util.List;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.vocabulary.DC_11;



public class TestSparql2 {
    static public final String NL = System.getProperty("line.separator") ; 
    
    public static void main(String[] args)
    {
        Model model = createModel() ;
        
        Query query = QueryFactory.make() ;

        query.setQueryType(Query.QueryTypeConstruct) ;
        
        // Build pattern
        
        ElementGroup elg = new ElementGroup() ;
        
        Var varTitle = Var.alloc("title") ;
        Var varX = Var.alloc("x") ;
        
        Triple t1 = new Triple(varX, DC_11.title.asNode(),  varTitle) ;
        elg.addTriplePattern(t1) ;
        
        // Don't use bNodes for anon variables.  The conversion is done in parsing.
        // BNodes here are assumed to be values from the target graph.
        Triple t2 = new Triple(varX, DC_11.description.asNode(), Var.alloc("desc")) ;
        elg.addTriplePattern(t2) ;
        
        
        // Attach the group to query.  
        query.setQueryPattern(elg) ;

        // Choose what we want - SELECT *
        //query.setQueryResultStar(true) ;
        query.addResultVar(varX.asNode());
        Var varType = Var.alloc("type") ;
        //query.setConstructTemplate(new TemplateTriple(model.getResource("1").asNode(), DC_11.type.asNode(), varType.asNode()));
        
        
        // Print query with line numbers
        // Prefix mapping just helps serialization
        query.getPrefixMapping().setNsPrefix("dc" , DC_11.getURI()) ;
        //query.serialize(new IndentedWriter(System.out,true)) ;
        System.out.println() ;
        
     // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset
        
        QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
        // Or QueryExecutionFactory.create(queryString, model) ;

        System.out.println("Titles: ") ;
        
        try {
            // Assumption: it's a SELECT query.
            Model rs = qexec.execConstruct(model) ;
            
            // The order of results is undefined.
            List<RDFNode> list = rs.listObjects().toList();
            for (RDFNode rdfNode : list) {
				System.out.println(rdfNode);
			}
        }
        finally
        {
            // QueryExecution objects should be closed to free any system resources 
            qexec.close() ;
        }

    }
    
    public static Model createModel()
    {
        Model model = ModelFactory.createDefaultModel() ;
        
        Resource r1 = model.createResource("http://example.org/book#1") ;
        Resource r2 = model.createResource("http://example.org/book#2") ;
        Resource r3 = model.createResource("http://example.org/book#3") ;
        
        r1.addProperty(DC_11.title, "SPARQL - the book")
          .addProperty(DC_11.description, "A book about SPARQL") ;
        
        r2.addProperty(DC_11.title, "Advanced techniques for SPARQL") ;

        r3.addProperty(DC_11.title, "Jena - an RDF framework for Java")
          .addProperty(DC_11.description, "A book about Jena") ;

        return model ;
    }


}
