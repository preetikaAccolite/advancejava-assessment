import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Getting the document
            Document document1 = builder.parse(new File("Licence1.xml"));
            Document document2 = builder.parse(new File("Licence2.xml"));
            // Normalize the XML structure
            document1.getDocumentElement().normalize();
            document2.getDocumentElement().normalize();
            // Get the elements
            NodeList CSRlist = document1.getElementsByTagName("CSR_Producer");
            Map<String,String> mergedmap=new HashMap<>();
            PrintWriter validWriter = new PrintWriter(new FileWriter("valid_licence.txt"));
            PrintWriter invalidWriter = new PrintWriter(new FileWriter("invalid_licence.txt"));

            for (int i = 0; i < CSRlist.getLength(); i++) {
                Node CSR = CSRlist.item(i);
                if (CSR.getNodeType() == Node.ELEMENT_NODE) {
                    Element CSRelement = (Element) CSR;
                    String nipr=CSRelement.getAttribute("NIPR_Number");
                    NodeList licenseList = CSRelement.getElementsByTagName("License");
                    for (int j = 0; j < licenseList.getLength(); j++) {
                        Node licenseNode = licenseList.item(j);
                        if (licenseNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element licenseElement = (Element) licenseNode;
                            String expirationDate = licenseElement.getAttribute("License_Expiration_Date");
                            String licenseNumber = licenseElement.getAttribute("License_Number");
                            String dateStatusEffective = licenseElement.getAttribute("Date_Status_Effective");
                            String stateCode = licenseElement.getAttribute("State_Code");
                            String licenseClass = licenseElement.getAttribute("License_Class");
                            String licenseClassCode = licenseElement.getAttribute("License_Class_Code");
                            String licenseIssueDate = licenseElement.getAttribute("License_Issue_Date");
                            String residentIndicator = licenseElement.getAttribute("Resident_Indicator");
                            String licenseStatus = licenseElement.getAttribute("License_Status");
                            String stateID = licenseElement.getAttribute("State_ID");
                            System.out.println(licenseClassCode);
                            String key=getMergedKey(expirationDate,licenseNumber,dateStatusEffective,stateCode);
                            if (!expirationDate.isEmpty()) {
                                try {
                                    Date expirationDateObj = dateFormat.parse(expirationDate);
                                    if (expirationDateObj.after(currentDate)) {
                                        validWriter.println("Valid"+" "+nipr+"   "+licenseNumber + "  " + dateStatusEffective + "  " + stateCode + "  " + expirationDate+"  "+licenseClass+"  "+"  "+licenseClassCode+"  "+licenseIssueDate+"  "+ residentIndicator+"  "+licenseStatus+"  "+stateID);
                                        mergedmap.put(key, "Valid" + "        " +nipr+"      "+ licenseNumber + "       " + dateStatusEffective + "       " + stateCode + "       " + expirationDate + "       " +licenseIssueDate + "        " +  residentIndicator+"                      "+ licenseStatus + "       " + stateID + "       " +licenseClassCode + "       " + licenseClass);
                                    } else {
                                        invalidWriter.println("Invalid"+"  "+nipr+"   "+licenseNumber + "  " + dateStatusEffective + "  " + stateCode + "  " + expirationDate+"  "+licenseClass+"  "+"  "+licenseClassCode+"  "+licenseIssueDate+"  "+ residentIndicator+"  "+licenseStatus+"  "+stateID);
                                        mergedmap.put(key, "InValid" + "        " +nipr+"      "+ licenseNumber + "       " + dateStatusEffective + "       " + stateCode + "       " + expirationDate + "       " +licenseIssueDate + "        " +  residentIndicator+"                       "+ licenseStatus + "       " + stateID + "       " +licenseClassCode + "       " + licenseClass);
                                                      }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Empty expiration date");
                            }
                        }
                    }
                }
            }
            NodeList CSRlist2= document2.getElementsByTagName("CSR_Producer");
            for (int i = 0; i < CSRlist2.getLength(); i++) {
                Node CSR2 = CSRlist2.item(i);
                if (CSR2.getNodeType() == Node.ELEMENT_NODE) {
                    Element CSRelement2 = (Element) CSR2;
                    String nipr=CSRelement2.getAttribute("NIPR_Number");
                    NodeList licenseList2 = CSRelement2.getElementsByTagName("License");
                    for (int j = 0; j < licenseList2.getLength(); j++) {
                        Node licenseNode2 = licenseList2.item(j);
                        if (licenseNode2.getNodeType() == Node.ELEMENT_NODE) {
                            Element licenseElement2 = (Element) licenseNode2;
                            String expirationDate = licenseElement2.getAttribute("License_Expiration_Date");
                            String licenseNumber = licenseElement2.getAttribute("License_Number");
                            String dateStatusEffective = licenseElement2.getAttribute("Date_Status_Effective");
                            String stateCode = licenseElement2.getAttribute("State_Code");
                            String licenseClass = licenseElement2.getAttribute("License_Class");
                            String licenseClassCode = licenseElement2.getAttribute("License_Class_Code");
                            String licenseIssueDate = licenseElement2.getAttribute("License_Issue_Date");
                            String residentIndicator = licenseElement2.getAttribute("Resident_Indicator");
                            String licenseStatus = licenseElement2.getAttribute("License_Status");
                            String stateID = licenseElement2.getAttribute("State_ID");
                            String key=getMergedKey(expirationDate,licenseNumber,dateStatusEffective,stateCode );
                            if (!expirationDate.isEmpty()) {
                                try {
                                    Date expirationDateObj = dateFormat.parse(expirationDate);
                                    if (expirationDateObj.after(currentDate)) {
                                        validWriter.println("Valid"+"   "+nipr+"  "+licenseNumber + "  " + dateStatusEffective + "  " + stateCode + "  " + expirationDate+"  "+licenseClass+"  "+"  "+licenseClassCode+"  "+licenseIssueDate+"  "+ residentIndicator+"  "+licenseStatus+"  "+stateID);
                                        mergedmap.put(key, "Valid" + "        " +nipr+"      "+ licenseNumber + "       " + dateStatusEffective + "       " + stateCode + "       " + expirationDate + "       " +licenseIssueDate + "        " +  residentIndicator+"                       "+ licenseStatus + "       " + stateID + "       " +licenseClassCode + "       " + licenseClass);
                                        }else{
                                        invalidWriter.println("InValid"+"   "+nipr+"  "+licenseNumber + "  " + dateStatusEffective + "  " + stateCode + "  " + expirationDate+"  "+licenseClass+"  "+"  "+licenseClassCode+"  "+licenseIssueDate+"  "+ residentIndicator+"  "+licenseStatus+"  "+stateID);
                                        mergedmap.put(key, "InValid" + "        " +nipr+"      "+ licenseNumber + "       " + dateStatusEffective + "       " + stateCode + "       " + expirationDate + "       " +licenseIssueDate + "        " +  residentIndicator+"                       "+ licenseStatus + "       " + stateID + "       " +licenseClassCode + "       " + licenseClass);
                                } }catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Empty expiration date");
                            }
                        }
                    }
                }
            }











            validWriter.close();
            invalidWriter.close();
//            mergefiles("valid_licence.txt","invalid_licence.txt","merged_licence.txt");
            writemergeddata(mergedmap,"merged_license.txt");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    private static String getMergedKey(String expirationDate, String licenseNumber, String dateStatusEffective, String stateCode) {
        return expirationDate + "|" + licenseNumber + "|" + dateStatusEffective + "|" + stateCode;
    }

    private static void writemergeddata(Map<String, String> mergedDataMap, String outputFile) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        String LICENSE_HEADER_ROW =
                "Validity    NIPR      licenseNumber dateStatusEffective stateCode expirationDate licenseIssueDate   residentIndicator  licenseStatus stateID  licenseClassCode  licenceclass";
        writer.println(LICENSE_HEADER_ROW);
        for (String mergedData : mergedDataMap.values()) {
            writer.println(mergedData);
        }
        writer.close();
    }
}