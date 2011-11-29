package org.liris.mTrace.xml;

import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe d'évaluation d'expressions XPath
 *
 */
public class XPathProcessor
{
 // Instance du singleton
 //
 protected static XPathProcessor _instance = new XPathProcessor();
 
 // Hashmap du cache d'expressions Xpath
 //
 protected HashMap<String, XPathExpression> _hmXPathExpressions = new HashMap<String, XPathExpression>();
 
 // Objet de traitement Xpath
 //
 XPath xPath = XPathFactory.newInstance().newXPath();
 
 /**
 * Constructeur privé
 */
 protected XPathProcessor() {}
 
 /**
 * @return L'instance du singleton
 */
 public static XPathProcessor getInstance()
 {
 return _instance;
 }
 
 /**
 * Récupération de l'expression XPath compilée à partir du cache
 *
 * @param sXpathExpress L'expression XPath sour forme de chaine
 * @return L'expression XPath compilée
 * @throws Exception
 */
 protected  XPathExpression getExpression(String sXpathExpress) throws Exception
 {
 XPathExpression expression;
 if (_hmXPathExpressions.containsKey(sXpathExpress)) {
 expression = _hmXPathExpressions.get(sXpathExpress);
 }
 else {
 expression = xPath.compile(sXpathExpress);
 _hmXPathExpressions.put(sXpathExpress, expression);
 }
 
 return expression;
 }
 
 /**
 * Evaluation d'une expression XPath sur un document XML
 * @param sXpathExpress Expression XPath sous forme de chaine
 * @param xml XML parsé
 * @return Le résultat unique de l'évaluation Xpath
 * @throws Exception
 */
 public String Evaluate(String sXpathExpress, Node xml) throws Exception
 {
 XPathExpression expr = getExpression(sXpathExpress);
 
 return expr.evaluate(xml);
 }
 
 /**
  * Evaluation d'une expression XPath sur un document XML
  * @param sXpathExpress Expression XPath sous forme de chaine
  * @param xml XML parsé
  * @return Le résultat unique de l'évaluation Xpath
  * @throws Exception
  */
  public Node EvaluateNode(String sXpathExpress, Node xml) throws Exception
  {
  XPathExpression expr = getExpression(sXpathExpress);
  
  return (Node)expr.evaluate(xml, XPathConstants.NODE);
  }
 
 /**
 * @param sXpathExpress Expression XPath sous forme de chaine
 * @param xml XML parsé
 * @return Le résultat multiple de l'évaluation Xpath
 * @throws Exception
 */
public NodeList EvaluateMutli(String sXpathExpress, Node xml) throws Exception
 {
 XPathExpression expr = getExpression(sXpathExpress);
 
 return (NodeList)expr.evaluate(xml, XPathConstants.NODESET);
 }
 
}