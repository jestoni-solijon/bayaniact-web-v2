document.getElementById('residentForm').addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent default form submission

    const formData = new FormData(this);

    // Submit resident form via AJAX
    fetch(this.action, {
        method: this.method,
        body: formData
    })
    .then(response => {
       if (!response.ok) throw new Error('Resident save failed');
            return response.json(); // Assuming backend returns resident data in JSON format
        })
        .then(data => {
            // Automatically submit the file form after resident is saved
            const fileForm = document.getElementById('fileForm');
            const fileData = new FormData(fileForm);
            fileData.append('residentId', data.uuid); // Include resident UUID

            return fetch(fileForm.action, {
                method: 'POST',
                body: fileData
            });
        })
        .then(response => {
            if (!response.ok) throw new Error('File upload failed');
            alert('Resident and file saved successfully!');
        })
        .catch(error => {
            console.error(error);
            alert('An error occurred');
        });
    });