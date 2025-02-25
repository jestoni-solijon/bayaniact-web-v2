document.addEventListener('DOMContentLoaded', function () {
    // Select all buttons in the table
    const buttons = document.querySelectorAll('.btn-transparent');

    buttons.forEach(button => {
      button.addEventListener('click', function () {
        // Get the parent row of the clicked button
        const row = this.closest('tr');

        // Extract data from the row's cells
        const cells = row.querySelectorAll('td, th'); // Select all cells in the row
        const rowData = Array.from(cells).map(cell => cell.textContent.trim());

        // Log the row data or use it for further processing
        console.log('Selected Row Data:', rowData);

        // Example: Populate modal fields
        document.querySelector('#modal-request-id').value = rowData[1];
        document.querySelector('#modal-resident-name').value = rowData[3]; // Requester Name
        document.querySelector('#modal-form-name').value = rowData[4]; // Form
        document.querySelector('#modal-form-type').value = rowData[5]; // Form Type
        document.querySelector('#modal-form-desc').value = rowData[6]; // Form Description
        document.querySelector('#modal-form-price').value = rowData[7]; // Form Price

        // Update the dropdown value dynamically
        const dropdown = document.querySelector('#modal-request-status');
        const statusValue = rowData[8]; // Extract the status from the table row
        if (['for approval', 'cancel', 'approve'].includes(statusValue.toLowerCase())) {
          dropdown.value = statusValue.toLowerCase(); // Match the value of the dropdown
        } else {
          dropdown.value = ''; // Reset to "Select status" if no match
        }
      });
    });
  });