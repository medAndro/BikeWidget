package com.medandro.bikewidget.ui.map

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.medandro.bikewidget.R
import com.medandro.bikewidget.data.station.StationRemoteDataSource
import com.medandro.bikewidget.data.station.StationRepositoryImpl
import com.medandro.bikewidget.databinding.FragmentMapBinding
import com.medandro.bikewidget.domain.Station
import com.medandro.bikewidget.utils.getColorFromAttribute
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class MapFragment :
    Fragment(),
    OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val mapViewModel: MapViewModel by viewModels {
        val repository = StationRepositoryImpl(StationRemoteDataSource())
        MapViewModelFactory(repository)
    }
    private lateinit var locationSource: FusedLocationSource
    private val currentMarkers = mutableListOf<Marker>()

    private lateinit var resizedMarkerIcon: OverlayImage

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private fun isDarkMode(): Boolean {
        val nightModeFlags = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        setupObservers()
    }

    private fun setupObservers() {
        mapViewModel.stations.observe(viewLifecycleOwner) { stationList ->
            if (::naverMap.isInitialized) {
                drawMarkers(stationList)
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setupMapUI()
        binding.customLocationButton.map = naverMap
        naverMap.mapType = NaverMap.MapType.Navi
        prepareMarkerIcon()
        naverMap.locationSource = locationSource
        mapViewModel.lastCameraPosition?.let { naverMap.cameraPosition = it }
        mapViewModel.stations.value?.let { drawMarkers(it) }
    }

    fun drawMarkers(stations: List<Station>) {
        currentMarkers.forEach { it.map = null }
        currentMarkers.clear()
        val markerTintColor = requireContext().getColorFromAttribute(R.attr.colorSeoulBikeGreen)
        stations.forEach { station ->
            val marker =
                Marker().apply {
                    position = LatLng(station.latitude, station.longitude)
                    captionOffset = -90
                    captionTextSize = 16f
                    captionText = station.parkingQuantity.toString()
                    map = this@MapFragment.naverMap
                    iconTintColor = markerTintColor
                    icon = resizedMarkerIcon
                }
            currentMarkers.add(marker)
        }
    }

    private fun setupMapUI() {
        naverMap.uiSettings.isLocationButtonEnabled = false
        naverMap.locationOverlay.isVisible = false
        naverMap.mapType = NaverMap.MapType.Navi
        when (isDarkMode()) {
            true -> naverMap.isNightModeEnabled = true
            false -> naverMap.isNightModeEnabled = false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
                Toast
                    .makeText(
                        context,
                        getString(R.string.location_permission_denied_message),
                        Toast.LENGTH_SHORT,
                    ).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun prepareMarkerIcon() {
        val resizedBitmap =
            getBitmapFromVectorDrawable(
                requireContext(),
                R.drawable.ic_marker,
                MARKER_SIZE,
                MARKER_SIZE,
            )

        if (resizedBitmap == null) {
            Log.e("MapFragment", "Failed to create bitmap from vector drawable.")
            return
        }

        resizedMarkerIcon = OverlayImage.fromBitmap(resizedBitmap)
    }

    private fun getBitmapFromVectorDrawable(
        context: Context,
        drawableId: Int,
        width: Int,
        height: Int,
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId) ?: return null
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::naverMap.isInitialized) {
            mapViewModel.saveCameraPosition(naverMap.cameraPosition)
        }
        mapView.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        private const val MARKER_SIZE = 100
    }
}
