document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('confirm-submit').addEventListener('click', function () {
        const form = document.querySelector('#incident-form');
        form.submit();
    });
});

document.addEventListener("DOMContentLoaded", function () {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});