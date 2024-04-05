document.addEventListener('DOMContentLoaded', function () {
    setActiveTabColor();
    autoResizeSetup();
    pinUnpinSetup();
    emojiPickerSetup();
    gifPickerSetup();
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
        button.addEventListener('click', function (e) {
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


function emojiPickerSetup() {
    let activeEmojiPicker = null;

    const emojiButtons = document.querySelectorAll('.fa-face-smile');
    const emojiPickers = document.querySelectorAll('.emoji-picker');

    function loadEmojisForPicker(emojiPicker) {
        fetch('https://emoji-api.com/emojis?access_key=f3cd92a9df25935aef5ea13894a092f2247ec290')
            .then(response => response.json())
            .then(emojis => {
                emojis.forEach(emoji => {
                    const span = document.createElement('span');
                    span.textContent = emoji.character;
                    span.title = emoji.slug;
                    span.onclick = function () {
                        const activeTextArea = emojiPicker.closest('.addPostRight').querySelector('textarea');
                        if (activeTextArea) {
                            activeTextArea.value += emoji.character;
                        }
                        emojiPicker.style.display = 'none';
                    };
                    emojiPicker.appendChild(span);
                });
            })
            .catch(error => console.error('Error loading emojis:', error));
    }

    emojiPickers.forEach(loadEmojisForPicker);

    emojiButtons.forEach((button, index) => button.onclick = function () {
        activeEmojiPicker = emojiPickers[index];
        activeEmojiPicker.style.display = 'block';
    });

    document.addEventListener('click', function (e) {
        if (!activeEmojiPicker) return;

        let isEmojiButtonClicked = Array.from(emojiButtons).some(button => button.contains(e.target));
        let isPickerClicked = activeEmojiPicker.contains(e.target);

        if (!isPickerClicked && !isEmojiButtonClicked) {
            activeEmojiPicker.style.display = 'none';
        }
    });
}

function gifPickerSetup() {
    const gifButtons = document.querySelectorAll('.fa-video');
    const gifPickers = document.querySelectorAll('.gif-picker');
    const apiKey = "QyiUiQSkUpN9nicD1QrTfzQoldke2QJg";

    gifPickers.forEach(gifPicker => {
        const searchInput = document.createElement('input');
        searchInput.setAttribute('type', 'text');
        searchInput.setAttribute('placeholder', 'Search GIFs');
        searchInput.classList.add('gif-search-input');
        gifPicker.prepend(searchInput);

        searchInput.addEventListener('input', function() {
            const searchTerm = this.value;
            loadGifsForPicker(gifPicker, searchTerm);
        });

        loadGifsForPicker(gifPicker, '');
    });

    gifButtons.forEach((button, index) => button.onclick = function() {
        const gifPicker = gifPickers[index];
        gifPicker.style.display = 'block';
    });

    document.addEventListener('click', function(e) {
        gifPickers.forEach(gifPicker => {
            if (!gifPicker.contains(e.target) && !Array.from(gifButtons).some(button => button.contains(e.target))) {
                gifPicker.style.display = 'none';
            }
        });
    });

    function loadGifsForPicker(gifPicker, searchTerm) {
        const url = searchTerm ? `https://api.giphy.com/v1/gifs/search?api_key=${apiKey}&q=${searchTerm}` : `https://api.giphy.com/v1/gifs/trending?api_key=${apiKey}`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                const gifs = data.data;
                const gifsContainer = document.createElement('div');
                gifsContainer.classList.add('gifs-container');

                gifs.forEach(gif => {
                    const img = document.createElement('img');
                    img.src = gif.images.fixed_height_small.url;
                    img.title = gif.title;
                    img.onclick = function () {
                        const gifDisplay = gifPicker.closest('.addPostRight').querySelector('.gif-display');
                        gifDisplay.innerHTML = '';
                        const clonedImg = img.cloneNode();
                        gifDisplay.appendChild(clonedImg);
                        gifPicker.style.display = 'none';

                        const gifUrlInput = gifPicker.closest('form').querySelector('.gifUrl');
                        gifUrlInput.value = img.src;
                    };
                    gifsContainer.appendChild(img);
                });

                const existingContainer = gifPicker.querySelector('.gifs-container');
                if (existingContainer) {
                    gifPicker.removeChild(existingContainer);
                }

                gifPicker.appendChild(gifsContainer);
            })
            .catch(error => console.error('Error loading GIFs:', error));
    }
}