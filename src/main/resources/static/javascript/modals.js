//---------------------------------------------Window for logout-------------------------------------------------------
function logoutWindow(event) {
    event.stopPropagation();
    let  options = event.currentTarget.querySelector('.profileOptionsWindow');
    let  isVisible = options.style.display === "block";

    document.querySelectorAll('.profileOptionsWindow').forEach(function(element) {
        element.style.display = 'none';
    });
    options.style.display = isVisible ? 'none' : 'block';
}

document.addEventListener('click', function(event) {
    if (!event.target.closest('.profile')) {
        document.querySelectorAll('.profileOptionsWindow').forEach(function(element) {
            element.style.display = 'none';
        });
    }
});

//----------------------------------------------Window for more--------------------------------------------------------
function moreWindow(event) {
    event.stopPropagation();
    let options = event.currentTarget.nextElementSibling;
    let isVisible = options.style.display === "block";

    document.querySelectorAll('.moreOptionsWindow').forEach(function(element) {
        element.style.display = 'none';
    });
    options.style.display = isVisible ? 'none' : 'block';
}

document.addEventListener('click', function(event) {
    if (!event.target.closest('.menu-link')) {
        document.querySelectorAll('.moreOptionsWindow').forEach(function(element) {
            element.style.display = 'none';
        });
    }
});
//------------------------------------------Window for post/comment options---------------------------------------------
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.fa-ellipsisPost').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            event.stopPropagation();

            const postElement = event.target.closest('.right') || event.target.closest('.postUser');
            const options = postElement.querySelector('.optionsWindow');
            let isVisible = options.style.display === "block";

            document.querySelectorAll('.optionsWindow').forEach(function(element) {
             element.style.display = 'none';
            });
            options.style.display = isVisible ? 'none' : 'block';
        });
    });
});

document.addEventListener('click', function(event) {
    if (!event.target.closest('.postDots')) {
        document.querySelectorAll('.optionsWindow').forEach(function(element) {
            element.style.display = 'none';
        });
    }
});

//----------------------------------------------Modals functions--------------------------------------------------------
let modals = {};
function initializeModalHandlers(modalId, closeButtonClass) {
    let modal = document.getElementById(modalId);
    let span = document.getElementsByClassName(closeButtonClass)[0];

    span.onclick = function () {
        modal.style.display = "none";
    }

    modals[modalId] = modal;
}

window.onclick = function (event) {
    Object.keys(modals).forEach(function(key) {
        let modal = modals[key];
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
}

if (document.body.id === 'homePage') {
    homeCommentModal();
    homePostModal();
} else if (document.body.id === 'profilePage') {
    profileCommentModal();
    profilePostModal();
    profileEditModal();
} else if (document.body.id === 'postPage' || document.body.id === 'commentPage') {
    postOrCommentCommentModal();
    postOrCommentPostModal();
} else if (document.body.id === 'settings') {
    settingsPostModal();
} else if (document.body.id === 'follow') {
    followPostModal();
}
//--------------------------------Modal for adding comment----------------------------------------------------------
document.querySelectorAll('.openCommentModal').forEach(item => {
    item.addEventListener('click', event => {
        event.preventDefault();
        event.stopPropagation();

        const postId = item.getAttribute('data-post-id');
        const commentId = item.getAttribute('data-comment-id');
        let myUser = {username: 'defaultUsername'};

        const formAction = commentId ? `/${myUser.username}/comment/${commentId}` : `/${myUser.username}/${postId}`;
        document.getElementById('commentForm').setAttribute('action', formAction);
        document.getElementById('commentModal').style.display = 'block';
    });
});

function homeCommentModal() {

    initializeModalHandlers("commentModal", "closeCommentModal");

    document.querySelectorAll('.openCommentModal').forEach(function(item) {
        item.addEventListener('click', function (event) {
            event.stopPropagation();
            event.preventDefault();
            document.getElementById('commentModal').style.display = 'block';
            document.querySelector('.topbar').style.position = 'relative';
            document.querySelector('.topbar').style.zIndex = '-1';
            document.querySelector('.search').style.position = 'relative';
            document.querySelector('.search').style.zIndex = '-1';
        });
    });

    document.querySelector('.closeCommentModal').onclick = function () {
        document.getElementById('commentModal').style.display = 'none';
        document.querySelector('.topbar').style.position = 'sticky';
        document.querySelector('.topbar').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function profileCommentModal() {

    initializeModalHandlers("commentModal", "closeCommentModal");

    document.querySelectorAll('.openCommentModal').forEach(function(item) {
        item.addEventListener('click', function (event) {
            event.stopPropagation();
            event.preventDefault();
        document.getElementById('commentModal').style.display = 'block';
        document.querySelector('.top').style.position = 'relative';
        document.querySelector('.top').style.zIndex = '-1';
        document.querySelector('.profilemain').style.position = 'relative';
        document.querySelector('.profilemain').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
        });
    });

    document.querySelector('.closeCommentModal').onclick = function () {
        document.getElementById('commentModal').style.display = 'none';
        document.querySelector('.top').style.position = 'sticky';
        document.querySelector('.top').style.zIndex = '1';
        document.querySelector('.profilemain').style.position = 'sticky';
        document.querySelector('.profilemain').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function postOrCommentCommentModal() {

    initializeModalHandlers("commentModal", "closeCommentModal");

    document.querySelectorAll('.openCommentModal').forEach(function(item) {
        item.addEventListener('click', function (event) {
            event.stopPropagation();
            event.preventDefault();
            document.getElementById('commentModal').style.display = 'block';
            document.querySelector('.postTop').style.position = 'relative';
            document.querySelector('.postTop').style.zIndex = '-1';
            document.querySelector('.search').style.position = 'relative';
            document.querySelector('.search').style.zIndex = '-1';
        });
    });

    document.querySelector('.closeCommentModal').onclick = function () {
        document.getElementById('commentModal').style.display = 'none';
        document.querySelector('.postTop').style.position = 'sticky';
        document.querySelector('.postTop').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

//-----------------------------------------Modal for adding post--------------------------------------------------------
function homePostModal() {

    initializeModalHandlers("postModal", "closePostModal");

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.topbar').style.position = 'relative';
        document.querySelector('.topbar').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closePostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.topbar').style.position = 'sticky';
        document.querySelector('.topbar').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function profilePostModal() {

    initializeModalHandlers("postModal", "closePostModal");

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.top').style.position = 'relative';
        document.querySelector('.top').style.zIndex = '-1';
        document.querySelector('.profilemain').style.position = 'relative';
        document.querySelector('.profilemain').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closePostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.top').style.position = 'sticky';
        document.querySelector('.top').style.zIndex = '1';
        document.querySelector('.profilemain').style.position = 'sticky';
        document.querySelector('.profilemain').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function postOrCommentPostModal() {

    initializeModalHandlers("postModal", "closePostModal");

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.postTop').style.position = 'relative';
        document.querySelector('.postTop').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closePostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.postTop').style.position = 'sticky';
        document.querySelector('.postTop').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function settingsPostModal() {

    initializeModalHandlers("postModal", "closePostModal");

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.top').style.position = 'relative';
        document.querySelector('.top').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closePostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.top').style.position = 'sticky';
        document.querySelector('.top').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function followPostModal() {

    initializeModalHandlers("postModal", "closePostModal");

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.stickyTopBar').style.position = 'relative';
        document.querySelector('.stickyTopBar').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closePostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.stickyTopBar').style.position = 'sticky';
        document.querySelector('.stickyTopBar').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

//------------------------------------------------Profile edit modal----------------------------------------------------
function profileEditModal() {

    initializeModalHandlers("editModal", "closeEditModal");

    document.getElementById('openEditModal').onclick = function () {
        document.getElementById('editModal').style.display = 'block';
        document.querySelector('.top').style.position = 'relative';
        document.querySelector('.top').style.zIndex = '-1';
        document.querySelector('.profilemain').style.position = 'relative';
        document.querySelector('.profilemain').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.closeEditModal').onclick = function () {
        document.getElementById('editModal').style.display = 'none';
        document.querySelector('.top').style.position = 'sticky';
        document.querySelector('.top').style.zIndex = '1';
        document.querySelector('.profilemain').style.position = 'sticky';
        document.querySelector('.profilemain').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}
//-----------------------------------Refreshing page after adding post in modal-----------------------------------------
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('#postModal .modal-content form');

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const formData = new FormData(form);

        fetch(form.getAttribute('action'), {
            method: 'POST',
            body: formData,
            credentials: 'include'
        })
            .then(response => {
                if(response.ok) {
                    document.getElementById('postModal').style.display = 'none';

                    location.reload();
                    console.log('Post added successfully');

                } else {
                    console.error('Something went wrong');
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
