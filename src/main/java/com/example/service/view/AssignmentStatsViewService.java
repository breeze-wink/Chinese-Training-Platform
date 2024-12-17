package com.example.service.view;

import com.example.model.view.AssignmentStatsView;

public interface AssignmentStatsViewService {
    AssignmentStatsView selectBySubmissionAnswerId(Long submissionAnswerId);
}
