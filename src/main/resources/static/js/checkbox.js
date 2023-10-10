console.log(`hello from checkbox.js`);

const scheduleCheckbox = document.querySelector("#scheduleCheckbox");
const dateScheduledField = document.querySelector("#dateScheduledField");
const dateScheduleCal = document.querySelector("#dateScheduled");

scheduleCheckbox.addEventListener("change", function() {
    if (scheduleCheckbox.checked) {
        dateScheduledField.style.display = "block";
        dateScheduleCal.value = new Date().toISOString().substring(0, 10);
    } else {
        dateScheduledField.style.display = "none";
        dateScheduleCal.value = "1969-12-31";
    }
});