document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.js-followToggle').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();

            const username = this.dataset.username;
            const isFollowing = this.classList.contains('unfollowBtn') || this.classList.contains('unfollowButton') || this.classList.contains('optionsWindowOptionUnfollow');
            const url = `/api/${isFollowing ? 'unfollow' : 'follow'}`;
            const headers = new Headers({
                'Content-Type': 'application/x-www-form-urlencoded',
            });

            fetch(url, {
                method: 'POST',
                headers: headers,
                body: `username=${username}`,
                credentials: 'include'
            }).then(response => {
                if (response.ok) {
                    if (isFollowing) {
                        this.textContent = 'Follow';
                        if (this.classList.contains('unfollowBtn')) {
                            this.classList.remove('unfollowBtn');
                            this.classList.add('followBtn');
                        } else if (this.classList.contains('unfollowButton')) {
                            this.classList.remove('unfollowButton');
                            this.classList.add('followButton');
                        } else if (this.classList.contains('optionsWindowOptionUnfollow')) {
                            this.classList.remove('optionsWindowOptionUnfollow');
                            this.classList.add('optionsWindowOptionFollow');
                        }
                    } else {
                        this.textContent = 'Unfollow';
                        if (this.classList.contains('followBtn')) {
                            this.classList.remove('followBtn');
                            this.classList.add('unfollowBtn');
                        } else if (this.classList.contains('followButton')) {
                            this.classList.remove('followButton');
                            this.classList.add('unfollowButton');
                        } else if (this.classList.contains('optionsWindowOptionFollow')) {
                            this.classList.remove('optionsWindowOptionFollow');
                            this.classList.add('optionsWindowOptionUnfollow');
                        }
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

    if (button.classList.contains('followButton') || button.classList.contains('followBtn')) {
        button.onmouseover = function() {
            this.style.cursor = 'pointer';
            this.style.backgroundColor = '#333333';
        };
        button.onmouseout = function() {
            this.style.backgroundColor = '';
        };
    }

    else if (button.classList.contains('unfollowButton') || button.classList.contains('unfollowBtn')) {
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

    if (button.classList.contains('optionsWindowOptionFollow')) {
        button.onmouseover = function() {
            this.style.cursor = 'pointer';
            this.style.backgroundColor = '#f0f0f0';
        };
        button.onmouseout = function() {
            this.style.backgroundColor = '';
        };
    }

    else if (button.classList.contains('optionsWindowOptionUnfollow')) {
        button.onmouseover = function() {
            this.style.cursor = 'pointer';
            this.style.backgroundColor = '#f0f0f0';
            this.style.color = 'red';
        };
        button.onmouseout = function() {
            this.style.backgroundColor = '';
            this.style.color = '';
        };
    }
}