function initMap(latitude, longitude) {
    var location = {lat: latitude, lng: longitude};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: location
    });
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });

    // Load nearest locations
    fetchNearestLocations(latitude, longitude);
}

function fetchNearestLocations(latitude, longitude) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/QrMapLocations/nearest-locations?lat=' + latitude + '&lon=' + longitude, true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById('nearest-locations').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}
