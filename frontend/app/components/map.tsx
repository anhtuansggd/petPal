import React, { useRef, useEffect, useState } from 'react';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';

mapboxgl.accessToken =
  'pk.eyJ1IjoidHN1ZHkiLCJhIjoiY2x0eWRpMHI1MGd2ejJpbzBla3JxNndmbiJ9.ICPd6DnmHNqmGN0P5-Sbmw';

const Map = () => {
  const mapContainerRef = useRef(null);
  const mapRef = useRef(null);
  const [lng, setLng] = useState(106.7);
  const [lat, setLat] = useState(10.8);
  const [zoom, setZoom] = useState(14);

  const generateRandomCoordinates = (center, radius = 0.01) => {
    const y0 = center.latitude;
    const x0 = center.longitude;
    const rd = radius / 111300; // about 111300 meters in one degree

    const u = Math.random();
    const v = Math.random();
    const w = rd * Math.sqrt(u);
    const t = 2 * Math.PI * v;
    const x = w * Math.cos(t);
    const y = w * Math.sin(t);

    return { 'latitude': y + y0, 'longitude': x + x0 };
  };

  const locateUser = () => {
    navigator.geolocation.getCurrentPosition(position => {
      const center = [position.coords.longitude, position.coords.latitude];
      if(mapRef.current){
        mapRef.current.flyTo({
            center: center,
            zoom: 14
        })
      }

      const markers = document.getElementsByClassName('mapboxgl-marker')
      while(markers[0]){
        markers[0].parentNode?.removeChild(markers[0]);
      }

      new mapboxgl.Marker({color: 'red'}).setLngLat(center).addTo(mapRef.current);


      // Simulate adding nearby caregivers
      for (let i = 0; i < 5; i++) { // Add 5 random caregivers
        const nearbyLocation = generateRandomCoordinates(position.coords, 1000); // 1000 meters radius
        new mapboxgl.Marker()
            .setLngLat([nearbyLocation.longitude, nearbyLocation.latitude])
            .addTo(mapRef.current);
      }
    }, (err) => {
      console.log("Geolocation error: ", err);
    }, {
      enableHighAccuracy: true,
      timeout: 5000,
      maximumAge: 0
    });
  };



  // Initialize map when component mounts
  useEffect(() => {

    navigator.geolocation.getCurrentPosition(function(position) {
        setLng(position.coords.longitude);
        setLat(position.coords.latitude);
    });

    const map = new mapboxgl.Map({
      container: mapContainerRef.current,
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [lng, lat],
      zoom: zoom
    });

    mapRef.current = map;

    // Add navigation control (the +/- zoom buttons)
    map.addControl(new mapboxgl.NavigationControl(), 'top-right');

    map.on('move', () => {
      setLng(map.getCenter().lng.toFixed(4));
      setLat(map.getCenter().lat.toFixed(4));
      setZoom(map.getZoom().toFixed(2));
    });

    // Clean up on unmount
    return () => map.remove();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps





  return (
      <div>
        <div className='sidebarStyle'>
          <div>
            Longitude: {lng} | Latitude: {lat} | Zoom: {zoom} | <button onClick={locateUser}>Locate Me</button>
          </div>
        </div>

        <div className='map-container' ref={mapContainerRef} style={{ width: '100%', height: '400px' }}/>
      </div>
  );
};

export default Map;