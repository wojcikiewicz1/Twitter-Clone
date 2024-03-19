document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.share-post').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();

            const postId = this.dataset.id;
            const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded',});

            fetch(`/api/repost`, {
                method: 'POST',
                headers: headers,
                body: `postId=${postId}`,
                credentials: 'include'
            }).then(response => {
                if (response.ok) {
                    alert('Post reposted!');
                } else {
                    throw new Error('Action failed');
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('Action failed');
            });

    });
    });
});


