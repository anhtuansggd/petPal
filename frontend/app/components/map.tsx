import React, { useRef, useEffect, useState } from "react";
import mapboxgl from "mapbox-gl";
import "mapbox-gl/dist/mapbox-gl.css";
import MapboxGeocoder from "@mapbox/mapbox-gl-geocoder";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import { Button, Typography } from "@material-tailwind/react";
import Link from "next/link";
mapboxgl.accessToken =
  "pk.eyJ1IjoidHN1ZHkiLCJhIjoiY2x0eWRpMHI1MGd2ejJpbzBla3JxNndmbiJ9.ICPd6DnmHNqmGN0P5-Sbmw";

const Map = () => {
  const mapContainerRef = useRef(null);
  const mapRef = useRef(null);
  const [lng, setLng] = useState(106.7);
  const [lat, setLat] = useState(10.8);
  const [zoom, setZoom] = useState(14);
  const [popoverInfo, setPopoverInfo] = useState({
    visible: false,
    x: 0,
    y: 0,
    content: "",
  });

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

    return { latitude: y + y0, longitude: x + x0 };
  };

  const locateUser = () => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const center = [position.coords.longitude, position.coords.latitude];
        if (mapRef.current) {
          mapRef.current.flyTo({
            center: center,
            zoom: 14,
          });
        }

        const markers = document.getElementsByClassName("mapboxgl-marker");
        while (markers[0]) {
          markers[0].parentNode?.removeChild(markers[0]);
        }

        const userMarker = new mapboxgl.Marker({ color: "red" })
          .setLngLat(center)
          .addTo(mapRef.current);

        userMarker.getElement().addEventListener("click", (e) => {
          const rect = e.target.getBoundingClientRect();
          setPopoverInfo({
            visible: true,
            x: rect.left,
            y: rect.top,
            content: "You are here",
            lng: center[0],
            lat: center[1],
          });
        });

        // Simulate adding nearby caregivers
        for (let i = 0; i < 1; i++) {
          const nearbyLocation = generateRandomCoordinates(
            position.coords,
            1000
          ); // 1000 meters radius
          const caregiverMarker = new mapboxgl.Marker()
            .setLngLat([nearbyLocation.longitude, nearbyLocation.latitude])
            .addTo(mapRef.current);

          caregiverMarker.getElement().addEventListener("click", (e) => {
            const rect = e.target.getBoundingClientRect();
            const imagePath = `/shannon.jpg`;
            console.log(`Image path: ${imagePath}`);
            setPopoverInfo({
              visible: true,
              x: rect.left,
              y: rect.top,

              // content: `<img src="${imagePath}" alt="Caregiver style="width:150px; height: auto;" /><br/> Caregiver ${
              //   i + 1
              // }`,

              content: `
                <div style="border: 1px solid #ccc; padding: 10px; border-radius: 8px;">
                  <img src="${imagePath}" alt="Caregiver ${
                i + 1
              }" style="width:150px; height: auto; display: block; margin-bottom: 10px;" />
                  <p className = "text-2xl">Shannon Payne</p>
                  <div style="color: #f5d142; font-size: 14px;">★★★★☆ (123 reviews)</div>
                </div>
                       `,
            });
          });
        }
      },
      (err) => {
        console.log("Geolocation error: ", err);
      },
      {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,
      }
    );
  };

  // Initialize map when component mounts
  useEffect(() => {
    navigator.geolocation.getCurrentPosition(function (position) {
      setLng(position.coords.longitude);
      setLat(position.coords.latitude);
    });

    const map = new mapboxgl.Map({
      container: mapContainerRef.current,
      style: "mapbox://styles/mapbox/streets-v11",
      center: [lng, lat],
      zoom: zoom,
    });

    mapRef.current = map;

    // Add navigation control (the +/- zoom buttons)
    map.addControl(new mapboxgl.NavigationControl(), "top-right");

    map.on("move", () => {
      setLng(map.getCenter().lng.toFixed(4));
      setLat(map.getCenter().lat.toFixed(4));
      setZoom(map.getZoom().toFixed(2));
    });

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

    return () => map.remove();
  }, []);

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
          <Typography
            className="mb-2"
            dangerouslySetInnerHTML={{ __html: popoverInfo.content }}
          />
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
