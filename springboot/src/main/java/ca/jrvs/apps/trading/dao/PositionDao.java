package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN_1 = "account_id";
  private final String ID_COLUMN_2 = "ticker";

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Optional<Position> findById(Integer accountId, String ticker) {
    Position position = null;
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_1 + " =?" + " AND "
        + ID_COLUMN_2 + " =?";

    try {
      position = jdbcTemplate
          .queryForObject(selectSql, BeanPropertyRowMapper.newInstance(Position.class),
              accountId, ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find position: " + accountId + " " + ticker, e);
    }
    if (position == null) {
      return Optional.empty();
    }

    return Optional.of(position);
  }

  public boolean existsById(Integer accountId, String ticker) {
    return findById(accountId, ticker).isPresent();
  }

  public List<Position> findAll() {
    String selectSql = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Position.class));
  }

  public long count() {
    return findAll().size();
  }
}
