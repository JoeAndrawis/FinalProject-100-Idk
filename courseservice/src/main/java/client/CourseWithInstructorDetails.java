package client;

import com.example.courseservice.model.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithInstructorDetails {
    private Course course;
    private InstructorDto instructor;
}