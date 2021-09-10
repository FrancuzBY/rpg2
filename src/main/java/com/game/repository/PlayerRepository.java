package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

//    @Query("SELECT count(id) FROM Player WHERE experience > :minExperience AND level > :minLevel")
//    public int findByExperienceCountGreaterThanAndMinLevelGreaterThan(
//            @Param("minExperience") int minExperience, @Param("minLevel") int minLevel);

//    public String getLengthFieldTitle();

    public int countAllByExperienceGreaterThanAndLevelGreaterThan(
            int minExperience, int minLevel);
    public int countAllByNameContainingAndLevelBefore(
            String name, int maxLevel);
    public int countAllByTitleContains(
            String title);
    public int countAllByRaceEqualsAndProfessionEqualsAndExperienceGreaterThan(
            Race race, Profession profession, int maxExperience);
    public int countAllByRaceEqualsAndProfessionEqualsAndBanned(
            Race race, Profession profession, boolean banned);
    public int countAllByRaceEqualsAndProfessionEqualsAndBirthdayBefore(
            Race race, Profession profession, Date before);
    public int countAllByBanned(
            boolean banned);

    /////////BEGIN GET ALL///////////

    public List<Player> findAllByBirthdayAfterAndBirthdayBeforeAndExperienceGreaterThanAndExperienceBefore(
            Date birthdayBefore, Date birthdayAfter, int maxExperience, int minExperience);

    public List<Player> findAllByBannedAndLevelGreaterThanAndLevelBefore(
            boolean banned, int minLevel, int maxLevel);

    public List<Player> findAllByNameContains(String name);

    public List<Player> findAllByTitleContains(String title);

    public List<Player> findAllByRaceEqualsAndProfessionEqualsAndExperienceGreaterThanAndExperienceLessThan(
            Race race, Profession profession, int minExperience, int maxExperience);

    public List<Player> findAllByBannedAndLevelLessThan(boolean banned, int maxLevel);

    public List<Player> findAllByRaceEqualsAndProfessionEqualsAndBirthdayAfterAndBirthdayBefore(
            Race race, Profession profession, Date after, Date before);

}
