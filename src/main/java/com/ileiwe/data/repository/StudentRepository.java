package com.ileiwe.data.repository;

import com.ileiwe.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByLearningParty_Email(String username);
}
