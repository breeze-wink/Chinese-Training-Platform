package com.example.service.essay;

import com.example.model.essay.Essay;

import java.util.List;

public interface EssayService {
    /**
     * Create a new essay.
     * @param essay The essay to create.
     * @return The created essay with generated ID.
     */
    Essay createEssay(Essay essay);

    /**
     * Retrieve an essay by its ID.
     * @param id The ID of the essay.
     * @return The essay if found, otherwise null.
     */
    Essay getEssayById(Long id);

    /**
     * Update an existing essay.
     * @param essay The essay with updated information.
     * @return The number of rows affected.
     */
    int updateEssay(Essay essay);

    /**
     * Delete an essay by its ID.
     * @param id The ID of the essay to delete.
     * @return The number of rows affected.
     */
    int deleteEssay(Long id);

    /**
     * Retrieve all essays.
     * @return A list of all essays.
     */
    List<Essay> getAllEssays();
}
