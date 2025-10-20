import folium

m = folium.Map(
    location=[35.72185811581757, 51.33469323443982],
    zoom_start=15,
    tiles="cartodb positron",
)

folium.Marker(
    location=[35.72185811581757, 51.33469323443982],
    popup="Tehran",
    tooltip="Spark Co.",
).add_to(m)

m.save("map.html")
