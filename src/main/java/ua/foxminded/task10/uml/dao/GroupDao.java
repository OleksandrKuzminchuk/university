package ua.foxminded.task10.uml.dao;

import ua.foxminded.task10.uml.model.Group;
import ua.foxminded.task10.uml.model.Student;

import java.util.List;
import java.util.Optional;

public interface GroupDao extends CrudRepositoryDao<Group, Integer> {

    void saveAll(List<Group> groups);

    List<Group> findGroupsByName(String groupName);

    void updateGroup(Integer groupId, Group group);

    void assignStudentToGroup(Student student, Group group);

    void assignStudentsToGroup(List<Student> students, Group group);
}

