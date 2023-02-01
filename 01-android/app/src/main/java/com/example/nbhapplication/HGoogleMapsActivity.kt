package com.example.nbhapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa:GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hgoogle_maps2)
        solicitarPermisos()
        iniciarLogicaMapa()
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{googleMap ->
            if (googleMap != null){
                mapa = googleMap
                establecerConfiguracionMapa()
            }
        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos =  permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true // tenemos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true // no tenemos permisos
        }
    }

    fun anadirMarcador(latLng:LatLng, tittle:String){
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(tittle)
        )
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }
    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION //Permisos a checkear
            )

        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos){
            permisos = true
        } else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf( // Areeglo de permisos
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
            1 //Código de peticion de permisos
            )
        }
    }
}
