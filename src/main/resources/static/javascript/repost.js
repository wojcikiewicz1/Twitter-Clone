document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.retweet').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();

            const postId = this.dataset.id;
            const commentId = this.dataset.commentId;
            const isReposted = this.classList.contains('unRetweetPost') || this.classList.contains("unRetweetComment");
            const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded',});

            if(postId) {
                const urlPost = `/api/${isReposted ? 'unrepost/post' : 'repost/post'}`;
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
            }

            if (commentId) {
                const urlComment = `/api/${isReposted ? 'unrepost/comment' : 'repost/comment'}`;
                fetch( urlComment, {
                    method: 'POST',
                    headers: headers,
                    body: `commentId=${commentId}`,
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
                        this.classList.remove('retweetComment');
                        this.classList.add('unRetweetComment');
                    } else {
                        this.classList.add('retweetComment');
                        this.classList.remove('unRetweetComment');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Action failed');
                });
            }
        });
    });
});