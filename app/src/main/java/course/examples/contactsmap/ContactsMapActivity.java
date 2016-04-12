package course.examples.contactsmap;

import android.app.Activity;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by praneeth on 12/4/16.
 */
public class ContactsMapActivity extends FragmentActivity  {

    private static final String TAG_CONTACTS_LIST = "list";
    private static final String TAG_LATITUDE_LIST = "latitude_list";
    private static final String TAG_LONGITUDE_LIST= "longitude_list";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_PHONE_OFFICE = "officePhone";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_section_dummy);

        Intent in = getParent().getIntent();

        ArrayList<HashMap<String, String>> contacts = (ArrayList<HashMap<String, String>>) in.getSerializableExtra(TAG_CONTACTS_LIST);


        String latitude,longitude;
        HashMap<String,String> hash;
        System.out.println(contacts);

        for(int i=0; i< contacts.size(); i++)
        {
            latitude = contacts.get(i).get("latitude");
            longitude = contacts.get(i).get("longitude");
            System.out.println(latitude + " " + longitude + "\n");
        }


    }
}
