 document.addEventListener('DOMContentLoaded', function () {
  const buttons = document.querySelectorAll('.btn-transparent');

  // Mapping for event statuses
  const statusMapping = {
    "For Approval": "0",
    "Approved": "1",
    "Declined": "2"
  };

  buttons.forEach(button => {
    button.addEventListener('click', function () {
      const row = this.closest('tr');

      // Extract data from data attributes or inner text
      const requestId = row.querySelector('[data-type="request-id"]').textContent.trim();
      const name = row.querySelector('[data-type="name"]').textContent.trim();
      const form = row.querySelector('[data-type="form"]').textContent.trim();
      const formType = row.querySelector('[data-type="form-type"]').textContent.trim();
      const desc = row.querySelector('[data-type="desc"]').textContent.trim();
      const price = row.querySelector('[data-type="price"]').textContent.trim();
      const requestDate = row.querySelector('[data-type="request-date"]').textContent.trim();

      // Extract the status text from the span inside the td
      const statusCell = row.querySelector('[data-type="status"]');
      const statusText = statusCell ? statusCell.querySelector('span').textContent.trim() : "";

      // Populate modal fields
      document.querySelector('#modal-request-id').value = requestId;
      document.querySelector('#modal-name').value = name;
      document.querySelector('#modal-form').value = form;
      document.querySelector('#modal-form-type').value = formType;
      document.querySelector('#modal-desc').value = desc;
      document.querySelector('#modal-price').value = price;
      document.querySelector('#modal-request-date').value = requestDate;

      // Match the event status text to its value and set the dropdown
      const dropdown = document.querySelector('#modal-status');
      dropdown.value = statusMapping[statusText] || ""; // Default to empty if no match found
    });
  });
});