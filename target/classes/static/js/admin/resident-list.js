document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.btn-transparent');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const row = this.closest('tr');

            // Define a mapping of data-type attributes to modal field IDs
            const fieldMappings = {
                'id': '.modal-body img',
                'first-name': 'modal-first-name',
                'middle-name': 'modal-middle-name',
                'last-name': 'modal-last-name',
                'gender': 'modal-gender',
                'birth-date': 'modal-birth-date',
                'birth-place': 'modal-birth-place',
                'nationality': 'modal-nationality',
                'occupation': 'modal-occupation',
                'status': 'modal-status',
                'address': 'modal-address',
                'contact-number': 'modal-contact-number',
                'email': 'modal-email',
                'valid-id': 'modal-valid-id',
                'valid-id-number': 'modal-valid-id-number',
                'medical-history': 'modal-medical-history',
                'certification-purpose': 'modal-certification-purpose',
                'affiliation': 'modal-affiliation',
                'resident-id': 'modal-resident-id'
            };

            // Loop through the mappings and populate modal fields
            for (const [dataType, modalField] of Object.entries(fieldMappings)) {
                const cell = row.querySelector(`[data-type="${dataType}"]`);

                if (cell) {
                    if (dataType === 'id') {
                        const modalImage = document.querySelector(modalField);
                        modalImage.src = cell.querySelector('img').getAttribute('src');
                    } else if (dataType === 'medical-history' || dataType === 'certification-purpose') {
                        const listField = document.getElementById(modalField);
                        listField.innerHTML = ''; // Clear previous contents

                        const items = cell.querySelectorAll('li');
                        items.forEach(item => {
                            const listItem = document.createElement('li');
                            listItem.textContent = item.textContent.trim();
                            listField.appendChild(listItem);
                        });

                        // Handle hidden input fields for medicalHistory and certificationPurpose
                        const hiddenField = document.getElementById(`hidden-${dataType}`);
                        if (hiddenField) {
                            const values = [];
                            items.forEach(item => {
                                values.push(item.textContent.trim());
                            });
                            hiddenField.value = values.join(','); // Join items with a comma
                        }

                    } else {
                        const modalInput = document.getElementById(modalField);
                        if (modalInput) {
                            modalInput.value = cell.textContent.trim();
                        }
                    }
                }
            }
        });
    });


   const zoomContainer = document.getElementById("zoom-container");
    const zoomImage = document.getElementById("zoom-image");
    const zoomRange = document.getElementById("customRange1");
    let scale = 1;
    let isDragging = false;
    let startX, startY, currentX = 0, currentY = 0;

    // Zoom with range slider
    zoomRange.addEventListener("input", function () {
        scale = parseFloat(this.value); // Get the value from the slider
        zoomImage.style.transform = `scale(${scale}) translate(${currentX}px, ${currentY}px)`;
    });

    // Zoom with scroll
    zoomContainer.addEventListener("wheel", function (e) {
        e.preventDefault();
        const zoomFactor = 0.1;
        if (e.deltaY < 0) {
            scale = Math.min(scale + zoomFactor, parseFloat(zoomRange.max)); // Zoom in
        } else if (e.deltaY > 0) {
            scale = Math.max(scale - zoomFactor, parseFloat(zoomRange.min)); // Zoom out
        }
        zoomRange.value = scale.toFixed(1); // Sync slider value
        zoomImage.style.transform = `scale(${scale}) translate(${currentX}px, ${currentY}px)`;
    });

    // Drag functionality
    zoomImage.addEventListener("mousedown", function (e) {
        isDragging = true;
        startX = e.clientX - currentX;
        startY = e.clientY - currentY;
        zoomContainer.style.cursor = "grabbing";
    });

    document.addEventListener("mousemove", function (e) {
        if (!isDragging) return;
        currentX = e.clientX - startX;
        currentY = e.clientY - startY;
        zoomImage.style.transform = `scale(${scale}) translate(${currentX}px, ${currentY}px)`;
    });

    document.addEventListener("mouseup", function () {
        isDragging = false;
        zoomContainer.style.cursor = "default";
    });

    const statusSelect = document.getElementById('modal-status');
        const declineReasonContainer = document.getElementById('decline-reason-container');
        const declineReasonTextarea = document.getElementById('decline-reason');
        const submitButton = document.getElementById('submit-button'); // Assuming you have a submit button
        const modal = document.getElementById('modal'); // Assuming your modal has an ID of 'modal'

        // Event listener for status change
        statusSelect.addEventListener('change', function () {
            const selectedStatus = statusSelect.value;

            if (selectedStatus === 'DECLINED') {
                // Show textarea for decline reason
                declineReasonContainer.style.display = 'block';
                submitButton.disabled = !declineReasonTextarea.value.trim(); // Disable submit if no reason is provided
            } else {
                // Hide textarea for decline reason
                declineReasonContainer.style.display = 'none';
                submitButton.disabled = false; // Enable submit
            }
        });

        // Ensure submit button is disabled if textarea is empty on input
        declineReasonTextarea.addEventListener('input', function () {
            submitButton.disabled = !declineReasonTextarea.value.trim();
        });

        // Prevent modal close if status is "DECLINED" and reason is empty
        modal.addEventListener('hide.bs.modal', function (event) {
            const selectedStatus = statusSelect.value;

            if (selectedStatus === 'DECLINED' && !declineReasonTextarea.value.trim()) {
                // Prevent modal from closing
                event.preventDefault();

                // Optionally, show an alert or message to inform the user to provide a reason
                alert("Please provide a reason for declining the application before closing the modal.");
            }
        });
});

