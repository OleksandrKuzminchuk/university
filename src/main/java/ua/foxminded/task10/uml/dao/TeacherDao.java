package ua.foxminded.task10.uml.dao;

import ua.foxminded.task10.uml.model.Subject;
import ua.foxminded.task10.uml.model.Teacher;

import java.util.List;

public interface TeacherDao extends CrudRepositoryDao<Teacher, Integer> {

    void saveAll(List<Teacher> teachers);

    void updateTeacher(Teacher teacher);

    void addTeacherToSubject(Integer teacherId, Integer subjectId);

    void addTeacherToSubjects(Integer teacherId, List<Subject> subjects);

}