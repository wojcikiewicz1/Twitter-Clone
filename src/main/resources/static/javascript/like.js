document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.like-heart').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();

            const postId = this.dataset.id;
            const commentId = this.dataset.commentId;
            const isLiked = this.classList.contains('unlikePost') || this.classList.contains('unlikeComment');
            const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded',});

            if(postId) {
                const urlPost = `/api/${isLiked ? 'unlike/post' : 'like/post'}`;
                fetch(urlPost, {
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
                    const likesCountSpan = this.querySelector('.postIcons2Numbers');
                    likesCountSpan.textContent = ` ${data.likesCount}`;

                    if (data.isLiked) {
                        this.classList.remove('likePost');
                        this.classList.add('unlikePost');
                    } else {
                        this.classList.add('likePost');
                        this.classList.remove('unlikePost');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Action failed');
                });
            }

            if(commentId) {
                const urlComment = `/api/${isLiked ? 'unlike/comment' : 'like/comment'}`;
                fetch(urlComment, {
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
                    const likesCountSpan = this.querySelector('.postIcons2Numbers');
                    likesCountSpan.textContent = ` ${data.likesCount}`;

                    if (data.isLiked) {
                        this.classList.remove('likeComment');
                        this.classList.add('unlikeComment');
                    } else {
                        this.classList.add('likeComment');
                        this.classList.remove('unlikeComment');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Action failed');
                });
            }
        });
    });
});