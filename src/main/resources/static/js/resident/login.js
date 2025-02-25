document.addEventListener('DOMContentLoaded', () => {
  const passwordInput = document.getElementById('password');
  const toggleButton = document.getElementById('passwordToggle');
  const toggleIcon = document.getElementById('toggleIcon');

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
});