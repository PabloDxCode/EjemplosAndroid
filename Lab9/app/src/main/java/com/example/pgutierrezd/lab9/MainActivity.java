package com.example.pgutierrezd.lab9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    TextView selection;
    ArrayList<String> items=new ArrayList<>();
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selection=(TextView)findViewById(R.id.selection);
        mListView = (ListView) findViewById(R.id.list);
        InputStream in = getResources().openRawResource(R.raw.palabras);
        //DOM
        try {
            DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc=builder.parse(in,null);
            NodeList palabras=doc.getElementsByTagName("word");
            //Node nodo = doc.getElementById("raiz");
            //NodeList hijos = nodo.getChildNodes();

            for(int i=0;i<palabras.getLength();i++){
                items.add(((Element)palabras.item(i)).getAttribute("value"));

            }
            in.close();
            mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
        } catch (ParserConfigurationException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException exec) {
            // TODO Auto-generated catch block
            exec.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
