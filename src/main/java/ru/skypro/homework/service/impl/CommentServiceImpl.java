package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.dto.CommentDto;
import ru.skypro.homework.db.entity.Comment;
import ru.skypro.homework.db.repository.CommentRepository;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с комментариями.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Метод создает новый объект комментария.
     *
     * @param commentDto      Модель комментария для создания.
     *
     * @return CommentDto     Созданная сущность комментария.
     */
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    /**
     * Метод ищет комментарий по его идентификатору.
     *
     * @param id              Идентификатор объявления для поиска.
     *
     * @return CommentDto     Найденный объект комментария
     */
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public CommentDto getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    /**
     * Метод возвращает все комментарии.
     *
     * @return List<AdDto>     Список всех комментариев
     */
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод обновляет комментарий пользователя по его идентификатору.
     *
     * @param id              Идентификатор комментария для обновления.
     * @param commentDto      Объект для обновления
     *
     * @return CommentDto     Обновленный объект комментария
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.hasPermission(@commentServiceImpl.getCommentById(#id))")
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentMapper.updateEntityFromDto(commentDto, comment);
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    /**
     * Метод удаляет комментарий пользователя по его идентификатору.
     *
     * @param id         Идентификатор объявления для удаления.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.hasPermission(@commentServiceImpl.getCommentById(#id))")
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
