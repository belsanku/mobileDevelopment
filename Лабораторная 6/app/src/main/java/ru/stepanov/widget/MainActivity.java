package ru.stepanov.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private Document doc;
    private Thread secondThread;
    private Runnable runnable;
    private static HashMap<String, String> metals = new HashMap<>();
    static final String urlCharset = StandardCharsets.UTF_8.name();
    static final String url = "https://www.cbr.ru/scripts/xml_metall.asp";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.cbrInfoMetals);
        try {
            for (Map.Entry<String, String> entry : getInfo().entrySet()) {
                textView.append(entry.getKey() + " : " + entry.getValue() + "\n" + "\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getInfo() throws ParserConfigurationException, InterruptedException, IOException, SAXException {
        HashMap<String, String> data = new HashMap<>();
        metals.put("1", "Золото");
        metals.put("2", "Серебро");
        metals.put("3", "Платина");
        metals.put("4", "Палладий");

        NodeList records = readXml(fetchData()).getElementsByTagName("Record");
        data.put("Дата", records.item(records.getLength() - 1).getAttributes().getNamedItem("Date").getTextContent());
        for (int i = records.getLength() - 4; i < records.getLength(); i++) {
            Node rec = records.item(i);
            data.put(metals.get(rec.getAttributes()
                    .getNamedItem("Code").getTextContent()),
                    rec.getChildNodes().item(0).getTextContent() +
                    "/" +
                    rec.getChildNodes().item(1).getTextContent());
        }
        System.out.println(data);
        return data;
    }

    private static String fetchData() throws InterruptedException {
        LinkedBlockingQueue<String> msgQueue = new LinkedBlockingQueue<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(url + "?" + getDateRequest()).openConnection();
                    connection.setRequestProperty("Accept-Charset", urlCharset);
                    InputStream response = connection.getInputStream();

                    if (connection.getResponseCode() > 199 && connection.getResponseCode() < 400) {
                        Scanner scanner = new Scanner(response);
                        msgQueue.add(scanner.useDelimiter("\\A").next());
                    } else {
                        msgQueue.add("");
                    }
                } catch (IOException e) {
                    msgQueue.add("");
                    e.printStackTrace();
                }
            }
        }).start();
        return msgQueue.take();
    }

    private static String getDateRequest() throws UnsupportedEncodingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("ru"));
        GregorianCalendar calendar = new GregorianCalendar();
        String dateEnd = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        String dateStart = dateFormat.format(calendar.getTime());
        return java.lang.String.format(
                "date_req1=%s&date_req2=%s",
                URLEncoder.encode(dateStart, urlCharset),
                URLEncoder.encode(dateEnd, urlCharset)
        );
    }

    private static Document readXml(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource xmlInput = new InputSource(new StringReader(xmlString));
        return (Document) db.parse(xmlInput);
    }
}