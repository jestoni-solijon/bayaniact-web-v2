// Sidebar toggle logic
// Sidebar toggle logic
document.getElementById('toggle-sidebar').addEventListener('click', function () {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');

    // Toggle the 'active' class to show or hide the sidebar
    sidebar.classList.toggle('active');
    content.classList.toggle('shifted');

    // Save the state of the sidebar in localStorage
    if (sidebar.classList.contains('active')) {
        localStorage.setItem('sidebarState', 'active');
    } else {
        localStorage.setItem('sidebarState', 'inactive');
    }
});

// On page load, check localStorage for sidebar state and apply it
window.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');

    // Retrieve the saved sidebar state from localStorage
    const savedState = localStorage.getItem('sidebarState');

    if (savedState === 'active') {
        sidebar.classList.add('active');
        content.classList.add('shifted');
    } else {
        sidebar.classList.remove('active');
        content.classList.remove('shifted');
    }
});

// On page load, check localStorage for sidebar state and apply it
window.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');

    // Retrieve the saved sidebar state from localStorage
    const savedState = localStorage.getItem('sidebarState');

    if (savedState === 'active') {
        sidebar.classList.add('active');
        content.classList.add('shifted');
    } else {
        sidebar.classList.remove('active');
        content.classList.remove('shifted');
    }
});

// Collapse/Expand menu logic (for icons)
document.querySelectorAll('.nav-link[data-bs-toggle="collapse"]').forEach(function (menuItem) {
    menuItem.addEventListener('click', function (event) {
        const icon = event.target.querySelector('.icon');
        const collapseTarget = document.querySelector(event.target.getAttribute('href'));

        // Toggle icon and collapse state
        if (collapseTarget.classList.contains('show')) {
            icon.textContent = '+'; // Collapse
        } else {
            icon.textContent = '-'; // Expand
        }
    });
});


  // On page load, check localStorage for sidebar state and apply it
  window.addEventListener('DOMContentLoaded', function () {
      const sidebar = document.getElementById('sidebar');
      const content = document.getElementById('content');

      // Retrieve the saved sidebar state from localStorage
      const savedState = localStorage.getItem('sidebarState');

      if (savedState === 'active') {
          sidebar.classList.add('active');
          content.classList.add('shifted');
      } else {
          sidebar.classList.remove('active');
          content.classList.remove('shifted');
      }
  });

  // Collapse/Expand menu logic (for icons)
  document.querySelectorAll('.nav-link[data-bs-toggle="collapse"]').forEach(function (menuItem) {
      menuItem.addEventListener('click', function (event) {
          const icon = event.target.querySelector('.icon');
          const collapseTarget = document.querySelector(event.target.getAttribute('href'));

          // Toggle icon and collapse state
          if (collapseTarget.classList.contains('show')) {
              icon.textContent = '+'; // Collapse
          } else {
              icon.textContent = '-'; // Expand
          }
      });
  });


    // Toggle icon for Residents menu
    const residentsMenu = document.getElementById('residentsMenu');
    const iconResidents = document.getElementById('icon-residents');
    residentsMenu.addEventListener('show.bs.collapse', () => {
        iconResidents.textContent = '-';
    });
    residentsMenu.addEventListener('hide.bs.collapse', () => {
        iconResidents.textContent = '+';
    });

    // Toggle icon for Officials menu
    const officialMenu = document.getElementById('official-menu');
    const iconOfficial = document.getElementById('icon-official');
    officialMenu.addEventListener('show.bs.collapse', () => {
        iconOfficial.textContent = '-';
    });
    officialMenu.addEventListener('hide.bs.collapse', () => {
        iconOfficial.textContent = '+';
    });

    // Toggle icon for Sales menu
    const salesMenu = document.getElementById('salesMenu');
    const iconSales = document.getElementById('icon-sales');
    salesMenu.addEventListener('show.bs.collapse', () => {
        iconSales.textContent = '-';
    });
    salesMenu.addEventListener('hide.bs.collapse', () => {
        iconSales.textContent = '+';
    });

