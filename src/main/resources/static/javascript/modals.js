//--------------------------------Window for logout------------------------
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

//--------------------------------Window for more------------------------
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
    if (!event.target.closest('.fa-ellipsis')) {
        document.querySelectorAll('.moreOptionsWindow').forEach(function(element) {
            element.style.display = 'none';
        });
    }
});

//--------------------------------Modal for adding posts------------------------
if (document.body.id === 'homePage') {
    homePostModal();
} else if (document.body.id === 'profilePage') {
    profilePostModal();
} else if (document.body.id === 'postPage') {
    postPostModal();
} else if (document.body.id === 'commentPage') {
    commentPostModal();
}

function homePostModal() {
    let modal = document.getElementById("postModal");
    let span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.topbar').style.position = 'relative';
        document.querySelector('.topbar').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.close').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.topbar').style.position = 'sticky';
        document.querySelector('.topbar').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function profilePostModal() {
    let modal = document.getElementById("postModal");
    let span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.top').style.position = 'relative';
        document.querySelector('.top').style.zIndex = '-1';
        document.querySelector('.profilemain').style.position = 'relative';
        document.querySelector('.profilemain').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.close').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.top').style.position = 'sticky';
        document.querySelector('.top').style.zIndex = '1';
        document.querySelector('.profilemain').style.position = 'sticky';
        document.querySelector('.profilemain').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function postPostModal() {
    let modal = document.getElementById("postModal");
    let span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.postTop').style.position = 'relative';
        document.querySelector('.postTop').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.close').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.postTop').style.position = 'sticky';
        document.querySelector('.postTop').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}

function commentPostModal() {
    let modal = document.getElementById("postModal");
    let span = document.getElementsByClassName("close")[0];

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('openPostModal').onclick = function () {
        document.getElementById('postModal').style.display = 'block';
        document.querySelector('.postTop').style.position = 'relative';
        document.querySelector('.postTop').style.zIndex = '-1';
        document.querySelector('.search').style.position = 'relative';
        document.querySelector('.search').style.zIndex = '-1';
    };

    document.querySelector('.close').onclick = function () {
        document.getElementById('postModal').style.display = 'none';
        document.querySelector('.postTop').style.position = 'sticky';
        document.querySelector('.postTop').style.zIndex = '1';
        document.querySelector('.search').style.position = 'sticky';
        document.querySelector('.search').style.zIndex = '1';
    };
}
//--------------------------------Textareas resizing------------------------
document.addEventListener('DOMContentLoaded', function() {
    const textareas = document.querySelectorAll('.whatishappening, .postyourreply');

    textareas.forEach(textarea => {
        textarea.addEventListener('input', autoResize, false);
    });
    function autoResize() {
        console.log("Auto resizing...");
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    }
});

//--------------------------------Deleting account-----------------------------------------
function confirmDelete() {
    let confirmAction = confirm("Are you sure you want to delete your account?");
    if (confirmAction) {
        document.getElementById('deleteForm').submit();
    } else {
        console.log('Account deletion cancelled.');
    }
}




