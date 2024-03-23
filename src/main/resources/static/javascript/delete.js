//-----------------------------------------------Deleting post/comment--------------------------------------------------
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.mowoption').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();

            const postId = this.dataset.id;
            const commentId = this.dataset.commentId;

            if(postId) {
                const confirmActionPost = confirm("Are you sure you want to delete your post?");
                if (confirmActionPost) {
                    const formData = new FormData();
                    formData.append('id', postId);

                    fetch('/deletePost', {
                        method: 'POST',
                        body: formData,
                        credentials: 'include'
                    }).then(response => {
                        if (response.ok) {
                            location.reload();
                        } else {
                            throw new Error('Action failed');
                        }
                    }).catch(error => {
                        console.error('Error:', error);
                        alert('Action failed');
                    });
                } else {
                    console.log('Post deletion cancelled.');
                }
            } else if(commentId) {
                const confirmActionComment = confirm("Are you sure you want to delete your comment?");
                if (confirmActionComment) {
                    const formData = new FormData();
                    formData.append('commentId', commentId);

                    fetch('/deleteComment', {
                        method: 'POST',
                        body: formData,
                        credentials: 'include'
                    }).then(response => {
                        if (response.ok) {
                            location.reload();
                        } else {
                            throw new Error('Action failed');
                        }
                    }).catch(error => {
                        console.error('Error:', error);
                        alert('Action failed');
                    });
                } else {
                    console.log('Comment deletion cancelled.');
                }
            }
        });
    });
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