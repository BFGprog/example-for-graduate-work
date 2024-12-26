package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;


@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Получение комментариев объявления")
    public CommentsDto getComments(@PathVariable Integer id, Authentication authentication) {
        return commentService.getCommentsForAd(id, authentication);
    }


    @PostMapping("/{id}/comments")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Добавление комментария к объявлению")
    public CommentDto addComment(@PathVariable Integer adId,
                                 @RequestBody CreateOrUpdateCommentDto comment,
                                 Authentication authentication) {
        return commentService.addComment(adId, comment, authentication);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Обновление комментария")
    public String updateComment(@PathVariable Integer adId,
                                @PathVariable Integer commentId,
                                @RequestBody CreateOrUpdateCommentDto comment,
                                Authentication authentication) {
        commentService.updateComment(adId, commentId, comment, authentication);
        return "Комментарий успешно обновлён";
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @adServiceImpl.findAdById(id).author.email.equals(authentication.name)")
    @Operation(summary = "Удаление комментария")
    public String deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId, Authentication authentication) {
        commentService.deleteComment(adId, commentId, authentication);
        return "Комментарий успешно удалён";
    }
}
