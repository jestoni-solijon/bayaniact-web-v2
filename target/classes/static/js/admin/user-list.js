document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.btn-transparent');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const row = this.closest('tr');

            // Define a mapping of data-type attributes to modal field IDs
            const fieldMappings = {
                'user-uuid': 'modal-user-uuid',
                'username': 'modal-username',
                'firstname': 'modal-firstname',
                'middlename': 'modal-middlename',
                'lastname': 'modal-lastname',
                'email': 'modal-email',
                'phone-number': 'modal-phone-number'
            };

            // Define a mapping of roles in the table to checkbox IDs
            const roleMappings = {
                'ROLE_ADMIN': 'admin-role',
                'ROLE_OFFICIAL': 'official-role',
                'ROLE_RESIDENT': 'resident-role',
                'ROLE_FOR_APPROVAL_RESIDENT': 'for-approval-resident-role'
            };

            // Populate modal fields based on data-type attributes
            for (const [dataType, modalField] of Object.entries(fieldMappings)) {
                const cell = row.querySelector(`[data-type="${dataType}"]`);
                if (cell) {
                    const modalInput = document.getElementById(modalField);
                    if (modalInput) {
                        modalInput.value = cell.textContent.trim();
                    }
                }
            }

            // Handle roles separately
            const roleCell = row.querySelector('[data-type="role"]');
            if (roleCell) {
                const roles = Array.from(roleCell.querySelectorAll('.badge')).map(badge => badge.textContent.trim());

                // Uncheck all checkboxes first
                Object.values(roleMappings).forEach(roleId => {
                    const checkbox = document.getElementById(roleId);
                    if (checkbox) checkbox.checked = false;
                });

                // Check the corresponding checkboxes if the role exists
                roles.forEach(role => {
                    const checkboxId = roleMappings[role];
                    if (checkboxId) {
                        const checkbox = document.getElementById(checkboxId);
                        if (checkbox) checkbox.checked = true;
                    }
                });
            }
        });
    });
});
