document.addEventListener('DOMContentLoaded', () => {
  const passwordInput = document.getElementById('password');
  const toggleButton = document.getElementById('passwordToggle');
  const toggleIcon = document.getElementById('toggleIcon');

  const submitBtn = document.getElementById("signUpButton");

  toggleButton.addEventListener('click', () => {
    // Toggle the type attribute
    const isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';

    // Toggle the icon
    toggleIcon.classList.toggle('bi-eye', isPassword);
    toggleIcon.classList.toggle('bi-eye-slash', !isPassword);

    // Update ARIA label for accessibility
    toggleButton.setAttribute('aria-label', isPassword ? 'Hide password' : 'Show password');
  });

  const passwordInput2 = document.getElementById('confirmPassword');
  const toggleButton2 = document.getElementById('passwordToggle2');
  const toggleIcon2 = document.getElementById('toggleIcon2');

  toggleButton2.addEventListener('click', () => {
      // Toggle the type attribute
      const isPassword = passwordInput.type === 'password';
      passwordInput2.type = isPassword ? 'text' : 'password';

      // Toggle the icon
      toggleIcon2.classList.toggle('bi-eye', isPassword);
      toggleIcon2.classList.toggle('bi-eye-slash', !isPassword);

      // Update ARIA label for accessibility
      toggleButton2.setAttribute('aria-label', isPassword ? 'Hide password' : 'Show password');
    });
});

// Enable the main checkbox and submit button when modal agreement checkbox is ticked
function toggleTermsCheckbox() {
    const modalCheckbox = document.getElementById('acceptTermsInModal');
    const mainCheckbox = document.getElementById('terms');
    const signUpButton = document.getElementById('signUpButton');

    mainCheckbox.checked = modalCheckbox.checked;
    mainCheckbox.disabled = !modalCheckbox.checked;
    signUpButton.disabled = !modalCheckbox.checked;
}




document.addEventListener("DOMContentLoaded", function () {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

