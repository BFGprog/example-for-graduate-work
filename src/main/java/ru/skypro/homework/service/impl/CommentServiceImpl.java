package ru.skypro.homework.service.impl;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public CommentsDto getCommentsForAd(Integer id, Authentication authentication) {
        return null;
    }
    @Override
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto comment, Authentication authentication) {



        return null;
    }

    @Override
    public CommentDto updateComment(Integer adId,
                                    Integer commentId,
                                    CreateOrUpdateCommentDto comment,
                                    Authentication authentication) {
        return null;
    }

    @Override
    public String deleteComment(Integer adId, Integer commentId, Authentication authentication) {
        return null;
    }
}
