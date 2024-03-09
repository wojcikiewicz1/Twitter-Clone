package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/{username}/{postId:[\\d]+}")
    public String getPostById(@PathVariable("username") String username, @PathVariable("postId") Long postId, Model model, Principal principal) {

        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        Post post = postService.getPostById(postId);
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        List<Post> posts = postService.getPostsWithCommentsCount();
        List<Comment> comments = commentService.getCommentsWithCommentsCount();

        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("posts", posts);
        model.addAttribute("myUser", myUser);
        model.addAttribute("comment", commentList);
        model.addAttribute("comments", comments);

        return "post";
    }

    @PostMapping("/home/addPost")
    public String addPost(@ModelAttribute("content") String content, Principal principal) {
        postService.addNewPost(principal, content);
        return "redirect:/home";
    }

/**

 @PostMapping (path = " / posts / share ")
 public ResponseEntity sharePost(@RequestHeader("username") String username, @RequestBody Long postId) {
 postService.sharePost(username, postId);
 return ResponseEntity.ok("Post shared");
 }

 @DeleteMapping (path = " / posts / { postId } ")
 public ResponseEntity deletePost (@PathVariable("postId") Long postId) {
 postService.deletePost(postId);
 return ResponseEntity.ok("Post deleted");
 }
 **/

}