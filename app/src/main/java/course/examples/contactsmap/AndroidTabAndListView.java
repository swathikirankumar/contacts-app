package course.examples.contactsmap;

import android.app.TabActivity;

/**
 * Created by praneeth on 12/4/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabAndListView extends TabActivity {
    // TabSpec Names
    private static final String CONTACTS = "All Contatcts";
    private static final String MAP = "Contacts Map";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidtabandlistview);

        TabHost tabHost = getTabHost();

        // Inbox Tab
        TabSpec inboxSpec = tabHost.newTabSpec(CONTACTS);
        // Tab Icon
        inboxSpec.setIndicator(CONTACTS);
        Intent inboxIntent = new Intent(this, ActivityJSON.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);

        // Outbox Tab

        TabSpec outboxSpec = tabHost.newTabSpec(MAP);
        outboxSpec.setIndicator(MAP);

        Intent outboxIntent = new Intent(this, MapsActivity.class);
        outboxSpec.setContent(outboxIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
    }
}
