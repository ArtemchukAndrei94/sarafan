package letscode.sarafan.service;

import letscode.sarafan.dto.EventType;
import letscode.sarafan.dto.ObjectType;
import letscode.sarafan.model.Comment;
import letscode.sarafan.model.User;
import letscode.sarafan.model.Views;
import letscode.sarafan.repo.CommentRepo;
import letscode.sarafan.util.WsSender;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;


    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}