package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntryToCreate) {
        String sql = "insert into time_entries (project_id, user_id, date, hours) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setLong(1, timeEntryToCreate.getProjectId());
                        ps.setLong(2, timeEntryToCreate.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntryToCreate.getDate()));
                        ps.setInt(4, timeEntryToCreate.getHours());
                    return ps;
                }, keyHolder);
        Number key = keyHolder.getKey();
        return new TimeEntry(key.longValue(),timeEntryToCreate);

    }

    @Override
    public TimeEntry find(long id) {
        String sql = "select id, project_id, user_id, date, hours from time_entries where id = ?";

        try {
            TimeEntry queryResult =
                    jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                        TimeEntry result = new TimeEntry(rs.getLong("id"),
                                rs.getLong("project_id"),
                                rs.getLong("user_id"),
                                rs.getDate("date").toLocalDate(),
                                rs.getInt("hours"));
                        return result;
                    });

            return queryResult;
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        String sql = "select id, project_id, user_id, date, hours from time_entries";

        try {
            List<TimeEntry> queryResult =
                    jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                        List<TimeEntry> result = new ArrayList<>();
                        do{
                            result.add(new TimeEntry(rs.getLong("id"),
                                    rs.getLong("project_id"),
                                    rs.getLong("user_id"),
                                    rs.getDate("date").toLocalDate(),
                                    rs.getInt("hours")));
                        }while(rs.next());
                        return result;
                    });

            return queryResult;
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntryToUpdate) {
        String sql = "update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setLong(1, timeEntryToUpdate.getProjectId());
                        ps.setLong(2, timeEntryToUpdate.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntryToUpdate.getDate()));
                        ps.setInt(4, timeEntryToUpdate.getHours());
                        ps.setLong(5, id);
                    return ps;
                });
        return new TimeEntry(id, timeEntryToUpdate);
    }

    @Override
    public TimeEntry delete(long id) {
        String sql = "delete from time_entries where id = ?";
        TimeEntry entryToDelete = find(id);
        jdbcTemplate.update(sql,id);
        return entryToDelete;

    }
}
