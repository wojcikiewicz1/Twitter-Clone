document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.retweet').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();

            const postId = this.dataset.id;
            const isReposted = this.classList.contains('unRetweetPost');
            const urlPost = `/api/${isReposted ? 'unrepost' : 'repost'}`;
            const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded',});

            fetch( urlPost, {
                method: 'POST',
                headers: headers,
                body: `postId=${postId}`,
                credentials: 'include'
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Action failed');
                }
            }).then(data => {
                const repostsCountSpan = this.querySelector('.postIcons2Numbers');
                repostsCountSpan.textContent = ` ${data.repostsCount}`;

                if (data.isReposted) {
                    this.classList.remove('retweetPost');
                    this.classList.add('unRetweetPost');
                } else {
                    this.classList.add('retweetPost');
                    this.classList.remove('unRetweetPost');
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('Action failed');
            });

        });
    });
});


