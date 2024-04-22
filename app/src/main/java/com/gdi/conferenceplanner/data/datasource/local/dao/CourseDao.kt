package com.gdi.conferenceplanner.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gdi.conferenceplanner.data.model.local.entities.Course
import com.gdi.conferenceplanner.data.model.local.CourseWithSpeakers
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Transaction
    suspend fun deleteCourseWithSpeakers(course: Course) {
        deleteCourseSpeakers(course.courseId)

        deleteCourse(course)
    }

    @Query("DELETE FROM CourseSpeakerCrossRef WHERE courseId = :courseId")
    suspend fun deleteCourseSpeakers(courseId: Long)

    @Transaction
    @Query("SELECT * FROM courses WHERE courseId = :courseId")
    suspend fun getCourseById(courseId: Int): CourseWithSpeakers?

    @Transaction
    @Query("SELECT * FROM courses")
    fun getCourses(): Flow<List<CourseWithSpeakers>>
}