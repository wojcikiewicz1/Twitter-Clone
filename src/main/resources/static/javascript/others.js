document.addEventListener('DOMContentLoaded', function() {
    setActiveTabColor();
    autoResizeSetup();
    pinUnpinSetup();
});

function setActiveTabColor() {
    const path = window.location.pathname;
    const followersTab = document.querySelector("#for-you");
    const followingTab = document.querySelector("#following");

    if (path.includes("/followers")) {
        followersTab.classList.add("activeTab");
    } else if (path.includes("/following")) {
        followingTab.classList.add("activeTab");
    }
}

function autoResizeSetup() {
    const textareas = document.querySelectorAll('.whatishappening, .postyourreply, .form-control');

    textareas.forEach(textarea => {
        textarea.addEventListener('input', autoResize, false);
    });

    const inputs = document.querySelectorAll('input.form-control');
    inputs.forEach(input => {
        input.addEventListener('input', autoResize, false);
    });

    function autoResize() {
        console.log("Auto resizing...");
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    }
}

function pinUnpinSetup() {
    document.querySelectorAll('.post-pinoption, .comment-pinoption').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const isComment = this.classList.contains('comment-pinoption');
            const id = this.dataset.id;
            const isPinned = this.dataset.isPinned === 'true';
            const actionType = isPinned ? 'unpin' : 'pin';
            const entityType = isComment ? 'Comment' : 'Post';
            const actionUrl = `/api/${actionType}${entityType}/${id}`;

            handlePinClick(actionUrl, this);
        });
    });

    function handlePinClick(actionUrl, element) {
        fetch(actionUrl, {
            method: 'POST',
            credentials: 'include'
        }).then(response => {
            if (response.ok) {
                const isPinned = element.dataset.isPinned === 'true';
                updatePinStatus(element, isPinned);
                window.location.reload();
            } else {
                throw new Error('Action failed');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('Action failed');
        });
    }

    function updatePinStatus(element, isPinned) {
        element.dataset.isPinned = isPinned ? 'false' : 'true';
        element.innerHTML = !isPinned ? 'Unpin' : 'Pin';
    }
}

function limitInputLength(element, maxLength) {
    if (element.value.length > maxLength) {
        element.value = element.value.slice(0, maxLength);
    }
}