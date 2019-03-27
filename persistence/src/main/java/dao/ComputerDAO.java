package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import mapper.ComputerMapper;
import model.Computer;
import model.QComputer;

@Repository
public class ComputerDAO {
  private Logger logger;
  private HikariDataSource dataSource;
  private LocalSessionFactoryBean sessionFactory;

  /**
   * ComputerFactory contient les méthodes spécifiques à la table computer.
   * @param dataSource la dataSource
   * @param sessionFactory la session
   * @throws SQLException SQLException
   */
  @Autowired
  public ComputerDAO(HikariDataSource dataSource, LocalSessionFactoryBean sessionFactory) throws SQLException {
    this.logger = LoggerFactory.getLogger(ComputerDAO.class);
    this.dataSource = dataSource;
    this.sessionFactory = sessionFactory;
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int count(String search) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QComputer computer = QComputer.computer;
    int count = 0;
    if (search == null || "".equals(search)) {
      count = (int) queryFactory.select(computer.id.count()).from(computer).fetchCount();
    } else {
      count = (int) queryFactory.select(computer.id.count()).from(computer).where(computer.name.like(new StringBuilder("%").append(search).append("%").toString())).fetchCount();
    }
    session.close();
    return count;
  }

  /**
   * Liste quelques ordinateurs contenus dans la table computer.
   * @param nombre nombre de résultat à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> list(int nombre, int offset, String search, String sort) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QComputer computer = QComputer.computer;
    List<Computer> computers = new ArrayList<Computer>();
    if (search == null || "".equals(search)) {
      if ("desc".equals(sort)) {
        computers = queryFactory.selectFrom(computer).orderBy(computer.name.desc()).limit(nombre).offset(offset).fetch();
      } else {
        computers = queryFactory.selectFrom(computer).orderBy(computer.name.asc()).limit(nombre).offset(offset).fetch();
      }
    } else {
      if ("desc".equals(sort)) {
        computers = queryFactory.selectFrom(computer).where(computer.name.like(new StringBuilder("%").append(search).append("%").toString())).orderBy(computer.name.desc()).limit(nombre).offset(offset).fetch();
      } else {
        computers = queryFactory.selectFrom(computer).where(computer.name.like(new StringBuilder("%").append(search).append("%").toString())).orderBy(computer.name.asc()).limit(nombre).offset(offset).fetch();
      }
    }
    session.close();
    return computers;
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param computer l'ordinateur à afficher
   * @return computers la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> showDetails(Computer computer) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QComputer qComputer = QComputer.computer;
    List<Computer> computers = queryFactory.selectFrom(qComputer).where(qComputer.id.eq(computer.getId())).fetch();
    session.close();
    return computers;
  }

  /**
   * Ajoute un ordinateur dans la table computer.
   * @param computer l'ordinateur à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void create(Computer computer) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    System.out.println(computer);
    session.save(computer);
    session.close();
  }

  /**
   * Met à jour un ordinateur dans la table computer.
   * @param computer l'ordinateur à mettre à jour
   * @throws SQLException SQLException
   */
  public void update(Computer computer) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QComputer qComputer = QComputer.computer;
    queryFactory.update(qComputer).where(qComputer.id.eq(computer.getId())).set(qComputer.name, computer.getName()).set(qComputer.introduced, computer.getIntroduced()).set(qComputer.discontinued, computer.getDiscontinued()).set(qComputer.company, computer.getCompany()).execute();
    session.close();
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param computer l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void delete(Computer computer) throws SQLException {
    Session session = this.sessionFactory.getObject().openSession();
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);
    QComputer qComputer = QComputer.computer;
    queryFactory.delete(qComputer).where(qComputer.id.eq(computer.getId())).execute();
    session.close();
  }
}
