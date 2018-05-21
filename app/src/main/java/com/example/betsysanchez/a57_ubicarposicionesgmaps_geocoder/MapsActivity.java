package com.example.betsysanchez.a57_ubicarposicionesgmaps_geocoder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageButton boton;
    EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        texto= findViewById(R.id.txtBuscar);
        boton = findViewById(R.id.btnBuscar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Buscar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Buscar() throws IOException {
        Geocoder geocoder = new Geocoder(this);
        String location = texto.getText().toString();
        if(geocoder.isPresent()){
            List<Address> list = geocoder.getFromLocationName(location,1);
            if(list.size()==0){
                Toast.makeText(this, "No address was found", Toast.LENGTH_SHORT).show();
                return;
            }
            Address address = list.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            LatLng add = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(add).title(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(add,13));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
