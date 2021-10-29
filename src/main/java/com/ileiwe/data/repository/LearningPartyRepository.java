package com.ileiwe.data.repository;

import com.ileiwe.data.model.LearningParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningPartyRepository extends JpaRepository<LearningParty, Long> {



    LearningParty findByEmail(String email);

}
