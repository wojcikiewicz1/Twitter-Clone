//---------------------------------------------Active tab color---------------------------------------------------------
document.addEventListener("DOMContentLoaded", function() {
    const path = window.location.pathname;

    const followersTab = document.querySelector("#for-you");
    const followingTab = document.querySelector("#following");

    if (path.includes("/followers")) {
        followersTab.classList.add("activeTab");
    } else if (path.includes("/following")) {
        followingTab.classList.add("activeTab");
    }
});
//--------------------------------------Textareas & inputs resizing and lengths------------------------------------------
document.addEventListener('DOMContentLoaded', function() {
    const textareas = document.querySelectorAll('.whatishappening, .postyourreply, .form-control');

    textareas.forEach(textarea => {
        textarea.addEventListener('input', autoResize, false);
    });

    function autoResize() {
        console.log("Auto resizing...");
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    }

    const inputs = document.querySelectorAll('input.form-control');

    inputs.forEach(input => {
        input.addEventListener('input', autoResize, false)
    });
});

function limitInputLength(element, maxLength) {
    if (element.value.length > maxLength) {
        element.value = element.value.slice(0, maxLength);
    }
}

