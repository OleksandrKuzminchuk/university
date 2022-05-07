package ua.foxminded.task10.uml.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.foxminded.task10.uml.dao.TeacherDao;
import ua.foxminded.task10.uml.model.Subject;
import ua.foxminded.task10.uml.model.Teacher;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class TeacherDaoImpl implements TeacherDao {

    private static final Logger logger = LoggerFactory.getLogger(TeacherDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Teacher> mapper;

    public TeacherDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.mapper = new BeanPropertyRowMapper<>(Teacher.class);
    }

    @Override
    public Optional<Teacher> save(Teacher teacher) {
        requireNonNull(teacher);
        logger.info("SAVING {}", teacher);
        final String SAVE_TEACHER = "INSERT INTO teachers (first_name, last_name) VALUES (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SAVE_TEACHER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            return statement;
        }, holder);
        Integer teacherId = requireNonNull(holder.getKey()).intValue();
        teacher.setId(teacherId);
        Optional<Teacher> result = Optional.of(teacher);
        logger.info("SAVED {} SUCCESSFULLY", teacher);
        return result;
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        requireNonNull(id);
        logger.info("FINDING TEACHER BY ID - {}", id);
        final String FIND_TEACHER_BY_ID = "SELECT * FROM teachers WHERE teacher_id = ?";
        Teacher result = jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, mapper, id);
        logger.info("FOUND {} BY ID SUCCESSFULLY", result);
        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        requireNonNull(id);
        logger.info("CHECKING... TEACHER EXISTS BY ID - {}", id);
        final String EXISTS_BY_ID = "SELECT COUNT(*) FROM teachers WHERE teacher_id = ?";
        Long count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Long.class, id);
        boolean exists = count != null && count > 0;
        logger.info("TEACHER BY ID - {} EXISTS - {}", id, exists);
        return exists;
    }

    @Override
    public List<Teacher> findAll() {
        logger.info("FINDING ALL TEACHERS...");
        final String FIND_ALL = "SELECT * FROM teachers";
        List<Teacher> teachers = jdbcTemplate.query(FIND_ALL, mapper);
        logger.info("FOUND ALL TEACHERS: {}", teachers);
        return teachers;
    }

    @Override
    public Long count() {
        logger.info("FIND COUNT ALL TEACHERS...");
        final String COUNT = "SELECT COUNT(*) FROM teachers";
        Long countTeachers = jdbcTemplate.queryForObject(COUNT, Long.class);
        logger.info("FOUND COUNT({}) TEACHERS SUCCESSFULLY", countTeachers);
        return countTeachers;
    }

    @Override
    public void deleteById(Integer id) {
        requireNonNull(id);
        logger.info("DELETE TEACHER BY ID - {}", id);
        final String DELETE_BY_ID = "DELETE FROM teachers WHERE teacher_id = ?";
        jdbcTemplate.update(DELETE_BY_ID, new Object[]{id}, mapper);
        logger.info("DELETED TEACHER BY ID - {} SUCCESSFULLY", id);
    }

    @Override
    public void delete(Teacher teacher) {
        requireNonNull(teacher);
        logger.info("DELETE {}...", teacher);
        final String DELETE_TEACHER = "DELETE FROM teachers WHERE first_name = ? AND last_name = ?";
        jdbcTemplate.update(DELETE_TEACHER, new Object[]{teacher.getFirstName(), teacher.getLastName()}, mapper);
        logger.info("DELETED {} SUCCESSFULLY", teacher);
    }

    @Override
    public void deleteAll() {
        logger.info("DELETE ALL TEACHERS...");
        final String DELETE_ALL = "DELETE FROM teachers";
        jdbcTemplate.update(DELETE_ALL, mapper);
        logger.info("DELETED ALL TEACHERS SUCCESSFULLY");
    }

    @Override
    public void saveAll(List<Teacher> teachers) {
        requireNonNull(teachers);
        logger.info("SAVING {} TEACHERS", teachers.size());
        teachers.forEach(this::save);
        logger.info("SAVED {} TEACHERS SUCCESSFULLY", teachers.size());
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        requireNonNull(teacher);
        logger.info("UPDATING TEACHER BY ID - {}", teacher.getId());
        final String UPDATE_TEACHER = "UPDATE teachers SET first_name = ?, last_name = ? WHERE teacher_id = ?";
        jdbcTemplate.update(UPDATE_TEACHER, new Object[]{
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getId()}, mapper);
        logger.info("UPDATED TEACHER BY ID - {} SUCCESSFULLY", teacher.getId());
    }

    @Override
    public void addTeacherToSubject(Integer teacherId, Integer subjectId) {
        requireNonNull(teacherId);
        requireNonNull(subjectId);
        logger.info("ADDING... TEACHER ID - {} TO SUBJECT ID - {}", teacherId, subjectId);
        final String ADD_TEACHER_TO_SUBJECT = "INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(ADD_TEACHER_TO_SUBJECT);
            statement.setInt(1, teacherId);
            statement.setInt(2, subjectId);
            statement.executeUpdate();
            logger.info("ADDED TEACHER ID - {} TO SUBJECT ID - {} SUCCESSFULLY", teacherId, subjectId);
            return statement;
        });
    }

    @Override
    public void addTeacherToSubjects(Integer teacherId, List<Subject> subjects) {
        requireNonNull(teacherId);
        requireNonNull(subjects);
        logger.info("ADDING... TEACHER ID - {} TO SUBJECTS {}", teacherId, subjects.size());
        subjects.forEach(subject -> addTeacherToSubject(teacherId, subject.getId()));
        logger.info("ADDED TEACHER ID - {} TO SUBJECTS {} SUCCESSFULLY", teacherId, subjects.size());
    }
}
