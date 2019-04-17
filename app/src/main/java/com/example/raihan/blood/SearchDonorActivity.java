package com.example.raihan.blood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import database.DatabaseHandler;

public class SearchDonorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static String USER_ID= "UserId";

    public static String BLOOD_GROUP="blood_group";
    public static String LOCATION="location";
    public static String DISTRICT="district";
    public static String STATION="station";

    EditText bloodGroup,location,district,station;
    Button searchButton;
    Spinner searchItemSpinner,citySpinner,areaSpinner;

    int id;
    String city;

    final DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);
        Intent i=getIntent();

        try {
            id=i.getExtras().getInt(UserHomeActivity.USER_ID);
        }
        catch (Exception ex)
        {
            id=i.getExtras().getInt(DonorHomeActivity.USER_ID);
        }

      //  bloodGroup=(EditText)findViewById(R.id.reqGrpTV);
        location=(EditText)findViewById(R.id.searchLocationET);
     //   district=(EditText)findViewById(R.id.searchDistET);
     //   station=(EditText)findViewById(R.id.searchStationET);

        searchButton=(Button)findViewById(R.id.searchBloodButton);
        searchItemSpinner=(Spinner)findViewById(R.id.searchBGSpinner);
        citySpinner=findViewById(R.id.citySpinner);

        citySpinner.setOnItemSelectedListener(SearchDonorActivity.this);
        loadCitySpinner();
    }

    private void loadCitySpinner() {
        List<String> lables = db.getCityList();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

     //   Toast.makeText(parent.getContext(), "You selected: " + item, Toast.LENGTH_LONG).show();

        city=item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onSearchButtonClick(View view) {

        String grp=searchItemSpinner.getSelectedItem().toString();
     //   bloodGroup.setText(grp);
     //   String reqGroup=bloodGroup.getText().toString().toUpperCase();

        String loc=location.getText().toString().trim().toUpperCase();
        String dist=city.trim().toUpperCase();

      //  String dist=district.getText().toString().toUpperCase().trim();
      //  String ps=station.getText().toString().toUpperCase().trim();
        String ps="dummy";


      //  if(!(loc.equals("")) || !(dist.equals("")) || !(ps.equals("")))

            if(loc.trim().length()!=0
                    &&
                    !(grp.equalsIgnoreCase("Select Blood Group"))
                    &&
                    !(city.equalsIgnoreCase("Select City"))
            )
            {
            Intent i = new Intent(this, SearchResultActivity.class);

            i.putExtra(BLOOD_GROUP,grp);
            i.putExtra(LOCATION,loc);
            i.putExtra(DISTRICT,dist);
            i.putExtra(STATION,ps);
            i.putExtra(USER_ID , id);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"All Fields are required!", Toast.LENGTH_SHORT).show();
        }

    }
}
