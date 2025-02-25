document.addEventListener('DOMContentLoaded', function () {
  const buttons = document.querySelectorAll('.btn-transparent');

// Mapping for event statuses
  const statusMapping = {
    "Upcoming Event": "0",
    "Ongoing Event": "1",
    "Completed Event": "2",
    "Cancelled": "3"
  };

  buttons.forEach(button => {
    button.addEventListener('click', function () {
      const row = this.closest('tr');

      // Extract data from data attributes
      const eventId = row.querySelector('[data-type="event-id"]').textContent.trim();
      const eventTitle = row.querySelector('[data-type="event-title"]').textContent.trim();
      const eventType = row.querySelector('[data-type="event-type"]').textContent.trim();
      const eventDesc = row.querySelector('[data-type="event-desc"]').textContent.trim();
      const eventLoc = row.querySelector('[data-type="event-loc"]').textContent.trim();
      const eventStartDate = row.querySelector('[data-type="event-start-date"]').textContent.trim();
      const eventEndDate = row.querySelector('[data-type="event-end-date"]').textContent.trim();
      const eventStatus = row.querySelector('[data-type="event-status"]').textContent.trim();

      console.log('Selected Row Data:', { eventTitle, eventType, eventDesc, eventLoc, eventStartDate, eventEndDate, eventStatus });

      // Populate modal fields
      document.querySelector('#modal-event-id').value = eventId;
      document.querySelector('#modal-event-title').value = eventTitle;
      document.querySelector('#modal-event-type').value = eventType;
      document.querySelector('#modal-event-desc').value = eventDesc;
      document.querySelector('#modal-event-loc').value = eventLoc;
      document.querySelector('#modal-event-start-date').value = eventStartDate;
      document.querySelector('#modal-event-end-date').value = eventEndDate;

      // Match the event status text to its value and set the dropdown
      const dropdown = document.querySelector('#modal-event-status');
      //dropdown.value = statusMapping[eventStatusText] || ""; // Default to empty if no match found
    });
  });
});