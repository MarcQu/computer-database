package configuration;

import java.sql.SQLException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import model.QUser;
import model.User;

@Repository
public class UserDAO {
  private Logger logger;
  private HikariDataSource dataSource;
  private LocalSessionFactoryBean sessionFactory;

  /**
   * CompanyFactory contient les méthodes spécifiques de la table company.
   * @param mapper le mapper de company
   * @param dataSource la dataSource
   * @param sessionFactory la session
   * @throws SQLException SQLException
   */
  public UserDAO(HikariDataSource dataSource, LocalSessionFactoryBean sessionFactory) throws SQLException {
    this.logger = LoggerFactory.getLogger(UserDAO.class);
    this.dataSource = dataSource;
    this.sessionFactory = sessionFactory;
  }
  
  public User showDetails(String name) {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QUser user = QUser.user;
    return queryFactory.selectFrom(user).where(user.name.eq(name)).fetchOne();
  }
}
