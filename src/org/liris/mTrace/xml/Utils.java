package org.liris.mTrace.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Utils {

	public static String xmlFileToString(String xmlFilePath) throws TransformerException, IOException{
		
		Node node = fileToDom(xmlFilePath);
		return nodeToString(node);
	}
	
	public static String xmlFileToStringWithoutXmlDeclaration(String xmlFilePath) throws TransformerException, IOException{
		
		Node node = fileToDom(xmlFilePath);
		String ret = nodeToString(node);
		ret = ret.substring(ret.indexOf(">") + 1, ret.length());
		return ret;
	}
	
	public static Document fileToDom(String xmlFilePath){
		try{
			// création d'une fabrique de documents
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			
			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();
			
			// lecture du contenu d'un fichier XML avec DOM
			return constructeur.parse(xmlFilePath);
		
		}catch(ParserConfigurationException pce){
			System.out.println("Erreur de configuration du parseur DOM");
			System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
		}catch(SAXException se){
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}catch(IOException ioe){
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}
		return null;
	}
	
	public static String nodeToString(Node node) throws TransformerException, IOException{
		DOMSource domSource = new DOMSource(node);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return(writer.toString());
	}
	
	public static void domToXmlFile(Node node , String fileDestination) throws TransformerException, IOException{
		String stringResult = nodeToString(node);
		FileWriter fw = new FileWriter(fileDestination, false);
		BufferedWriter output = new BufferedWriter(fw);
		output.write(stringResult);
		output.flush();
		output.close();
	}
	
	public static boolean deleteLine(String fileName, int lineNumber) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
 
            StringBuffer sb = new StringBuffer(); 
            String line;    
            int nbLinesRead = 0;       
            while ((line = reader.readLine()) != null) {
                if (nbLinesRead != lineNumber) {
                    sb.append(line + "\n");
                }
                nbLinesRead++;
            }
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sb.toString());
            out.close();
 
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	
	public static boolean deleteLines(String fileName, int lineNumber) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
 
            StringBuffer sb = new StringBuffer(); 
            String line;    
            int nbLinesRead = 0;       
            while ((line = reader.readLine()) != null) {
                if (nbLinesRead > lineNumber) {
                    sb.append(line + "\n");
                }
                nbLinesRead++;
            }
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sb.toString());
            //System.out.println(sb.toString());
            out.close();
 
        } catch (Exception e) {
            return false;
        }
        return true;
    }

	public static Node convertStringToNode(String Str){
		Node result=null;
		Document document;				
		
		try{			
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();								
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();								
			StringBuffer Buffer = new StringBuffer(Str);
			ByteArrayInputStream Bis1 = new ByteArrayInputStream(Buffer.toString().getBytes("UTF-8"));
			document = constructeur.parse(Bis1);
			Element racine = document.getDocumentElement();		
			result=racine.getParentNode(); 											
		}
		catch(Exception e){	}
		return result;
	}	
}
