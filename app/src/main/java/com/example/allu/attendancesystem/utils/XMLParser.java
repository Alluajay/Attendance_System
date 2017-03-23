package com.example.allu.attendancesystem.utils;

import android.content.Context;
import android.util.Log;

import com.example.allu.attendancesystem.pojo.Aadhar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by allu on 3/18/17.
 */

public class XMLParser {

    String TAG = "XMLParser";
    XmlPullParser parser;
    XmlPullParserFactory xmlPullParserFactory;

    Aadhar aadhar;


    public XMLParser(Context context){

        aadhar = new Aadhar();
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            parser = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public Aadhar processScannedData(String scanData){
        Log.d("Rajdeol",scanData);
        XmlPullParserFactory pullParserFactory;
        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));
            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Rajdeol","Start document");
                } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    aadhar.uid = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    //name
                    aadhar.name = parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    //gender
                    aadhar.gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);
                    // year of birth
                    aadhar.yob = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    //gname
                    aadhar.gname = parser.getAttributeValue(null,DataAttributes.AADHAR_GNAME_ATTR);

                    // care of
                    aadhar.careof = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);

                    // street
                    aadhar.street = parser.getAttributeValue(null,DataAttributes.AADHAR_STREET_ATTR);

                    // house
                    aadhar.house = parser.getAttributeValue(null,DataAttributes.AADHAR_HOUSE_ATTR);

                    // landmark
                    aadhar.landmark = parser.getAttributeValue(null,DataAttributes.AADHAR_LM_ATTR);

                    // village
                    aadhar.vtc = parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    aadhar.postoffice = parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR);
                    // district
                    aadhar.dist = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);

                    // subdistrict
                    aadhar.subdist = parser.getAttributeValue(null,DataAttributes.AADHAR_SUBDIST_ATTR);

                    // state
                    aadhar.state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    aadhar.pincode = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);


                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d(TAG,"End tag "+parser.getName());
                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d(TAG,"Text "+parser.getText());
                }
                // update eventType
                eventType = parser.next();
            }
            // display the data on screen
        } catch (XmlPullParserException e) {
            aadhar = null;
            e.printStackTrace();
        } catch (IOException e) {
            aadhar = null;
            e.printStackTrace();
        }

        return aadhar;
    }
}
