package course.examples.contactsmap;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityJSON extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    //private static String url = "http://api.androidhive.info/contacts/";
    private static String url = "http://private-b08d8d-nikitest.apiary-mock.com/contacts";

    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    //private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_CONTACTS_LIST = "list";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_LATITUDE_LIST = "latitude_list";
    private static final String TAG_LONGITUDE_LIST= "longitude_list";
    private static final String TAG_PHONE = "phone";
    //private static final String TAG_PHONE_MOBILE = "mobile";
    //private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "officePhone";

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<String> latitude_list;
    ArrayList<String> longitude_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String cost = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();

                String office_number = ((TextView) view.findViewById(R.id.office_number))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_EMAIL, cost);
                in.putExtra(TAG_PHONE, description);
                in.putExtra(TAG_PHONE_OFFICE, office_number);
                startActivity(in);


            }
        });




        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ActivityJSON.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray contacts1 = new JSONArray(jsonStr);

                    // Getting JSON Array node
                    //contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    JSONObject contacts2 = contacts1.getJSONObject(0);

                    JSONArray contacts = contacts2.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    String name, email , phone , office, latitude, longitude ;
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        //String id = c.getString(TAG_ID);
                        if(c.has(TAG_NAME))
                        {
                            name = c.getString(TAG_NAME);
                        }
                        else
                        {
                            Log.i("Name ","No Such Tag as TAG_NAME");
                            name = "no value";
                        }
//                        String name = c.getString(TAG_NAME);
//                        String email = c.getString(TAG_EMAIL);
//                        String phone = c.getString(TAG_PHONE);
//                        String office = c.getString(TAG_PHONE_OFFICE);


                        if(c.has(TAG_EMAIL))
                        {
                            email = c.getString(TAG_EMAIL);
                        }
                        else
                        {
                            Log.i("Email ","No Such Tag as TAG_EMAIL");
                            email = "No email";
                        }


                        if(c.has(TAG_PHONE))
                        {
                            phone = c.getString(TAG_PHONE);
                        }
                        else
                        {
                            Log.i("Phone ","No Such Tag as TAG_PHONE");
                            phone = "No phone";
                        }

                        if(c.has(TAG_PHONE_OFFICE))
                        {
                            office = c.getString(TAG_PHONE_OFFICE);
                        }
                        else
                        {
                            Log.i("Office ","No Such Tag as TAG_PHONE_OFFICE");
                            office = "No office";
                        }


                        if(c.has(TAG_LATITUDE))
                        {
                            latitude = c.getString(TAG_LATITUDE);
                        }
                        else
                        {
                            Log.i("Latitude ","No Such Tag as TAG_LATITUDE");
                            latitude = "No latitude";
                        }

                        if(c.has(TAG_LONGITUDE))
                        {
                            longitude = c.getString(TAG_LONGITUDE);
                        }
                        else
                        {
                            Log.i("Longitude ","No Such Tag as TAG_LONGITUDE");
                            longitude = "No longitude";
                        }


                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //contact.put(TAG_ID, id);
                        contact.put(TAG_NAME, name);
                        contact.put(TAG_EMAIL, email);
                        contact.put(TAG_PHONE, phone);
                        contact.put(TAG_PHONE_OFFICE, office);
                        contact.put(TAG_LATITUDE,latitude);
                        contact.put(TAG_LONGITUDE,longitude);

                        // adding contact to contact list
                        contactList.add(contact);
                        //latitude_list.add(latitude);
                        //longitude_list.add(longitude);

                        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                .build());

                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                                .build());

                        //Log.d("test","2222222222");
                        ops.add(ContentProviderOperation.
                                newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                .build());

                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                                .build());

                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, office)
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                                .build());

                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, 3)
                                .build());

                        //Log.d("test","333333");

                        try {
                            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

                        } catch (Exception e) {

                            // Display warning
                            Context ctx = getApplicationContext();
                            CharSequence txt = "contactCreationFailure";
                            //int duration = Toast.LENGTH_SHORT;
                            //Toast toast = Toast.makeText(ctx, txt, duration);
                            //toast.show();

                            // Log exception
                            Log.e("test", "Exceptoin encoutered while inserting contact: " + e);
                        }




                    }
                    Intent in1 = new Intent();
                    //Intent in = new Intent(getApplicationContext(),
                    //      ContactsMap.class);

                    getParent().getIntent().putExtra(TAG_CONTACTS_LIST,contactList);

                    //startActivity(in);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.e("praneeth","abc");
            /**
             * Updating parsed JSON data into ListView
             *
             * */


            ListAdapter adapter = new SimpleAdapter(
                    ActivityJSON.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                    TAG_PHONE, TAG_PHONE_OFFICE }, new int[] { R.id.name,
                    R.id.email, R.id.mobile , R.id.office_number  });

            setListAdapter(adapter);
        }

    }

}