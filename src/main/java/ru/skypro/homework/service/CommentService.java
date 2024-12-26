package ru.skypro.homework.service;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;


public interface CommentService {

    public CommentsDto getCommentsForAd(Integer id, Authentication authentication);
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto comment, Authentication authentication);

    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto comment, Authentication authentication);


    public String deleteComment(Integer adId, Integer commentId, Authentication authentication);
}