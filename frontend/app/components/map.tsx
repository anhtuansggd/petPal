"use client"
import React, { useRef, useEffect, useState } from "react";
import mapboxgl from "mapbox-gl";
import "mapbox-gl/dist/mapbox-gl.css";
import MapboxGeocoder from "@mapbox/mapbox-gl-geocoder";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import { Button, Typography } from "@material-tailwind/react";
import Link from "next/link";
mapboxgl.accessToken =
  "pk.eyJ1IjoidHN1ZHkiLCJhIjoiY2x0eWRpMHI1MGd2ejJpbzBla3JxNndmbiJ9.ICPd6DnmHNqmGN0P5-Sbmw";

interface Coordinates {
  latitude: number;
  longitude: number;
}

const generateRandomCoordinates = (center: Coordinates, radius = 0.01) => {
  const y0 = center.latitude;
  const x0 = center.longitude;
  const rd = radius / 111300; // about 111300 meters in one degree

  const u = Math.random();
  const v = Math.random();
  const w = rd * Math.sqrt(u);
  const t = 2 * Math.PI * v;
  const x = w * Math.cos(t);
  const y = w * Math.sin(t);

  return { latitude: y + y0, longitude: x + x0 };
};

interface PopoverInfo {
  visible: boolean;
  x: number;
  y: number;
  content: string;
  lng?: number; // Optional longitude
  lat?: number; // Optional latitude
}

const Map = () => {
  const mapContainerRef = useRef(null);
  const mapRef = useRef< mapboxgl.Map |null>(null);
  const [lng, setLng] = useState(106.7);
  const [lat, setLat] = useState(10.8);
  const [zoom, setZoom] = useState(14);
  const [popoverInfo, setPopoverInfo] = useState<PopoverInfo>({
    visible: false,
    x: 0,
    y: 0,
    content: "",
  });
  const [userMarker, setUserMarker] = useState<mapboxgl.Marker | null>(null);
  const [caregiverMarkers, setCaregiverMarkers] = useState<mapboxgl.Marker[]>([]);

  useEffect(() => {
    if (mapContainerRef.current && !mapRef.current) {
      const map = new mapboxgl.Map({
        container: mapContainerRef.current,
        style: "mapbox://styles/mapbox/streets-v11",
        center: [lng, lat],
        zoom: zoom,
      });

      mapRef.current = map;

      // Add navigation control (the +/- zoom buttons)
      map.addControl(new mapboxgl.NavigationControl(), "top-right");

      const geocoder = new MapboxGeocoder({
        accessToken: mapboxgl.accessToken,
        mapboxgl: mapboxgl,
      });

      map.addControl(geocoder);

      geocoder.on("result", (e) => {
        const { center, place_name } = e.result;
        setLng(center[0]);
        setLat(center[1]);
        setZoom(14);
      });

      // Initial marker placement
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const center: [number, number] = [position.coords.longitude, position.coords.latitude];
          setLng(center[0]);
          setLat(center[1]);
          setZoom(14);
          
          addMarkers(center);
        },
        (err) => console.log("Geolocation error: ", err),
        { enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
      );
    }
  }, []);

  const addMarkers = (center: [number, number]) => {
    if (!mapRef.current) return;

    // Remove existing markers
    if (userMarker) userMarker.remove();
    caregiverMarkers.forEach(marker => marker.remove());

    // Add user marker
    const newUserMarker = new mapboxgl.Marker({ color: "red" })
      .setLngLat(center)
      .addTo(mapRef.current);
    setUserMarker(newUserMarker);

    // Add caregiver markers
    const newCaregiverMarkers = [];
    for (let i = 0; i < 5; i++) {
      const nearbyLocation = generateRandomCoordinates({ latitude: center[1], longitude: center[0] }, 1000);
      const caregiverMarker = new mapboxgl.Marker()
        .setLngLat([nearbyLocation.longitude, nearbyLocation.latitude])
        .addTo(mapRef.current);
      newCaregiverMarkers.push(caregiverMarker);
    }
    setCaregiverMarkers(newCaregiverMarkers);

    // Add click listeners to markers
    newUserMarker.getElement().addEventListener("click", (e) => {
      if (!e.target) return; // Ensure e.target is not null
      const rect = (e.target as HTMLElement).getBoundingClientRect();
      setPopoverInfo({
        visible: true,
        x: rect.left,
        y: rect.top,
        content: "You are here",
        lng: center[0],
        lat: center[1],
      });
    });

    newCaregiverMarkers.forEach((marker, index) => {
      marker.getElement().addEventListener("click", (e) => {
        const rect = (e.target as HTMLElement).getBoundingClientRect();
        const imagePath = `/shannon.jpg`;
        console.log(`Image path: ${imagePath}`);
        setPopoverInfo({
          visible: true,
          x: rect.left,
          y: rect.top,

          content: `
            <div style="border: 1px solid #ccc; padding: 10px; border-radius: 8px;">
              <img src="${imagePath}" alt="Caregiver ${
            index + 1
          }" style="width:150px; height: auto; display: block; margin-bottom: 10px;" />
              <p className = "text-2xl">Shannon Payne</p>
              <div style="color: #f5d142; font-size: 14px;">★★★★☆ (123 reviews)</div>
            </div>
                         `,
        });
      });
    });
  };

  // Update map when lng, lat, or zoom changes
  useEffect(() => {
    if (mapRef.current) {
      mapRef.current.setCenter([lng, lat]);
      mapRef.current.setZoom(zoom);
    }
  }, [lng, lat, zoom]);

  const locateUser = () => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const center: [number, number] = [position.coords.longitude, position.coords.latitude];
        setLng(center[0]);
        setLat(center[1]);
        setZoom(14);
        addMarkers(center);
      },
      (err) => console.log("Geolocation error: ", err),
      { enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
    );
  };

  return (
    <div>
      <div className="sidebarStyle">
        <div>
          Longitude: {lng} | Latitude: {lat} | Zoom: {zoom} |{" "}
          <button onClick={locateUser}>Locate Me</button>
        </div>
      </div>
      <div id="geocoder" className="geocoder"></div>
      <div
        className="map-container"
        ref={mapContainerRef}
        style={{ width: "100%", height: "500px" }}
      />
      {popoverInfo.visible && (
        <div
          style={{
            position: "absolute",
            left: popoverInfo.x,
            top: popoverInfo.y,
            backgroundColor: "white",
            padding: "10px",
            borderRadius: "10px",

            border: "1px solid black",
          }}
        >
           <Typography className="mb-2">
            <div dangerouslySetInnerHTML={{ __html: popoverInfo.content }} />
          </Typography>
          <Link href="/sitter-info">
            <Button
              className="bg-primary-light-green hover:bg-primary-dark-green duration-300"
              onClick={() => setPopoverInfo({ ...popoverInfo, visible: false })}
            >
              Contact
            </Button>
          </Link>
        </div>
      )}
    </div>
  );
};

export default Map;
