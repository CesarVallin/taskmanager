/// Function to show the delete confirmation modal
function showDeleteConfirmationModal(form) {
    let modal = document.querySelector('#deleteConfirmationModal');
    let taskTitleSpan = document.querySelector('#taskTitle');


    // Extract the task title and ID from the form's data attributes
    let taskTitle = form.getAttribute('data-task-title');
    let taskId = form.getAttribute('data-task-id');

    // Set the task title in the modal
    taskTitleSpan.innerText = taskTitle;

    modal.classList.add('show');
    modal.style.display = 'block';

    // Attach a click event listener to the Cancel button
    const cancelButton = document.querySelector('#deleteConfirmationModal button.btn-secondary');
    cancelButton.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    // Attach a click event listener to the Delete button
    const confirmDeleteButton = document.querySelector('#confirmDelete');
    confirmDeleteButton.addEventListener('click', function () {
        // Submit the form associated with the clicked button
        form.submit();
    });
}

//////////////////////////////////////////////////////////////////////////////////

/// Function to show the complete confirmation modal
function showCompleteConfirmationModal(form) {
    let modal = document.querySelector('#completeConfirmationModal');
    let taskTitleSpan = document.querySelector('#taskTitleC');


    // Extract the task title and ID from the form's data attributes
    let taskTitle = form.getAttribute('data-task-title');
    let taskId = form.getAttribute('data-task-id');

    // Set the task title in the modal
    taskTitleSpan.innerText = taskTitle;

    modal.classList.add('show');
    modal.style.display = 'block';

    // Attach a click event listener to the Cancel button
    const cancelButton = document.querySelector('#completeConfirmationModal button.btn-secondary');
    cancelButton.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    // Attach a click event listener to the Delete button
    const confirmDeleteButton = document.querySelector('#confirmComplete');
    confirmDeleteButton.addEventListener('click', function () {
        // Submit the form associated with the clicked button
        form.submit();
    });
}


//////////////////////////////////////////////////////////////////////////////////

/// Function to show the incomplete confirmation modal
function showIncompleteConfirmationModal(form) {
    let modal = document.querySelector('#incompleteConfirmationModal');
    let taskTitleSpan = document.querySelector('#taskTitleI');


    // Extract the task title and ID from the form's data attributes
    let taskTitle = form.getAttribute('data-task-title');
    let taskId = form.getAttribute('data-task-id');

    // Set the task title in the modal
    taskTitleSpan.innerText = taskTitle;

    modal.classList.add('show');
    modal.style.display = 'block';

    // Attach a click event listener to the Cancel button
    const cancelButton = document.querySelector('#incompleteConfirmationModal button.btn-secondary');
    cancelButton.addEventListener('click', function () {
        modal.style.display = 'none';
    });

    // Attach a click event listener to the Delete button
    const confirmDeleteButton = document.querySelector('#confirmIncomplete');
    confirmDeleteButton.addEventListener('click', function () {
        // Submit the form associated with the clicked button
        form.submit();
    });
}