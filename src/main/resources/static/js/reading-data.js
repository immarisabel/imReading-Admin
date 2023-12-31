  document.addEventListener("DOMContentLoaded", function () {
        var showFormBtn = document.getElementById('showFormBtn');
        var overlay = document.getElementById('overlay');

        if (showFormBtn && overlay) {
            showFormBtn.addEventListener('click', openForm);

            function openForm() {
                console.log("Opening form...");
                overlay.style.display = 'block';
            }

            function closeForm() {
                console.log("Closing form...");
                overlay.style.display = 'none';
            }
        } else {
            console.error("Element with ID 'showFormBtn' or 'overlay' not found.");
        }
    });