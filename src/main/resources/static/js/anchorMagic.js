// Add event listeners to anchor tags with class "anchorMagic"
document.querySelectorAll('.anchorMagic').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault(); // Prevent the default behavior of the anchor tag

        // Get the target task ID from the href attribute of the anchor
        const targetTaskId = anchor.getAttribute('href').replace('#task-', '');

        // Scroll to the target task in the second section
        const targetElement = document.getElementById('task-' + targetTaskId);
        if (targetElement) {
            targetElement.scrollIntoView({ behavior: 'smooth' }); // You can use 'auto' for instant scrolling
        }

        // Open the accordion for the target task by targeting the button based on data-bs-target
        document.querySelectorAll('.accordion-button').forEach(button => {
            const targetAttribute = button.getAttribute('data-bs-target');
            if (targetAttribute === '#collapse' + targetTaskId) {
                button.click();
            }
        });
    });
});