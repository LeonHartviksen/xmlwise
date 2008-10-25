package xmlwise;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.StringReader;

/**
 * XML helper methods.
 *
 * @author Christoffer Lerno
 * @version $Revision$ $Date$   $Author$
 */
public class Xml
{

	private Xml() {}

	/**
	 * Creates a DOM Document from the specified XML string, ignoring DTD-validation.
	 *
	 * @param xml a valid XML document, ie the String can't be null or empty
	 * @return the <code>Document</code> object for the specified string.
	 * @throws XmlParseException if we fail to parse the XML.
	 */
	public static Document createDocument(String xml) throws XmlParseException
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xml)));
		}
		catch (Exception e)
		{
			throw new XmlParseException(e);
		}
	}

	/**
	 * Loads an XML document.
	 *
	 * @param fileName the path to the file.
	 * @return an XML document.
	 * @throws IOException if we fail to load the file.
	 * @throws XmlParseException if there is a problem parsing the xml in the file.
	 */
	public static Document loadDocument(String fileName) throws IOException, XmlParseException
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			return builder.parse(fileName);
		}
		catch (IOException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new XmlParseException(e);
		}
	}

	public static String escapeXML(String stringToEscape)
	{
		int size = stringToEscape.length();
		if (size == 0) return "";
		StringBuilder s = new StringBuilder(size);
		for (int i = 0; i < size; i++)
		{
			char c = stringToEscape.charAt(i);
			switch (c)
			{
				case '<':
					s.append("&lt;");
					break;
				case '>':
					s.append("&gt;");
					break;
				case '&':
					s.append("&amp;");
					break;
				case '"':
					s.append("&quot;");
					break;
				case '\'':
					s.append("&apos;");
					break;
				default: s.append(c);
			}
		}
		return s.toString();
	}

	/**
	 * Loads a document from file and transforms it into an XmlElement tree.
	 *
	 * @param filename the path to the file.
	 * @return an XmlElement to use.
	 * @throws XmlParseException if parsing the file failed for some reason.
	 * @throws IOException if there were any problems reading from the file.
	 */
	public static XmlElement loadXmlTree(String filename) throws XmlParseException, IOException
	{
		return new XmlElement(loadDocument(filename).getDocumentElement());
	}

	/**
	 * Creates a document from a string and transforms it into an XmlElement tree.
	 *
	 * @param xml the xml as a string.
	 * @return an XmlElement to use.
	 * @throws XmlParseException if parsing the xml failed to validate for some reason.
	 */
	public static XmlElement createXmlTree(String xml) throws XmlParseException
	{
		return new XmlElement(createDocument(xml).getDocumentElement());
	}

}
