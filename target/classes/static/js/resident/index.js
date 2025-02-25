/*document.addEventListener('DOMContentLoaded', () => {
    // Initialize the map centered on Barangay Dioquino Zobel, Quezon City, Philippines
    const map = L.map('map').setView([14.646039, 121.049322], 16); // Coordinates for Barangay Dioquino Zobel

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
        maxZoom: 19,
    }).addTo(map);

    // Add a marker at the barangay location
    L.marker([14.646039, 121.049322]).addTo(map)
        .bindPopup('Barangay Dioquino Zobel, Quezon City')
        .openPopup();
});*/

document.addEventListener('DOMContentLoaded', () => {
    // Coordinates for No. 18 Gen. Lizardo Street, 20th Avenue, Project 4, Quezon City
    const newCoordinates = [14.621697, 121.067811]; // Approximate latitude & longitude

    // Initialize the map centered on the new location
    const map = L.map('map').setView(newCoordinates, 16);

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
        maxZoom: 19,
    }).addTo(map);

    // Add a marker at the new location
    L.marker(newCoordinates).addTo(map)
        .bindPopup('No. 18 Gen. Lizardo Street, 20th Avenue, Project 4, Quezon City')
        .openPopup();
});



