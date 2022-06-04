package ua.foxminded.task10.uml.service;

import ua.foxminded.task10.uml.model.Group;
import ua.foxminded.task10.uml.model.Student;

import java.util.List;

public interface StudentService extends CrudRepositoryService<Student, Integer> {

    void saveAll(List<Student> students);

    List<Student> findByCourseNumber(Integer courseNumber);

    void updateStudent(Integer studentId, Student updatedStudent);

    List<Student> findStudentsByGroupName(Group groupName);

    void deleteStudentsByCourseNumber(Integer courseNumber);

    void deleteStudentsByGroupId(Integer groupId);

    Student findStudentByNameSurname(Student student);

    void deleteTheStudentGroup(Integer studentId);

    void updateTheStudentGroup(Integer groupId, Integer studentId);
}
