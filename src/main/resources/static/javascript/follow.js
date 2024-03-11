document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.btntofollow').forEach(button => {
        button.addEventListener('click', function(e) {
            const username = this.dataset.username;
            const isFollowing = this.classList.contains('unfollowButton');
            const url = `/api/${isFollowing ? 'unfollow' : 'follow'}`;
            const headers = new Headers({
                'Content-Type': 'application/x-www-form-urlencoded',
            });

            fetch(url, {
                method: 'POST',
                headers: headers,
                body: `username=${username}`,
                credentials: 'include'
            })
                .then(response => {
                    if (response.ok) {
                        if (isFollowing) {
                            this.textContent = 'Follow';
                            this.classList.remove('unfollowButton');
                            this.classList.add('followButton');
                        } else {
                            this.textContent = 'Unfollow';
                            this.classList.add('unfollowButton');
                            this.classList.remove('followButton');
                        }
                        updateButtonStyles(this);
                    } else {
                        throw new Error('Action failed');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Action failed');
                });
        });
    });
});

function updateButtonStyles(button) {
    button.style = '';

    if (button.classList.contains('followButton')) {
        button.onmouseover = function() {
            this.style.cursor = 'pointer';
            this.style.backgroundColor = '#333333';
        };
        button.onmouseout = function() {
            this.style.backgroundColor = '';
        };
    } else if (button.classList.contains('unfollowButton')) {
        button.onmouseover = function() {
            this.style.cursor = 'pointer';
            this.style.backgroundColor = '#ff8c73';
            this.style.color = 'red';
            this.style.border = '1px solid red';
        };
        button.onmouseout = function() {
            this.style.backgroundColor = '';
            this.style.color = '';
            this.style.border = '';
        };
    }
}