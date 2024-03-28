package com.plcoding.daggerhiltcourse.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.daggerhiltcourse.data.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Query("SELECT * FROM course WHERE id = :id")
    suspend fun getCourseById(id: Int): Course?

    @Query("SELECT * FROM course")
    fun getCourses(): Flow<List<Course>>
}