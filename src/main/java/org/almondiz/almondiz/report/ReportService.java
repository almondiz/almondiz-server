package org.almondiz.almondiz.report;

import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.Status;
import org.almondiz.almondiz.exception.exception.ReportExistedException;
import org.almondiz.almondiz.exception.exception.UserNotFoundException;
import org.almondiz.almondiz.post.PostService;
import org.almondiz.almondiz.post.entity.Post;
import org.almondiz.almondiz.user.UserService;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PostService postService;

    private final ReportRepository reportRepository;

    private final UserService userService;

    @Transactional
    public Report create(String uid, ReportRequestDto requestDto) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        Post post = postService.findPostByPostId(requestDto.getPostId());

        Optional<Report> report = reportRepository.findByUserAndPost(user, post);

        Report newReport;
        if (report.isPresent()) {
            throw new ReportExistedException();
        } else {
            newReport = Report.builder()
                              .post(post)
                              .user(user)
                              // .status(Status.ALIVE)
                              .text(requestDto.getText())
                              .type(requestDto.getType())
                              .build();
        }

        return reportRepository.save(newReport);
    }

    @Transactional
    public List<Report> findAllReport() {
        return reportRepository.findAll();
    }

    @Transactional
    public List<Report> findAllReportByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return reportRepository.findAllByUser(user);
    }

    @Transactional
    public List<Report> findAllReportByUserId(Long userId) {
        User user = userService.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        return reportRepository.findAllByUser(user);
    }

    @Transactional
    public List<Report> findAllReportByPost(Long postId) {
        Post post = postService.findPostByPostId(postId);

        return reportRepository.findAllByPost(post);
    }

    @Transactional
    public long findReportCountByUser(String uid) {
        User user = userService.findByUid(uid).orElseThrow(UserNotFoundException::new);

        return reportRepository.countByUser(user);
    }

    @Transactional
    public long findReportCountByUser(Long userId) {
        User user = userService.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        return reportRepository.countByUser(user);
    }

    @Transactional
    public long findReportCountByPost(Long postId) {
        Post post = postService.findPostByPostId(postId);

        return reportRepository.countByPost(post);
    }
}
